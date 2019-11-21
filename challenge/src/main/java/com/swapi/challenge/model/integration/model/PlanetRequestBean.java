package com.swapi.challenge.model.integration.model;

import com.swapi.challenge.model.Planet;

public class PlanetRequestBean {

    private String name;
    private String climate;
    private String terrain;
    private Integer films;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClimate() { return climate; }
    public void setClimate(String climate) { this.climate = climate; }

    public String getTerrain() { return terrain; }
    public void setTerrain(String terrain) { this.terrain = terrain; }

    public Integer getFilms() { return films; }
    public void setFilms(Integer films) { this.films = films; }

    public Planet toModel() {
        Planet planet = new Planet();
        planet.setName(this.getName());
        planet.setClimate(this.getClimate());
        planet.setTerrain(this.getTerrain());
        planet.setFilms(this.getFilms());
        return planet;
    }
}