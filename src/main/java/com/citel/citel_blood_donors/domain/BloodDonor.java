package com.citel.citel_blood_donors.domain;

import com.citel.citel_blood_donors.dto.*;
import com.citel.citel_blood_donors.form.BloodDonorsForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@SqlResultSetMapping(
        name = "DonorsStatisticsByStateMapping",
        classes = @ConstructorResult(
                targetClass = DonorsStatisticsByStateDto.class,
                columns = {
                        @ColumnResult(name = "state"),
                        @ColumnResult(name = "donors_qtt")
                }
        )
)
@NamedNativeQuery(
        name = "DonorsStatisticsByStateMapping",
        resultClass = DonorsStatisticsByStateDto.class,
        query = """
                SELECT
                    state, count(*) AS donors_qtt
                FROM
                    blood_donor
                GROUP BY 1
                """,
        resultSetMapping = "DonorsStatisticsByStateMapping"
)
@SqlResultSetMapping(
        name = "AverageIMCStatictsByAgeGroupDtoMapping",
        classes = @ConstructorResult(
                targetClass = AverageIMCStatictsByAgeGroupDto.class,
                columns = {
                        @ColumnResult(name = "age_group"),
                        @ColumnResult(name = "average_imc", type = Double.class)
                }
        )
)
@NamedNativeQuery(
        name = "AverageIMCStatictsByAgeGroupDtoMapping",
        resultClass = AverageIMCStatictsByAgeGroupDto.class,
        query = """
                WITH imc_calc AS (
                SELECT
                    id,
                    name,
                    birth_date,
                    weight,
                    height,
                    (weight / (height * height)) AS imc,
                    TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) AS age
                FROM blood_donor
            ),
            age_groups AS (
                SELECT
                    id,
                    name,
                    imc,
                    CASE
                        WHEN age BETWEEN 0 AND 10 THEN '0-10'
                        WHEN age BETWEEN 11 AND 20 THEN '11-20'
                        WHEN age BETWEEN 21 AND 30 THEN '21-30'
                        ELSE '31+'
                    END AS age_group
                FROM imc_calc
            )
            SELECT
                age_group,
                AVG(imc) AS average_imc
            FROM age_groups
            GROUP BY age_group
            ORDER BY age_group;
                """,
        resultSetMapping = "AverageIMCStatictsByAgeGroupDtoMapping"
)
@SqlResultSetMapping(
        name = "PercentageObesePeopleStatisticsDtoMapping",
        classes = @ConstructorResult(
                targetClass = PercentageObesePeopleStatisticsDto.class,
                columns = {
                        @ColumnResult(name = "gender"),
                        @ColumnResult(name = "total_count", type = Integer.class),
                        @ColumnResult(name = "obese_count", type = Integer.class),
                        @ColumnResult(name = "obesity_percentage", type = Float.class)
                }
        )
)
@NamedNativeQuery(
        name = "PercentageObesePeopleStatisticsDtoMapping",
        resultClass = PercentageObesePeopleStatisticsDto.class,
        query = """
                WITH imc_calc AS (
                      SELECT
                          id,
                          gender,
                          (weight / (height * height)) AS imc
                      FROM blood_donor
                  ),
                  obese_counts AS (
                      SELECT
                          gender,
                          COUNT(*) AS total_count,
                          SUM(CASE WHEN imc > 30 THEN 1 ELSE 0 END) AS obese_count
                      FROM imc_calc
                      GROUP BY gender
                  )
                  SELECT
                      gender,
                      total_count,
                      obese_count,
                      (obese_count / total_count) * 100 AS obesity_percentage
                  FROM obese_counts
                  WHERE gender IN ('Masculino', 'Feminino');
                """,
        resultSetMapping = "PercentageObesePeopleStatisticsDtoMapping"
)
@SqlResultSetMapping(
        name = "AverageAgeStatictsByTypeBloodDtoMapping",
        classes = @ConstructorResult(
                targetClass = AverageAgeStatictsByTypeBloodDto.class,
                columns = {
                        @ColumnResult(name = "blood_type"),
                        @ColumnResult(name = "average_age", type = Double.class)
                }
        )
)
@NamedNativeQuery(
        name = "AverageAgeStatictsByTypeBloodDtoMapping",
        resultClass = AverageAgeStatictsByTypeBloodDto.class,
        query = """
            WITH age_calc AS (
                SELECT
                    id,
                    blood_type,
                    TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) AS age
                FROM blood_donor
            )
            SELECT
                blood_type,
                AVG(age) AS average_age
            FROM age_calc
            GROUP BY blood_type
            ORDER BY blood_type;
            """,
        resultSetMapping = "AverageAgeStatictsByTypeBloodDtoMapping"
)
@SqlResultSetMapping(
        name = "SuitableDonorsStatictsByTypeBloodMapping",
        classes = @ConstructorResult(
                targetClass = SuitableDonorsStatictsByTypeBloodDto.class,
                columns = {
                        @ColumnResult(name = "blood_type"),
                        @ColumnResult(name = "qtt_possible_donors", type = Integer.class)
                }
        )
)
@NamedNativeQuery(
        name = "SuitableDonorsStatictsByTypeBloodMapping",
        resultClass = SuitableDonorsStatictsByTypeBloodDto.class,
        query = """
                SELECT
                    'A+' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('A+', 'A-', 'O+', 'O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'A-' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('A-', 'O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'B+' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('B+', 'B-', 'O+', 'O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'B-' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('B-', 'O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'AB+' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('A+', 'B+', 'O+', 'AB+', 'A-', 'B-', 'O-', 'AB-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'AB-' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('A-', 'B-', 'O-', 'AB-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'O+' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('O+', 'O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50
                UNION ALL
                SELECT
                    'O-' AS blood_type,
                    COUNT(cpf) AS qtt_possible_donors
                FROM
                    blood_donor
                WHERE
                    blood_type IN ('O-')
                    AND TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) BETWEEN 16 AND 69
                    AND weight > 50;
                """,
        resultSetMapping = "SuitableDonorsStatictsByTypeBloodMapping"
)
@Table(name = "blood_donor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BloodDonor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "rg", unique = true)
    private String rg;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "mother")
    private String mother;

    @Column(name = "father")
    private String father;

    @Column(name = "email")
    private String email;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "number")
    private Integer number;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "landline")
    private String landline;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "blood_type")
    private String bloodType;

    public static BloodDonor buildBloodDonorByBloodDonorsForm(BloodDonorsForm bloodDonorsForm) {

        BloodDonor donor = BloodDonor.builder()
                .name(bloodDonorsForm.getName())
                .cpf(bloodDonorsForm.getCpf())
                .rg(bloodDonorsForm.getRg())
                .birthDate(bloodDonorsForm.getBirthDate())
                .gender(bloodDonorsForm.getGender())
                .mother(bloodDonorsForm.getMother())
                .father(bloodDonorsForm.getFather())
                .email(bloodDonorsForm.getEmail())
                .zipCode(bloodDonorsForm.getZipCode())
                .address(bloodDonorsForm.getAddress())
                .number(bloodDonorsForm.getNumber())
                .neighborhood(bloodDonorsForm.getNeighborhood())
                .city(bloodDonorsForm.getCity())
                .state(bloodDonorsForm.getState())
                .landline(bloodDonorsForm.getLandline())
                .mobile(bloodDonorsForm.getMobile())
                .height(bloodDonorsForm.getHeight())
                .weight(bloodDonorsForm.getWeight())
                .bloodType(bloodDonorsForm.getBloodType())
                .build();

        return donor;
    }
}

