package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
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
public class FireStationServiceTest {

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    FireStationService fireStationService;

    @Test
    public void findPersonsByFirestation_ShouldReturnFirestations() throws IOException, ParseException {
        Mockito.when(fireStationRepository
                .findByStation("3"))
                .thenReturn(Optional.of(new FireStation("Abidjan", "3", new ArrayList<>())));

        List<Person> persons = new ArrayList<>(){{
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

        Mockito.when(personRepository.findByStation("Abidjan"))
                .thenReturn(persons);

        List<MedicalRecord> medicalRecords = new ArrayList<>(){{
            add(new MedicalRecord(
                    "kone",
                    "ben hassan",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<>() {{
                        add("shellfish");
                    }},
                    new ArrayList<>() {{
                        add("shellfish");
                    }}
            ));
        }};

        Mockito.when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);

        Mockito.when(personRepository
                .filterPersonsByAge(persons.stream(), medicalRecords, 18))
                .thenReturn(new ArrayList<>());

        Mockito.when(personRepository
                .filterPersonsByAge(persons.stream(), medicalRecords, -18))
                .thenReturn(new ArrayList<>());

        Map<String, Object> fireStations = fireStationService.findPersonsByFirestation("3");

        Assert.assertEquals(((List<Person>)fireStations.get("person")).size(), 1);
    }


    @Test
    public void findMedicalRecord_ShouldReturnMedicalRecord() throws IOException {
        Mockito.when(fireStationRepository.findById("Abidjan")).thenReturn(Optional.of(new FireStation("Abidjan", "3", new ArrayList<>())));

        fireStationService.findFiresStation("Abidjan");

        Mockito.verify(fireStationRepository, Mockito.times(1)).findById("Abidjan");
    }

    @Test
    public void create_ShouldReturnSuccess() throws IOException {
        FireStation fireStation = new FireStation("Abidjan", "3", new ArrayList<>());

        fireStationService.createFiresStation(fireStation);
        Mockito.verify(fireStationRepository, Mockito.times(1)).save(fireStation);
    }

    @Test
    public void updatePerson_shouldSuccessfulUpdate() throws IOException {

        FireStation fireStation = new FireStation("Abidjan", "3", new ArrayList<>());


        Mockito.when(fireStationRepository.update("Abidjan", fireStation)).thenReturn(fireStation);

        fireStationService.updateFiresStation("Abidjan", fireStation);

        Mockito.verify(fireStationRepository, Mockito.times(1)).update("Abidjan", fireStation);

    }

    @Test
    public void delete_shouldSuccessfulDelete() throws IOException {
        fireStationService.deleteFireStation("Abidjan");
    }
}
