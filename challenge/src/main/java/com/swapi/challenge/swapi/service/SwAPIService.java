package com.swapi.challenge.swapi.service;

import com.swapi.challenge.model.integration.model.ListPlanetResponseBean;

import reactor.core.publisher.Mono;

public interface SwAPIService {

    public Mono<ListPlanetResponseBean> findPlanetByName(String name);
}
