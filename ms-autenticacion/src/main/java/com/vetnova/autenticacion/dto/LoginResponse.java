package com.vetnova.autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String mensaje;
    private Long idUsuario;
    private String token;
}
