package com.aplicaciones.mundialmongo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aplicaciones.mundialmongo.model.Club;
import com.aplicaciones.mundialmongo.repository.ClubRepository;

@RestController
@RequestMapping("/api/clubes")
@CrossOrigin(origins = "*")
public class ClubController {

    private final ClubRepository clubRepository;

    public ClubController(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    // Listar todos los clubes
    @GetMapping
    public List<Club> listar() {
        return clubRepository.findAll();
    }

    // Obtener un club por ID
    @GetMapping("/{id}")
    public ResponseEntity<Club> obtenerPorId(@PathVariable String id) {
        return clubRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo club
    @PostMapping
    public ResponseEntity<Club> crear(@RequestBody Club club) {
        Club guardado = clubRepository.save(club);
        return ResponseEntity.created(URI.create("/api/clubes/" + guardado.getId())).body(guardado);
    }

    // Actualizar un club existente
    @PutMapping("/{id}")
    public ResponseEntity<Club> actualizar(@PathVariable String id, @RequestBody Club clubActualizado) {
        return clubRepository.findById(id)
                .map(club -> {
                    club.setNombre(clubActualizado.getNombre());
                    club.setEntrenador(clubActualizado.getEntrenador());
                    club.setAsociacion(clubActualizado.getAsociacion());
                    club.setJugadores(clubActualizado.getJugadores());
                    club.setCompeticiones(clubActualizado.getCompeticiones());
                    return ResponseEntity.ok(clubRepository.save(club));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un club
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (clubRepository.existsById(id)) {
            clubRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
