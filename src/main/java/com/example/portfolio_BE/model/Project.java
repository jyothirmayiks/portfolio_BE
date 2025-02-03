package com.example.portfolio_BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(length = 1000)
    private String description;


    @Column(length = 1000)
    private String description1;

    private String heading;

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @JsonIgnore
    private List<byte[]> images;

    @Column(length = 5000)
    private String description2;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public List<String> getBase64Images() {
        return images != null ? images.stream()
                .map(image -> Base64.getEncoder().encodeToString(image))
                .collect(Collectors.toList()) : null;
    }
}
