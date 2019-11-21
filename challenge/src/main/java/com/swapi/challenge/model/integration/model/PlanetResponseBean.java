package com.swapi.challenge.model.integration.model;

import com.swapi.challenge.model.Planet;

import java.util.ArrayList;

public class PlanetResponseBean {

    private String name;
    private String climate;
    private String terrain;
    private ArrayList<String> films;

    public String getName() { return name; }

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

    public ArrayList<String> getFilms() { return films; }

    public void setFilms(ArrayList<String> films) { this.films = films; }

    @Override
    public String toString() {
        return "PlanetRequest{" +
                "name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", terrain='" + terrain + '\'' +
                ", films='" + films + '\'' +
                '}';
    }

}