package com.vetnova.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Se conecta al microservicio de Usuarios de Gabo (asumimos puerto 8082)
@FeignClient(name = "usuario-service", url = "http://localhost:8082/api/v1/usuarios")
public interface UsuarioClient {

    @GetMapping("/validar/{email}")
    boolean validarUsuarioExiste(@PathVariable("email") String email);
}