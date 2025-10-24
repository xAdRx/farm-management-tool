import java.util.ArrayList; // Allows to use Array for fields
import java.util.Collections; // Allows to return unmodifiable list
import java.util.List;  // Allows to use List for fields
import java.util.Scanner; // Allows to read user input

public class FarmManagementApp {
    private static Farm currentFarm = null;
    private static Scanner scanner = new Scanner(System.in);

    public static class Crop {
        private String name;
        private Double expectedYieldPerHectare;
        private Double fertilizerCostPerHectare;
        private Double limeCostPerHectare;
        private Double seedCostPerHectare;

        public Crop(String name, Double expectedYieldPerHectare,
                    Double fertilizerCostPerHectare, Double limeCostPerHectare, Double seedCostPerHectare) {
            this.name = name;
            this.expectedYieldPerHectare = expectedYieldPerHectare;
            this.fertilizerCostPerHectare = fertilizerCostPerHectare;
            this.limeCostPerHectare = limeCostPerHectare;
            this.seedCostPerHectare = seedCostPerHectare;
        }

        public String getName() {
            return name;
        }

        public Double getExpectedYieldPerHectare() {
            return expectedYieldPerHectare;
        }

        public Double getFertilizerCostPerHectare() {
            return fertilizerCostPerHectare;
        }

        public Double getLimeCostPerHectare() {
            return limeCostPerHectare;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setExpectedYieldPerHectare(Double expectedYieldPerHectare) {
            this.expectedYieldPerHectare = expectedYieldPerHectare;
        }

        public void setFertilizerCostPerHectare(Double fertilizerCostPerHectare) {
            this.fertilizerCostPerHectare = fertilizerCostPerHectare;
        }

        public void setLimeCostPerHectare(Double limeCostPerHectare) {
            this.limeCostPerHectare = limeCostPerHectare;
        }

        public Double getSeedCostPerHectare() {
            return seedCostPerHectare;
        }

        public void setSeedCostPerHectare(Double seedCostPerHectare) {
            this.seedCostPerHectare = seedCostPerHectare;
        }
    }

    public static class Field {
        private String fieldName;
        private Double areaInHectares;
        private Crop crop;
        private Boolean isSeeded;
        private Boolean isFertilized;
        private Boolean isLimed;
        private Integer classOfSoil;
        private Double predictedYield;

        public Field(String fieldName, Double areaInHectares, Integer classOfSoil, Crop crop) {
            this.fieldName = fieldName;
            this.areaInHectares = areaInHectares;
            this.crop = crop;
            this.isSeeded = false;
            this.isFertilized = false;
            this.isLimed = false;
            this.classOfSoil = classOfSoil;
            this.predictedYield = 0.0;
        }

        @Override
        public String toString() {
            if (!isSeeded) {
                return "  - Name = '" + fieldName + '\'' +
                        ", areaInHectares = " + areaInHectares +
                        ", classOfSoil = " + classOfSoil +
                        ", isSeeded = " + isSeeded +
                        ", isFertilized = " + isFertilized +
                        ", isLimed = " + isLimed +
                        ", predictedYield = " + predictedYield+" tons";
            }
            return "  - Name = '" + fieldName + '\'' +
                    ", areaInHectares = " + areaInHectares +
                    ", classOfSoil = " + classOfSoil +
                    ", isSeeded = " + isSeeded + " " + crop.getName() +
                    ", isFertilized = " + isFertilized +
                    ", isLimed = " + isLimed +
                    ", predictedYield = " + predictedYield+" tons";
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

        public Crop getCrop() {
            return crop;
        }

        public void setCrop(Crop crop) {
            this.crop = crop;
        }

        public Boolean getIsSeeded() {
            return isSeeded;
        }

        public void setIsSeeded(Boolean isSeeded) {
            this.isSeeded = isSeeded;
        }

        public Boolean getIsFertilized() {
            return isFertilized;
        }

        public void setIsFertilized(Boolean isFertilized) {
            this.isFertilized = isFertilized;
        }

        public Boolean getIsLimed() {
            return isLimed;
        }

        public void setIsLimed(Boolean isLimed) {
            this.isLimed = isLimed;
        }

        public Integer getClassOfSoil() {
            return classOfSoil;
        }

        public void setClassOfSoil(Integer classOfSoil) {
            this.classOfSoil = classOfSoil;
        }

        public Double getPredictedYield() {
            return predictedYield;
        }

        public void setPredictedYield(Double predictedYield) {
            this.predictedYield = predictedYield;
        }

        public Double calculatePredictedYield() {
            double baseYield;
            switch (classOfSoil) {
                case 1 -> baseYield = 1.2;
                case 2 -> baseYield = 1.0;
                case 3 -> baseYield = 0.8;
                case 4 -> baseYield = 0.6;
                case 5 -> baseYield = 0.4;
                case 6 -> baseYield = 0.2;
                default -> baseYield = 0.2;
            }

            double yieldModifier = 1.0;
            if (isFertilized) yieldModifier += 0.2;
            if (isLimed) yieldModifier += 0.1;
            if (!isSeeded) yieldModifier = 0;

            this.predictedYield = this.crop.expectedYieldPerHectare * areaInHectares * baseYield * yieldModifier / 10; // in kg
            return this.predictedYield;
        }
    }


