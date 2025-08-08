// src/main/java/com/example/demo/controller/GlobalExceptionHandler.java
package com.example.demo.controller;

import com.example.demo.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.exception.UnauthorizedException; // Bu satırı ekleyin

@ControllerAdvice
public class GlobalExceptionHandler {

    // İş kuralı hataları için: 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Kaynak bulunamadı hataları için: 404 Not Found
    // Bu, örneğin bir ID ile arama yapıldığında projenin bulunamaması durumunda kullanılabilir.
    @ExceptionHandler(EntityNotFoundException.class) // Varsayılan JPA istisnası olabilir
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Yetkilendirme hataları için: 403 Forbidden
    // Özel bir yetki kontrolü istisnası tanımladığınızda kullanışlıdır.
    @ExceptionHandler(UnauthorizedException.class) // Kendi özel istisnanız
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // İsteğe bağlı: Beklenmeyen diğer tüm hatalar için 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Beklenmeyen bir sunucu hatası oluştu.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}