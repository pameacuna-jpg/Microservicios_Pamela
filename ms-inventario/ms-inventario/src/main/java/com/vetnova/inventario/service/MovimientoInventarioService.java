package com.vetnova.inventario.service;

import com.vetnova.inventario.dto.NotificacionRequest;
import org.springframework.web.client.RestTemplate;
import com.vetnova.inventario.model.MovimientoInventario;
import com.vetnova.inventario.model.Producto;
import com.vetnova.inventario.repository.MovimientoInventarioRepository;
import com.vetnova.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<MovimientoInventario> listarMovimientos() {

        return movimientoRepository.findAll();
    }

    public MovimientoInventario buscarPorId(Long id) {

        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
    }

    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {

        Producto producto = productoRepository.findById(movimiento.getIdProducto())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + movimiento.getIdProducto()));

        String tipo = movimiento.getTipoMovimiento().toUpperCase();

        switch (tipo) {

            case "ENTRADA":

                producto.setStockActual(
                        producto.getStockActual() + movimiento.getCantidad()
                );

                break;

            case "SALIDA":

                if (producto.getStockActual() < movimiento.getCantidad()) {

                    throw new RuntimeException("Stock insuficiente para realizar la salida");
                }

                producto.setStockActual(
                        producto.getStockActual() - movimiento.getCantidad()
                );

                break;

            case "AJUSTE":

                producto.setStockActual(movimiento.getCantidad());

                break;

            default:

                throw new RuntimeException("Tipo de movimiento inválido");
        }

        productoRepository.save(producto);

        if (producto.getStockActual() <= producto.getStockMinimo()) {

            NotificacionRequest notificacion =
                    new NotificacionRequest(
                            "inventario@vetnova.cl",
                            "ALERTA: El producto " + producto.getNombre()
                            + " posee stock bajo.",
                            "STOCK_BAJO",
                            "EMAIL",
                             "ALTA"
                    );
               restTemplate.postForObject(
                             "http://localhost:8089/api/v1/notificaciones",
                             notificacion,
                             Object.class
                   );
        }           

        return movimientoRepository.save(movimiento);
    }
    @Autowired
    private RestTemplate restTemplate;   

    public List<MovimientoInventario> buscarPorProducto(Long idProducto) {

        return movimientoRepository.findByIdProducto(idProducto);
    }

    public List<MovimientoInventario> buscarPorSucursal(Long idSucursal) {

        return movimientoRepository.findByIdSucursal(idSucursal);
    }

    public List<MovimientoInventario> buscarPorTipo(String tipo) {

        return movimientoRepository.findByTipoMovimientoIgnoreCase(tipo);
    }
}