    public static class Farm {
        private String name;
        private String location;
        private Double totalAreaInHectares;
        private List<Field> fields;
        private List<Crop> crops;

        public Farm(String name, String location) {
            this.name = name;
            this.location = location;
            this.fields = new ArrayList<>();
            this.crops = new ArrayList<>();
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
            this.totalAreaInHectares += areaInHectares;
        }

        public List<Field> getFields() {
            return Collections.unmodifiableList(fields);
        }

        public void addField(Field field) {
            if (field == null) return;
            field.calculatePredictedYield();
            this.fields.add(field);
            changeTotalAreaInHectares(field.getAreaInHectares());
        }

        public void removeField(Field field) {
            if (field == null) return;
            if (this.fields.remove(field)) {
                changeTotalAreaInHectares(-field.getAreaInHectares());
            }
        }

        public void editField(Field field) {
            if (field == null) return;
            boolean running = true;

            while (running){
                System.out.println("What do you want to edit?");
                System.out.println("1. Change field name [" + field.getFieldName() + "]: ");
                System.out.println("2. Change field classOfSoil [" + field.classOfSoil + "]: ");
                System.out.println("3. Change if field is fertilized [" + field.isFertilized + "]: ");
                System.out.println("4. Change if field is limed [" + field.isLimed + "]: ");
                if(field.isSeeded)
                    System.out.println("5. Change if field is seeded [" + field.isSeeded + " " + field.crop.getName() + "]: ");
                else
                    System.out.println("5. Change if field is seeded [" + field.isSeeded + "]: ");
                System.out.println("0. Back to field management menu");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter new field name: ");
                        String fieldName = scanner.next();
                        field.setFieldName(fieldName);
                        System.out.println("Field name updated successfully!");
                        System.out.println("");
                    }
                    case 2 -> {
                        System.out.print("Enter new class of soil (1 - best, 6 - worst): ");
                        int classOfSoil = scanner.nextInt();
                        field.setClassOfSoil(classOfSoil);
                        System.out.println("Field class of soil updated successfully!");
                        field.calculatePredictedYield();
                        System.out.println("");
                    }
                    case 3 -> {
                        if(field.isFertilized){
                            field.setIsFertilized(false);
                            System.out.println("Field is now marked as not fertilized.");
                        } else {
                            field.setIsFertilized(true);
                            System.out.println("Field is now marked as fertilized.");
                        }
                        field.calculatePredictedYield();
                        System.out.println("");
                    }
                    case 4 -> {
                        if(field.isLimed){
                            field.setIsLimed(false);
                            System.out.println("Field is now marked as not limed.");
                        } else {
                            field.setIsLimed(true);
                            System.out.println("Field is now marked as limed.");
                        }
                        field.calculatePredictedYield();
                        System.out.println("");
                    }
                    case 5 -> {
                        if(field.isSeeded){
                            field.setIsSeeded(false);
                            System.out.println("Field is now marked as not seeded.");
                        } else {
                            if (this.crops.isEmpty()) {
                                System.out.println("No crops available to seed.");
                            } else {
                                int cropChoice;
                                while (true) {
                                    System.out.println("What would you like to seed?");
                                    for (int i = 0; i < this.crops.size(); i++) {
                                        System.out.println((i + 1) + ". " + this.crops.get(i).getName());
                                    }
                                    System.out.print("Your choice: ");
                                    if (!scanner.hasNextInt()) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        scanner.next(); // consume invalid token
                                        continue;
                                    }
                                    cropChoice = scanner.nextInt();
                                    if (cropChoice >= 1 && cropChoice <= this.crops.size()) break;
                                    System.out.println("Invalid crop choice.");
                                }
                                field.setCrop(this.crops.get(cropChoice - 1));
                                field.setIsSeeded(true);
                                System.out.println("Field is now marked as seeded.");
                            }
                        }
                        field.calculatePredictedYield();
                        System.out.println("");
                    }
                    case 0 -> {
                        running = false;
                        System.out.println("");
                    }
                    default -> {
                        System.out.println("Invalid option.");
                        System.out.println("");
                    }
                }
            }
        }

