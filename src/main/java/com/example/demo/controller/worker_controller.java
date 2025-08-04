package com.example.demo.controller;

import com.example.demo.entity.project;
import com.example.demo.entity.worker;
import com.example.demo.service.worker_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/worker")

public class worker_controller {
    @Autowired
    private worker_service worker_service;

    //Tüm çalışanları listele
    @GetMapping("/list")
    public List<worker> getAllWorker(){
        return worker_service.allGetWorkers();
    }

    //Belirli çalışanı getir
    @GetMapping("/{id}")
    public ResponseEntity<worker> getWorkerById(@PathVariable int id){
        Optional<worker> worker = worker_service.getIdworker(id);
        return worker
                .map(ResponseEntity::ok) // varsa 200 OK
                .orElse(ResponseEntity.notFound().build()); // yoksa 404 Not Found

    }
    //Yeni çalışan ekle
    @PostMapping("/create")
    public ResponseEntity<worker> createWorker(@RequestBody worker worker){
        worker_service.createWorker(worker);
        return ResponseEntity.ok(worker);

    }
    //Var olan çalışanı güncelle
    @PutMapping("/update/{id}")
    public ResponseEntity<worker> updateWorker(@PathVariable int id,@RequestBody worker Worker){
        Optional<worker> updated_worker  = worker_service.updateWorker(id,Worker);
        return updated_worker
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
    //Çalışanı sil
    @DeleteMapping("/delete/{id}")
    public boolean deleteWorker(@PathVariable int id,worker worker){
        return worker_service.deleteWorker(id,worker);
    }


    //Çalışanlara proje ataması yap
    @PostMapping("/{worker_id}/project/{project_id}")
    public ResponseEntity<String> assignWorkerToProject(@PathVariable int worker_id, @PathVariable int project_id){
        worker_service.assignToWorker(worker_id,project_id);
        return ResponseEntity.ok("Assigned to worker");
    }





}
