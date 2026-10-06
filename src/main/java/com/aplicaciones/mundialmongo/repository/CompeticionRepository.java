package com.aplicaciones.mundialmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.aplicaciones.mundialmongo.model.Competicion;

public interface CompeticionRepository extends MongoRepository<Competicion, String> {
}
