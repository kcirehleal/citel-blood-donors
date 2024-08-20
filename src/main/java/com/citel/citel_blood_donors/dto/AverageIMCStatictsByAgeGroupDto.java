package com.citel.citel_blood_donors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageIMCStatictsByAgeGroupDto {

    @JsonProperty("age_group")
    private String ageGroup;

    @JsonProperty("average_imc")
    private Double averageImc;
}
