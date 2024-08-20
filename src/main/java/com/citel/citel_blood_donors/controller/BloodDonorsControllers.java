package com.citel.citel_blood_donors.controller;

import com.citel.citel_blood_donors.form.BloodDonorsForm;
import com.citel.citel_blood_donors.dto.response.BloodDonorsResponse;
import com.citel.citel_blood_donors.services.BloodDonorsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/blood-donors")
public class BloodDonorsControllers {

    final BloodDonorsService bloodDonorsService;

    BloodDonorsControllers (@Autowired BloodDonorsService bloodDonorsService) {this.bloodDonorsService = bloodDonorsService;}

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BloodDonorsResponse> register(@RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<BloodDonorsForm> bloodDonorsFormList = objectMapper.readValue(file.getInputStream(), new TypeReference<List<BloodDonorsForm>>() {});

        BloodDonorsResponse bloodDonorsResponse = bloodDonorsService.register(bloodDonorsFormList);

        return ResponseEntity.ok(bloodDonorsResponse);
    }

    @GetMapping
    public BloodDonorsResponse findStatistics() {
        return bloodDonorsService.findStatistics();
    }
}
