package com.example.demo.repository;

import com.example.demo.entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface worker_repository extends JpaRepository<worker, Integer> {

}
