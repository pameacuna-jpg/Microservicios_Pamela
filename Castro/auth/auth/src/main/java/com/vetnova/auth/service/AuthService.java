package com.vetnova.auth.service;

import com.vetnova.auth.client.UsuarioClient;
import com.vetnova.auth.dto.LoginRequest;
import com.vetnova.auth.security.JwtUtil;
// Importante: Asegúrate de tener estas excepciones creadas en tu paquete exception
import com.vetnova.auth.exception.ResourceNotFoundException; 
import com.vetnova.auth.exception.InvalidCredentialsException; 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j // Logs estructurados: fundamental para auditoría en microservicios
@Service
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private JwtUtil jwtUtil;

    public String procesarLogin(LoginRequest request) {
        log.info("Iniciando proceso de autenticación para el usuario: {}", request.getEmail());

        // 1. Integración real con el Microservicio de Usuarios vía Feign
        // Esto verifica en la base de datos de usuarios si la cuenta existe
        boolean existe = usuarioClient.validarUsuarioExiste(request.getEmail());
        
        if (!existe) {
            log.error("Fallo de autenticación: El usuario {} no existe.", request.getEmail());
            throw new ResourceNotFoundException("Usuario no encontrado en el sistema");
        }

        // 2. Validación de credenciales
        // NOTA: En producción, aquí deberías usar BCryptPasswordEncoder para comparar el hash
        if (!request.getPassword().equals("password123")) { 
            log.warn("Fallo de autenticación: Contraseña incorrecta para {}", request.getEmail());
            throw new InvalidCredentialsException("Credenciales inválidas, intente nuevamente");
        }

        // 3. Generación de Token JWT
        String tokenJwt = jwtUtil.generateToken(request.getEmail());
        
        log.info("Autenticación exitosa. Token generado para el usuario: {}", request.getEmail());

        return tokenJwt;
    }
}