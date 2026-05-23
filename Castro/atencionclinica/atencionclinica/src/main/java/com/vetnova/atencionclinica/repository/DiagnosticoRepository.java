package com.vetnova.atencionclinica.repository;

import com.vetnova.atencionclinica.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
}