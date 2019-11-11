package com.swapi.challenge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;


@Component
@Document(collection = "planets")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Planet {

    @Id
    private String id;
    private String name;
    private String climate;
    private String terrain;
    private boolean isDb = true;
    
    @Transient
    private Integer films;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getFilms() { return films; }

    public void setFilms(Integer films) { this.films = films; }

    public boolean isDb() {
        return isDb;
    }

    public void setDb(boolean db) {
        isDb = db;
    }
}