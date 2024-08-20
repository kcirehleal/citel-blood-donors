package com.citel.citel_blood_donors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PercentageObesePeopleStatisticsDto {

    private String gender;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("obese_count")
    private Integer obeseCount;

    @JsonProperty("obesity_percentage")
    private Float obesityPercentage;
}
