package com.citel.citel_blood_donors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonorsStatisticsByStateDto {

    @JsonProperty("state")
    private String state;

    @JsonProperty("donors_qtt")
    private Long donorsQtt;

}
