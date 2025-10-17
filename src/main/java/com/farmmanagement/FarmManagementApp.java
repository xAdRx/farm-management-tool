package com.farmmanagement;

import com.farmmanagement.model.Animal;
import com.farmmanagement.model.Crop;
import com.farmmanagement.model.Farm;

public class FarmManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Farm Management Tool ===");
        System.out.println();

        // Create a new farm
        Farm myFarm = new Farm("Green Valley Farm", "California", 500.0);
        System.out.println("Created farm: " + myFarm.getName());
        System.out.println("Location: " + myFarm.getLocation());
        System.out.println("Total area: " + myFarm.getTotalAreaInAcres() + " acres");
        System.out.println();

        // Add some animals
        Animal cow1 = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        Animal cow2 = new Animal("A002", "Daisy", "Cow", 3, 1100.0);
        Animal chicken1 = new Animal("A003", "Clucky", "Chicken", 2, 5.5);

        myFarm.addAnimal(cow1);
        myFarm.addAnimal(cow2);
        myFarm.addAnimal(chicken1);

        System.out.println("Added animals:");
        for (Animal animal : myFarm.getAnimals()) {
            System.out.println("  - " + animal);
        }
        System.out.println();

        // Add some crops
        Crop wheat = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        Crop corn = new Crop("C002", "Sweet Corn", "Vegetable", 50.0, "2024-05-01", "2024-09-01");

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
