package com.swapi.challenge.model.integration;
import java.util.Arrays;

import com.swapi.challenge.model.integration.model.ListPlanetRequestBean;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PlanetSwApi {
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
                ListPlanetRequestBean planet = result.getBody();
                return planet;
            }
            else return null;
        } catch (Exception ex) {

            System.out.println(ex.getMessage());

        }
        return null;
    }
}