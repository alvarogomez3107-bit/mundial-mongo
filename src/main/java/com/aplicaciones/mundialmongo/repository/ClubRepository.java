package com.aplicaciones.mundialmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.aplicaciones.mundialmongo.model.Club;

public interface ClubRepository extends MongoRepository<Club, String> {
}
