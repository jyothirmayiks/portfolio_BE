package com.example.portfolio_BE.dto;

import com.example.portfolio_BE.model.Skill;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class SkillDTO {
    private UUID id;
    private String name;
    private String image;  // Base64 encoded image

    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.image = skill.getBase64Image();  // Get the Base64 encoded image
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
