package com.safetynet.alerts.repository;

import com.safetynet.alerts.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findTopByFirstNameAndLastName(String firstName, String lastName);
}
