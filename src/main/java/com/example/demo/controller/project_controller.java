package com.example.demo.controller;


import com.example.demo.entity.project;
import com.example.demo.service.project_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<project> getAllProject(){
        return project_service.getAllProjects();
    }

    //Belirli projeyi getir(id)
    @GetMapping("/{id}")
    public ResponseEntity<project> getProjectById(@PathVariable int id){
        Optional<project> project = project_service.getProjectById(id);
        return project
                .map(ResponseEntity::ok) // varsa 200 OK
                .orElse(ResponseEntity.notFound().build()); // yoksa 404 Not Found

    }

    //Yeni proje ekle
    @PostMapping("/create")
    public ResponseEntity<project> createProject(@RequestBody project project){
        project_service.addProject(project);
        return ResponseEntity.ok(project);
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




}