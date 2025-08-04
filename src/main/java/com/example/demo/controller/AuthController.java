
package com.example.demo.controller;
import com.example.demo.config.JwtTokenProvider;
import com.example.demo.entity.LoginRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // sadece test için, güvenli değil
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("username", userDetails.getUsername());
            response.put("role", userDetails.getAuthorities());
            response.put("token", token);  // Eksik olan kısım

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı adı veya şifre yanlış");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        User newUser = new User();
        newUser.setUser_username(request.getUsername());

        // HASHLEME İŞLEMİ BURADA OLMALI
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setUser_password(hashedPassword);

        userRepository.save(newUser);

        return ResponseEntity.ok("Kullanıcı başarıyla oluşturuldu.");
    }
}
