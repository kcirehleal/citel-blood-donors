package com.citel.citel_blood_donors.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BloodDonorsForm {

    @JsonProperty("nome")
    private String name;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("rg")
    private String rg;

    @JsonProperty("data_nasc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    @JsonProperty("sexo")
    private String gender;

    @JsonProperty("mae")
    private String mother;

    @JsonProperty("pai")
    private String father;

    @JsonProperty("email")
    private String email;

    @JsonProperty("cep")
    private String zipCode;

    @JsonProperty("endereco")
    private String address;

    @JsonProperty("numero")
    private Integer number;

    @JsonProperty("bairro")
    private String neighborhood;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("estado")
    private String state;

    @JsonProperty("telefone_fixo")
    private String landline;

    @JsonProperty("celular")
    private String mobile;

    @JsonProperty("altura")
    private Float height;

    @JsonProperty("peso")
    private Float weight;

    @JsonProperty("tipo_sanguineo")
    private String bloodType;

}
