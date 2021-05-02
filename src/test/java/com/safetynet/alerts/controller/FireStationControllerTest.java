package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.equalTo;


@RunWith(SpringRunner.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {
    @MockBean
    FireStationService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFireStations() throws Exception {
        HashMap<String, Object> personsByStation = new HashMap<String, Object>();
        personsByStation.put("person", 0);
        personsByStation.put("nbAdult", 0);
        personsByStation.put("nbChild", 0);

        Mockito.when(service.findPersonsByFirestation("1"))
                .thenReturn(personsByStation);

        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nbAdult", equalTo(0) ))
                .andExpect(jsonPath("$.person", equalTo(0) ))
                .andExpect(jsonPath("$.person", equalTo(0) ))

        ;
    }

    @Test
    public void testGetFireStation() throws Exception {
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostFireStations() throws Exception {
        FireStation fireStation = new FireStation();
        fireStation.setStation("1L");
        fireStation.setAddress("orly");
        when(service.createFiresStation(fireStation)).thenReturn(fireStation);
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fireStation)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutFireStations() throws Exception {
        FireStation fireStation = new FireStation();
        fireStation.setStation("1L");
        fireStation.setAddress("orly");
        when(service.updateFiresStation("1", fireStation)).thenReturn(fireStation);
        mockMvc.perform(put("/firestation/1")
                .content(asJsonString(fireStation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelFireStations() throws Exception {
        mockMvc.perform(delete("/firestation/1"))
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
