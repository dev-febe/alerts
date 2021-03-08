package com.safetynet.alerts.service;

import com.safetynet.alerts.entity.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    MedicalRecordRepository repository;

    MedicalRecordService(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    public List<MedicalRecord> findMedicalRecords() {
        return repository.findAll();
    }

    public MedicalRecord findMedicalRecord(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
        return repository.save(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecordToUpdate) {
        return repository
                .findById(id)
                .map((person -> {
                    person.setPerson(medicalRecordToUpdate.getPerson());
                    return repository.save(person);
                }))
                .orElseThrow();
    }

    public void deleteMedicalRecord(Long id) {
        repository.deleteById(id);
    }
}
