package com.example.portfolio_BE.service;

import com.example.portfolio_BE.model.Skill;
import com.example.portfolio_BE.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }

    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(UUID id, Skill updatedSkill) {
        return skillRepository.findById(id).map(existingSkill -> {
            existingSkill.setName(updatedSkill.getName());
            existingSkill.setLogo(updatedSkill.getLogo());
            return skillRepository.save(existingSkill);
        }).orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
    }

    public void deleteSkill(UUID id) {
        skillRepository.deleteById(id);
    }
}
