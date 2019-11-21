package com.swapi.challenge.swapi.service;

import com.swapi.challenge.config.SwapiConfig;
import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;
import com.swapi.challenge.model.integration.model.ListPlanetResponseBean;
import com.swapi.challenge.model.integration.model.PlanetResponseBean;
import com.swapi.challenge.swapi.response.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SwAPIService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private SwapiConfig swapiConfig;

    public SwAPIService() {
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
