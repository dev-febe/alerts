package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {
    PersonRepository personRepository;
    MedicalRecordRepository medicalRecordRepository;
    FireStationRepository fireStationRepository;

    @Autowired
    PersonService(
            PersonRepository personRepository,
            MedicalRecordRepository medicalRecordRepository,
            FireStationRepository fireStationRepository
    ) {
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.fireStationRepository = fireStationRepository;
    }

    /**
     * Return list of all persons stored in json file
     */
    public List<Person> findPersons() throws IOException {
        return personRepository.findAll();
    }

    /**
     * Return list of person who have age under 18
     */
    public List<Person> findChildren(String address) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        Stream<Person> persons = personRepository
                .findAll()
                .stream()
                .filter(person -> person.getAddress().equals(address));

        return personRepository.filterPersonsByAge(
                persons,
                medicalRecords,
                -18
        );

    }

    /**
     * Return list of person phones stored by station number
     */
    public List<String> findPersonPhones(String fireStationNumber) throws IOException {
        // We find the firestation save for this stationNumber
        Optional<FireStation> fireStation = this.fireStationRepository
                .findAll()
                .stream()
                .filter(_fireStation -> _fireStation.getStation().equals(fireStationNumber))
                .findFirst();

        List<String> persons = new ArrayList<>();
        if (fireStation.isPresent()) {
            persons = personRepository
                    .findAll()
                    .stream()
                    .filter(person -> person.getAddress().equals(fireStation.get().getAddress()))
                    .map(Person::getPhone)
                    .collect(Collectors.toList());
        }

        return persons;
    }

    /**
     * Return list of persons by address
     */
    public List<Person> findPersonsFireStations(String address) throws IOException {
        Optional<FireStation> fireStation = this.fireStationRepository
                .findAll()
                .stream()
                .filter(_fireStation -> _fireStation.getAddress().equals(address))
                .findFirst();

        List<Person> persons = new ArrayList<>();
        if (fireStation.isPresent()) {
            List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
            persons = personRepository
                    .findAll()
                    .stream()
                    .peek(person -> personRepository.updatePersonMedicalRecord(person, medicalRecords))
                    .filter(person -> person.getAddress().equals(fireStation.get().getAddress()))
                    .collect(Collectors.toList());
        }

        return persons;
    }

    /**
     * Update firestation by updating person covered by the station
     */
    void updateFireStationPerson(FireStation fireStation) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        List<Person> persons = personRepository
                .findAll()
                .stream()
                .peek(person -> personRepository.updatePersonMedicalRecord(person, medicalRecords))
                .filter(person -> person.getAddress().equals(fireStation.getAddress()))
                .collect(Collectors.toList());
        fireStation.setPersons(persons);
    }

    /**
     * Return list of firestation with each persons covered
     */
    public List<FireStation> findPersonsGroupFireStations(String address) throws IOException {
        return this.fireStationRepository
                .findAll()
                .stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .peek(fireStation -> {
                    try {
                        updateFireStationPerson(fireStation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .collect(Collectors.toList());
    }

    public List<String> findPersonsEmailByCity(String city) throws IOException {
        return personRepository
                .findAll()
                .stream()
                .filter(person -> person.getCity().equals(city))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    public List<Person> findPersonInfo(String firstName, String lastName) throws IOException {
        return personRepository
                .findAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public Person findPerson(String firstName, String lastName) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);
        return personRepository.findById(person).orElseThrow();
    }

    public Person createPerson(Person person) throws IOException {
        personRepository.save(person);
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person personToUpdate) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);

        return personRepository.update(person, personToUpdate);
    }

    public void deletePerson(String firstName, String lastName) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);

        personRepository.deleteById(person);
    }
}
