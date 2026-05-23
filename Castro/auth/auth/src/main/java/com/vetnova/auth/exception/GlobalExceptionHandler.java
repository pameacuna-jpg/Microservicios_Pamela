package com.vetnova.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Atrapa los errores del @Valid (ej. si el correo viene vacío)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejoErroresValidacion(MethodArgumentNotValidException ex) {
        log.warn("Error de validación en la petición de login");
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores); // Devuelve 400
    }

    // Atrapa los errores de credenciales inválidas que lanzamos en el AuthService
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejoErrorGeneral(RuntimeException ex) {
        log.error("Error en el proceso de autenticación: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error); // Devuelve 401
    }
}