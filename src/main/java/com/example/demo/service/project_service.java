package com.example.demo.service;
import com.example.demo.entity.project;
import com.example.demo.entity.worker;
import com.example.demo.repository.project_repository;
import com.example.demo.repository.worker_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class project_service {
    @Autowired
    private project_repository projectRepository;

    @Autowired
    private worker_repository workerRepository;

    // Doğru: Repository'nin findAll metodu Pageable nesnesini kabul eder.
    public Page<project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    //İstenilen projeyi getir (id)
    public Optional<project> getProjectById(int id) {
        return projectRepository.findById(id);
    };



    @Transactional
    public project createProject(project project) {
        // İsteğe bağlı: Yeni bir proje kaydedilmeden önce proje adının benzersiz olup olmadığını kontrol edebilirsiniz.
        // Eğer ProjectRepository'de findByName diye bir metodunuz yoksa bu kontrol satırını kaldırabilirsiniz
        // veya ProjectRepository'ye eklemeniz gerekir.
        if (project.getName() != null && projectRepository.findByName(project.getName()).isPresent()) {
            throw new RuntimeException("Bu proje adı zaten kullanılıyor!");
        }

        // Project entity'sini veritabanına kaydediyoruz.
        // projectRepository.save() metodu, JPA/Hibernate tarafından sağlanır ve
        // Project objesini veritabanına ekler veya günceller (eğer ID'si varsa).
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

    // YENİ: Sayfalama olmayan, tüm projeleri getiren metot
    public List<project> getAllProjectsNonPaginated() {
        return projectRepository.findAll();
    }

}
