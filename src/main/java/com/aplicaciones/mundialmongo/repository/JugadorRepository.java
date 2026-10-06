package com.aplicaciones.mundialmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.aplicaciones.mundialmongo.model.Jugador;

public interface JugadorRepository extends MongoRepository<Jugador, String> {
}
