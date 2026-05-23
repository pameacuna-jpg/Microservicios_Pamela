package com.vetnova.atencionclinica.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Apunta correctamente al puerto 8085 del Microservicio de Mascotas
@FeignClient(name = "mascota-service", url = "http://localhost:8085/api/v1/mascotas")
public interface MascotaClient {

    // Este es el método exacto que tu FichaClinicaService está buscando
    @GetMapping("/validar/{id}")
    boolean validarMascota(@PathVariable("id") Long id);
}