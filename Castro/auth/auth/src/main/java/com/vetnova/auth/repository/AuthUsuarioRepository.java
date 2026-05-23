package com.vetnova.auth.repository;

import com.vetnova.auth.model.AuthUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interfaz se encarga de la persistencia de datos [9]
public interface AuthUsuarioRepository extends JpaRepository<AuthUsuario, Long> {
    
    // Método mágico de Spring Data JPA: buscará al usuario automáticamente por su email
    Optional<AuthUsuario> findByEmail(String email);
}