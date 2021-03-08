package com.safetynet.alerts.service;

import com.safetynet.alerts.entity.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {
    @Mock
    PersonRepository repository;

    @InjectMocks
    PersonService personService;

    @Test
    public void when_save_person_it_should_return_person() {
        Person personToCreate = new Person();
        Person personCreated = personService.createPerson(personToCreate);
    }
}
