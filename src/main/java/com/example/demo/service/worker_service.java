package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.project;
import com.example.demo.entity.worker;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.project_repository;
import com.example.demo.repository.worker_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class worker_service {

    @Autowired
    private worker_repository worker_repository;

    @Autowired
    private project_repository project_repository;

    @Autowired
    private UserRepository userRepository;

    public Page<worker> allGetWorkers(Pageable pageable) {
        // Repository'nin findAll metodu zaten Pageable nesnesini kabul eder
        // ve Page<worker> nesnesi döndürür.
        return worker_repository.findAll(pageable);
    }

    public Optional<worker> getIdworker(int id) {
        return worker_repository.findById(id);
    }
    @Transactional
    public worker createWorker(worker worker) {
        if (worker_repository.existsByWorker_email(worker.getWorker_email())) {
            throw new RuntimeException("Bu e-posta adresi zaten kullanılıyor!");
        }
        return worker_repository.save(worker);
    }

    public Optional<worker> updateWorker(int id, worker updatedWorker) {
        return worker_repository.findById(id).map(existingWorker -> {
            if (updatedWorker.getWorker_email() != null &&
                    !existingWorker.getWorker_email().equals(updatedWorker.getWorker_email())) {
                if (worker_repository.existsByWorker_email(updatedWorker.getWorker_email())) {
                    throw new RuntimeException("Bu e-posta adresi zaten kullanılıyor!");
                }
            }
            existingWorker.setName(updatedWorker.getName());
            existingWorker.setAge(updatedWorker.getAge());
            existingWorker.setGender(updatedWorker.getGender());
            existingWorker.setWorker_email(updatedWorker.getWorker_email());
            return worker_repository.save(existingWorker);
        });
    }

    public boolean deleteWorker(int id) {
        if (worker_repository.existsById(id)) {
            worker_repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void assignProjectToWorker(int workerId, int projectId) {
        worker foundWorker = worker_repository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı."));
        project foundProject = project_repository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı."));

        // ⚠️ Bu kontrol, sorunu çözecektir:
        // Eğer çalışan zaten projeye atanmışsa, işlem yapmadan geri dön.
        if (foundWorker.getProjects().contains(foundProject)) {
            // İsteğe bağlı: Frontend'e daha açıklayıcı bir hata mesajı göndermek için bir istisna fırlatılabilir.
            throw new IllegalArgumentException("Bu çalışan zaten bu projeye atanmış.");
        }

        foundWorker.getProjects().add(foundProject);
        // İlişkinin diğer tarafını da güncellemeyi unutmayın.
        // Ancak burada save işlemi sadece bir taraf için yapıldığından bu da bir sorun kaynağı olabilir.
        foundProject.getWorkers().add(foundWorker);

        // Yalnızca bir tarafı kaydetmeniz yeterli olmalı.
        // @Transactional etiketini kullanıyorsanız, Hibernate her iki tarafı da yönetecktir.
        worker_repository.save(foundWorker);
    }
    // YENİ: Sayfalama olmadan, tüm çalışanları getiren metot
    public List<worker> getAllWorkersNonPaginated() {
        return worker_repository.findAll();
    }
}
