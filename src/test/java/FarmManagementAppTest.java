import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test suite for Farm Management Application
 * Tests the core functionality of Crop, Field, and Farm classes
 */
public class FarmManagementAppTest {

    private FarmManagementApp.Farm testFarm;
    private FarmManagementApp.Crop testCropWheat;
    private FarmManagementApp.Crop testCropCorn;
    private FarmManagementApp.Field testField;

    @BeforeEach
    void setUp() {
        // Initialize test data before each test
        testFarm = new FarmManagementApp.Farm("Test Farm", "Test Location");
        testCropWheat = new FarmManagementApp.Crop("Pszenica", 5.2, 775.0, 150.0, 240.0, 220.0);
        testCropCorn = new FarmManagementApp.Crop("Kukurydza", 7.3, 896.0, 225.0, 320.0, 350.0);
        testField = new FarmManagementApp.Field("TestField", 10.0, 2, testCropWheat);
    }

    // ========== CROP TESTS ==========

    @Test
    @DisplayName("Test Crop creation and getters")
    void testCropCreation() {
        assertEquals("Pszenica", testCropWheat.getName());
        assertEquals(5.2, testCropWheat.getExpectedYieldPerHectare(), 0.01);
        assertEquals(775.0, testCropWheat.getPriceOnSell(), 0.01);
        assertEquals(150.0, testCropWheat.getFertilizerCostPerHectare(), 0.01);
        assertEquals(240.0, testCropWheat.getLimeCostPerHectare(), 0.01);
        assertEquals(220.0, testCropWheat.getSeedCostPerHectare(), 0.01);
    }

    // ========== FIELD TESTS ==========

    @Test
    @DisplayName("Test Field creation with default values")
    void testFieldCreation() {
        assertEquals("TestField", testField.getFieldName());
        assertEquals(10.0, testField.getAreaInHectares(), 0.01);
        assertEquals(2, testField.getClassOfSoil());
        assertFalse(testField.isSeeded());
        assertFalse(testField.isFertilized());
        assertFalse(testField.isLimed());
        assertEquals(0.0, testField.getPredictedYield(), 0.01);
    }

    @Test
    @DisplayName("Test Field setters")
    void testFieldSetters() {
        testField.setFieldName("NewName");
        assertEquals("NewName", testField.getFieldName());

        testField.setAreaInHectares(15.5);
        assertEquals(15.5, testField.getAreaInHectares(), 0.01);

        testField.setClassOfSoil(3);
        assertEquals(3, testField.getClassOfSoil());

        testField.setIsSeeded(true);
        assertTrue(testField.isSeeded());

        testField.setIsFertilized(true);
        assertTrue(testField.isFertilized());

        testField.setIsLimed(true);
        assertTrue(testField.isLimed());
    }

    @Test
    @DisplayName("Test soil class boundary validation (below minimum)")
    void testSoilClassBelowMinimum() {
        testField.setClassOfSoil(0);
        assertEquals(1, testField.getClassOfSoil());

        testField.setClassOfSoil(-5);
        assertEquals(1, testField.getClassOfSoil());
    }

    @Test
    @DisplayName("Test soil class boundary validation (above maximum)")
    void testSoilClassAboveMaximum() {
        testField.setClassOfSoil(7);
        assertEquals(6, testField.getClassOfSoil());

        testField.setClassOfSoil(10);
        assertEquals(6, testField.getClassOfSoil());
    }

    @Test
    @DisplayName("Test predicted yield calculation - not seeded")
    void testPredictedYieldNotSeeded() {
        testField.setIsSeeded(false);
        double yield = testField.calculatePredictedYield();
        assertEquals(0.0, yield, 0.01);
    }

    @Test
    @DisplayName("Test predicted yield calculation - seeded only")
    void testPredictedYieldSeededOnly() {
        testField.setIsSeeded(true);
        double yield = testField.calculatePredictedYield();
        // Class 2: baseYield = 1.0, no fertilizer/lime modifiers
        // yield = 5.2 * 10.0 * 1.0 * 1.0 / 10 = 5.2
        assertEquals(5.2, yield, 0.01);
    }

    @Test
    @DisplayName("Test predicted yield calculation - seeded and fertilized")
    void testPredictedYieldSeededAndFertilized() {
        testField.setIsSeeded(true);
        testField.setIsFertilized(true);
        double yield = testField.calculatePredictedYield();
        // Class 2: baseYield = 1.0, fertilized modifier = 1.2
        // yield = 5.2 * 10.0 * 1.0 * 1.2 / 10 = 6.24
        assertEquals(6.24, yield, 0.01);
    }

