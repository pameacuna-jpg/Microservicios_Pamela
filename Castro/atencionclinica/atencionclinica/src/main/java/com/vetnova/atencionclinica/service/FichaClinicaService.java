package com.vetnova.atencionclinica.service;

import com.vetnova.atencionclinica.model.FichaClinica;
import com.vetnova.atencionclinica.repository.FichaClinicaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j // Cumplimiento de Logs Estructurados
@Service
public class FichaClinicaService {

    @Autowired
    private FichaClinicaRepository repository;

    // Aquí iría el @Autowired de tu FeignClient (MascotaClient)

    @Transactional
    public FichaClinica crearFicha(FichaClinica ficha) {
        log.info("Iniciando creación de ficha clínica para la Mascota ID: {}", ficha.getIdMascota());

        /* 
        // LÓGICA DE COMUNICACIÓN CON MS MASCOTAS (Requisito IE 2.4.1)
        boolean mascotaExiste = mascotaClient.validarMascota(ficha.getIdMascota());
        if(!mascotaExiste) {
            log.error("Fallo al crear ficha: La mascota con ID {} no existe.", ficha.getIdMascota());
            throw new RuntimeException("Mascota no encontrada en el sistema central.");
        }
        */

        // Vinculamos los diagnósticos con la ficha para respetar la integridad referencial
        if (ficha.getDiagnosticos() != null) {
            ficha.getDiagnosticos().forEach(diag -> diag.setFichaClinica(ficha));
        }

        FichaClinica nuevaFicha = repository.save(ficha);
        log.info("Ficha creada exitosamente con ID: {}", nuevaFicha.getIdFicha());
        return nuevaFicha;
    }

    public List<FichaClinica> obtenerTodas() {
        return repository.findAll();
    }
}