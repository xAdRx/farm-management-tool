# Farm Management Tool

A Java-based application for managing farm operations including animals and crops.

## Features

- Manage farm information (name, location, area)
- Track animals (ID, name, type, age, weight)
- Track crops (ID, name, type, area, planting/harvest dates)
- Add and remove animals and crops
- View farm summary statistics

## Project Structure

```
farm-management-tool/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── farmmanagement/
│   │               ├── FarmManagementApp.java
│   │               └── model/
│   │                   ├── Animal.java
│   │                   ├── Crop.java
│   │                   └── Farm.java
│   └── test/
│       └── java/
│           └── com/
│               └── farmmanagement/
│                   └── model/
│                       ├── AnimalTest.java
│                       ├── CropTest.java
│                       └── FarmTest.java
├── pom.xml
└── README.md
```

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

To build the project, run:

```bash
mvn clean compile
```

## Running the Application

To run the application, use:

```bash
mvn exec:java -Dexec.mainClass="com.farmmanagement.FarmManagementApp"
```

Or build a JAR and run it:

```bash
mvn clean package
java -jar target/farm-management-tool-1.0.0.jar
```

## Running Tests

To run all tests:

```bash
mvn test
```

## Domain Model

### Farm
- **name**: Name of the farm
- **location**: Geographic location
- **totalAreaInAcres**: Total farm area
- **animals**: List of animals on the farm
- **crops**: List of crops on the farm

### Animal
- **id**: Unique identifier
- **name**: Animal's name
- **type**: Type of animal (e.g., Cow, Chicken, Pig)
- **age**: Age in years
- **weight**: Weight in pounds

### Crop
- **id**: Unique identifier
- **name**: Crop name
- **type**: Type of crop (e.g., Grain, Vegetable, Fruit)
- **areaInAcres**: Area occupied by the crop
- **plantingDate**: Date when planted
- **harvestDate**: Expected harvest date

## Example Usage

```java
// Create a new farm
Farm myFarm = new Farm("Green Valley Farm", "California", 500.0);

// Add animals
Animal cow = new Animal("A001", "Bessie", "Cow", 5, 1200.0);
myFarm.addAnimal(cow);

// Add crops
Crop wheat = new Crop("C001", "Winter Wheat", "Grain", 100.0, "2024-10-01", "2025-06-01");
myFarm.addCrop(wheat);

// Get statistics
System.out.println("Total animals: " + myFarm.getTotalAnimals());
System.out.println("Total crops: " + myFarm.getTotalCrops());
```

## Future Enhancements

- Persistent storage (database integration)
- Web-based user interface
- Reporting and analytics
- Financial tracking (expenses, revenue)
- Weather integration
- Task scheduling and reminders

## License

This project is open source and available for educational purposes.