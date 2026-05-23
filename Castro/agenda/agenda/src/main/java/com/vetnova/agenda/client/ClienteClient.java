package com.vetnova.agenda.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Apunta al microservicio de Clientes de su compañero (Puerto 8084)
@FeignClient(name = "cliente-service", url = "http://localhost:8084/api/v1/clientes")
public interface ClienteClient {
    @GetMapping("/validar/{id}")
    boolean validarClienteExiste(@PathVariable("id") Long id);
}