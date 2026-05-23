package com.vetnova.atencionclinica.repository;

import com.vetnova.atencionclinica.model.FichaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Long> {
    Optional<FichaClinica> findByIdMascota(Long idMascota);
}