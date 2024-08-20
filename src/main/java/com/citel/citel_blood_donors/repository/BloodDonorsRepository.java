package com.citel.citel_blood_donors.repository;

import com.citel.citel_blood_donors.domain.BloodDonor;
import com.citel.citel_blood_donors.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloodDonorsRepository extends JpaRepository<BloodDonor, Long> {

    @Query(nativeQuery = true, name = "DonorsStatisticsByStateMapping")
    List<DonorsStatisticsByStateDto> showDonorsStatisticsByState();

    @Query(nativeQuery = true, name = "AverageIMCStatictsByAgeGroupDtoMapping")
    List<AverageIMCStatictsByAgeGroupDto> showAverageIMCStatictsByAgeGroup();

    @Query(nativeQuery = true, name = "PercentageObesePeopleStatisticsDtoMapping")
    List<PercentageObesePeopleStatisticsDto> showPercentageObesePeopleStatistics();

    @Query(nativeQuery = true, name = "AverageAgeStatictsByTypeBloodDtoMapping")
    List<AverageAgeStatictsByTypeBloodDto> showAverageAgeStatictsByTypeBlood();

    @Query(nativeQuery = true, name = "SuitableDonorsStatictsByTypeBloodMapping")
    List<SuitableDonorsStatictsByTypeBloodDto> showSuitableDonorsStatictsByTypeBlood();
}
