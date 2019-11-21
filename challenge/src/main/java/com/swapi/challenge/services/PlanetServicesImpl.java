package com.swapi.challenge.services;

import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.model.Planet;
import com.swapi.challenge.model.integration.model.ListPlanetResponseBean;
import com.swapi.challenge.model.integration.model.PlanetRequestBean;
import com.swapi.challenge.repository.PlanetRepository;

import com.swapi.challenge.swapi.service.SwAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlanetServicesImpl implements PlanetServices {

    private final PlanetRepository planetRepository;

    @Autowired
    private SwAPIService swAPIService;

    public PlanetServicesImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

//    private void savePlanet(Flux<Planet> planets, Lis listPlanetResponseBean) {
//
//
//        Planet plt = new Planet();
//        if (listPlanetResponseBean != null) {
//            for (PlanetRequestBean planetRequestBean : listPlanetResponseBean.results) {
//                planetRequestBean.setIn(true);
//
//
//                if (!planets.toString().isEmpty() && planets.toString().contains(planetRequestBean.getName())) {
//                    planetRequestBean.setIn(false);
//                } else planetRequestBean.setIn(true);
//                if (planetRequestBean.isIn()) {
//                    plt.setUuid(UUID.randomUUID().toString());
//                    plt.setName(planetRequestBean.getName());
//                    plt.setClimate(planetRequestBean.getClimate());
//                    plt.setTerrain(planetRequestBean.getTerrain());
//                    plt.setFilms(planetRequestBean.getFilms().size());
//                    this.planetRepository.save(plt);
//                }
//            }
//        }
//    }

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
                    planet.setFilms(planetResponseBean.results
                            .stream()
                            .findFirst().get().getFilms().size());

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