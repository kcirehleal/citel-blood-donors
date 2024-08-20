package com.citel.citel_blood_donors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageAgeStatictsByTypeBloodDto {

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("average_age")
    private Double averageAge;
}
