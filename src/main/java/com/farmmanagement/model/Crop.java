package com.farmmanagement.model;

public class Crop {
    private String id;
    private String name;
    private String type;
    private double areaInAcres;
    private String plantingDate;
    private String harvestDate;

    public Crop(String id, String name, String type, double areaInAcres, String plantingDate, String harvestDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.areaInAcres = areaInAcres;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAreaInAcres() {
        return areaInAcres;
    }

    public void setAreaInAcres(double areaInAcres) {
        this.areaInAcres = areaInAcres;
    }

    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", areaInAcres=" + areaInAcres +
                ", plantingDate='" + plantingDate + '\'' +
                ", harvestDate='" + harvestDate + '\'' +
                '}';
    }
}
