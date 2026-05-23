package com.vetnova.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventario-service", url = "http://localhost:8087/api/v1/inventario")
public interface InventarioClient {
    @GetMapping("/validar-stock/{idProducto}/{cantidad}")
    boolean validarStock(@PathVariable("idProducto") Long idProducto, @PathVariable("cantidad") Integer cantidad);
}