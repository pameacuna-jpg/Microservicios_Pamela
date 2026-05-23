package com.vetnova.autenticacion.service;

import com.vetnova.autenticacion.model.Credencial;
import com.vetnova.autenticacion.repository.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredencialService {

    @Autowired
    private CredencialRepository credencialRepository;

    public List<Credencial> listarCredenciales() {
        return credencialRepository.findAll();
    }

    public Credencial buscarPorId(Long id) {
        return credencialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credencial no encontrada con ID: " + id));
    }

    public Credencial crearCredencial(Credencial credencial) {
        if  (credencialRepository.findByUsername(credencial.getUsername()).isPresent()) {
            throw new RuntimeException("Ya existe una credencial registrada con ese username");
        }

        credencial.setEstado("ACTIVA");
        return credencialRepository.save(credencial);
    }

    public Credencial validarLogin(String username, String passwordHash) {
        Credencial credencial = credencialRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        if (!credencial.getPasswordHash().equals(passwordHash)) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        if (!credencial.getEstado().equalsIgnoreCase("ACTIVA")) {
            throw new RuntimeException("La credencial se encuentra inactiva");
        }

        return credencial;
    }

    public void desactivarCredencial(Long id) {
        Credencial credencial = buscarPorId(id);
        credencial.setEstado("INACTIVA");
        credencialRepository.save(credencial);
    }
}