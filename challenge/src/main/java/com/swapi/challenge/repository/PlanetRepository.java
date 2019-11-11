package com.swapi.challenge.repository;

import com.swapi.challenge.model.Planet;

import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends ReactiveMongoRepository<Planet, String> {
    Mono<Planet> findByName(String name);
}