package com.cita.cita.controller;

import com.cita.cita.model.Medico;
import com.cita.cita.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")

public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getMedicoById(@PathVariable Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        if (medico.isPresent()) {
            return ResponseEntity.ok(medico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Medico createMedico(@RequestBody Medico medico) {
        return medicoRepository.save(medico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> updateMedico(@PathVariable Long id, @RequestBody Medico medicoDetails) {
        Optional<Medico> medico = medicoRepository.findById(id);
        if (medico.isPresent()) {
            Medico updatedMedico = medico.get();
            updatedMedico.setNombre(medicoDetails.getNombre());
            updatedMedico.setEspecialidad(medicoDetails.getEspecialidad());
            updatedMedico.setTelefono(medicoDetails.getTelefono());
            return ResponseEntity.ok(medicoRepository.save(updatedMedico));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        Optional<Medico> medico = medicoRepository.findById(id);
        if (medico.isPresent()) {
            medicoRepository.delete(medico.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
