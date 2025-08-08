package com.example.demo.repository;

import com.example.demo.entity.project;
import com.example.demo.entity.project;
import com.example.demo.entity.worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface project_repository extends JpaRepository<project, Integer> {

    @Query("SELECT p FROM project p JOIN p.workers w JOIN w.user u WHERE u.userUsername = :user_username")
    List<project> findByWorkersContaining(String user_username);

    Optional<project> findByName(String name);





}
