package com.example.demo.repository;

import com.example.demo.entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface worker_repository extends JpaRepository<worker, Integer> {
    @Query("SELECT w FROM worker w WHERE w.user.userUsername = :username")
    Optional<worker> findWorkerByUsername(@Param("username") String username);
    boolean existsByWorkerEmail(String worker_email);

}
