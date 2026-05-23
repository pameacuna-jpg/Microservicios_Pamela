package com.vetnova.autenticacion.controller;

import com.vetnova.autenticacion.dto.LoginRequest;
import com.vetnova.autenticacion.dto.LoginResponse;
import com.vetnova.autenticacion.model.Credencial;
import com.vetnova.autenticacion.service.CredencialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private CredencialService credencialService;

    @GetMapping("/credenciales")
    public List<Credencial> listarCredenciales() {
        return credencialService.listarCredenciales();
    }

    @PostMapping("/registro")
    public Credencial crearCredencial(@Valid @RequestBody Credencial credencial) {
        return credencialService.crearCredencial(credencial);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Credencial credencial = credencialService.validarLogin(
                loginRequest.getUsername(),
                loginRequest.getPasswordHash()
        );

        String tokenSimulado = "JWT-DEMO-" + credencial.getIdUsuario();

        return new LoginResponse(
                "Login exitoso",
                credencial.getIdUsuario(),
                tokenSimulado
        );
    }

    @DeleteMapping("/credenciales/{id}")
    public String desactivarCredencial(@PathVariable Long id) {
        credencialService.desactivarCredencial(id);
        return "Credencial desactivada correctamente";
    }
}
