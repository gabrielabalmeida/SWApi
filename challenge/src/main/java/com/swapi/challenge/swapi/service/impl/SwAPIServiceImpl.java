package com.swapi.challenge.swapi.service.impl;

import com.swapi.challenge.swapi.config.SwapiConfig;
import com.swapi.challenge.model.integration.model.list.ListPlanetResponseBean;
import com.swapi.challenge.swapi.service.SwAPIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SwAPIServiceImpl implements SwAPIService{

    @Autowired
    private WebClient webClient;

    @Autowired
    private SwapiConfig swapiConfig;

    public SwAPIServiceImpl() {
    }

    public Mono<ListPlanetResponseBean> findPlanetByName(String name) {
        return this.webClient
                .get().uri(String.format("%s/?search=%s", this.swapiConfig.getUrl(), name))
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ListPlanetResponseBean.class);
                    } else {
                        return Mono.empty();
                    }
                });
    }


}
