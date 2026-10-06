package com.aplicaciones.mundialmongo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aplicaciones.mundialmongo.model.Competicion;
import com.aplicaciones.mundialmongo.repository.CompeticionRepository;

@RestController
@RequestMapping("/api/competiciones")
@CrossOrigin(origins = "*")
public class CompeticionController {

    private final CompeticionRepository competicionRepository;

    public CompeticionController(CompeticionRepository competicionRepository) {
        this.competicionRepository = competicionRepository;
    }

    @GetMapping
    public List<Competicion> listar() {
        return competicionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competicion> obtenerPorId(@PathVariable String id) {
        return competicionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Competicion> crear(@RequestBody Competicion competicion) {
        Competicion guardada = competicionRepository.save(competicion);
        return ResponseEntity.created(URI.create("/api/competiciones/" + guardada.getId())).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competicion> actualizar(@PathVariable String id, @RequestBody Competicion actualizado) {
        return competicionRepository.findById(id)
                .map(c -> {
                    c.setNombre(actualizado.getNombre());
                    c.setMontoPremio(actualizado.getMontoPremio());
                    c.setFechaInicio(actualizado.getFechaInicio());
                    c.setFechaFin(actualizado.getFechaFin());
                    return ResponseEntity.ok(competicionRepository.save(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (competicionRepository.existsById(id)) {
            competicionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
