package com.example.portfolio_BE.controller;

import com.example.portfolio_BE.dto.SkillDTO;
import com.example.portfolio_BE.model.Skill;
import com.example.portfolio_BE.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();

        List<SkillDTO> skillDTOs = skills.stream()
                .map(SkillDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "statusCode", 200,
                "msg", "Skills fetched successfully",
                "response", skillDTOs
        ));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSkillById(@PathVariable UUID id) {
        try {
            Optional<Skill> currentSkill = skillService.getSkillById(id);

            if (currentSkill.isPresent()) {
                Skill skill = currentSkill.get();
                return ResponseEntity.ok(Map.of(
                        "statusCode", 200,
                        "msg", "Skill fetched successfully",
                        "response", new SkillDTO(skill)  // Return DTO
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "statusCode", 404,
                        "msg", "Skill not found",
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

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getUserCount() {
        int count = skillService.getSkillCount();  // Call service method
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createSkill(
            @RequestParam String name,
            @RequestParam MultipartFile image) {

        try {
            byte[] imageBytes = image.getBytes();

            Skill skill = new Skill();
            skill.setName(name);
            skill.setLogo(imageBytes);

            Skill savedSkill = skillService.saveSkill(skill);

            return ResponseEntity.status(201).body(Map.of(
                    "statusCode", 201,
                    "msg", "Skill created successfully",
                    "response", new SkillDTO(savedSkill)  // Return DTO
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Error saving skill",
                    "response", null
            ));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSkill(
            @PathVariable UUID id,
            @RequestParam String name,
            @RequestParam(required = false) MultipartFile image) {

        try {
            Skill existingSkill = skillService.getSkillById(id)
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            existingSkill.setName(name);

            if (image != null) {
                existingSkill.setLogo(image.getBytes());
            }

            Skill updatedSkill = skillService.saveSkill(existingSkill);

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Skill updated successfully",
                    "response", new SkillDTO(updatedSkill)  // Return DTO
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "statusCode", 404,
                    "msg", e.getMessage(),
                    "response", null
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Error updating skill",
                    "response", null
            ));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSkill(@PathVariable UUID id) {
        try {
            skillService.deleteSkill(id);

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Skill deleted successfully",
                    "response", Collections.emptyMap()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error while deleting skill",
                    "response", Collections.emptyMap()
            ));
        }
    }
}
