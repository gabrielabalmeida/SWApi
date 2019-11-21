package com.swapi.challenge.model.integration;
import java.net.URI;
import java.util.Arrays;

import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class PlanetSwApi {

    @Autowired
    private WebClient webClient;

    private static final String uri = "https://swapi.co/api";

    public static ListPlanetRequestBean getAllPlanetsSw(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "SWAPI-Java-Client/1.0");

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            String url = "https://swapi.co/api/planets/";
            ResponseEntity<ListPlanetRequestBean> result = restTemplate.exchange(url, HttpMethod.GET, entity, ListPlanetRequestBean.class);

            if (result.getStatusCode() == HttpStatus.OK) {
                return result.getBody();
            }
            else return null;
        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }
        return null;
    }

    public static ListPlanetRequestBean getAllPlanetsSwName(String name){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("User-Agent", "SWAPI-Java-Client/1.0");

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            String url = "https://swapi.co/api/planets/?search=" + name;
            ResponseEntity<ListPlanetRequestBean> result = restTemplate.exchange(url, HttpMethod.GET, entity, ListPlanetRequestBean.class);

            if (result.getStatusCode() == HttpStatus.OK) {
                return result.getBody();
            }
            else return null;
        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }
        return null;
    }

    public Flux<ListPlanetRequestBean> getPlanetsByName(String name) {
        return webClient.get()
                .uri(uri + "/planets/?search=" + name)
                .retrieve()
                .bodyToFlux(ListPlanetRequestBean.class);
    }

}