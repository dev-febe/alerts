package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {

    MedicalRecordService service;

    @Autowired
    MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping("/medicalRecord")
    List<MedicalRecord> getMedicalRecords() throws IOException {
        return this.service.findMedicalRecords();
    }

    @GetMapping("/medicalRecord/firstName/{firstName}/lastName/{lastName}")
    MedicalRecord getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) throws IOException {
        return service.findMedicalRecord(firstName, lastName);
    }

    @PostMapping("/medicalRecord")
    MedicalRecord postMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException {
        return service.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecord/firstName/{firstName}/lastName/{lastName}")
    MedicalRecord putMedicalRecord(
            @RequestBody MedicalRecord medicalRecordToUpdate,
            @PathVariable String firstName,
            @PathVariable String lastName
    ) throws IOException {
        return service.updateMedicalRecord(firstName, lastName, medicalRecordToUpdate);
    }

    @DeleteMapping("/medicalRecord/firstName/{firstName}/lastName/{lastName}")
    void delMedicalRecord(@PathVariable String firstName,
                          @PathVariable String lastName) throws IOException {
        service.deleteMedicalRecord(firstName, lastName);
    }
}
