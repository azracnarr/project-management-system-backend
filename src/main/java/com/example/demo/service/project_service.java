package com.example.demo.service;
import com.example.demo.entity.project;
import com.example.demo.repository.project_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class project_service {
    @Autowired
    private project_repository projectRepository;

    //Tüm projeleri listele
    public List<project> getAllProjects() {
        return projectRepository.findAll();
    };

    //İstenilen projeyi getir (id)
    public Optional<project> getProjectById(int id) {
        return projectRepository.findById(id);
    };

    //Yeni proje ekle
    public project addProject(project project) {
        return projectRepository.save(project);
    }


    //Var olan projeyi güncelle
    public Optional<project> updateProject(int id ,project project) {
        return projectRepository.findById(id).map(existingProject -> {
            // Bu blok, sadece proje varsa çalışır.
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setProject_status(project.getProject_status());
            return projectRepository.save(existingProject);

        });
    }

    //Projeyi sil
    public boolean deleteProject(int id) {
        return projectRepository.findById(id).map(project->{
            projectRepository.delete(project);
            return true;

        }).orElse(false);
    }


}
