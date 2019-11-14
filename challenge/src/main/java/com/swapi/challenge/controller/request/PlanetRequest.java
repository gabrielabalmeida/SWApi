package com.swapi.challenge.controller.request;

import com.swapi.challenge.model.Planet;

public class PlanetRequest {

    private String name;
    private String climate;
    private String terrain;
    private Integer films;

    public PlanetRequest() {
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

    @Override
    public String toString() {
        return "PlanetRequest{" +
                "name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", terrain='" + terrain + '\'' +
                ", films='" + films + '\'' +
                '}';
    }

    public Planet toModel() {
        Planet planet = new Planet();
        planet.setName(this.getName());
        planet.setClimate(this.getClimate());
        planet.setTerrain(this.getTerrain());
        planet.setFilms(this.getFilms());
        return planet;
    }
}
