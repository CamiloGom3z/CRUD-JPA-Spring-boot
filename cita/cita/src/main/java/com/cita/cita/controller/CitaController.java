package com.cita.cita.controller;

import com.cita.cita.model.Cita;
import com.cita.cita.model.Medico;
import com.cita.cita.model.Paciente;
import com.cita.cita.repository.CitaRepository;
import com.cita.cita.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cita.cita.repository.PacienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
public class CitaController {
    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> getCitaById(@PathVariable Long id) {
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            return ResponseEntity.ok(cita.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) {
        Optional<Paciente> paciente = pacienteRepository.findById(cita.getPaciente().getCedula());
        Optional<Medico> medico = medicoRepository.findById(cita.getMedico().getIdentificacion());

        if (paciente.isPresent() && medico.isPresent()) {
            cita.setPaciente(paciente.get());
            cita.setMedico(medico.get());
            return ResponseEntity.ok(citaRepository.save(cita));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita citaDetails) {
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            Cita updatedCita = cita.get();
            updatedCita.setFecha(citaDetails.getFecha());
            updatedCita.setHora(citaDetails.getHora());
            return ResponseEntity.ok(citaRepository.save(updatedCita));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            citaRepository.delete(cita.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
