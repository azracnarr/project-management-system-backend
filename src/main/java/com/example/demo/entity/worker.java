package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity

public class worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer worker_id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true)
    private String worker_email;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @ManyToMany(mappedBy = "workers", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<project> projects = new HashSet<>();

    @OneToOne(mappedBy = "worker", cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    public worker(){}

    // Getters & Setters
    public Integer getWorker_id() { return worker_id; }
    public void setWorker_id(Integer worker_id) { this.worker_id = worker_id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getWorker_email() { return worker_email; }
    public void setWorker_email(String worker_email) { this.worker_email = worker_email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Set<project> getProjects() { return projects; }
    public void setProjects(Set<project> projects) { this.projects = projects; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
