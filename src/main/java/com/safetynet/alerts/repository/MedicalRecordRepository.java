package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.helper.Dto;
import com.safetynet.alerts.helper.JsonToDtoHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MedicalRecordRepository implements JsonRepository<MedicalRecord, Map<String, String>> {
    JsonToDtoHelper jsonToDtoHelper;

    MedicalRecordRepository(JsonToDtoHelper jsonToDtoHelper) {
        this.jsonToDtoHelper = jsonToDtoHelper;
    }

    @Override
    public List<MedicalRecord> findAll() throws IOException {
        return jsonToDtoHelper
                .getJson()
                .getMedicalrecords();
    }

    @Override
    public Optional<MedicalRecord> findById(Map<String, String> person) throws IOException {
        List<MedicalRecord> medicalRecords = jsonToDtoHelper
                .getJson()
                .getMedicalrecords();

        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(person.get("firstName"))
                        && medicalRecord.getLastName().equals(person.get("lastName"))
                )
                .findFirst();
    }

    @Override
    public boolean save(MedicalRecord medicalRecord) throws IOException {
        Dto dto = jsonToDtoHelper.getJson();
        dto.getMedicalrecords().add(medicalRecord);
        jsonToDtoHelper.saveJson(dto);
        return false;
    }

    @Override
    public MedicalRecord update(Map<String, String > person, MedicalRecord medicalRecordToUpdate) throws IOException {
        Dto dto = jsonToDtoHelper.getJson();

        dto.getMedicalrecords()
                .stream()
                .filter(_medicalRecord -> _medicalRecord.getFirstName().equals(person.get("firstName"))
                        && _medicalRecord.getLastName().equals(person.get("lastName")))
                .forEach(_medicalRecord -> {
                    _medicalRecord.setFirstName(medicalRecordToUpdate.getLastName());
                    _medicalRecord.setLastName(medicalRecordToUpdate.getFirstName());
                });

        jsonToDtoHelper.saveJson(dto);

        return medicalRecordToUpdate;
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
}
