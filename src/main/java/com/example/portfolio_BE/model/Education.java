package com.example.portfolio_BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Base64;
import java.util.UUID;

@Entity
public class Education {

    @Id
    @GeneratedValue
    private UUID id;

    private String degree;

    private String institution;

    private String year;

    @Lob
    @JsonIgnore
    private byte[] image;


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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getBase64Image() {
        return image != null ? Base64.getEncoder().encodeToString(image) : null;
    }
}
