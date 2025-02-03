package com.example.portfolio_BE.controller;

import com.example.portfolio_BE.dto.ProjectDTO;
import com.example.portfolio_BE.model.Project;
import com.example.portfolio_BE.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;



    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();


        List<ProjectDTO> projectDTOs = projects.stream()
                .map(ProjectDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "statusCode", 200,
                "msg", "Projects fetched successfully",
                "response", projectDTOs
        ));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProjectById(@PathVariable UUID id) {
        try {

            Optional<Project> currentProject = projectService.getProjectById(id);

            if (currentProject.isPresent()) {
                Project project = currentProject.get();
                return ResponseEntity.ok(Map.of(
                        "statusCode", 200,
                        "msg", "Project fetched successfully",
                        "response", new ProjectDTO(project)  // Use DTO
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "statusCode", 404,
                        "msg", "Project not found",
                        "response", null
                ));
            }
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error: " + e.getMessage(),  // Log the exception message
                    "response", null
            ));
        }
    }



    @PostMapping
    public ResponseEntity<Map<String, Object>> createProject(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String description1,
            @RequestParam String heading,
            @RequestParam List<MultipartFile> images,
            @RequestParam String description2) {

        try {
            List<byte[]> imageBytes = new ArrayList<>();
            for (MultipartFile file : images) {
                imageBytes.add(file.getBytes());
            }

            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setDescription1(description1);
            project.setHeading(heading);
            project.setImages(imageBytes);
            project.setDescription2(description2);

            Project savedProject = projectService.saveProject(project);

            return ResponseEntity.status(201).body(Map.of(
                    "statusCode", 201,
                    "msg", "Project created successfully",
                    "response", new ProjectDTO(savedProject)  // Return DTO
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Error saving project",
                    "response", null
            ));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProject(
            @PathVariable UUID id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String description1,
            @RequestParam String heading,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam String description2) {

        try {
            Project existingProject = projectService.getProjectById(id)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            existingProject.setTitle(title);
            existingProject.setDescription(description);
            existingProject.setDescription1(description1);
            existingProject.setHeading(heading);
            existingProject.setDescription2(description2);


            if (images != null && !images.isEmpty()) {
                List<byte[]> imageBytes = new ArrayList<>();
                for (MultipartFile file : images) {
                    imageBytes.add(file.getBytes());
                }
                existingProject.setImages(imageBytes);
            }

            Project updatedProject = projectService.saveProject(existingProject);

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Project updated successfully",
                    "response", new ProjectDTO(updatedProject)  // Return DTO
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
                    "msg", "Error updating project",
                    "response", null
            ));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProject(@PathVariable UUID id) {
        try {
            projectService.deleteProject(id);

            return ResponseEntity.ok(Map.of(
                    "statusCode", 200,
                    "msg", "Project deleted successfully",
                    "response", Collections.emptyMap()  // Avoid using null, use an empty map instead
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "statusCode", 500,
                    "msg", "Internal server error while deleting project",
                    "response", Collections.emptyMap()  // Avoid using null, use an empty map instead
            ));
        }
    }

}
