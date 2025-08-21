package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.worker;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.worker_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/worker")
public class worker_controller {

    @Autowired
    private worker_service worker_service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public Page<worker> getAllWorkers(
            @RequestParam(defaultValue = "0") int page, // URL'den sayfa numarasını alır, varsayılanı 0
            @RequestParam(defaultValue = "5") int size // URL'den sayfa boyutunu alır, varsayılanı 10
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return worker_service.allGetWorkers(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<worker> getWorkerById(@PathVariable int id) {
        Optional<worker> worker = worker_service.getIdworker(id);
        return worker.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<worker> createWorker(@RequestBody worker worker) {
        worker created = worker_service.createWorker(worker);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<worker> updateWorker(@PathVariable int id, @RequestBody worker worker) {
        Optional<worker> updated_worker = worker_service.updateWorker(id, worker);
        return updated_worker.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable int id) {
        boolean isDeleted = worker_service.deleteWorker(id);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{workerId}/project/{projectId}")
    public ResponseEntity<String> assignProjectToWorker(@PathVariable int workerId, @PathVariable int projectId) {
        worker_service.assignProjectToWorker(workerId, projectId);
        return ResponseEntity.ok("Proje çalışana başarıyla atandı.");
    }
    @GetMapping("/all")
    public List<worker> getAllWorkersNonPaginated() {
        return worker_service.getAllWorkersNonPaginated();
    }

}
