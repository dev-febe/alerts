package com.safetynet.alerts.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class JsonToDtoHelper {
    public Dto getJson() throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get("data.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, Dto.class);
    }

    public boolean saveJson(Dto var) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("data.json"), var);
        return true;
    }
}
