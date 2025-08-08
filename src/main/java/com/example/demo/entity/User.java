package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "app_user")  // veya başka bir isim
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(unique = true)
    private String userUsername;

    private String user_password;



    public User() {}
    public User(Integer user_id, String userUsername, String user_password) {
        this.user_id = user_id;
        this.userUsername = userUsername;
        this.user_password = user_password;

    }

    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<role> roles;

    @OneToOne

    @JoinColumn(name = "worker_id")
    @JsonIgnore
    private worker worker;

    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public String getUser_username() {
        return userUsername;
    }
    public void setUser_username(String user_username) {
        this.userUsername = user_username;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public Set<role> getRoles() {
        return roles;
    }
    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }
    public worker getWorker() {
        return worker;
    }
    public void setWorker(worker worker) {
        this.worker = worker;
    }



}
