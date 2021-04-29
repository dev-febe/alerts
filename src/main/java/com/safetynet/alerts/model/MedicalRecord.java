package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicalRecord {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonProperty("birthdate")
    private Date birthdate;
    @JsonProperty("birthdate")
    private List<String> medications;
    @JsonProperty("birthdate")
    private List<String> allergies;
}
