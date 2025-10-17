package com.farmmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FarmTest {
    private Farm farm;

    @BeforeEach
    void setUp() {
        farm = new Farm("Green Valley Farm", "California", 500.0);
    }

    @Test
    void testFarmCreation() {
        assertEquals("Green Valley Farm", farm.getName());
        assertEquals("California", farm.getLocation());
        assertEquals(500.0, farm.getTotalAreaInAcres());
        assertEquals(0, farm.getTotalAnimals());
        assertEquals(0, farm.getTotalCrops());
    }

    @Test
    void testAddAnimal() {
        Animal animal = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        farm.addAnimal(animal);
        
        assertEquals(1, farm.getTotalAnimals());
        assertTrue(farm.getAnimals().contains(animal));
    }

    @Test
    void testRemoveAnimal() {
        Animal animal = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        farm.addAnimal(animal);
        assertEquals(1, farm.getTotalAnimals());
        
        farm.removeAnimal(animal);
        assertEquals(0, farm.getTotalAnimals());
        assertFalse(farm.getAnimals().contains(animal));
    }

    @Test
    void testAddCrop() {
        Crop crop = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        farm.addCrop(crop);
        
        assertEquals(1, farm.getTotalCrops());
        assertTrue(farm.getCrops().contains(crop));
    }

    @Test
    void testRemoveCrop() {
        Crop crop = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        farm.addCrop(crop);
        assertEquals(1, farm.getTotalCrops());
        
        farm.removeCrop(crop);
        assertEquals(0, farm.getTotalCrops());
        assertFalse(farm.getCrops().contains(crop));
    }

    @Test
    void testFarmSetters() {
        farm.setName("Sunny Farm");
        farm.setLocation("Texas");
        farm.setTotalAreaInAcres(750.0);

        assertEquals("Sunny Farm", farm.getName());
        assertEquals("Texas", farm.getLocation());
        assertEquals(750.0, farm.getTotalAreaInAcres());
    }

    @Test
    void testFarmWithMultipleAnimalsAndCrops() {
        Animal cow = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        Animal chicken = new Animal("A002", "Clucky", "Chicken", 2, 5.5);
        Crop wheat = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        Crop corn = new Crop("C002", "Sweet Corn", "Vegetable", 50.0, "2024-05-01", "2024-09-01");

        farm.addAnimal(cow);
        farm.addAnimal(chicken);
        farm.addCrop(wheat);
        farm.addCrop(corn);

        assertEquals(2, farm.getTotalAnimals());
        assertEquals(2, farm.getTotalCrops());
    }
}
