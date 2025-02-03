package com.example.portfolio_BE.dto;

import com.example.portfolio_BE.model.Project;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectDTO {
    private UUID id;
    private String title;
    private String description;
    private String description1;
    private String heading;
    private List<String> images;  // Base64 encoded images
    private String description2;


    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.description1 = project.getDescription1();
        this.heading = project.getHeading();
        this.description2 = project.getDescription2();


        this.images = project.getImages() != null ? project.getImages().stream()
                .map(img -> Base64.getEncoder().encodeToString(img))
                .collect(Collectors.toList()) : null;
    }


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDescription1() { return description1; }
    public void setDescription1(String description1) { this.description1 = description1; }

    public String getHeading() { return heading; }
    public void setHeading(String heading) { this.heading = heading; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getDescription2() { return description2; }
    public void setDescription2(String description2) { this.description2 = description2; }
}
