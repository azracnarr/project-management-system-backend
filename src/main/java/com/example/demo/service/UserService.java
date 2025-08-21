

package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.role_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private role_repository roleRepository;

    // Çalışana rol atayan metot
    public void assignRoleToUser(int userId, int roleId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        role foundRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı"));

        foundUser.getRoles().add(foundRole);
        userRepository.save(foundUser);
    }
}