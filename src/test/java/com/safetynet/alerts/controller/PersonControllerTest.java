package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {
    @MockBean
    PersonService service;

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before  // This method will execute it before each method is executed.
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  // Initialize MockMVC object
    }


    @Test
    public void testGetPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        when(service.findPersons()).thenReturn(persons);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChildPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        // when(service.findChildren("orly")).thenReturn(persons);
        mockMvc.perform(get("/childAlert"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonPhones() throws Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        // when(service.findChildren("orly")).thenReturn(persons);
        mockMvc.perform(get("/phoneAlert"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonPhonesByAddress() throws Exception {
        mockMvc.perform(get("/fire"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsGroupFireStation() throws Exception {
        mockMvc.perform(get("/flood/stations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostPerson() throws Exception {
        Person person = new Person();
        when(service.createPerson(person)).thenReturn(person);
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(person)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutPerson() throws Exception {
        Person person = new Person();
        when(service.updatePerson("1L", "", person)).thenReturn(person);
        mockMvc.perform(put("/person/firstName/ben/lastName/christ")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelPersons() throws Exception {
        mockMvc.perform(delete("/person/firstName/ben/lastName/christ"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