        private void listAllFields() {
            System.out.println("Fields in the farm:");
            // iterate this farm's fields (use accessor)
            for (Field field : this.getFields()) {
                System.out.println(field);
            }
            System.out.println();
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

    private static void randomizeFarmInfo() {
        String[] farmNames = {"Sloneczne Hektary", "Farma na wzgorzu", "Ranczo w dolinie", "Lesna ostoja", "Zielony Zakatek"};
        String[] locations = {"Mazowieckie", "Malopolskie", "Wielkopolskie", "Lubelskie", "Podlaskie"};

        String randomName = farmNames[(int) (Math.random() * farmNames.length)];
        String randomLocation = locations[(int) (Math.random() * locations.length)];

        currentFarm = new Farm(randomName, randomLocation);

        System.out.println("Farm information randomized successfully!");
    }

    private static void addDefaultCropsToFarm() {
        System.out.println("Adding crops to the farm.");
        currentFarm.crops.add(new Crop("Pszenica", 120.0, 300.0, 150.0, 100.0));
        currentFarm.crops.add(new Crop("Kukurydza", 120.0, 300.0, 150.0, 100.0));
        currentFarm.crops.add(new Crop("Jeczmien", 120.0, 300.0, 150.0, 100.0));
        currentFarm.crops.add(new Crop("Rzepak", 120.0, 300.0, 150.0, 100.0));
        currentFarm.crops.add(new Crop("Trawa", 120.0, 300.0, 150.0, 100.0));
    }

    private static void createNewFarm() {
        System.out.println("Would you like to create farm manually or randomize it?");
        System.out.println("1. Manually");
        System.out.println("2. Randomly");
        System.out.println("0. Exit to farm managment menu");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> {
                System.out.println("Returning to farm management menu.");
            }
            case 1 -> {
                System.out.print("Enter farm name: ");
                String name = scanner.next();
                System.out.print("Enter farm location: ");
                String location = scanner.next();
                currentFarm = new Farm(name, location);
                System.out.println("New farm created successfully!");
                addDefaultCropsToFarm();
            }
            case 2 -> {
                randomizeFarmInfo();
                addDefaultCropsToFarm();
            }
            default -> {
                System.out.println("Invalid option.");
            }
        }
    }

