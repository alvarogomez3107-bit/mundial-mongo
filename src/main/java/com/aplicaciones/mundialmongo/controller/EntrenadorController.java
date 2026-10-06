package com.aplicaciones.mundialmongo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aplicaciones.mundialmongo.model.Entrenador;
import com.aplicaciones.mundialmongo.repository.EntrenadorRepository;

@RestController
@RequestMapping("/api/entrenadores")
@CrossOrigin(origins = "*")
public class EntrenadorController {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorController(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    @GetMapping
    public List<Entrenador> listar() {
        return entrenadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> obtenerPorId(@PathVariable String id) {
        return entrenadorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entrenador> crear(@RequestBody Entrenador entrenador) {
        Entrenador guardado = entrenadorRepository.save(entrenador);
        return ResponseEntity.created(URI.create("/api/entrenadores/" + guardado.getId())).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizar(@PathVariable String id, @RequestBody Entrenador actualizado) {
        return entrenadorRepository.findById(id)
                .map(e -> {
                    e.setNombre(actualizado.getNombre());
                    e.setApellido(actualizado.getApellido());
                    e.setEdad(actualizado.getEdad());
                    e.setNacionalidad(actualizado.getNacionalidad());
                    return ResponseEntity.ok(entrenadorRepository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (entrenadorRepository.existsById(id)) {
            entrenadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
