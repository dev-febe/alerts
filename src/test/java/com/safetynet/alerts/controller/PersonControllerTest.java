package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @MockBean
    PersonService service;

    @Autowired
    private MockMvc mockMvc;

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
        // List<Person> persons = new ArrayList<>();
        // persons.add(new Person());
        // when(service.findChildren("orly")).thenReturn(persons);
        mockMvc.perform(get("/childAlert"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonPhones() throws Exception {
        // List<Person> persons = new ArrayList<>();
        // persons.add(new Person());
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
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=ben&lastName=kone"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCommunityEmail() throws Exception {
        mockMvc.perform(get("/communityEmail"))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetPerson() throws Exception {
        mockMvc.perform(get("/person/firstName/ben/lastName/fousseni"))
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
