package com.example.demo.repository;
import com.example.demo.repository.UserRepository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Kullanıcı adından kullanıcıyı bulmak için method
    Optional<User> findByUserUsername(String userUsername);

}
