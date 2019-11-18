package com.swapi.challenge.services;

import com.swapi.challenge.controller.request.PlanetRequest;
import com.swapi.challenge.model.Planet;
import com.swapi.challenge.model.integration.PlanetSwApi;
import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;
import com.swapi.challenge.model.integration.model.PlanetRequestBean;
import com.swapi.challenge.repository.PlanetRepository;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PlanetServicesImpl implements PlanetServices {

    private final PlanetRepository planetRepository;

    public PlanetServicesImpl(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    private void savePlanet(Flux<Planet> planets, ListPlanetRequestBean listPlanetRequestBean, Planet plt){
        if (listPlanetRequestBean != null) {
            for (PlanetRequestBean planetRequestBean : listPlanetRequestBean.results) {
                planetRequestBean.setIn(true);
                if (!planets.toString().isEmpty() && planets.toString().contains(planetRequestBean.getName())) {
                        planetRequestBean.setIn(false);
                } else planetRequestBean.setIn(true);
                if(planetRequestBean.isIn()){
                    plt.setId(UUID.randomUUID().toString());
                    plt.setName(planetRequestBean.getName());
                    plt.setClimate(planetRequestBean.getClimate());
                    plt.setTerrain(planetRequestBean.getTerrain());
                    plt.setFilms(planetRequestBean.getFilms().size());
                    this.planetRepository.save(plt);
                }
            }
        }
    }

    @Override
    public Flux<Planet> getAll() {
        Planet plt = new Planet();
        Flux<Planet> planetsList =  this.planetRepository.findAll();
        ListPlanetRequestBean listPlanetRequestBean = PlanetSwApi.getAllPlanetsSw();

        this.savePlanet(planetsList, listPlanetRequestBean, plt);

        return this.planetRepository.findAll();
    }

    @Override
    public Flux<Planet> findByName(String name){
        Mono<Planet> planets = planetRepository.findByName(name);
        ListPlanetRequestBean listPlanetRequestBean = PlanetSwApi.getAllPlanetsSwName(name);
        Planet plt = new Planet();

        this.savePlanet(planets.flux(), listPlanetRequestBean, plt);

        return this.planetRepository.findByName(name).flux();
    }

    @Override
    public Mono<Planet> findById(String id){
        return this.planetRepository.findById(id);
    }

    @Override
    public Mono<Planet> addPlanet(Planet planet){

        return this.planetRepository.save(planet);

    }

    @Override
    public Flux<Planet> updatePlanet(String id, PlanetRequest planet){
        Mono<Planet> planetId = planetRepository.findById(id);

        return planetId.flux()
                .flatMap(planets -> {
                    planets.setName(planet.getName());
                    planets.setClimate(planet.getClimate());
                    planets.setTerrain(planet.getTerrain());
                    planets.setFilms(planet.getFilms());

                    planetRepository.save(planets);
                    return null;
                });
    }

    @Override
    public void removePlanetById(String id){
        this.planetRepository.deleteById(id);
    }

    @Override
    public void removeAll(){
        this.planetRepository.deleteAll();
    }

}