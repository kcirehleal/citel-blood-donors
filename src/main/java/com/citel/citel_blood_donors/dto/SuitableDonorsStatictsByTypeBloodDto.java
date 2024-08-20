package com.citel.citel_blood_donors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuitableDonorsStatictsByTypeBloodDto {

    @JsonProperty("blood_type")
    private String bloodType;

    @JsonProperty("qtt_possible_donors")
    private Integer qttPossibleDonors;
}
