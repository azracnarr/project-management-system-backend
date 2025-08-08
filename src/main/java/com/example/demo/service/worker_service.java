package com.example.demo.service;

import com.example.demo.entity.project;
import com.example.demo.entity.role;
import com.example.demo.entity.worker;
import com.example.demo.repository.project_repository;
import com.example.demo.repository.role_repository;
import com.example.demo.repository.worker_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class worker_service {

    @Autowired
    private worker_repository worker_repository;
    @Autowired
    private project_repository project_repository;
    @Autowired
    private role_repository role_repository;





    //Tüm çalışanları getir
    public List<worker> allGetWorkers(){
        return worker_repository.findAll();
    }

    //İstenilen çalışanı getir (id)
    public Optional<worker> getIdworker(int id){
        return worker_repository.findById(id);
    }

    //Yeni çalışan ekle
    public worker createWorker(worker worker){
        if (worker_repository.existsByWorkerEmail(worker.getWorker_email())) {
            throw new RuntimeException("Bu e-mail zaten kayıtlı!");
        }
        return worker_repository.save(worker);
    }

    //Var olan çalışanı güncelle
    public Optional<worker> updateWorker(int id,worker worker){
        return worker_repository.findById(id).map(existingWorker->{
             existingWorker.setName(worker.getName());
             existingWorker.setAge(worker.getAge());
             existingWorker.setGender(worker.getGender());
             existingWorker.setWorker_email(worker.getWorker_email());
             return worker_repository.save(existingWorker);
         });

    }

    //Çalışanı sil
    public boolean deleteWorker(int id) {
        // ID'ye sahip bir çalışanın var olup olmadığını kontrol eder.
        if (worker_repository.existsById(id)) {
            // Eğer varsa, deleteById metodu ile siler.
            worker_repository.deleteById(id);
            return true;
        }
        // Eğer yoksa, false döner.
        return false;
    }

    //Çalışana proje atamak
    public void assignToWorker(int worker_id, int project_id){
        worker worker=worker_repository.findById(worker_id)
                .orElseThrow(()->new RuntimeException("Çalışan bulunamadı"));
        project project= project_repository.findById(project_id)
                .orElseThrow(()->new RuntimeException("proje bulunamadı"));

        worker.setProject(project);
        // Java tarafında iki yönlü ilişkiyi güncelle
        project.getWorkers().add(worker);
        worker_repository.save(worker);

    }


    // Çalışana rol ataması yapmak
    public void assignToRole(int worker_id, int role_id) {
        worker worker = worker_repository.findById(worker_id)
                .orElseThrow(() -> new RuntimeException("worker not found"));

        role role = role_repository.findById(role_id)  // role_repository kullanmalısın
                .orElseThrow(() -> new RuntimeException("role not found"));


        worker_repository.save(worker);
    }






}



