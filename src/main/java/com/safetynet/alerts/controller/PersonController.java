package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class PersonController {
    Logger logger = LoggerFactory.getLogger(FireStationController.class);

    PersonService service;

    @Autowired
    PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/person")
    List<Person> getPersons() {
        try {
            logger.info("Person retrieved successfully");
            return this.service.findPersons();
        } catch (IOException e) {
            logger.error("Source file not found");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Source file not found", e);
        }
    }

    @GetMapping("/childAlert")
    List<Person> getChildPersons(@RequestParam(required = false) String address) throws IOException {
        return this.service.findChildren(address);
    }

    @GetMapping("/phoneAlert")
    List<String> getPersonPhones(@RequestParam(required = false) String fireStationNumber) throws IOException {
        return this.service.findPersonPhones(fireStationNumber);
    }

    @GetMapping("/fire")
    List<Person> getPersonPhonesByAddress(@RequestParam(required = false) String address) throws IOException {
        return this.service.findPersonsFireStations(address);
    }

    @GetMapping("/flood/stations")
    List<FireStation> getPersonsGroupFireStation(@RequestParam(required = false) String address) throws IOException {
        return this.service.findPersonsGroupFireStations(address);
    }

    @GetMapping("/personInfo")
    List<Person> getPersonsInfo(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) throws IOException {
        return this.service.findPersonInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    List<String> getPersonsEmail(@RequestParam(required = false) String city) throws IOException {
        return this.service.findPersonsEmailByCity(city);
    }

    @GetMapping("/person/firstName/{firstName}/lastName/{lastName}")
    Person getPerson(@PathVariable String firstName, @PathVariable String lastName) throws IOException {
        return service.findPerson(firstName, lastName);
    }

    @PostMapping("/person")
    Person postPerson(@RequestBody Person person) throws IOException {
        return service.createPerson(person);
    }

    @PutMapping("/person/firstName/{firstName}/lastName/{lastName}")
    Person putPerson(@RequestBody Person personToUpdate, @PathVariable String firstName, @PathVariable String lastName) throws IOException {
        return service.updatePerson(firstName, lastName, personToUpdate);
    }

    @DeleteMapping("/person/firstName/{firstName}/lastName/{lastName}")
    void delPerson(@PathVariable String firstName, @PathVariable String lastName) throws IOException {
        service.deletePerson(firstName, lastName);
    }
}
