package com.swapi.challenge.repository;

import com.swapi.challenge.model.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetSyncRepository extends CrudRepository<Planet, String> {
    List<Planet> findByName(String name);
}