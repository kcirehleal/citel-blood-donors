package com.citel.citel_blood_donors.services;

import com.citel.citel_blood_donors.domain.BloodDonor;
import com.citel.citel_blood_donors.dto.*;
import com.citel.citel_blood_donors.dto.response.BloodDonorsResponse;
import com.citel.citel_blood_donors.form.BloodDonorsForm;
import com.citel.citel_blood_donors.repository.BloodDonorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BloodDonorsService {

    final BloodDonorsRepository bloodDonorsRepository;

    BloodDonorsService(@Autowired BloodDonorsRepository bloodDonorsRepository) {
        this.bloodDonorsRepository = bloodDonorsRepository;
    }

    @Transactional
    public BloodDonorsResponse register(List<BloodDonorsForm> bloodDonorsForm) {

        List<BloodDonor> bloodDonorList = new ArrayList<>();

        bloodDonorsForm.forEach(bloodDonor -> {
            bloodDonorList.add(BloodDonor.buildBloodDonorByBloodDonorsForm(bloodDonor));
        });

        bloodDonorsRepository.saveAll(bloodDonorList);

        BloodDonorsResponse bloodDonorsResponse = getBloodDonorsResponse();

        return bloodDonorsResponse;
    }

    private BloodDonorsResponse getBloodDonorsResponse() {

        List<DonorsStatisticsByStateDto> donorsStatisticsByStateDtoList = bloodDonorsRepository.showDonorsStatisticsByState();

        List<AverageIMCStatictsByAgeGroupDto> averageIMCStatictsByAgeGroupDtoList = bloodDonorsRepository.showAverageIMCStatictsByAgeGroup();

        List<PercentageObesePeopleStatisticsDto> percentageObesePeopleStatisticsDtoList = bloodDonorsRepository.showPercentageObesePeopleStatistics();

        List<AverageAgeStatictsByTypeBloodDto> averageAgeStatictsByTypeBloodDtoList = bloodDonorsRepository.showAverageAgeStatictsByTypeBlood();

        List<SuitableDonorsStatictsByTypeBloodDto> suitableDonorsStatictsByTypeBloodDtoList = bloodDonorsRepository.showSuitableDonorsStatictsByTypeBlood();

        BloodDonorsResponse bloodDonorsResponse = BloodDonorsResponse.builder()
                .donorsStatisticsByStateDto(donorsStatisticsByStateDtoList)
                .averageIMCStatictsByAgeGroupDto(averageIMCStatictsByAgeGroupDtoList)
                .percentageObesePeopleStatisticsDto(percentageObesePeopleStatisticsDtoList)
                .averageAgeStatictsByTypeBloodDto(averageAgeStatictsByTypeBloodDtoList)
                .suitableDonorsStatictsByTypeBloodDto(suitableDonorsStatictsByTypeBloodDtoList)
                .build();

        return bloodDonorsResponse;
    }

    public BloodDonorsResponse findStatistics() {
        return getBloodDonorsResponse();
    }

}
