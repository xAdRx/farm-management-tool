package com.farmmanagement.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testAnimalCreation() {
        Animal animal = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        
        assertEquals("A001", animal.getId());
        assertEquals("Bessie", animal.getName());
        assertEquals("Cow", animal.getType());
        assertEquals(5, animal.getAge());
        assertEquals(1200.0, animal.getWeight());
    }

    @Test
    void testAnimalSetters() {
        Animal animal = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        
        animal.setId("A002");
        animal.setName("Daisy");
        animal.setType("Bull");
        animal.setAge(6);
        animal.setWeight(1300.0);

        assertEquals("A002", animal.getId());
        assertEquals("Daisy", animal.getName());
        assertEquals("Bull", animal.getType());
        assertEquals(6, animal.getAge());
        assertEquals(1300.0, animal.getWeight());
    }

    @Test
    void testAnimalToString() {
        Animal animal = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
        String expected = "Animal{id='A001', name='Bessie', type='Cow', age=5, weight=1200.0}";
        
        assertEquals(expected, animal.toString());
    }
}
