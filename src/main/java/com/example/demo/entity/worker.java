package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer worker_id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;




    @ManyToOne
    @JoinColumn(name="project_id")//foreign key
    @JsonBackReference //sonsuzluğu önlüyor
    //Yani, her çalışan nesnesinin içinde ona bağlı olan proje nesnesi tutuluyor.
    private project project;


    @OneToOne(mappedBy = "worker")
    private User user;

    public worker(){};
    public worker(int worker_id, String name, int age, String gender, project project) {
        this.worker_id=worker_id;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.project=project;

    };

    public int getId(){
        return worker_id;
    };
    public void setId(int id){
        this.worker_id=id;
    };
    public String getName(){
        return name;
    };
    public void setName(String name){
        this.name=name;
    };
    public int getAge(){
        return age;
    };
    public void setAge(int age){
        this.age=age;
    };
    public String getGender(){
        return gender;
    };
    public void setGender(String gender){
        this.gender=gender;
    };
    public project getProject(){
        return project;
    }
    public void setProject(project project){
        this.project=project;
    }

}
