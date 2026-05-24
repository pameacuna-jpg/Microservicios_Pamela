package com.vetnova.inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequest {

    private String destinatario;
    private String mensaje;
    private String tipo;
    private String canal;
    private String prioridad;
}