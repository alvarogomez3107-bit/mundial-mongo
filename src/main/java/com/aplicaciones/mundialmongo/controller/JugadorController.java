package com.aplicaciones.mundialmongo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aplicaciones.mundialmongo.model.Jugador;
import com.aplicaciones.mundialmongo.repository.JugadorRepository;

@RestController
@RequestMapping("/api/jugadores")
@CrossOrigin(origins = "*")
public class JugadorController {

    private final JugadorRepository jugadorRepository;

    public JugadorController(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @GetMapping
    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerPorId(@PathVariable String id) {
        return jugadorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Jugador> crear(@RequestBody Jugador jugador) {
        Jugador guardado = jugadorRepository.save(jugador);
        return ResponseEntity.created(URI.create("/api/jugadores/" + guardado.getId())).body(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable String id, @RequestBody Jugador actualizado) {
        return jugadorRepository.findById(id)
                .map(j -> {
                    j.setNombre(actualizado.getNombre());
                    j.setApellido(actualizado.getApellido());
                    j.setNumero(actualizado.getNumero());
                    j.setPosicion(actualizado.getPosicion());
                    return ResponseEntity.ok(jugadorRepository.save(j));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