    @Test
    @DisplayName("Test predicted yield calculation - seeded, fertilized, and limed")
    void testPredictedYieldFullyTreated() {
        testField.setIsSeeded(true);
        testField.setIsFertilized(true);
        testField.setIsLimed(true);
        double yield = testField.calculatePredictedYield();
        // Class 2: baseYield = 1.0, fertilized + limed modifier = 1.3
        // yield = 5.2 * 10.0 * 1.0 * 1.3 / 10 = 6.76
        assertEquals(6.76, yield, 0.01);
    }

    @Test
    @DisplayName("Test predicted yield with different soil classes")
    void testPredictedYieldDifferentSoilClasses() {
        testField.setIsSeeded(true);
        
        // Class 1 (best)
        testField.setClassOfSoil(1);
        testField.calculatePredictedYield();
        assertEquals(6.24, testField.getPredictedYield(), 0.01);

        // Class 3
        testField.setClassOfSoil(3);
        testField.calculatePredictedYield();
        assertEquals(4.16, testField.getPredictedYield(), 0.01);

        // Class 6 (worst)
        testField.setClassOfSoil(6);
        testField.calculatePredictedYield();
        assertEquals(1.04, testField.getPredictedYield(), 0.01);
    }

    @Test
    @DisplayName("Test Field toString method")
    void testFieldToString() {
        testField.setIsSeeded(false);
        String result = testField.toString();
        assertTrue(result.contains("TestField"));
        assertTrue(result.contains("10.00"));
        assertTrue(result.contains("isSeeded = false"));
    }

    // ========== FARM TESTS ==========

    @Test
    @DisplayName("Test Farm creation")
    void testFarmCreation() {
        assertEquals("Test Farm", testFarm.getName());
        assertEquals("Test Location", testFarm.getLocation());
        assertEquals(0.0, testFarm.getTotalAreaInHectares(), 0.01);
        assertNotNull(testFarm.getFields());
        assertTrue(testFarm.getFields().isEmpty());
    }

    @Test
    @DisplayName("Test Farm setters")
    void testFarmSetters() {
        testFarm.setName("New Farm Name");
        assertEquals("New Farm Name", testFarm.getName());

        testFarm.setLocation("New Location");
        assertEquals("New Location", testFarm.getLocation());
    }

    @Test
    @DisplayName("Test adding field to farm")
    void testAddFieldToFarm() {
        testFarm.addField(testField);
        assertEquals(1, testFarm.getFields().size());
        assertEquals(10.0, testFarm.getTotalAreaInHectares(), 0.01);
    }

    @Test
    @DisplayName("Test adding multiple fields to farm")
    void testAddMultipleFields() {
        FarmManagementApp.Field field1 = new FarmManagementApp.Field("Field1", 10.0, 2, testCropWheat);
        FarmManagementApp.Field field2 = new FarmManagementApp.Field("Field2", 15.5, 3, testCropCorn);
        FarmManagementApp.Field field3 = new FarmManagementApp.Field("Field3", 20.0, 1, testCropWheat);

        testFarm.addField(field1);
        testFarm.addField(field2);
        testFarm.addField(field3);

        assertEquals(3, testFarm.getFields().size());
        assertEquals(45.5, testFarm.getTotalAreaInHectares(), 0.01);
    }

    @Test
    @DisplayName("Test adding null field to farm")
    void testAddNullField() {
        testFarm.addField(null);
        assertEquals(0, testFarm.getFields().size());
        assertEquals(0.0, testFarm.getTotalAreaInHectares(), 0.01);
    }

    @Test
    @DisplayName("Test removing field from farm")
    void testRemoveFieldFromFarm() {
        testFarm.addField(testField);
        assertEquals(1, testFarm.getFields().size());
        assertEquals(10.0, testFarm.getTotalAreaInHectares(), 0.01);

        testFarm.removeField(testField);
        assertEquals(0, testFarm.getFields().size());
        assertEquals(0.0, testFarm.getTotalAreaInHectares(), 0.01);
    }

    @Test
    @DisplayName("Test removing null field from farm")
    void testRemoveNullField() {
        testFarm.addField(testField);
        testFarm.removeField(null);
        assertEquals(1, testFarm.getFields().size());
    }

    @Test
    @DisplayName("Test removing non-existent field")
    void testRemoveNonExistentField() {
        testFarm.addField(testField);
        FarmManagementApp.Field otherField = new FarmManagementApp.Field("Other", 5.0, 3, testCropWheat);
        
        testFarm.removeField(otherField);
        assertEquals(1, testFarm.getFields().size());
        assertEquals(10.0, testFarm.getTotalAreaInHectares(), 0.01);
    }

