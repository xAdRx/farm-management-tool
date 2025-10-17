package com.farmmanagement;
package com.farmmanagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Field{
    private String fieldName;
    private Double areaInHectares;
    private String crop;
    private Boolean isSeeded;
    private Boolean isFertilized;
    private Boolean isLimed;
    private Integer growthStage;
    private Integer classOfSoil;
    private Double predictedYield;

    public Field(String fieldName, Double areaInHectares) {
        this.fieldName = fieldName;
        this.areaInHectares = areaInHectares;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Double getAreaInHectares() {
        return areaInHectares;
    }

    public void setAreaInHectares(Double areaInHectares) {
        this.areaInHectares = areaInHectares;
    }

    public void updateGrowthStage(int percentage) {
        if (percentage < 0) {
            this.growthStage = 0; // Minimum growth stage
        } else if (percentage > 100) {
            this.growthStage = 100; // Maximum growth stage
        } else {
            this.growthStage = percentage; // Set to the given percentage
        }
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldName='" + fieldName + '\'' +
                ", areaInHectares=" + areaInHectares +
                '}';
    }
}

public class Farm {
    private String name;
    private String location;
    private Double totalAreaInHectares
    private List<Field> fields;

    public Farm(String name, String location) {
        this.name = name;
        this.location = location;
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

    public double getTotalAreaInHectares() {
        return this.totalAreaInHectares;
    }

    public void changeTotalAreaInHectares(double areaInHectares) {
        this.totalAreaInHectares += totalAreaInHectares;
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public void addField(Field field) {
        this.fields.add(field);
        changeTotalAreaInHectares(field.getAreaInHectares());
    }


    @Override
    public String toString() {
        return "Farm{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", totalAreaInHectares=" + totalAreaInHectares +
                '}';
    }
}

public class FarmManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Farm Management Tool ===");
        System.out.println();

        // Create a new farm
        Farm myFarm = new Farm("Rajska Farma", "Ignacow");
        System.out.println("Created farm: " + myFarm.getName());
        System.out.println("Location: " + myFarm.getLocation());
        System.out.println();

        myFarm.addCrop(wheat);
        myFarm.addCrop(corn);

        System.out.println("Added crops:");
        for (Crop crop : myFarm.getCrops()) {
            System.out.println("  - " + crop);
        }
        System.out.println();

        // Display farm summary
        System.out.println("=== Farm Summary ===");
        System.out.println("Total animals: " + myFarm.getTotalAnimals());
        System.out.println("Total crops: " + myFarm.getTotalCrops());
        System.out.println();
        System.out.println("Farm Management Tool running successfully!");
    }
}
