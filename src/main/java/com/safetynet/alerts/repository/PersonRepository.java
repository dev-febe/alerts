package com.safetynet.alerts.repository;

import com.safetynet.alerts.helper.Dto;
import com.safetynet.alerts.helper.JsonToDtoHelper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PersonRepository implements JsonRepository<Person, Map<String, String>> {
    JsonToDtoHelper jsonToDtoHelper;

    PersonRepository(JsonToDtoHelper jsonToDtoHelper) {
        this.jsonToDtoHelper = jsonToDtoHelper;
    }

    @Override
    public List<Person> findAll() throws IOException {
        return jsonToDtoHelper
                .getJson()
                .getPersons();
    }


    @Override
    public Optional<Person> findById(Map<String, String> person) throws IOException {
        List<Person> persons = jsonToDtoHelper
                .getJson()
                .getPersons();

        return persons.stream()
                .filter(_person -> _person.getFirstName().equals(person.get("firstName"))
                        && _person.getLastName().equals(person.get("lastName"))
                )
                .findFirst();
    }

    public List<Person> findByStation(String address) throws IOException {
        List<Person> persons = jsonToDtoHelper
                .getJson()
                .getPersons();

        return persons.stream()
                .filter(_person -> _person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(Person person) throws IOException {
        Dto dto = jsonToDtoHelper.getJson();
        dto.getPersons().add(person);
        jsonToDtoHelper.saveJson(dto);
        return true;
    }

    @Override
    public Person update(Map<String, String> person, Person personToUpdate) throws IOException {
        Dto dto = jsonToDtoHelper.getJson();

        dto.getPersons()
                .stream()
                .filter(_person -> _person.getFirstName().equals(person.get("firstName")) && _person.getLastName().equals(person.get("lastName")))
                .forEach(_person -> {
                    _person.setFirstName(personToUpdate.getLastName());
                    _person.setLastName(personToUpdate.getFirstName());
                });

        jsonToDtoHelper.saveJson(dto);

        return personToUpdate;
    }

    @Override
    public void deleteById(Map<String, String> person) throws IOException {
        Dto dto = jsonToDtoHelper.getJson();

        dto.getMedicalrecords()
                .removeIf(medicalRecord ->
                        medicalRecord.getFirstName().equals(person.get("firstName"))
                                && medicalRecord.getLastName().equals(person.get("lastName"))
                );

        jsonToDtoHelper.saveJson(dto);
    }

    /**
     * Update person age based on birthdate stored on his medical record
     */
    public void updatePersonMedicalRecord(Person person, List<MedicalRecord> medicalRecords) {
        Optional<MedicalRecord> medicalRecord = medicalRecords
                .stream()
                .filter(_medicalRecord ->
                        _medicalRecord.getLastName().equals(person.getLastName())
                                && _medicalRecord.getFirstName().equals(person.getFirstName())
                ).findFirst();
        if (medicalRecord.isPresent()) {
            LocalDate personBirthDate = medicalRecord
                    .get()
                    .getBirthdate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate now = LocalDate.now();
            Period period = Period.between(now, personBirthDate);
            person.setAge(Math.abs(period.getYears()));

            Map<String, List<String>> personMedicalRecord = new HashMap<>();
            personMedicalRecord.put("medications", medicalRecord.get().getMedications());
            personMedicalRecord.put("allergies", medicalRecord.get().getAllergies());
            person.setMedicalRecords(personMedicalRecord);
        }
    }

    /**
     * Filter persons whether they are adult or not
     */
    public List<Person> filterPersonsByAge(
            Stream<Person> persons,
            List<MedicalRecord> medicalRecords,
            int age
    ) {
        return persons
                .peek(person -> updatePersonMedicalRecord(person, medicalRecords))
                .filter(person -> age > 0 ? person.getAge() > Math.abs(age) : person.getAge() < Math.abs(age))
                .collect(Collectors.toList());
    }
}
