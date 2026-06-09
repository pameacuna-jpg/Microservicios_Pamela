package com.vetnova.inventario.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vetnova.inventario.model.MovimientoInventario;
import com.vetnova.inventario.model.Producto;
import com.vetnova.inventario.repository.MovimientoInventarioRepository;
import com.vetnova.inventario.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoInventarioService {

    private static final Logger logger =
            LoggerFactory.getLogger(MovimientoInventarioService.class);

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

        logger.info("Registrando movimiento de inventario. Producto ID: {}, Tipo: {}, Cantidad: {}",
                movimiento.getIdProducto(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidad());

        Producto producto = productoRepository.findById(movimiento.getIdProducto())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con ID: " + movimiento.getIdProducto()));

        String tipo = movimiento.getTipoMovimiento().toUpperCase();

        switch (tipo) {
            case "ENTRADA":
                producto.setStockActual(producto.getStockActual() + movimiento.getCantidad());
                break;

            case "SALIDA":
                if (producto.getStockActual() < movimiento.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para realizar la salida");
                }
                producto.setStockActual(producto.getStockActual() - movimiento.getCantidad());
                break;

            case "AJUSTE":
                producto.setStockActual(movimiento.getCantidad());
                break;

            default:
                throw new RuntimeException("Tipo de movimiento inválido");
        }

        productoRepository.save(producto);

        logger.info("Stock actualizado para producto {}. Stock actual: {}",
                producto.getNombre(),
                producto.getStockActual());

        if (producto.getStockActual() <= producto.getStockMinimo()) {

            logger.warn("Evento StockBajo generado. Producto: {}, Stock actual: {}, Stock mínimo: {}, Sucursal ID: {}",
                    producto.getNombre(),
                    producto.getStockActual(),
                    producto.getStockMinimo(),
                    movimiento.getIdSucursal());

            /*
             * Aquí NO llamamos directamente a ms-notificaciones.
             * En arquitectura desacoplada, ms-inventario debe publicar un evento StockBajo
             * y ms-notificaciones debe consumirlo.
             *
             * Evento futuro:
             * {
             *   "eventType": "StockBajo",
             *   "source": "ms-inventario",
             *   "productoId": producto.getIdProducto(),
             *   "nombreProducto": producto.getNombre(),
             *   "stockActual": producto.getStockActual(),
             *   "stockMinimo": producto.getStockMinimo(),
             *   "sucursalId": movimiento.getIdSucursal()
             * }
             */
        }

        return movimientoRepository.save(movimiento);
    }

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