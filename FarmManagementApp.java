package com.farmmanagement;
package com.farmmanagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    public Field(String fieldName, Double areaInHectares, Integer classOfSoil) {
        this.fieldName = fieldName;
        this.areaInHectares = areaInHectares;
        this.crop = "";
        this.isSeeded = false;
        this.isFertilized = false;
        this.isLimed = false;
        this.growthStage = 0;
        this.classOfSoil = classOfSoil;
        this.predictedYield = 0.0;
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
    private Double totalAreaInHectares;
    private List<Field> fields;

    public Farm(String name, String location) {
        this.name = name;
        this.location = location;
        this.fields = new ArrayList<>();
        this.totalAreaInHectares = 0.0;
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

    public void removeField(Field field) {
        this.fields.remove(field);
        changeTotalAreaInHectares((field.getAreaInHectares())*(-1));
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
    private static Farm currentFarm = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=== Farm Management Tool ===");
        System.out.println("=== 126031 ===");
        System.out.println("=== Adam Rajs ===");
        System.out.println();

        while (running) {
            System.out.println("Menu Options:");
            System.out.println("1. Farm management");
            System.out.println("2. Field management");
            System.out.println("3. Status menu");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleFarmManagement();
                    break;
                case 2:
                    handleFieldManagement();
                    break;
                case 3:
                    showStatus();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void saveFarmInfo() {
        // Placeholder for saving farm info to storage
        System.out.println("Saving farm information... (not implemented)");
    }

    private static void loadFarmInfo() {
        // Placeholder for loading farm info from storage
        System.out.println("Loading farm information... (not implemented)");
    }

    private static void createNewFarm() {
        System.out.print("Enter farm name: ");
        String name = scanner.nextLine();
        System.out.print("Enter farm location: ");
        String location = scanner.nextLine();
        currentFarm = new Farm(name, location);
        System.out.println("New farm created successfully!");
    }

    private static void editFarmInfo() {
        System.out.println("What information you want to change:");
        System.out.println("1. Change farm name [" + currentFarm.getName() + "]: ");
        System.out.println("2. Change farm location [" + currentFarm.getLocation() + "]: ");
        System.out.println("0. Back to farm management menu");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                System.out.print("Enter new farm name: ");
                String name = scanner.nextLine();
                currentFarm.setName(name);
                System.out.println("Farm name updated successfully!");
                break;
            case 2:
                System.out.print("Enter new farm location: ");
                String location = scanner.nextLine();
                currentFarm.setLocation(location);
                System.out.println("Farm location updated successfully!");
                break;
            case 0:
                return;
        }
    }

    private static void handleFarmManagement() {
        if (currentFarm == null) {
            createNewFarm();
        } else {
            System.out.println("Current farm: " + currentFarm.getName());
            System.out.println("1. Create new farm");
            System.out.println("2. Change farm information");
            System.out.println("3. Load farm information");
            System.out.println("0. Back to main menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createNewFarm();
                    break;
                case 2:
                    editFarmInfo();
                    break;
                case 3:
                    loadFarmInfo();
                    break;
            }
        }
    }

    private static void handleFieldManagement() {
        if (currentFarm == null) {
            System.out.println("Please create a farm first!");
            return;
        }

        System.out.println("Field Management:");
        System.out.println("1. Add new field");
        System.out.println("2. Remove field");
        System.out.println("3. View all fields");
        System.out.println("0. Back to main menu");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter field name: ");
                String fieldName = scanner.nextLine();
                System.out.print("Enter area in hectares: ");
                Double area = scanner.nextDouble();
                System.out.print("Enter class of soil (1 - best, 6 - worst): ");
                Double classOfSoil = scanner.nextInt();
                currentFarm.addField(new Field(fieldName, area, classOfSoil));
                System.out.println("Field added successfully!");
                break;
            case 2:
                System.out.println("Fields:");
                for (Field field : currentFarm.getFields()) {
                    System.out.println(field.getFieldName());
                }
                System.out.print("Enter field name you want to remove: ");
                String fieldName = scanner.nextLine();
                fieldToRemove = from currentFarm.getFields()
                        .stream()
                        .filter(f -> f.getFieldName().equals(fieldName))
                        .findFirst()
                        .orElse(null);
                currentFarm.removeField(fieldToRemove);
                System.out.println("Field removed successfully!");
                break;
            case 3:
                System.out.println("Fields:");
                for (Field field : currentFarm.getFields()) {
                    System.out.println(field);
                }
                break;
        }
    }

    private static void showStatus() {
        if (currentFarm == null) {
            System.out.println("Please create a farm first!");
            return;
        }

        System.out.println("=== Farm Status ===");
        System.out.println("Farm: " + currentFarm.getName());
        System.out.println("Location: " + currentFarm.getLocation());
        System.out.println("Total Area: " + currentFarm.getTotalAreaInHectares() + " hectares");
        System.out.println("Number of fields: " + currentFarm.getFields().size());
    }
}
