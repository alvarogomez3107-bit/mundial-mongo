package com.aplicaciones.mundialmongo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aplicaciones.mundialmongo.model.*;
import com.aplicaciones.mundialmongo.repository.*;

@Controller
public class MundialWebController {

    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;
    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;

    public MundialWebController(ClubRepository clubRepository,
                                EntrenadorRepository entrenadorRepository,
                                JugadorRepository jugadorRepository,
                                AsociacionRepository asociacionRepository,
                                CompeticionRepository competicionRepository) {
        this.clubRepository = clubRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.jugadorRepository = jugadorRepository;
        this.asociacionRepository = asociacionRepository;
        this.competicionRepository = competicionRepository;
    }

    // Ruta raíz: Redirige al listado visual del mundial
    @GetMapping("/")
    public String index() {
        return "redirect:/listar";
    }

    // Listar todos los clubes y entidades desde MongoDB Atlas
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Club> clubes = clubRepository.findAll();
        model.addAttribute("clubes", clubes);
        model.addAttribute("totalClubes", clubes.size());
        
        List<Asociacion> asociaciones = asociacionRepository.findAll();
        model.addAttribute("asociaciones", asociaciones);
        model.addAttribute("totalAsociaciones", asociaciones.size());

        List<Competicion> competiciones = competicionRepository.findAll();
        model.addAttribute("competiciones", competiciones);
        model.addAttribute("totalCompeticiones", competiciones.size());

        List<Entrenador> entrenadores = entrenadorRepository.findAll();
        model.addAttribute("entrenadores", entrenadores);
        model.addAttribute("totalEntrenadores", entrenadores.size());

        List<Jugador> jugadores = jugadorRepository.findAll();
        model.addAttribute("jugadores", jugadores);
        model.addAttribute("totalJugadores", jugadores.size());

        return "listar";
    }

    // Formulario para inscribir un nuevo club en MongoDB
    @GetMapping({"/nuevo", "/clubes/nuevo"})
    public String nuevoClub(Model model) {
        Club club = new Club();
        club.setEntrenador(new Entrenador());
        
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador());
        jugadores.add(new Jugador());
        club.setJugadores(jugadores);

        model.addAttribute("club", club);
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("competicionesDisponibles", competicionRepository.findAll());
        return "index";
    }

    // Guardar el club en MongoDB Atlas con sus referencias
    @PostMapping("/guardar")
    public String guardar(Club club,
                          @RequestParam(value = "asociacionId", required = false) String asociacionId,
                          @RequestParam(value = "competicionesIds", required = false) List<String> competicionesIds) {

        // 1. Guardar Entrenador primero si tiene datos
        if (club.getEntrenador() != null && club.getEntrenador().getNombre() != null 
                && !club.getEntrenador().getNombre().trim().isEmpty()) {
            Entrenador entrenadorGuardado = entrenadorRepository.save(club.getEntrenador());
            club.setEntrenador(entrenadorGuardado);
        }

        // 2. Guardar Jugadores
        if (club.getJugadores() != null) {
            List<Jugador> validos = club.getJugadores().stream()
                    .filter(j -> j.getNombre() != null && !j.getNombre().trim().isEmpty())
                    .collect(Collectors.toList());
            if (!validos.isEmpty()) {
                List<Jugador> guardados = jugadorRepository.saveAll(validos);
                club.setJugadores(guardados);
            } else {
                club.setJugadores(new ArrayList<>());
            }
        }

        // 3. Vincular Asociación seleccionada
        if (asociacionId != null && !asociacionId.trim().isEmpty()) {
            asociacionRepository.findById(asociacionId).ifPresent(club::setAsociacion);
        }

        // 4. Vincular Competiciones seleccionadas
        if (competicionesIds != null && !competicionesIds.isEmpty()) {
            List<Competicion> seleccionadas = competicionRepository.findAllById(competicionesIds);
            club.setCompeticiones(seleccionadas);
        }

        clubRepository.save(club);
        return "redirect:/listar";
    }

    // Ver detalle completo ("Ficha del Club")
    @GetMapping("/clubes/{id}")
    public String detalleClub(@PathVariable("id") String id, Model model) {
        Optional<Club> clubOpt = clubRepository.findById(id);
        if (clubOpt.isPresent()) {
            model.addAttribute("club", clubOpt.get());
            return "detalle";
        }
        return "redirect:/listar";
    }

    // Eliminar club de MongoDB Atlas
    @GetMapping("/clubes/eliminar/{id}")
    public String eliminarClub(@PathVariable("id") String id) {
        clubRepository.deleteById(id);
        return "redirect:/listar";
    }
}
