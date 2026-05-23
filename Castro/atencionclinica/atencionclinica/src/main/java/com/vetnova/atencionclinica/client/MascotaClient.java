package com.vetnova.atencionclinica.client;

import com.vetnova.atencionclinica.dto.MascotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Asumimos que Mascotas correrá en el puerto 8082
@FeignClient(name = "mascotas-service", url = "http://localhost:8082/api/v1/mascotas")
public interface MascotaClient {

    @GetMapping("/{id}")
    MascotaDTO obtenerMascotaPorId(@PathVariable("id") Long id);
}