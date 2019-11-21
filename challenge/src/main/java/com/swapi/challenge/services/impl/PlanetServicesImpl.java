package com.swapi.challenge.services.impl;

import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.model.Planet;
import com.swapi.challenge.model.integration.model.list.ListPlanetResponseBean;
import com.swapi.challenge.model.integration.model.PlanetRequestBean;
import com.swapi.challenge.repository.PlanetRepository;

import com.swapi.challenge.services.PlanetServices;
import com.swapi.challenge.swapi.service.impl.SwAPIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetServicesImpl implements PlanetServices {

    private final PlanetRepository planetRepository;

    @Autowired
    private SwAPIServiceImpl swAPIService;

    public PlanetServicesImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Override
    public Flux<Planet> getAll() {

        return this.planetRepository.findAll();
    }

    @Override
    public Mono<Planet> findById(String uuid) {
        return this.planetRepository.findById(uuid);
    }

    @Override
    public Mono<ListPlanetResponseBean> addPlanet(PlanetRequestBean planet) {
        return this.swAPIService
                .findPlanetByName(planet.getName())
                .map(planetResponseBean -> {

                    if (planetResponseBean.results.stream().findFirst().get().getName().equals(planet.getName())) {
                        planet.setClimate(planetResponseBean.results
                                .stream()
                                .findFirst().get().getClimate());
                        planet.setTerrain(planetResponseBean.results
                                .stream()
                                .findFirst().get().getTerrain());
                        planet.setFilms(planetResponseBean.results
                                .stream()
                                .findFirst().get().getFilms().size());
                    }

                    this.planetRepository.save(planet.toModel());

                    return planetResponseBean;
                });
    }

    @Override
    public Flux<Planet> updatePlanet(String id, PlanetRequest planet) {
        Mono<Planet> planetId = planetRepository.findById(id);

        return planetId.flux()
                .flatMap(planets -> {
                    planets.setName(planet.getName());
                    planets.setClimate(planet.getClimate());
                    planets.setTerrain(planet.getTerrain());
                    planets.setFilms(planet.getFilms());

                    this.planetRepository.save(planets);
                    return null;
                });
    }

    @Override
    public void removePlanetById(String id) {
        this.planetRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        this.planetRepository.deleteAll();
    }

}