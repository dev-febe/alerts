package com.safetynet.alerts.repository;

import com.safetynet.alerts.helper.Dto;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.helper.JsonToDtoHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class FireStationRepository implements JsonRepository<FireStation, String> {
    @Override
    public List<FireStation> findAll() throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();

        return jsonToDtoHelper
                .getJson()
                .getFirestations();
    }

    @Override
    public Optional<FireStation> findById(String address) throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();

        List<FireStation> firestations = jsonToDtoHelper
                .getJson()
                .getFirestations();

        return firestations.stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();
    }

    public Optional<FireStation> findByStation(String station) throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();

        List<FireStation> firestations = jsonToDtoHelper
                .getJson()
                .getFirestations();

        return firestations.stream()
                .filter(fireStation -> fireStation.getStation().equals(station))
                .findFirst();
    }

    @Override
    public boolean save(FireStation fireStation) throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();
        Dto dto = jsonToDtoHelper.getJson();
        dto.getFirestations().add(fireStation);
        jsonToDtoHelper.saveJson(dto);
        return false;
    }

    @Override
    public FireStation update(String address, FireStation fireStationToUpdate) throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();
        Dto dto = jsonToDtoHelper.getJson();

        dto.getFirestations()
                .stream()
                .filter(_fireStation -> _fireStation.getAddress().equals(address))
                .forEach(_fireStation -> {
                    _fireStation.setStation(fireStationToUpdate.getStation());
                    _fireStation.setAddress(fireStationToUpdate.getAddress());
                });

        jsonToDtoHelper.saveJson(dto);

        return fireStationToUpdate;
    }

    @Override
    public void deleteById(String address) throws IOException {
        JsonToDtoHelper jsonToDtoHelper = new JsonToDtoHelper();
        Dto dto = jsonToDtoHelper.getJson();

        dto.getFirestations()
                .removeIf(_fireStation -> _fireStation.getAddress().equals(address));

        jsonToDtoHelper.saveJson(dto);
    }
}
