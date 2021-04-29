package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalRecordService {

    MedicalRecordRepository repository;

    @Autowired
    MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public List<MedicalRecord> findMedicalRecords() throws IOException {
        return repository.findAll();
    }

    public MedicalRecord findMedicalRecord(
            String firstName,
            String lastName
    ) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);
        return repository.findById(person).orElseThrow();
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws IOException {
        repository.save(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecordToUpdate) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);
        return repository.update(person, medicalRecordToUpdate);
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws IOException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", firstName);
        person.put("lastName", lastName);
        repository.deleteById(person);
    }
}