    private static void editFarmInfo() {
        boolean running = true;
        while (running){
            System.out.println("What information you want to change:");
            System.out.println("1. Change farm name [" + currentFarm.getName() + "]: ");
            System.out.println("2. Change farm location [" + currentFarm.getLocation() + "]: ");
            System.out.println("0. Back to farm management menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new farm name: ");
                    String name = scanner.next();
                    currentFarm.setName(name);
                    System.out.println("Farm name updated successfully!");
                }
                case 2 -> {
                    System.out.print("Enter new farm location: ");
                    String location = scanner.next();
                    currentFarm.setLocation(location);
                    System.out.println("Farm location updated successfully!");
                }
                case 0 -> {
                    running = false;
                    System.out.println("Returning to farm management menu.");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void handleFarmManagement() {
        if (currentFarm == null) {
            createNewFarm();
        } else {
            boolean running = true;
            while (running) {
                System.out.println("Current farm: " + currentFarm.getName());
                System.out.println("1. Create new farm");
                System.out.println("2. Change farm information");
                System.out.println("0. Back to main menu");
                System.out.print("Your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> createNewFarm();
                    case 2 -> editFarmInfo();
                    case 0 -> {
                        running = false;
                        System.out.println("Returning to farm management menu.");
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    private static void handleFieldManagement() {
        if (currentFarm == null) {
            System.out.println("Please create a farm first!");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("Field Management:");
            System.out.println("1. Add new field");
            System.out.println("2. Edit field");
            System.out.println("3. Remove field");
            System.out.println("4. View all fields");
            System.out.println("0. Back to main menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    boolean uniqueFieldName = true;
                    boolean fieldSizeInRange = true;
                    String fieldName = "";
                    Double area = 0.0;
                    while (uniqueFieldName) {
                        System.out.print("Enter unique field name: ");
                        fieldName = scanner.next();
                        uniqueFieldName = false;
                        for (Field f : currentFarm.getFields()) {
                            if (f.getFieldName().equals(fieldName)) {
                                System.out.println("Field name already exists. Please enter a unique field name.");
                                uniqueFieldName = true;
                                break;
                            }
                        }
                    }
                    while(fieldSizeInRange){
                        System.out.print("Enter area in hectares: ");
                        area = scanner.nextDouble();
                        if (area <= 0) {
                            System.out.println("Area must be greater than 0. Please enter a valid area.");
                        } else {
                            fieldSizeInRange = false;
                        }
                    }
                    System.out.print("Enter class of soil (1 - best, 6 - worst): ");
                    int classOfSoil = scanner.nextInt();
                    if (classOfSoil < 1){
                        classOfSoil = 1;
                    } else if (classOfSoil > 6){
                        classOfSoil = 6;
                    }
                    currentFarm.addField(new Field(fieldName, area, Integer.valueOf(classOfSoil), currentFarm.crops.get(0)));
                    
                    System.out.println("Field added successfully!");
                }
                case 2 -> {
                    currentFarm.listAllFields();
                    System.out.print("Enter field name you want to edit: ");
                    String fieldName = scanner.next();
                    Field fieldToEdit = null;
                    for (Field f : currentFarm.getFields()) {
                        if (f.getFieldName().equals(fieldName)) {
                            fieldToEdit = f;
                            break;
                        }
                    }
                    if (fieldToEdit == null) {
                        System.out.println("Field not found.");
                    } else {
                        currentFarm.editField(fieldToEdit);
                        System.out.println("Field edited successfully!");
                    }
                }
                case 3 -> {
                    System.out.println("Fields:");
                    currentFarm.listAllFields();
                    System.out.print("Enter field name you want to remove: ");
                    String fieldName = scanner.next();
                    Field fieldToRemove = null;
                    for (Field f : currentFarm.getFields()) {
                        if (f.getFieldName().equals(fieldName)) {
                            fieldToRemove = f;
                            break;
                        }
                    }
                    if (fieldToRemove == null) {
                        System.out.println("Field not found.");
                    } else {
                        currentFarm.removeField(fieldToRemove);
                        System.out.println("Field removed successfully!");
                    }
                }
                case 4 -> {
                    currentFarm.listAllFields();
                }
                case 0 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showStatus() {
        if (currentFarm == null) {
            System.out.println("Please create a farm first!");
            return;
        }
        boolean running = true;
        while (running) {

            System.out.println("What status do you want to see?");
            System.out.println("1. Get all fields ready to be seeded");
            System.out.println("2. Get all fields required to be fertilized");
            System.out.println("3. Get all fields required to be limed");
            System.out.println("4. Calculate required expenses");
            System.out.println("0. Exit to main menu");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    for (Field field : currentFarm.getFields()) {
                        if (!field.isSeeded) {
                            System.out.println(field);
                        }
                    }
                    System.out.println();
                }
                case 2 -> {
                    Double totalExpenses = 0.0;
                    for (Field field : currentFarm.getFields()) {
                        if (!field.isFertilized) {
                            System.out.println(field);
                            totalExpenses += field.areaInHectares * field.crop.getFertilizerCostPerHectare();
                        }
                    }
                    System.out.println("Total fertilizer expenses for all unfertilized fields: " + totalExpenses + " PLN");
                    System.out.println();
                }
                case 3 -> {
                    Double totalExpenses = 0.0;
                    for (Field field : currentFarm.getFields()) {
                        if (!field.isLimed) {
                            System.out.println(field);
                            totalExpenses += field.areaInHectares * field.crop.getLimeCostPerHectare();
                        }
                    }
                    System.out.println("Total lime expenses for all unlimed fields: " + totalExpenses + " PLN");
                    System.out.println();
                }
                case 4 -> {
                    Double totalExpenses = 0.0;
                    for (Field field : currentFarm.getFields()) {
                        Double fieldExpenses = 0.0;
                        if (!field.isSeeded) {
                            fieldExpenses += field.areaInHectares * field.crop.getSeedCostPerHectare();
                        }
                        if (!field.isFertilized) {
                            fieldExpenses += field.areaInHectares * field.crop.getFertilizerCostPerHectare();
                        }
                        if (!field.isLimed) {
                            fieldExpenses += field.areaInHectares * field.crop.getLimeCostPerHectare();
                        }
                        if (fieldExpenses > 0) {
                            System.out.println("Field: " + field.getFieldName() + " - Expenses: " + fieldExpenses + " PLN");
                            totalExpenses += fieldExpenses;
                        }
                    }
                    System.out.println("Total expenses for all fields: " + totalExpenses + " PLN");
                    System.out.println();
                }
                case 0 -> {
                    running = false;
                    System.out.println("Returning to main menu.");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showFarmInformation() {
        if (currentFarm == null) {
            System.out.println("Please create a farm first!");
            return;
        }

        System.out.println("Farm Information:");
        System.out.println("Name: " + currentFarm.getName());
        System.out.println("Location: " + currentFarm.getLocation());
        System.out.println("Total Area (hectares): " + currentFarm.getTotalAreaInHectares());
        System.out.println("Number of Fields: " + currentFarm.getFields().size());
        currentFarm.listAllFields();
    }

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=== Farm Management Tool ===");
        System.out.println("=== 126031 ===");
        System.out.println("=== Adam Rajs ===");
        System.out.println();

        randomizeFarmInfo();
        addDefaultCropsToFarm();

        // use addField so totalAreaInHectares is updated and yields calculated
        currentFarm.addField(new Field("Pole1", 100.0, 1, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole2", 100.0, 2, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole3", 100.0, 3, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole4", 100.0, 4, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole5", 100.0, 5, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole6", 100.0, 6, currentFarm.crops.get(0)));

        while (running) {
            System.out.println("Menu Options:");
            System.out.println("1. Farm management");
            System.out.println("2. Field management");
            System.out.println("3. Status menu");
            System.out.println("4. Show farm information");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println();
                    System.out.println("==========");
                    handleFarmManagement();
                    System.out.println("==========");
                    System.out.println();
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("==========");
                    handleFieldManagement();
                    System.out.println("==========");
                    System.out.println();
                }
                case 3 -> {
                    System.out.println();
                    System.out.println("==========");
                    showStatus();
                    System.out.println("==========");
                    System.out.println();
                }
                case 4 -> {
                    System.out.println();
                    System.out.println("==========");
                    showFarmInformation();
                    System.out.println("==========");
                    System.out.println();
                }
                case 0 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }
}
