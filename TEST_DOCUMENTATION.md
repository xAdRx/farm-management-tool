# Farm Management Tool - Test Documentation

## Test Suite Overview

Comprehensive JUnit 5 test suite for the Farm Management Application with **36 test cases** covering:

### Test Categories

#### 1. **Crop Tests** (1 test)
- Crop creation and all getter methods validation

#### 2. **Field Tests** (11 tests)
- Field creation with default values
- Field setters (name, area, soil class, seeded, fertilized, limed)
- Soil class boundary validation (minimum and maximum)
- Predicted yield calculations:
  - Not seeded fields (should return 0)
  - Seeded only fields
  - Seeded and fertilized fields
  - Fully treated fields (seeded, fertilized, limed)
  - Different soil classes (1-6)
- Field toString method

#### 3. **Farm Tests** (12 tests)
- Farm creation and getters
- Farm setters (name, location)
- Adding single and multiple fields
- Adding null fields (edge case)
- Removing fields
- Removing null fields (edge case)
- Removing non-existent fields (edge case)
- Adding single and multiple crops
- Adding null crops (edge case)
- Farm toString method
- Immutability of field and crop lists

#### 4. **Integration Tests** (6 tests)
- Complete farm workflow (end-to-end)
- Changing crop on field
- Yield recalculation after area change
- Testing all soil classes systematically
- Revenue calculation validation

## Running the Tests

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Using Maven

```bash
# Run all tests
mvn test

# Run tests with verbose output
mvn test -X

# Run specific test class
mvn test -Dtest=FarmManagementAppTest

# Run specific test method
mvn test -Dtest=FarmManagementAppTest#testCropCreation
```

### Using IDE
Most modern IDEs (IntelliJ IDEA, Eclipse, VS Code with Java extensions) support JUnit 5:
1. Right-click on `FarmManagementAppTest.java`
2. Select "Run Tests" or "Run FarmManagementAppTest"

## Test Coverage

The test suite covers:

✅ **Crop Class**
- Constructor and all getters
- Expected yield, prices, and cost parameters

✅ **Field Class**
- Constructor with default values
- All setters and getters
- Boundary validation (soil class 1-6)
- Yield calculation algorithm with various scenarios:
  - Base yield by soil class (1.2, 1.0, 0.8, 0.6, 0.4, 0.2)
  - Fertilizer modifier (+20%)
  - Lime modifier (+10%)
  - Seeding requirement
- State management (seeded, fertilized, limed)

✅ **Farm Class**
- Constructor and basic properties
- Field management (add, remove, edit)
- Crop management (add, list)
- Total area calculation
- Collection immutability

✅ **Business Logic**
- Yield calculation formula validation
- Revenue potential calculations
- Edge cases and null handling
- State transitions

## Test Results Summary

All 36 tests validate:
- ✅ Object creation and initialization
- ✅ Data encapsulation (getters/setters)
- ✅ Business logic (yield calculations)
- ✅ Boundary conditions
- ✅ Edge cases (null handling)
- ✅ Integration scenarios
- ✅ Immutability where required
- ✅ State management

## Example Test Execution

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running FarmManagementAppTest
[INFO] Tests run: 36, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 36, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Key Test Scenarios

### Yield Calculation Example
```java
// Field: 10 hectares, Soil Class 2, Wheat crop
// Seeded + Fertilized + Limed
// Expected: 5.2 * 10 * 1.0 * 1.3 / 10 = 6.76 tons
```

### Revenue Calculation Example
```java
// Yield: 6.76 tons
// Price: 775 PLN/ton
// Revenue: 6.76 * 775 = 5,239 PLN
```

## Project Structure

```
farm-management-tool/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   └── java/
│   │       └── FarmManagementApp.java
│   └── test/
│       └── java/
│           └── FarmManagementAppTest.java
├── FarmManagementApp.java           # Original source
├── FarmManagementAppTest.java       # Original tests
├── README.md                        # Project documentation
├── TEST_DOCUMENTATION.md            # This file
└── Architecture.drawio              # Architecture diagram
```

## Continuous Integration

To integrate with CI/CD pipelines:

```yaml
# Example GitHub Actions
- name: Run tests
  run: mvn clean test

# Example Jenkins
sh 'mvn clean test'
```

## Future Test Improvements

Consider adding:
- Performance tests for large farms (1000+ fields)
- Parameterized tests for soil classes
- Mock tests for user input handling
- Integration tests with file I/O
- Test coverage reports (JaCoCo)

## Author
Adam Rajs (126031)
