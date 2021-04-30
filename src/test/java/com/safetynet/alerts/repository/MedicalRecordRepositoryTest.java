package com.safetynet.alerts.repository;

import com.safetynet.alerts.helper.Dto;
import com.safetynet.alerts.helper.JsonToDtoHelper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MedicalRecordRepositoryTest {
    @Mock
    JsonToDtoHelper jsonToDtoHelper;

    @InjectMocks
    MedicalRecordRepository medicalRecordRepository;

    void mockGetJson() throws IOException, ParseException {
        List<Person> persons = new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "",
                    0,
                    new HashMap<>()
            ));
        }};

        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
        }};

        List<MedicalRecord> medicalRecords = new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben hassan",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<>() {{
                        add("shellfish");
                    }},
                    new ArrayList<>() {{
                        add("shellfish");
                    }}));
        }};

        Mockito.when(jsonToDtoHelper.getJson())
                .thenReturn(new Dto(persons, fireStations, medicalRecords));
    }

    @Test
    public void findAll_shouldReturnList() throws IOException, ParseException {
        mockGetJson();

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();

        Assert.assertTrue(medicalRecords.size() > 0);
    }

    @Test
    public void findById_shouldReturnFireStation() throws IOException, ParseException {
        mockGetJson();

        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(_person);

        Assert.assertEquals(medicalRecord.get().getFirstName(), _person.get("firstName"));
    }

    @Test
    public void createStation_shouldReturnSuccess() throws IOException, ParseException {
        mockGetJson();

        List<Person> persons = new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "",
                    0,
                    new HashMap<>()
            ));
        }};

        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
        }};

        List<MedicalRecord> medicalRecords = new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben hassan",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<String>() {{
                        add("shellfish");
                    }},
                    new ArrayList<String>() {{
                        add("shellfish");
                    }}));
        }};

        MedicalRecord medicalRecord = new MedicalRecord(
                "kone",
                "ben hassan",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }});

        Dto mockDto = new Dto(persons, fireStations, medicalRecords);
        mockDto.getMedicalrecords().add(medicalRecord);
        //Dto dto = new Dto(persons, fireStations, medicalRecords);
        Mockito.when(jsonToDtoHelper
                .saveJson(mockDto))
                .thenReturn(true);

        medicalRecordRepository.save(medicalRecord);

        Mockito.verify(jsonToDtoHelper, Mockito.times(1)).getJson();
    }

    @Test
    public void updateStation_shouldReturnSuccess() throws IOException, ParseException {
        mockGetJson();

        MedicalRecord medicalRecord = new MedicalRecord(
                "kone",
                "ben hassan",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }});

        List<Person> persons = new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "",
                    0,
                    new HashMap<>()
            ));
        }};

        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
        }};

        List<MedicalRecord> medicalRecords = new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben hassan",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<String>() {{
                        add("shellfish");
                    }},
                    new ArrayList<String>() {{
                        add("shellfish");
                    }}));
        }};

        Dto dto = new Dto(persons, fireStations, medicalRecords);
        Mockito.when(jsonToDtoHelper
                .saveJson(dto))
                .thenReturn(true);

        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        medicalRecordRepository.update(_person, medicalRecord);

        Mockito.verify(jsonToDtoHelper, Mockito.times(1)).getJson();
    }

    @Test
    public void deleteById_shouldReturnSuccess() throws IOException, ParseException {
        mockGetJson();

        MedicalRecord medicalRecord = new MedicalRecord(
                "kone",
                "ben hassan",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }});

        List<Person> persons = new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "",
                    0,
                    new HashMap<>()
            ));
        }};

        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
        }};

        List<MedicalRecord> medicalRecords = new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben hassan",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<String>() {{
                        add("shellfish");
                    }},
                    new ArrayList<String>() {{
                        add("shellfish");
                    }}));
        }};

        Dto dto = new Dto(persons, fireStations, medicalRecords);
        Mockito.when(jsonToDtoHelper
                .saveJson(dto))
                .thenReturn(true);

        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        medicalRecordRepository.deleteById(_person);

        Mockito.verify(jsonToDtoHelper, Mockito.times(1)).getJson();
    }
}

