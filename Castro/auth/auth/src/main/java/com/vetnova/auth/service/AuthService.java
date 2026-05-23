package com.vetnova.auth.service;

import com.vetnova.auth.client.UsuarioClient;
import com.vetnova.auth.dto.LoginRequest;
import com.vetnova.auth.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j // Anotación obligatoria de la rúbrica para Logs Estructurados
@Service
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private JwtUtil jwtUtil; // Inyectamos nuestra fábrica de tokens reales

    public String procesarLogin(LoginRequest request) {
        log.info("Iniciando proceso de login para el usuario: {}", request.getEmail());

        // 1. SILENCIAMOS TEMPORALMENTE A FEIGN PARA PODER PROBAR EL TOKEN
        /* 
        boolean existe = usuarioClient.validarUsuarioExiste(request.getEmail());
        
        if (!existe) {
            log.warn("Intento de login fallido: El usuario {} no existe en la base de datos.", request.getEmail());
            throw new RuntimeException("Credenciales inválidas o usuario no encontrado");
        }
        */

        // 2. Validar contraseña (Simulación temporal, luego se usará BCryptPasswordEncoder)
        if (!request.getPassword().equals("password123")) { 
            log.warn("Contraseña incorrecta para el usuario: {}", request.getEmail());
            throw new RuntimeException("Credenciales inválidas");
        }

        // 3. Generar JWT (Token Stateless real con firma criptográfica)
        String tokenJwt = jwtUtil.generateToken(request.getEmail());
        log.info("Login exitoso. JWT generado para el usuario: {}", request.getEmail());

        return tokenJwt;
    }
}