package com.aplicaciones.mundialmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.aplicaciones.mundialmongo.model.Entrenador;

public interface EntrenadorRepository extends MongoRepository<Entrenador, String> {
}
