package com.citel.citel_blood_donors.dto.response;

import com.citel.citel_blood_donors.dto.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BloodDonorsResponse {

    private List<DonorsStatisticsByStateDto> donorsStatisticsByStateDto;
    private List<AverageIMCStatictsByAgeGroupDto> averageIMCStatictsByAgeGroupDto;
    private List<PercentageObesePeopleStatisticsDto> percentageObesePeopleStatisticsDto;
    private List<AverageAgeStatictsByTypeBloodDto> averageAgeStatictsByTypeBloodDto;
    private List<SuitableDonorsStatictsByTypeBloodDto> suitableDonorsStatictsByTypeBloodDto;

}
