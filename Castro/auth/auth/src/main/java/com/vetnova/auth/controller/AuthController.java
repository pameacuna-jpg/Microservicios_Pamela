package com.vetnova.auth.controller;

import com.vetnova.auth.dto.LoginRequest;
import com.vetnova.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // La validación se hace sola por el @Valid.
        // Si todo está bien, el servicio nos devuelve el Token JWT.
        String token = authService.procesarLogin(request);
        
        // Armamos el JSON de respuesta exitosa
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Autenticación exitosa");
        response.put("token", token);

        return ResponseEntity.ok(response); // Devuelve 200 OK
    }
}