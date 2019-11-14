package com.swapi.challenge.services;


import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.model.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetServices {

    Flux<Planet> getAll();

    Flux<Planet> findByName(String name);

    Mono<Planet> findById(String id);

    Mono<Planet> addPlanet(Planet planet);

    Flux<Planet> updatePlanet(String id, PlanetRequest planet);

    void removePlanetById(String id);

    void removeAll();
}