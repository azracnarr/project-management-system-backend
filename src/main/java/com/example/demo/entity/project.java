package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

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


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)  //// worker sınıfındaki "project" alanına bağlanır
    @JsonManagedReference //sonsuzluğu önlüyor
    private List<worker> workers;


    public project(){};
    public project(int project_id, String name, String description, String project_status, List<worker> workers) {
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
    public List<worker> getWorkers(){
        return workers;
    }
    public void setWorkers(List<worker> workers){
        this.workers = workers;
    }
    public String getProject_status(){
        return project_status;
    }
    public void setProject_status(String project_status){
        this.project_status = project_status;
    }

}
