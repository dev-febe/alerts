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
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FireStationService {
    FireStationRepository repository;
    PersonRepository personRepository;
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    FireStationService(
            FireStationRepository repository,
            PersonRepository personRepository,
            MedicalRecordRepository medicalRecordRepository
    ) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public HashMap<String, Object> findPersonsByFirestation(String station) throws IOException {
        Optional<FireStation> fireStation = repository.findByStation(station);

        List<Person> persons = new ArrayList<>();
        long adultCount = 0L;
        long adoCount = 0L;

        if (fireStation.isPresent()) {
            List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

            Stream<Person> _person = personRepository.findByStation(fireStation.get().getAddress());

            adultCount = personRepository.filterPersonsByAge(_person, medicalRecords, 18).size();

            adoCount = personRepository.filterPersonsByAge(_person, medicalRecords, -18).size();

            persons = _person.collect(Collectors.toList());
        }

        HashMap<String, Object> personsByFirestation = new HashMap<>();
        personsByFirestation.put("person", persons);
        personsByFirestation.put("nbAdult", adultCount);
        personsByFirestation.put("nbChild", adoCount);

        return personsByFirestation;
    }

    public FireStation findFiresStation(String address) throws IOException {
        return repository.findById(address).orElseThrow();
    }

    public FireStation createFiresStation(FireStation person)
            throws IOException {
        repository.save(person);
        return person;
    }

    public FireStation updateFiresStation(
            String address,
            FireStation fireStationToUpdate
    ) throws IOException {
        return repository.update(address, fireStationToUpdate);
    }

    public void deleteFireStation(String address) throws IOException {
        repository.deleteById(address);
    }
}
