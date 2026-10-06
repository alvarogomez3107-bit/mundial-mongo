package com.aplicaciones.mundialmongo.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aplicaciones.mundialmongo.model.*;
import com.aplicaciones.mundialmongo.repository.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClubRepository clubRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final JugadorRepository jugadorRepository;
    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;

    public DataInitializer(ClubRepository clubRepository,
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

    @Override
    public void run(String... args) throws Exception {
        if (clubRepository.count() > 0) {
            return; // Ya existen datos en MongoDB Atlas
        }

        // 1. Asociaciones
        Asociacion fcf = new Asociacion("Federación Colombiana de Fútbol", "Colombia", "Ramón Jesurún", "FCF");
        Asociacion rfef = new Asociacion("Real Federación Española de Fútbol", "España", "Pedro Rocha", "RFEF");
        List<Asociacion> asocs = asociacionRepository.saveAll(Arrays.asList(fcf, rfef));
        Asociacion fcfSaved = asocs.get(0);

        // 2. Competiciones
        Competicion copaLibertadores = new Competicion("Copa Libertadores", 18000000, 
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 11, 28));
        Competicion ligaBetPlay = new Competicion("Liga BetPlay Dimayor", 2000000, 
                LocalDate.of(2026, 1, 15), LocalDate.of(2026, 6, 20));
        Competicion copaPostobon = new Competicion("Copa Postobón", 1000000, 
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 10, 15));
        Competicion superliga = new Competicion("Superliga LatinoAmericana", 3000000, 
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 25));

        List<Competicion> comps = competicionRepository.saveAll(
                Arrays.asList(copaLibertadores, ligaBetPlay, copaPostobon, superliga));
        Competicion copaLibertadoresSaved = comps.get(0);
        Competicion ligaBetPlaySaved = comps.get(1);
        Competicion copaPostobonSaved = comps.get(2);
        Competicion superligaSaved = comps.get(3);

        // 3. Millonarios FC
        Entrenador entrenadorMillos = entrenadorRepository.save(
                new Entrenador("Alberto", "Gamero", 60, "Colombiana"));
        List<Jugador> jugadoresMillos = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Radamel", "Falcao García", 9, "Delantero"),
                new Jugador("David", "Mackalister Silva", 14, "Mediocampista"),
                new Jugador("Álvaro", "Montero", 31, "Portero")
        ));
        Club millonarios = new Club("Millonarios FC", entrenadorMillos, fcfSaved, jugadoresMillos,
                Arrays.asList(copaLibertadoresSaved, ligaBetPlaySaved));

        // 4. Independiente Santa Fe
        Entrenador entrenadorSantaFe = entrenadorRepository.save(
                new Entrenador("Pablo", "Peirano", 50, "Uruguaya"));
        List<Jugador> jugadoresSantaFe = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Hugo", "Rodallega", 11, "Delantero"),
                new Jugador("Daniel", "Torres", 16, "Mediocampista"),
                new Jugador("Andrés", "Mosquera", 1, "Portero")
        ));
        Club santaFe = new Club("Independiente Santa Fe", entrenadorSantaFe, fcfSaved, jugadoresSantaFe,
                Arrays.asList(copaLibertadoresSaved, copaPostobonSaved, superligaSaved));

        // 5. Atlético Nacional
        Entrenador entrenadorNacional = entrenadorRepository.save(
                new Entrenador("Efraín", "Juárez", 36, "Mexicana"));
        List<Jugador> jugadoresNacional = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("James", "Rodríguez", 23, "Mediapunta"),
                new Jugador("Luis", "Marquínez", 1, "Portero"),
                new Jugador("Edwin", "Cardona", 8, "Mediocampista"),
                new Jugador("Alfredo", "Morelos", 9, "Delantero")
        ));
        Club atleticoNacional = new Club("Atlético Nacional", entrenadorNacional, fcfSaved, jugadoresNacional,
                Arrays.asList(copaLibertadoresSaved, ligaBetPlaySaved, copaPostobonSaved));

        clubRepository.saveAll(Arrays.asList(millonarios, santaFe, atleticoNacional));
    }
}
