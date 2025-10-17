package com.farmmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private String name;
    private String location;
    private double totalAreaInAcres;
    private List<Animal> animals;
    private List<Crop> crops;

    public Farm(String name, String location, double totalAreaInAcres) {
        this.name = name;
        this.location = location;
        this.totalAreaInAcres = totalAreaInAcres;
        this.animals = new ArrayList<>();
        this.crops = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTotalAreaInAcres() {
        return totalAreaInAcres;
    }

    public void setTotalAreaInAcres(double totalAreaInAcres) {
        this.totalAreaInAcres = totalAreaInAcres;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
    }

    public List<Crop> getCrops() {
        return crops;
    }

    public void addCrop(Crop crop) {
        this.crops.add(crop);
    }

    public void removeCrop(Crop crop) {
        this.crops.remove(crop);
    }

    public int getTotalAnimals() {
        return animals.size();
    }

    public int getTotalCrops() {
        return crops.size();
    }

    @Override
    public String toString() {
        return "Farm{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", totalAreaInAcres=" + totalAreaInAcres +
                ", animals=" + animals.size() +
                ", crops=" + crops.size() +
                '}';
    }
}
