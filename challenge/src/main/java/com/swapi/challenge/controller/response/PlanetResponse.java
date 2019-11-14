package com.swapi.challenge.controller.response;

import com.swapi.challenge.model.Planet;

public class PlanetResponse {


    private String name;
    private String climate;
    private String terrain;
    private Integer films;


    public PlanetResponse(Planet planet) {
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.terrain = planet.getTerrain();
        this.films = planet.getFilms();
    }

    public PlanetResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getFilms() {
        return films;
    }

    public void setFilms(Integer films) {
        this.films = films;
    }

    public PlanetResponse toResponse(Planet planet) {
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.terrain = planet.getTerrain();
        this.films = planet.getFilms();
        return this;
    }

}
