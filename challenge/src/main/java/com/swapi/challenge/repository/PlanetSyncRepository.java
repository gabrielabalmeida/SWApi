package com.swapi.challenge.repository;

import com.swapi.challenge.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlanetSyncRepository extends MongoRepository<Planet, String> {
    List<Planet> findByName(String name);

}
