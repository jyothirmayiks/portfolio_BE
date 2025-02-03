package com.example.portfolio_BE.service;

import com.example.portfolio_BE.model.Education;
import com.example.portfolio_BE.repository.EducationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EducationService {
    private final EducationRepository educationRepository;

    // Constructor to inject the EducationRepository
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    // Method to get all Education records
    public List<Education> getAllEducationDetails() {
        return educationRepository.findAll();
    }

    // Method to get Education by its ID
    public Optional<Education> getEducationById(UUID id) {
        return educationRepository.findById(id);
    }

    // Method to add new Education record with image
    public Education addEducation(Education education, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            education.setImage(file.getBytes()); // Set image as bytes
        }
        return educationRepository.save(education); // Save to repository
    }

    public Education saveEducation(Education education, MultipartFile file) throws IOException {
        return addEducation(education, file); // reusing the logic from addEducation
    }

    // Method to update existing Education record with image
    public Education updateEducation(UUID id, Education education, MultipartFile file) throws IOException {
        return educationRepository.findById(id).map(existingEducation -> {
            existingEducation.setDegree(education.getDegree());
            existingEducation.setInstitution(education.getInstitution());
            existingEducation.setYear(education.getYear());

            if (file != null && !file.isEmpty()) {
                try {
                    existingEducation.setImage(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return educationRepository.save(existingEducation); // Save updated record
        }).orElse(null);
    }

    // Method to delete Education by its ID
    public void deleteEducation(UUID id) {
        educationRepository.deleteById(id);
    }
}
