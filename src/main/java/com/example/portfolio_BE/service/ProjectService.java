package com.example.portfolio_BE.service;

import com.example.portfolio_BE.model.Project;
import com.example.portfolio_BE.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(UUID id, Project updatedProject) {
        return projectRepository.findById(id).map(existingProject -> {
            existingProject.setTitle(updatedProject.getTitle());
            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setDescription1(updatedProject.getDescription1());
            existingProject.setHeading(updatedProject.getHeading());
            existingProject.setImages(updatedProject.getImages());
            existingProject.setDescription2(updatedProject.getDescription2());
            return projectRepository.save(existingProject);
        }).orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public int getProjectCount() {
        return (int) projectRepository.count();
    }

    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }
}
