package com.farmmanagement.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CropTest {

    @Test
    void testCropCreation() {
        Crop crop = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        
        assertEquals("C001", crop.getId());
        assertEquals("Winter Wheat", crop.getName());
        assertEquals("Grain", crop.getType());
        assertEquals(100.0, crop.getAreaInAcres());
        assertEquals("2024-10-01", crop.getPlantingDate());
        assertEquals("2025-06-01", crop.getHarvestDate());
    }

    @Test
    void testCropSetters() {
        Crop crop = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        
        crop.setId("C002");
        crop.setName("Sweet Corn");
        crop.setType("Vegetable");
        crop.setAreaInAcres(50.0);
        crop.setPlantingDate("2024-05-01");
        crop.setHarvestDate("2024-09-01");

        assertEquals("C002", crop.getId());
        assertEquals("Sweet Corn", crop.getName());
        assertEquals("Vegetable", crop.getType());
        assertEquals(50.0, crop.getAreaInAcres());
        assertEquals("2024-05-01", crop.getPlantingDate());
        assertEquals("2024-09-01", crop.getHarvestDate());
    }

    @Test
    void testCropToString() {
        Crop crop = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
        String expected = "Crop{id='C001', name='Winter Wheat', type='Grain', areaInAcres=100.0, plantingDate='2024-10-01', harvestDate='2025-06-01'}";
        
        assertEquals(expected, crop.toString());
    }
}
