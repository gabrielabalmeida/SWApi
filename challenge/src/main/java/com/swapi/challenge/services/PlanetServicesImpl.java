package com.swapi.challenge.services;

import com.swapi.challenge.model.Planet;
import com.swapi.challenge.model.integration.PlanetSwApi;
import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;
import com.swapi.challenge.model.integration.model.PlanetRequestBean;
import com.swapi.challenge.repository.PlanetRepository;

import com.swapi.challenge.repository.PlanetSyncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanetServicesImpl implements PlanetServices {


    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetSyncRepository planetSyncRepository;

    @Override
    public Flux<Planet> getAll() {
        List<Planet> planetsList = planetSyncRepository.findAll();
        Planet plt = new Planet();
        plt.setDb(true);

        ListPlanetRequestBean listPlanetRequestBean = PlanetSwApi.getAllPlanetsSw();
        if (listPlanetRequestBean != null) {
            for (PlanetRequestBean planetRequestBean : listPlanetRequestBean.results) {
                for(Planet planet : planetsList) {
                    if (planet.getName().equals(planetRequestBean.getName())) {
                        plt.setDb(false);
                    }
                    if(plt.isDb()){
                        plt.setId(null);
                        plt.setName(planetRequestBean.getName());
                        plt.setClimate(planetRequestBean.getClimate());
                        plt.setTerrain(planetRequestBean.getTerrain());
                        plt.setFilms(planetRequestBean.getFilms().size());
                        planetSyncRepository.save(plt);
                    }
                }
            }
        }
        return planetRepository.findAll();
    }

    @Override
    public Flux<Planet> findByName(String name){
    List<Planet> planets = planetSyncRepository.findByName(name);
    ListPlanetRequestBean listPlanetRequestBean = PlanetSwApi.getAllPlanetsSwName(name);
    List<Planet> allPlanets = new ArrayList<>();
    Planet plt = new Planet();
    plt.setDb(true);

    if(listPlanetRequestBean != null){
        for(PlanetRequestBean planetRequestBean : listPlanetRequestBean.results) {
            for(Planet planet : planets) {
                if(planet.getName().equals(planetRequestBean.getName())) {
                    plt.setDb(false);
                }
            }
            if(plt.isDb()){
                plt.setId(null);
                plt.setName(planetRequestBean.getName());
                plt.setClimate(planetRequestBean.getClimate());
                plt.setTerrain(planetRequestBean.getClimate());
                plt.setFilms(planetRequestBean.getFilms().size());

                allPlanets.add(plt);
                planetSyncRepository.saveAll(allPlanets);
            }
        }
    }
    return planetRepository.findByName(name).flux();
    }

    @Override
    public Mono<Planet> findById(String id){
    return this.planetRepository.findById(id);
    }

    public Mono<Planet> addPlanet(Planet planetAdd){
    return this.planetRepository.save(planetAdd);
    }

    @Override
    public Flux<Planet> updatePlanet(String id, Planet planet){
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
    this.planetSyncRepository.deleteById(id);
    }

}