package com.swapi.challenge.controller.response;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
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

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute
    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    @DynamoDBAttribute
    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    @DynamoDBAttribute
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
}
