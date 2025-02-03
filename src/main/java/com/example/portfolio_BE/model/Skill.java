package com.example.portfolio_BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Skill {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Lob
    @JsonIgnore
    private byte[] logo;

    // Getter and setter methods

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getBase64Image() {
        return logo != null ? Base64.getEncoder().encodeToString(logo) : null;
    }
}
