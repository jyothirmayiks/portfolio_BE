package com.example.portfolio_BE.dto;

import com.example.portfolio_BE.model.Education;
import java.util.Base64;
import java.util.UUID;

public class EducationDTO {
    private UUID id;
    private String degree;
    private String institution;
    private String year;
    private String image; // Base64 encoded image

    // Constructor to map the Education entity to DTO
    public EducationDTO(Education education) {
        this.id = education.getId();
        this.degree = education.getDegree();
        this.institution = education.getInstitution();
        this.year = education.getYear();
        this.image = education.getImage() != null ? Base64.getEncoder().encodeToString(education.getImage()) : null;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
