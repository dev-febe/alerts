package com.safetynet.alerts.helper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class JsonToDtoHelperTest {
    @InjectMocks
    JsonToDtoHelper jsonToDtoHelper;

    @Test
    public void getJson_ShouldReturnDtoObject() throws IOException {
        Dto dto = jsonToDtoHelper.getJson();

        Assert.assertTrue(dto.getFirestations().size() > 0);
        Assert.assertTrue(dto.getPersons().size() > 0);
        Assert.assertTrue(dto.getMedicalrecords().size() > 0);
    }

    @Test
    public void saveJson_ShouldSuccess() throws IOException {
        Dto dto = jsonToDtoHelper.getJson();
        boolean json = jsonToDtoHelper.saveJson(dto);
        Assert.assertTrue(json);
    }
}
