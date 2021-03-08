package com.safetynet.alerts.controller;

import com.safetynet.alerts.entity.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    MedicalRecordService service;

    MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping("/medicalRecords")
    List<MedicalRecord> getMedicalRecords() {
        return this.service.findMedicalRecords();
    }

    @GetMapping("/medicalRecords/{id}")
    MedicalRecord getMedicalRecord(@PathVariable Long id) {
        return service.findMedicalRecord(id);
    }

    @PostMapping("/medicalRecords")
    MedicalRecord postMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return service.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecords/{id}")
    MedicalRecord putMedicalRecord(@RequestBody MedicalRecord medicalRecordToUpdate, @PathVariable Long id) {
        return service.updateMedicalRecord(id, medicalRecordToUpdate);
    }

    @DeleteMapping("/medicalRecords/{id}")
    void delMedicalRecord(@PathVariable Long id) {
        service.deleteMedicalRecord(id);
    }
}
