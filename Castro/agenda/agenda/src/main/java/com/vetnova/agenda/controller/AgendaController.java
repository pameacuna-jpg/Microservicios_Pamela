package com.vetnova.agenda.controller;

import com.vetnova.agenda.dto.CitaRequestDTO;
import com.vetnova.agenda.model.Cita;
import com.vetnova.agenda.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    // 1. Agendar Hora utilizando DTO
    @PostMapping("/agendar")
    public ResponseEntity<Cita> agendarHora(@Valid @RequestBody CitaRequestDTO dto) {
        // Mapeo manual del DTO a la Entidad
        Cita cita = new Cita();
        cita.setIdCliente(dto.getIdCliente());
        cita.setIdMascota(dto.getIdMascota());
        cita.setIdVeterinario(dto.getIdVeterinario());
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivo(dto.getMotivo());

        Cita nuevaCita = agendaService.agendarHora(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    // 2. Reprogramar Hora
    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<Cita> reprogramarHora(@PathVariable Long id, @RequestBody Map<String, String> request) {
        LocalDateTime nuevaFecha = LocalDateTime.parse(request.get("nuevaFechaHora"));
        Cita citaActualizada = agendaService.reprogramarHora(id, nuevaFecha);
        return ResponseEntity.ok(citaActualizada);
    }

    // 3. Cancelar Hora
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarHora(@PathVariable Long id) {
        Cita citaCancelada = agendaService.cancelarHora(id);
        return ResponseEntity.ok(citaCancelada);
    }

    // 4. Confirmar Asistencia
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Cita> confirmarAsistencia(@PathVariable Long id) {
        Cita citaConfirmada = agendaService.confirmarAsistencia(id);
        return ResponseEntity.ok(citaConfirmada);
    }
    // 5. Consultar todas las citas (Leer datos)
    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas() {
        List<Cita> citas = agendaService.obtenerTodasLasCitas();
        return ResponseEntity.ok(citas);
    }

    // 6. Consultar una cita por ID (Leer datos)
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCita(@PathVariable Long id) {
        Cita cita = agendaService.obtenerCitaPorId(id);
        return ResponseEntity.ok(cita);
    }
}