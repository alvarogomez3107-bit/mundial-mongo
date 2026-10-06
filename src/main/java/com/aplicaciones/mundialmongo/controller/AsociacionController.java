package com.aplicaciones.mundialmongo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aplicaciones.mundialmongo.model.Asociacion;
import com.aplicaciones.mundialmongo.repository.AsociacionRepository;

@RestController
@RequestMapping("/api/asociaciones")
@CrossOrigin(origins = "*")
public class AsociacionController {

    private final AsociacionRepository asociacionRepository;

    public AsociacionController(AsociacionRepository asociacionRepository) {
        this.asociacionRepository = asociacionRepository;
    }

    @GetMapping
    public List<Asociacion> listar() {
        return asociacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asociacion> obtenerPorId(@PathVariable String id) {
        return asociacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Asociacion> crear(@RequestBody Asociacion asociacion) {
        Asociacion guardada = asociacionRepository.save(asociacion);
        return ResponseEntity.created(URI.create("/api/asociaciones/" + guardada.getId())).body(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asociacion> actualizar(@PathVariable String id, @RequestBody Asociacion actualizado) {
        return asociacionRepository.findById(id)
                .map(a -> {
                    a.setNombre(actualizado.getNombre());
                    a.setPais(actualizado.getPais());
                    a.setPresidente(actualizado.getPresidente());
                    a.setSiglas(actualizado.getSiglas());
                    return ResponseEntity.ok(asociacionRepository.save(a));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (asociacionRepository.existsById(id)) {
            asociacionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
