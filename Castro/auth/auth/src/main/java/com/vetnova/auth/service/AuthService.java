package com.vetnova.auth.service;

import com.vetnova.auth.client.UsuarioClient;
import com.vetnova.auth.dto.LoginRequest;
import com.vetnova.auth.security.JwtUtil;
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

        // 1. SILENCIAMOS TEMPORALMENTE A FEIGN PARA PODER PROBAR EL LOGIN
        // Esto evita el error de "Connection refused" mientras construyes el MS de Usuarios
        
        boolean existe = usuarioClient.validarUsuarioExiste(request.getEmail());
        if (!existe) {
            log.error("Fallo de autenticación: El usuario {} no existe.", request.getEmail());
            throw new ResourceNotFoundException("Usuario no encontrado en el sistema");
        }
        

        // Simulamos temporalmente que el usuario siempre existe en la base de datos
        log.info("Simulando existencia de usuario para la prueba de JWT");

        // 2. VALIDACIÓN DE CREDENCIALES REAL (activa)
        // NOTA: En producción, aquí deberías usar BCryptPasswordEncoder para comparar el hash
        if (!request.getPassword().equals("password123")) { 
            log.warn("Fallo de autenticación: Contraseña incorrecta para {}", request.getEmail());
            // Lanza la excepción que el GlobalExceptionHandler convertirá en un 401 Unauthorized
            throw new InvalidCredentialsException("Credenciales inválidas, intente nuevamente");
        }

        // 3. Generación de Token JWT (Solo llega aquí si la clave fue "password123")
        String tokenJwt = jwtUtil.generateToken(request.getEmail());
        
        log.info("Autenticación exitosa. Token generado para el usuario: {}", request.getEmail());

        return tokenJwt;
    }

    // Método para Recuperación de Contraseña
    public String recuperarContrasena(String email) {
        log.info("Procesando solicitud de recuperación de contraseña para: {}", email);
        
        // En una implementación real, aquí se llamaría a FeignClient para validar si el correo existe
        // y luego se enviaría un correo electrónico a través del Microservicio de Notificaciones.
        
        return "Si el correo electrónico está registrado, se enviarán las instrucciones para recuperar la contraseña.";
    }
}