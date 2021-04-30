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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FireStationRepositoryTest {
    @Mock
    JsonToDtoHelper jsonToDtoHelper;

    @InjectMocks
    FireStationRepository fireStationRepository;

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

        List<FireStation> fireStations = fireStationRepository.findAll();

        Assert.assertTrue(fireStations.size() > 0);
    }

    @Test
    public void findById_shouldReturnFireStation() throws IOException, ParseException {
        mockGetJson();

        Optional<FireStation> fireStation = fireStationRepository.findById("Abidjan");

        Assert.assertEquals(fireStation.get().getAddress(), "Abidjan");
    }

    @Test
    public void findByStation_shouldReturnFireStation() throws IOException, ParseException {
        mockGetJson();

        Optional<FireStation> fireStation = fireStationRepository.findByStation("3");

        Assert.assertEquals(fireStation.get().getAddress(), "Abidjan");
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

        FireStation fireStation = new FireStation("Abidjan", "3", new ArrayList<>());

        Dto mockDto = new Dto(persons, fireStations, medicalRecords);
        mockDto.getFirestations().add(fireStation);
        //Dto dto = new Dto(persons, fireStations, medicalRecords);
        Mockito.when(jsonToDtoHelper
                .saveJson(mockDto))
                .thenReturn(true);

        fireStationRepository.save(fireStation);

        Mockito.verify(jsonToDtoHelper, Mockito.times(1)).getJson();
    }

    @Test
    public void updateStation_shouldReturnSuccess() throws IOException, ParseException {
        mockGetJson();

        FireStation fireStation = new FireStation("Abidjan", "3", new ArrayList<>());

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

        fireStationRepository.update("Abidjan", fireStation);

        Mockito.verify(jsonToDtoHelper, Mockito.times(1)).getJson();
    }
}
