import java.util.ArrayList; // Allows to use Array for fields
import java.util.Collections; // Allows to return unmodifiable list
import java.util.List;  // Allows to use List for fields
import java.util.Scanner; // Allows to read user input

public class FarmManagementApp {
    private static Farm currentFarm = null;
    private static Scanner scanner = new Scanner(System.in);

    public static class Field {
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
            return "  - Name = '" + fieldName + '\'' +
                    ", areaInHectares = " + areaInHectares;
        }
    }


    public static class Farm {
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
            // use the passed parameter (was incorrectly using the field)
            this.totalAreaInHectares += areaInHectares;
        }

        public List<Field> getFields() {
            return Collections.unmodifiableList(fields);
        }

        public void addField(Field field) {
            if (field == null) return;
            this.fields.add(field);
            changeTotalAreaInHectares(field.getAreaInHectares());
        }

        public void removeField(Field field) {
            if (field == null) return;
            if (this.fields.remove(field)) {
                changeTotalAreaInHectares(-field.getAreaInHectares());
            }
        }

        private void listAllFields() {
        System.out.println("Fields in the farm:");
        for (Field field : currentFarm.getFields()) {
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

    private static void createNewFarm() {
        System.out.println("Would you like to create farm manually or randomize it?");
        System.out.println("1. Manually");
        System.out.println("2. Randomly");
        System.out.println("0. Exit to farm managment menu");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        boolean running = true;
        while (running){
            switch (choice) {
                case 0 -> {
                    running = false;
                    System.out.println("Returning to farm management menu.");
                }
                case 1 -> {
                    System.out.print("Enter farm name: ");
                    String name = scanner.next();
                    System.out.print("Enter farm location: ");
                    String location = scanner.next();
                    currentFarm = new Farm(name, location);
                    System.out.println("New farm created successfully!");
                }
                case 2 -> {
                    randomizeFarmInfo();
                }
                default -> {
                    System.out.println("Invalid option. Returning to main menu.");
                }
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
                    System.out.print("Enter field name: ");
                    String fieldName = scanner.next();
                    System.out.print("Enter area in hectares: ");
                    Double area = scanner.nextDouble();
                    System.out.print("Enter class of soil (1 - best, 6 - worst): ");
                    int classOfSoil = scanner.nextInt();
                    currentFarm.addField(new Field(fieldName, area, Integer.valueOf(classOfSoil)));
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
                        currentFarm.removeField(fieldToEdit);
                        System.out.println("Field removed successfully!");
                    }
                }
                case 3 -> {
                    currentFarm.listAllFields();
                    System.out.println("Fields:");
                    for (Field field : currentFarm.getFields()) {
                        System.out.println(field.getFieldName());
                    }
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
            System.out.println("1. Get all fields ready to be harvested");
            System.out.println("2. Get all fields ready to be seeded");
            System.out.println("3. Get all fields required to be fertilized");
            System.out.println("4. Get all fields required to be limed");
            System.out.println("5. Calculate required expenses");
            System.out.println("0. Exit to main menu");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Functionality not implemented yet.");
                case 2 -> System.out.println("Functionality not implemented yet.");
                case 3 -> System.out.println("Functionality not implemented yet.");
                case 4 -> System.out.println("Functionality not implemented yet.");
                case 5 -> System.out.println("Functionality not implemented yet.");
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
        System.out.println("Fields:");
        for (Field field : currentFarm.getFields()) {
            System.out.println(" - " + field);
        }
    }

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
