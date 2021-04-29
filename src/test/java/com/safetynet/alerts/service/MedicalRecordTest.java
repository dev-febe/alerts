package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordTest {
    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    MedicalRecordService medicalRecordService;


    @Test
    public void findMedicalRecords_ShouldReturnMedicalRecordList() throws IOException, ParseException {
        Mockito.when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben fousseni",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<String>() {{
                        add("shellfish");
                    }},
                    new ArrayList<String>() {{
                        add("shellfish");
                    }}));
        }});

        List<MedicalRecord> medicalRecords = medicalRecordService.findMedicalRecords();

        Assert.assertEquals(medicalRecords.size(), 1);
    }

    @Test
    public void findMedicalRecord_ShouldReturnMedicalRecord() throws IOException, ParseException {
        Map<String, String> person = new HashMap<>();
        person.put("firstName", "kone");
        person.put("lastName", "ben hassan");

        Mockito.when(medicalRecordRepository.findById(person)).thenReturn(Optional.of(
                new MedicalRecord(
                        "kone",
                        "ben fousseni",
                        new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                        new ArrayList<String>() {{
                            add("shellfish");
                        }},
                        new ArrayList<String>() {{
                            add("shellfish");
                        }})
        ));

       medicalRecordService.findMedicalRecord("kone", "ben hassan");

        Mockito.verify(medicalRecordRepository, Mockito.times(1)).findById(person);
    }

    @Test
    public void create_ShouldReturnSuccess() throws IOException, ParseException {
        MedicalRecord medicalRecord = new MedicalRecord(
                "kone",
                "ben fousseni",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }});

        medicalRecordService.createMedicalRecord(medicalRecord);
        Mockito.verify(medicalRecordRepository, Mockito.times(1)).save(medicalRecord);
    }

    @Test
    public void updatePerson_shouldSuccessfulUpdate() throws IOException, ParseException {
        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        MedicalRecord medicalRecord = new MedicalRecord(
                "kone",
                "ben fousseni",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }});

        Mockito.when(medicalRecordRepository.update(_person, medicalRecord)).thenReturn(medicalRecord);

        medicalRecordService.updateMedicalRecord("kone", "ben hassan", medicalRecord);

        Mockito.verify(medicalRecordRepository, Mockito.times(1)).update(_person, medicalRecord);

    }

    @Test
    public void delete_shouldSuccessfulDelete() throws IOException {
        medicalRecordService.deleteMedicalRecord("kone", "ben hassan");
    }
}
