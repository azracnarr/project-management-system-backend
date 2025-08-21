package com.example.demo.controller;

import com.example.demo.dto.ProjectCreateRequest;
import com.example.demo.entity.worker;
import com.example.demo.service.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.demo.entity.project;
import com.example.demo.service.project_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/project")
public class project_controller {
    @Autowired
    private project_service project_service;

    //Tüm projeleri listele
    @GetMapping("/list")
    public Page<project> getAllProjects(@PageableDefault(size = 5) Pageable pageable) {

        return project_service.getAllProjects(pageable);
    }

    //Belirli projeyi getir(id)
    @GetMapping("/{id}")
    public ResponseEntity<project> getProjectById(@PathVariable int id){
        Optional<project> project = project_service.getProjectById(id);
        return project
                .map(ResponseEntity::ok) // varsa 200 OK
                .orElse(ResponseEntity.notFound().build()); // yoksa 404 Not Found

    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject( @RequestBody ProjectCreateRequest request) {
        project newProject = new project();
        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());
        newProject.setProject_status(request.getProject_status()); // DTO'dan gelen veriyi entity'ye ata

        // Projeyi kaydetmek için ProjectService'i çağırıyoruz.
        // Servis katmanı hala bir Project entity beklediği için bu şekilde gönderiyoruz.
        project createdProject = project_service.createProject(newProject);

        // Başarılı yanıt olarak oluşturulan Project entity'sini döndürüyoruz.
        return ResponseEntity.ok(createdProject);
    }

    //Var olan projeyi güncelle
    @PutMapping("/update/{id}")
    public ResponseEntity<project> updateProject( @PathVariable int id,@RequestBody project projects){
        Optional<project> updatedProject  = project_service.updateProject(id,projects);
        return updatedProject
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    //Projeyi sil
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id){
        boolean deleted=project_service.deleteProject(id);
        if(deleted){
            return ResponseEntity.ok().build();

        }else{
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("/my_project")
    public ResponseEntity<List<project>> getUserProjects(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // CustomUserDetails nesnesini direkt kullanabilirsiniz.
        String username = userDetails.getUsername();
        List<project> projects = project_service.getProjectsByUsername(username);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/all")
    public List<project> getAllProjectsNonPaginated() {
        return project_service.getAllProjectsNonPaginated();
    }


}