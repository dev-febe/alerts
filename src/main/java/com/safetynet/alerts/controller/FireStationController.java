package com.safetynet.alerts.controller;

import com.safetynet.alerts.entity.FireStation;
import com.safetynet.alerts.entity.Person;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FireStationController {

    FireStationService service;

    FireStationController(FireStationService service) {
        this.service = service;
    }

    @GetMapping("/firestations")
    List<Person> getFireStations(@RequestParam Optional<Long> stationNumber) {
        return this.service.findPersonByFireStations(stationNumber.orElseGet(null));
    }

    @GetMapping("/firestations/{id}")
    FireStation getFireStation(@PathVariable Long id) {
        return service.findFiresStation(id);
    }

    @PostMapping("/firestations")
    FireStation postFireStation(@RequestBody FireStation fireStation) {
        return service.createFiresStation(fireStation);
    }

    @PutMapping("/firestations/{id}")
    FireStation putFireStation(@RequestBody FireStation fireStationToUpdate, @PathVariable Long id) {
        return service.updateFiresStation(id, fireStationToUpdate);
    }

    @DeleteMapping("/firestations/{id}")
    void delFireStation(@PathVariable Long id) {
        service.deleteFireStation(id);
    }
}
