package com.safetynet.alerts.controller;

import com.safetynet.alerts.entity.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    PersonService service;

    PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/persons")
    List<Person> getPersons() {
        return this.service.findPersons();
    }

    @GetMapping("/childAlert")
    List<Person> getChildPersons(@RequestParam(required = false) String address) {
        return this.service.findChildren(address);
    }

    @GetMapping("/phoneAlert")
    List<Person> getPersonPhones(@RequestParam(required = false) Long fireStationNumber) {
        return this.service.findPersonPhones(fireStationNumber);
    }

    @GetMapping("/fire")
    List<Person> getPersonPhones(@RequestParam(required = false) String address) {
        return this.service.findPersonsFireStations(address);
    }

    @GetMapping("/flood/stations")
    List<Person> getPersonsGroupFireStation(@RequestParam(required = false) String address) {
        return this.service.findPersonsGroupFireStations(address);
    }

    @GetMapping("/personInfo")
    List<Person> getPersonsInfo(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        return this.service.findPersonInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    List<Person> getPersonsEmail(@RequestParam(required = false) String city) {
        return this.service.findPersonsEmailByCity(city);
    }

    @GetMapping("/persons/{id}")
    Person getPerson(@PathVariable Long id) {
        return service.findPerson(id);
    }

    @PostMapping("/persons")
    Person postPerson(@RequestBody Person person) {
        return service.createPerson(person);
    }

    @PutMapping("/persons/{id}")
    Person putPerson(@RequestBody Person personToUpdate, @PathVariable Long id) {
        return service.updatePerson(id, personToUpdate);
    }

    @DeleteMapping("/persons/{id}")
    void delPerson(@PathVariable Long id) {
        service.deletePerson(id);
    }
}