    @Test
    @DisplayName("Test adding crop to farm")
    void testAddCropToFarm() {
        testFarm.addCrop(testCropWheat);
        assertEquals(1, testFarm.getCrops().size());
        assertEquals("Pszenica", testFarm.getCrops().get(0).getName());
    }

    @Test
    @DisplayName("Test adding multiple crops to farm")
    void testAddMultipleCrops() {
        testFarm.addCrop(testCropWheat);
        testFarm.addCrop(testCropCorn);
        assertEquals(2, testFarm.getCrops().size());
    }

    @Test
    @DisplayName("Test adding null crop to farm")
    void testAddNullCrop() {
        testFarm.addCrop(null);
        assertEquals(0, testFarm.getCrops().size());
    }

    @Test
    @DisplayName("Test Farm toString method")
    void testFarmToString() {
        String result = testFarm.toString();
        assertTrue(result.contains("Test Farm"));
        assertTrue(result.contains("Test Location"));
        assertTrue(result.contains("0.00"));
    }

    @Test
    @DisplayName("Test getFields returns unmodifiable list")
    void testGetFieldsUnmodifiable() {
        testFarm.addField(testField);
        List<FarmManagementApp.Field> fields = testFarm.getFields();
        
        assertThrows(UnsupportedOperationException.class, () -> {
            fields.add(new FarmManagementApp.Field("New", 5.0, 2, testCropWheat));
        });
    }

    @Test
    @DisplayName("Test getCrops returns unmodifiable list")
    void testGetCropsUnmodifiable() {
        testFarm.addCrop(testCropWheat);
        List<FarmManagementApp.Crop> crops = testFarm.getCrops();
        
        assertThrows(UnsupportedOperationException.class, () -> {
            crops.add(testCropCorn);
        });
    }

    // ========== INTEGRATION TESTS ==========

    @Test
    @DisplayName("Integration test: Complete farm workflow")
    void testCompleteFarmWorkflow() {
        // Add crops
        testFarm.addCrop(testCropWheat);
        testFarm.addCrop(testCropCorn);

        // Create and configure fields
        FarmManagementApp.Field field1 = new FarmManagementApp.Field("Field1", 10.0, 1, testCropWheat);
        field1.setIsSeeded(true);
        field1.setIsFertilized(true);
        field1.setIsLimed(true);

        FarmManagementApp.Field field2 = new FarmManagementApp.Field("Field2", 20.0, 3, testCropCorn);
        field2.setIsSeeded(true);
        field2.setIsFertilized(true);

        // Add fields to farm
        testFarm.addField(field1);
        testFarm.addField(field2);

        // Verify farm state
        assertEquals(2, testFarm.getFields().size());
        assertEquals(30.0, testFarm.getTotalAreaInHectares(), 0.01);

        // Calculate yields
        double yield1 = field1.calculatePredictedYield();
        double yield2 = field2.calculatePredictedYield();

        assertTrue(yield1 > 0);
        assertTrue(yield2 > 0);
    }

    @Test
    @DisplayName("Test changing crop on field")
    void testChangingCropOnField() {
        testField.setCrop(testCropWheat);
        assertEquals("Pszenica", testField.getCrop().getName());

        testField.setCrop(testCropCorn);
        assertEquals("Kukurydza", testField.getCrop().getName());
    }

    @Test
    @DisplayName("Test yield calculation after changing field area")
    void testYieldAfterAreaChange() {
        testField.setIsSeeded(true);
        testField.setAreaInHectares(10.0);
        double yield1 = testField.calculatePredictedYield();

        testField.setAreaInHectares(20.0);
        double yield2 = testField.calculatePredictedYield();

        assertEquals(yield1 * 2, yield2, 0.01);
    }

    @Test
    @DisplayName("Test field with all soil classes")
    void testAllSoilClasses() {
        testField.setIsSeeded(true);
        testField.setIsFertilized(true);
        testField.setIsLimed(true);

        for (int soilClass = 1; soilClass <= 6; soilClass++) {
            testField.setClassOfSoil(soilClass);
            double yield = testField.calculatePredictedYield();
            assertTrue(yield > 0, "Yield should be positive for soil class " + soilClass);
        }
    }

    @Test
    @DisplayName("Test revenue calculation potential")
    void testRevenueCalculation() {
        testField.setIsSeeded(true);
        testField.setIsFertilized(true);
        testField.setIsLimed(true);
        testField.setAreaInHectares(10.0);
        testField.setClassOfSoil(2);

        double yield = testField.calculatePredictedYield();
        double revenue = yield * testField.getCrop().getPriceOnSell();

        assertTrue(revenue > 0);
        assertEquals(yield * 775.0, revenue, 0.01);
    }
}
