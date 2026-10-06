package com.aplicaciones.mundialmongo.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "clubes")
public class Club {

    @Id
    private String id;
    private String nombre;

    @DocumentReference
    private Entrenador entrenador;

    @DocumentReference
    private Asociacion asociacion;

    @DocumentReference
    private List<Jugador> jugadores = new ArrayList<>();

    @DocumentReference
    private List<Competicion> competiciones = new ArrayList<>();

    public Club() {}

    public Club(String nombre, Entrenador entrenador, Asociacion asociacion, List<Jugador> jugadores, List<Competicion> competiciones) {
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.asociacion = asociacion;
        this.jugadores = jugadores != null ? jugadores : new ArrayList<>();
        this.competiciones = competiciones != null ? competiciones : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public List<Competicion> getCompeticiones() { return competiciones; }
    public void setCompeticiones(List<Competicion> competiciones) { this.competiciones = competiciones; }
}
