package com.swapi.challenge.repository;

import com.swapi.challenge.model.Planet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface PlanetRepository {

    Mono<Planet> findByName(String name);
    Flux<Planet> findAll();
    Mono<Planet> findById(String id);
    Mono<Planet> save(Planet planet);
    void deleteById(String id);
    void deleteAll();
}