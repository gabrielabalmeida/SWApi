package com.swapi.challenge.services;

import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.model.Planet;
import com.swapi.challenge.model.integration.model.list.ListPlanetResponseBean;
import com.swapi.challenge.model.integration.model.PlanetRequestBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetServices {

    Flux<Planet> getAll();

    Mono<Planet> findById(String uuid);

    Mono<ListPlanetResponseBean> addPlanet(PlanetRequestBean planet);

    Flux<Planet> updatePlanet(String id, PlanetRequest planet);

    void removePlanetById(String id);

    void removeAll();
}