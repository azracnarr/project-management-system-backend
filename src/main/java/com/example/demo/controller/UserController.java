// src/main/java/com/example/demo/controller/user_controller.java

package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable int userId, @PathVariable int roleId) {
        // try-catch bloğuna gerek yok.
        // Hata durumunda fırlatılacak istisna GlobalExceptionHandler tarafından yakalanacak.
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Rol başarıyla atandı.");
    }
}