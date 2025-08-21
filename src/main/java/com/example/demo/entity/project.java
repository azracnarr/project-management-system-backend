package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer project_id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String project_status;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_workers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    @JsonIgnore
    private Set<worker> workers = new HashSet<>();

    public project(){};
    public project(int project_id, String name, String description, String project_status, Set<worker> workers) {
        this.project_id=project_id;
        this.name=name;
        this.description=description;
        this.project_status=project_status;
        this.workers = workers;
    };

    public int getId(){
        return project_id;
    }
    public void setId(int project_id){
        this.project_id = project_id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Set<worker> getWorkers(){
        return workers;
    }
    public void setWorkers(Set<worker> workers){
        this.workers = workers;
    }
    public String getProject_status(){
        return project_status;
    }
    public void setProject_status(String project_status){
        this.project_status = project_status;
    }


}
