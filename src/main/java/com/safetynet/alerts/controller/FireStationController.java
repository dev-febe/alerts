package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class FireStationController {
    Logger logger = LoggerFactory.getLogger(FireStationController.class);

    FireStationService service;

    @Autowired
    FireStationController(FireStationService service) {
        this.service = service;
    }

    @GetMapping("/firestation")
    HashMap<String, Object> getFireStations(@RequestParam String stationNumber) {
        try {
            logger.info("Persons was found successfully by station number");
            return this.service.findPersonsByFirestation(stationNumber);
        } catch (IOException e) {
            logger.error("Source file not found");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/firestation/{address}")
    FireStation getFireStation(@PathVariable String address) throws IOException {
        return service.findFiresStation(address);
    }

    @PostMapping("/firestation")
    FireStation postFireStation(@RequestBody FireStation fireStation) throws IOException {
        return service.createFiresStation(fireStation);
    }

    @PutMapping("/firestation/{address}")
    FireStation putFireStation(@RequestBody FireStation fireStationToUpdate, @PathVariable String address) throws IOException {
        return service.updateFiresStation(address, fireStationToUpdate);
    }

    @DeleteMapping("/firestation/{address}")
    void delFireStation(@PathVariable String address) throws IOException {
        service.deleteFireStation(address);
    }
}
