package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FireStationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {
    @MockBean
    FireStationService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetFireStations() throws Exception {
        mockMvc.perform(get("/firestations?stationNumber=1"))
                .andExpect(status().isOk());
    }
}
