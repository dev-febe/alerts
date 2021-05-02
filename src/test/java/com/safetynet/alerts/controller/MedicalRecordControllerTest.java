package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {
    @MockBean
    MedicalRecordService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMedicalRecord() throws Exception {
        mockMvc.perform(get("/medicalRecord/firstName/ben/lastName/christ"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostMedicalRecord() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("ddd");
        when(service.createMedicalRecord(medicalRecord)).thenReturn(medicalRecord);
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(medicalRecord)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutFireStations() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setLastName("kone");
        when(service.updateMedicalRecord("kone", "ben", medicalRecord)).thenReturn(medicalRecord);
        mockMvc.perform(put("/medicalRecord/firstName/ben/lastName/christ")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelMedicalRecords() throws Exception {
        mockMvc.perform(delete("/medicalRecord/firstName/ben/lastName/christ"))
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
