package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.FireStationRepository;
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
public class FireStationServiceTest {

    @Mock
    FireStationRepository fireStationRepository;

    @InjectMocks
    FireStationService fireStationService;

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
