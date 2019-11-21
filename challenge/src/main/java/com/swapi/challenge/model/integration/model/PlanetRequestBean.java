package com.swapi.challenge.model.integration.model;

import com.swapi.challenge.model.Planet;

public class PlanetRequestBean {

    private String name;
    // private String diameter;
    // private String rotation_period;
    // private String orbital_period;
    // private String gravity;
    // private String population;
    private String climate;
    private String terrain;
    // private String surface_water;
    // private ArrayList<String> residents;
    private Integer films;
    private boolean isIn;
    // private String url;
    // private String created;
    // private String edited;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // public String getDiameter() { return diameter; }
    // public void setDiameter(String diameter) { this.diameter = diameter; }

    // public String getRotation_period() { return rotation_period; }
    // public void setRotation_period(String rotation_period) { this.rotation_period = rotation_period; }
    
    // public String getOrbital_period() { return orbital_period; }
    // public void setOrbital_period(String orbital_period) { this.orbital_period = orbital_period; }

    // public String getGravity() { return gravity; }
    // public void setGravity(String gravity) { this.gravity = gravity; }

    // public String getPopulation() { return population; }
    // public void setPopulation(String population) { this.population = population; }

    public String getClimate() { return climate; }
    public void setClimate(String climate) { this.climate = climate; }

    public String getTerrain() { return terrain; }
    public void setTerrain(String terrain) { this.terrain = terrain; }

    // public String getSurface_water() { return surface_water; }
    // public void setSurface_water(String surface_water) { this.surface_water = surface_water; }

    // public ArrayList<String> getResidents() { return residents; }
    // public void setResidents(ArrayList<String> residents) { this.films = residents; }

    public Integer getFilms() { return films; }
    public void setFilms(Integer films) { this.films = films; }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    // public String getUrl() { return url; }
    // public void setUrl(String url) { this.url = url; }

    // public String getCreated() { return created; }
    // public void setCreated(String created) { this.created = created; }

    // public String getEdited() { return edited; }
    // public void setEdited(String edited) { this.edited = edited; }

    public Planet toModel() {
        Planet planet = new Planet();
        planet.setName(this.getName());
        planet.setClimate(this.getClimate());
        planet.setTerrain(this.getTerrain());
        planet.setFilms(this.getFilms());
        return planet;
    }
}