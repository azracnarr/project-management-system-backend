package com.example.demo.service;
import com.example.demo.entity.project;
import com.example.demo.entity.worker;
import com.example.demo.repository.project_repository;
import com.example.demo.repository.worker_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class project_service {
    @Autowired
    private project_repository projectRepository;

    @Autowired
    private worker_repository workerRepository;

    //Tüm projeleri listele
    public List<project> getAllProjects() {
        return projectRepository.findAll();
    };

    //İstenilen projeyi getir (id)
    public Optional<project> getProjectById(int id) {
        return projectRepository.findById(id);
    };



    // Yeni proje ekle - Unique kontrolü yapıldı
    public project addProject(project project) {
        // Veritabanında aynı isimde bir proje var mı diye kontrol et
        Optional<project> existingProject = projectRepository.findByName(project.getName());

        if (existingProject.isPresent()) {
            // Eğer varsa, bir hata (exception) fırlat
            throw new IllegalArgumentException("Bu isimde bir proje zaten mevcut.");
        }

        // Eğer aynı isimde proje yoksa, kaydet ve geri dön
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

    public List<project> getProjectsByUsername(String username) {
        // workerRepository'den Optional<worker> döndürmesi bekleniyor
        Optional<worker> optionalWorker = workerRepository.findWorkerByUsername(username);
                return projectRepository.findByWorkersContaining(username);
    }

}
