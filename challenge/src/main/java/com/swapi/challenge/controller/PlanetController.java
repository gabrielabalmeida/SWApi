package com.swapi.challenge.controller;

import com.swapi.challenge.model.Planet;
import com.swapi.challenge.services.PlanetServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    Planet planet;

    @Autowired
    private PlanetServices planetServices;

    @GetMapping
    public @ResponseBody ResponseEntity<?> getAll() {

        Flux<Planet> planets = this.planetServices.getAll();

        return new ResponseEntity<>(planets, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?> createPlanets(@RequestBody Planet planets) {
        Mono<Planet> lala =this.planetServices.addPlanet(planets);

        return new ResponseEntity<>(lala,HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody ResponseEntity<?> deletePlanets(@PathVariable(name = "id") String id) {
        this.planetServices.removePlanetById(id);

        return new ResponseEntity<>(id + " deletado",HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePlanet(@PathVariable String id, @Valid @RequestBody Planet planet) {

        Flux<Planet> p = this.planetServices.updatePlanet(id, planet);

        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping(path = "/findId/{id}")
    public @ResponseBody ResponseEntity<?> searchById(@PathVariable(name = "id") String id) {
        Mono<Planet> planet = planetServices.findById(id);
        return new ResponseEntity<>(planet, HttpStatus.OK);
    }

    @PostMapping(path = "/findName/{name}")
    public @ResponseBody ResponseEntity<?> searchByName(@PathVariable(name = "name") String name) {
        Flux<Planet> planet = planetServices.findByName(name);

        return new ResponseEntity<>(planet,HttpStatus.OK);
    }
}