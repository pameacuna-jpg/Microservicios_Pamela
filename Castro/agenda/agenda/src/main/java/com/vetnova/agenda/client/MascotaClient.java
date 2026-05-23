package com.vetnova.agenda.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Apunta al microservicio de Mascotas de su compañero (Puerto 8085)
@FeignClient(name = "mascota-service", url = "http://localhost:8085/api/v1/mascotas")
public interface MascotaClient {
    @GetMapping("/validar/{id}")
    boolean validarMascotaExiste(@PathVariable("id") Long id);
}