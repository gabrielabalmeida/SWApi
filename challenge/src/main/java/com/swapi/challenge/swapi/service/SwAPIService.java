package com.swapi.challenge.swapi.service;

import com.swapi.challenge.model.integration.model.list.ListPlanetResponseBean;

import reactor.core.publisher.Mono;

public interface SwAPIService {

    Mono<ListPlanetResponseBean> findPlanetByName(String name);
}
