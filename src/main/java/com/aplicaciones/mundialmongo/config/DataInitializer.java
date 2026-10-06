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
        // Limpiar datos anteriores para sincronizar exactamente los 6 clubes
        clubRepository.deleteAll();
        entrenadorRepository.deleteAll();
        jugadorRepository.deleteAll();
        asociacionRepository.deleteAll();
        competicionRepository.deleteAll();

        // 1. Asociaciones Afiliadas
        Asociacion fcf = new Asociacion("Federación Colombiana de Fútbol (FCF)", "Colombia", "Ramón Jesurún", "FCF");
        Asociacion rfef = new Asociacion("Real Federación Española de Fútbol (RFEF)", "España", "Pedro Rocha", "RFEF");
        Asociacion dfb = new Asociacion("Deutscher Fußball-Bund (DFB)", "Alemania", "Bernd Neuendorf", "DFB");
        Asociacion fff = new Asociacion("LFP / Fédération Française de Football", "Francia", "Philippe Diallo", "FFF");
        Asociacion saff = new Asociacion("Saudi Arabian Football Federation (SAFF)", "Arabia Saudita", "Yasser Al Misehal", "SAFF");

        List<Asociacion> asocs = asociacionRepository.saveAll(Arrays.asList(fcf, rfef, dfb, fff, saff));
        Asociacion fcfSaved = asocs.get(0);
        Asociacion rfefSaved = asocs.get(1);
        Asociacion dfbSaved = asocs.get(2);
        Asociacion fffSaved = asocs.get(3);
        Asociacion saffSaved = asocs.get(4);

        // 2. Competiciones Oficiales
        Competicion libertadores = new Competicion("Copa CONMEBOL Libertadores", 23000000, 
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 11, 28));
        Competicion champions = new Competicion("UEFA Champions League", 22000000, 
                LocalDate.of(2026, 9, 15), LocalDate.of(2027, 5, 29));
        Competicion bundesliga = new Competicion("Bundesliga", 80000000, 
                LocalDate.of(2026, 8, 21), LocalDate.of(2027, 5, 22));
        Competicion ligue1 = new Competicion("Ligue 1", 65000000, 
                LocalDate.of(2026, 8, 14), LocalDate.of(2027, 5, 23));
        Competicion laliga = new Competicion("LaLiga EA Sports", 60000000, 
                LocalDate.of(2026, 8, 14), LocalDate.of(2027, 5, 23));
        Competicion afcElite = new Competicion("AFC Champions League Elite", 10000000, 
                LocalDate.of(2026, 9, 14), LocalDate.of(2027, 5, 8));

        List<Competicion> comps = competicionRepository.saveAll(
                Arrays.asList(libertadores, champions, bundesliga, ligue1, laliga, afcElite));
        Competicion libSaved = comps.get(0);
        Competicion champSaved = comps.get(1);
        Competicion bundesSaved = comps.get(2);
        Competicion ligueSaved = comps.get(3);
        Competicion laligaSaved = comps.get(4);
        Competicion afcSaved = comps.get(5);

        // 3. Club 1: ATLÉTICO NACIONAL
        Entrenador entNacional = entrenadorRepository.save(
                new Entrenador("Lucas", "González", 45, "Colombiana"));
        List<Jugador> jugNacional = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("James", "Rodríguez", 23, "Mediapunta"),
                new Jugador("William", "Tesillo", 2, "Defensa"),
                new Jugador("Franco", "Armani", 1, "Portero"),
                new Jugador("Edwin", "Cardona", 10, "Centrocampista"),
                new Jugador("Alfredo", "Morelos", 9, "Delantero")
        ));
        Club nacional = new Club("Club Atlético Nacional S. A.", entNacional, fcfSaved, jugNacional,
                Arrays.asList(libSaved));

        // 4. Club 2: REAL MADRID
        Entrenador entMadrid = entrenadorRepository.save(
                new Entrenador("José", "Mourinho", 63, "Portuguesa"));
        List<Jugador> jugMadrid = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Thibaut", "Courtois", 1, "Portero"),
                new Jugador("Jude", "Bellingham", 5, "Centrocampista"),
                new Jugador("Federico", "Valverde", 8, "Centrocampista"),
                new Jugador("Vinícius", "Júnior", 7, "Delantero"),
                new Jugador("Kylian", "Mbappé", 9, "Delantero")
        ));
        Club madrid = new Club("Real Madrid Club de Fútbol", entMadrid, rfefSaved, jugMadrid,
                Arrays.asList(champSaved, laligaSaved));

        // 5. Club 3: BAYERN MÚNICH
        Entrenador entBayern = entrenadorRepository.save(
                new Entrenador("Vincent", "Kompany", 40, "Belga"));
        List<Jugador> jugBayern = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Manuel", "Neuer", 1, "Portero"),
                new Jugador("Luis", "Díaz", 14, "Delantero"),
                new Jugador("Jamal", "Musiala", 42, "Centrocampista"),
                new Jugador("Michael", "Olise", 17, "Delantero"),
                new Jugador("Harry", "Kane", 9, "Delantero")
        ));
        Club bayern = new Club("Fußball-Club Bayern München e. V.", entBayern, dfbSaved, jugBayern,
                Arrays.asList(bundesSaved, champSaved));

        // 6. Club 4: PARIS SAINT-GERMAIN (PSG)
        Entrenador entPsg = entrenadorRepository.save(
                new Entrenador("Luis Enrique", "Martínez", 56, "Española"));
        List<Jugador> jugPsg = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Khvicha", "Kvaratskhelia", 7, "Delantero"),
                new Jugador("Achraf", "Hakimi", 2, "Defensa"),
                new Jugador("Marquinhos", "Corrêa", 5, "Defensa"),
                new Jugador("Vitinha", "Machado", 17, "Centrocampista"),
                new Jugador("Ousmane", "Dembélé", 10, "Delantero")
        ));
        Club psg = new Club("Paris Saint-Germain Football Club", entPsg, fffSaved, jugPsg,
                Arrays.asList(ligueSaved, champSaved));

        // 7. Club 5: BARCELONA
        Entrenador entBarca = entrenadorRepository.save(
                new Entrenador("Hans-Dieter", "Flick", 61, "Alemana"));
        List<Jugador> jugBarca = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Pedri", "González", 8, "Centrocampista"),
                new Jugador("Frenkie", "de Jong", 21, "Centrocampista"),
                new Jugador("Lamine", "Yamal", 19, "Delantero"),
                new Jugador("Raphinha", "Dias", 11, "Delantero"),
                new Jugador("Rodrigo", "Hernández", 16, "Centrocampista")
        ));
        Club barca = new Club("Fútbol Club Barcelona", entBarca, rfefSaved, jugBarca,
                Arrays.asList(laligaSaved, champSaved));

        // 8. Club 6: AL-NASSR
        Entrenador entAlNassr = entrenadorRepository.save(
                new Entrenador("Ange", "Postecoglou", 61, "Australiana"));
        List<Jugador> jugAlNassr = jugadorRepository.saveAll(Arrays.asList(
                new Jugador("Aymeric", "Laporte", 27, "Defensa"),
                new Jugador("Marcelo", "Brozović", 77, "Centrocampista"),
                new Jugador("Otávio", "Monteiro", 25, "Centrocampista"),
                new Jugador("Sadio", "Mané", 10, "Delantero"),
                new Jugador("Cristiano", "Ronaldo", 7, "Delantero")
        ));
        Club alNassr = new Club("Al-Nassr Football Club", entAlNassr, saffSaved, jugAlNassr,
                Arrays.asList(afcSaved));

        // Guardar todos los clubes en MongoDB Atlas
        clubRepository.saveAll(Arrays.asList(nacional, madrid, bayern, psg, barca, alNassr));
    }
}
