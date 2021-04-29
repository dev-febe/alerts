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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;

    @Mock
    FireStationRepository fireStationRepository;

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    PersonService personService;

    @Test
    public void findPersons_shouldReturnPersonList() throws IOException {
        Person ben = new Person(
                "kone",
                "ben",
                "",
                "",
                "",
                "",
                "",
                0,
                new HashMap<>()
        );

        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<Person>() {{
            add(ben);
        }});

        List<Person> persons = personService.findPersons();

        Assert.assertEquals(persons.size(), 1);

        Mockito.verify(personRepository, Mockito.times(1)).findAll();

    }

    @Test
    public void findChildren_shouldReturnChildrenList() throws IOException, ParseException {
        Person personUnder18 = new Person(
                "kone",
                "ben hassan",
                "Abidjan",
                "",
                "",
                "",
                "",
                0,
                new HashMap<>()
        );

        MedicalRecord medicalRecordUnder18 = new MedicalRecord(
                "kone",
                "ben hassan",
                new SimpleDateFormat("yyyy-mm-dd").parse("2019-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }}
        );

        Person personOver18 = new Person(
                "kone",
                "ben fousseni",
                "Abidjan",
                "",
                "",
                "",
                "",
                0,
                new HashMap<>()
        );


        MedicalRecord medicalRecordOver18 = new MedicalRecord(
                "kone",
                "ben fousseni",
                new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                new ArrayList<String>() {{
                    add("shellfish");
                }},
                new ArrayList<String>() {{
                    add("shellfish");
                }}
        );

        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(personOver18);
            add(personUnder18);
        }});

        Mockito.when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(medicalRecordOver18);
            add(medicalRecordUnder18);
        }});

        List<Person> persons = personService.findChildren("Abidjan");

        Assert.assertEquals(persons.size(), 1);
    }

    @Test
    public void findPersonPhones_shouldReturnPhoneNumberList() throws IOException {
        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
            add(new FireStation("Paris", "4", new ArrayList<>()));
        }};
        Mockito.when(fireStationRepository.findAll()).thenReturn(fireStations);

        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
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
            add(new Person(
                    "kone",
                    "ben fousseni",
                    "Paris",
                    "Paris",
                    "",
                    "+2250777059871",
                    "",
                    0,
                    new HashMap<>()
            ));
        }});

        List<String> phones = personService.findPersonPhones("3");

        Assert.assertEquals(phones.size(), 1);
    }

    @Test
    public void findPersonsFireStations_shouldReturnPersonList() throws IOException, ParseException {
        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
            add(new FireStation("Paris", "4", new ArrayList<>()));
        }};
        Mockito.when(fireStationRepository.findAll()).thenReturn(fireStations);

        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
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
            add(new Person(
                    "kone",
                    "ben fousseni",
                    "Paris",
                    "Paris",
                    "",
                    "+2250777059871",
                    "",
                    0,
                    new HashMap<>()
            ));
        }});

        Mockito.when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben fousseni",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<>() {{
                        add("shellfish");
                    }},
                    new ArrayList<>() {{
                        add("shellfish");
                    }}
            ));
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
        }});

        List<Person> persons = personService.findPersonsFireStations("Abidjan");

        Assert.assertEquals(persons.size(), 1);
    }

    @Test
    public void findPersonsGroupFireStations_shouldReturnFireStationList() throws IOException, ParseException {

        List<FireStation> fireStations = new ArrayList<>() {{
            add(new FireStation("Abidjan", "3", new ArrayList<>()));
            add(new FireStation("Paris", "4", new ArrayList<>()));
        }};
        Mockito.when(fireStationRepository.findAll()).thenReturn(fireStations);

        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
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
            add(new Person(
                    "kone",
                    "ben fousseni",
                    "Paris",
                    "Paris",
                    "",
                    "+2250777059871",
                    "",
                    0,
                    new HashMap<>()
            ));
        }});

        Mockito.when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new MedicalRecord(
                    "kone",
                    "ben fousseni",
                    new SimpleDateFormat("yyyy-mm-dd").parse("1990-03-03"),
                    new ArrayList<>() {{
                        add("shellfish");
                    }},
                    new ArrayList<>() {{
                        add("shellfish");
                    }}
            ));
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
        }});

        List<FireStation> persons = personService.findPersonsGroupFireStations("Abidjan");

        Assert.assertEquals(persons.size(), 1);
    }

    @Test
    public void findPersonsEmailByCity_shouldReturnEmailList() throws IOException {
        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "foussenichrist@gmail.com",
                    0,
                    new HashMap<>()
            ));
            add(new Person(
                    "kone",
                    "ben fousseni",
                    "Paris",
                    "Paris",
                    "",
                    "+2250777059871",
                    "",
                    0,
                    new HashMap<>()
            ));
        }});
        List<String> emails = personService.findPersonsEmailByCity("Abidjan");

        Assert.assertEquals(emails.size(), 1);
    }

    @Test
    public void findPersonInfo_shouldReturnPersonMatched() throws IOException {
        Mockito.when(personRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Person(
                    "kone",
                    "ben hassan",
                    "Abidjan",
                    "Abidjan",
                    "",
                    "+2250777059870",
                    "foussenichrist@gmail.com",
                    0,
                    new HashMap<>()
            ));
            add(new Person(
                    "kone",
                    "ben fousseni",
                    "Paris",
                    "Paris",
                    "",
                    "+2250777059871",
                    "",
                    0,
                    new HashMap<>()
            ));
        }});

        List<Person> persons = personService.findPersonInfo("kone", "ben fousseni");

        Assert.assertEquals(persons.size(), 1);
    }


    @Test
    public void findPerson_shouldReturnPersonMatched() throws IOException {
        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        Mockito.when(personRepository.findById(_person)).thenReturn(Optional.of(new Person(
                "kone",
                "ben hassan",
                "Abidjan",
                "Abidjan",
                "",
                "+2250777059870",
                "foussenichrist@gmail.com",
                0,
                new HashMap<>()
        )));

        Person person = personService.findPerson("kone", "ben hassan");

        Mockito.verify(personRepository, Mockito.times(1)).findById(_person);
        Assert.assertEquals(person.getFirstName(), _person.get("firstName"));
        Assert.assertEquals(person.getLastName(), _person.get("lastName"));
    }


    @Test
    public void when_save_person_it_should_return_person() throws IOException {
        Person personToCreate = new Person();
        personService.createPerson(personToCreate);
        Mockito.verify(personRepository, Mockito.times(1)).save(personToCreate);
    }

    @Test
    public void updatePerson_shouldSuccessfulUpdate() throws IOException {
        Map<String, String> _person = new HashMap<>();
        _person.put("firstName", "kone");
        _person.put("lastName", "ben hassan");

        Person personToUpdate = new Person(
                "kone",
                "ben",
                "",
                "",
                "",
                "",
                "",
                0,
                new HashMap<>()
        );

        Mockito.when(personRepository.update(_person, personToUpdate)).thenReturn(personToUpdate);

        personService.updatePerson("kone", "ben hassan", personToUpdate);

        Mockito.verify(personRepository, Mockito.times(1)).update(_person, personToUpdate);

    }

    @Test
    public void delete_shouldSuccessfulDelete() throws IOException {
        personService.deletePerson("kone", "ben hassan");
    }
}
