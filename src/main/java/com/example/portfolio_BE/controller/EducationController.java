package com.example.portfolio_BE.controller;

import com.example.portfolio_BE.dto.EducationDTO;
import com.example.portfolio_BE.model.Education;
import com.example.portfolio_BE.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/education")
public class EducationController {

    @Autowired
    private EducationService educationService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEducation() {
        List<Education> educationList = educationService.getAllEducationDetails();

        List<EducationDTO> educationDTOs = educationList.stream()
                .map(EducationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "statusCode", 200,
                "msg", "Education records fetched successfully",
                "response", educationDTOs
        ));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEducationById(@PathVariable UUID id) {
        try {
            Optional<Education> currentEducation = educationService.getEducationById(id);

            if (currentEducation.isPresent()) {
                Education education = currentEducation.get();
                return ResponseEntity.ok(Map.of(
                        "statusCode", 200,
                        "msg", "Education record fetched successfully",
                        "response", new EducationDTO(education) // Use DTO
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "statusCode", 404,
                        "msg", "Education record not found",
                        "response", null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error: " + e.getMessage(),
                    "response", null
            ));
        }
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createEducation(
            @RequestParam String degree,
            @RequestParam String institution,
            @RequestParam String year,
            @RequestParam MultipartFile image) {
        try {
            Education education = new Education();
            education.setDegree(degree);
            education.setInstitution(institution);
            education.setYear(year);


            Education savedEducation = educationService.saveEducation(education, image);

            return ResponseEntity.status(201).body(Map.of(
                    "statusCode", 201,
                    "msg", "Education saved successfully",
                    "response", savedEducation
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Error saving education",
                    "response", null
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEducation(
            @PathVariable UUID id,
            @RequestParam String degree,
            @RequestParam String institution,
            @RequestParam String year,
            @RequestParam(required = false) MultipartFile image) {
        try {
            Education education = new Education();
            education.setDegree(degree);
            education.setInstitution(institution);
            education.setYear(year);

            // Ensure to pass both Education and MultipartFile image to saveEducation method
            Education updatedEducation = educationService.updateEducation(id, education, image);  // Corrected method call

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Education updated successfully",
                    "response", updatedEducation
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Error updating education",
                    "response", null
            ));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getUserCount() {
        int count = educationService.getEducationCount();  // Call service method
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEducation(@PathVariable UUID id) {
        try {
            educationService.deleteEducation(id);

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Education record deleted successfully",
                    "response", Collections.emptyMap() // Return an empty map instead of null
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error while deleting education record",
                    "response", Collections.emptyMap() // Return an empty map instead of null
            ));
        }
    }
}
