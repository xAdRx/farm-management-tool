import java.util.ArrayList; // Uzywane przy tworzeniu listy pól i upraw
import java.util.Collections; // Uzywane do zwrocenia niemodyfikowalnej listy pól
import java.util.List;  // Uzywane do definiowania listy pól i upraw
import java.util.Scanner; // Potrzebne do zbierania danych wejsciowych od uzytkownika

public class FarmManagementApp {
    private static Farm currentFarm = null; // Bieżąca farma zarządzana w aplikacji
    private static Scanner scanner = new Scanner(System.in); // Skaner do odczytu danych wejściowych od użytkownika

    public static class Crop { // Klasa reprezentująca uprawę
        private String name; // Nazwa uprawy
        private Double expectedYieldPerHectare; // Oczekiwany plon na hektar
        private Double priceOnSell; // Cena sprzedaży za tonę
        private Double fertilizerCostPerHectare; // Koszt nawozu na hektar
        private Double limeCostPerHectare; // Koszt wapna na hektar
        private Double seedCostPerHectare; // Koszt nasion na hektar

        public Crop(String name, Double expectedYieldPerHectare, Double priceOnSell, // Konstruktor klasy Crop
                    Double fertilizerCostPerHectare, Double limeCostPerHectare, Double seedCostPerHectare) {
            this.name = name;
            this.expectedYieldPerHectare = expectedYieldPerHectare;
            this.priceOnSell = priceOnSell;
            this.fertilizerCostPerHectare = fertilizerCostPerHectare;
            this.limeCostPerHectare = limeCostPerHectare;
            this.seedCostPerHectare = seedCostPerHectare;
        }

        public String getName() {
            return name;
        }

        public Double getFertilizerCostPerHectare() {
            return fertilizerCostPerHectare;
        }

        public Double getLimeCostPerHectare() {
            return limeCostPerHectare;
        }

        public Double getSeedCostPerHectare() {
            return seedCostPerHectare;
        }

        public Double getExpectedYieldPerHectare() {
            return expectedYieldPerHectare;
        }

        public Double getPriceOnSell() {
            return priceOnSell;
        }
    }

    public static class Field { // Klasa reprezentująca pole na farmie
        private String fieldName; // Nazwa pola
        private Double areaInHectares; // Powierzchnia pola w hektarach
        private Crop crop; // Uprawa na polu
        private Boolean isSeeded; // Czy pole jest zasiane
        private Boolean isFertilized; // Czy pole jest nawożone
        private Boolean isLimed; // Czy pole jest wapnowane
        private Integer classOfSoil; // Klasa gleby pola
        private Double predictedYield; // Przewidywany plon pola

        public Field(String fieldName, Double areaInHectares, Integer classOfSoil, Crop crop) { // Konstruktor klasy Field
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
        public String toString() { // Metoda do reprezentacji pola jako łańcuch znaków
            String areaStr = String.format("%.2f", areaInHectares);
            String predictedStr = String.format("%.2f", predictedYield);
            if (!isSeeded) { // Jeśli pole nie jest zasiane
                return "  - Name = '" + fieldName + '\'' +
                        ", areaInHectares = " + areaStr +
                        ", classOfSoil = " + classOfSoil +
                        ", isSeeded = " + isSeeded +
                        ", isFertilized = " + isFertilized +
                        ", isLimed = " + isLimed +
                        ", predictedYield = " + predictedStr + " tons";
            }
            return "  - Name = '" + fieldName + '\'' + // Jeśli pole jest zasiane
                    ", areaInHectares = " + areaStr +
                    ", classOfSoil = " + classOfSoil +
                    ", isSeeded = " + isSeeded + " " + crop.getName() +
                    ", isFertilized = " + isFertilized +
                    ", isLimed = " + isLimed +
                    ", predictedYield = " + predictedStr + " tons";
        }

        public String getFieldName() { // Akcesor do nazwy pola
            return fieldName;
        }

        public void setFieldName(String fieldName) { // Mutator do nazwy pola
            this.fieldName = fieldName;
        }

        public Double getAreaInHectares() { // Akcesor do powierzchni pola w hektarach
            return areaInHectares;
        }

        public void setAreaInHectares(Double areaInHectares) { // Mutator do powierzchni pola w hektarach
            this.areaInHectares = areaInHectares;
        }

        public void setCrop(Crop crop) { // Mutator do uprawy pola
            this.crop = crop;
        }

        public void setIsSeeded(Boolean isSeeded) { // Mutator do stanu zasiania pola
            this.isSeeded = isSeeded;
        }

        public void setIsFertilized(Boolean isFertilized) { // Mutator do stanu nawożenia pola
            this.isFertilized = isFertilized;
        }

        public void setIsLimed(Boolean isLimed) { // Mutator do stanu wapnowania pola
            this.isLimed = isLimed;
        }

        public void setClassOfSoil(Integer classOfSoil) { // Mutator do klasy gleby pola
            if (classOfSoil < 1){ // Sprawdzenie, czy klasa gleby jest w zakresie 1-6
                classOfSoil = 1;
            } else if (classOfSoil > 6){
                classOfSoil = 6;
            }

        }

        public void setPredictedYield(Double predictedYield) { // Mutator do przewidywanego plonu pola
            this.predictedYield = predictedYield;
        }

        public Double calculatePredictedYield() { // Metoda do obliczania przewidywanego plonu pola
            double baseYield;
            switch (classOfSoil) { // Modyfikator plonu na podstawie klasy gleby
                case 1 -> baseYield = 1.2; // najlepsza klasa gleby
                case 2 -> baseYield = 1.0; // bardzo dobra klasa gleby
                case 3 -> baseYield = 0.8; // dobra klasa gleby
                case 4 -> baseYield = 0.6; // srednia klasa gleby
                case 5 -> baseYield = 0.4; // gorsza klasa gleby
                case 6 -> baseYield = 0.2; // najgorsza klasa gleby
                default -> baseYield = 0.2; // domyślnie najgorsza klasa gleby
            }

            double yieldModifier = 1.0; // Modyfikator plonu na podstawie zabiegów agrotechnicznych
            if (isFertilized) yieldModifier += 0.2; // nawożenie zwiększa plon o 20%
            if (isLimed) yieldModifier += 0.1; // wapnowanie zwiększa plon o 10%
            if (!isSeeded) yieldModifier = 0; // jeśli pole nie jest zasiane, plon wynosi 0

            this.predictedYield = this.crop.getExpectedYieldPerHectare() * areaInHectares * baseYield * yieldModifier / 10; // Obliczenie przewidywanego plonu w tonach
            return this.predictedYield; // Zwrócenie przewidywanego plonu
        }
    }


    public static class Farm { // Klasa reprezentująca farmę
        private String name; // Nazwa farmy
        private String location; // Lokalizacja farmy
        private Double totalAreaInHectares; // Całkowita powierzchnia farmy w hektarach
        private List<Field> fields; // Lista pól na farmie
        private List<Crop> crops; // Lista upraw na farmie

        public Farm(String name, String location) { // Konstruktor klasy Farm
            this.name = name;
            this.location = location;
            this.fields = new ArrayList<>(); // Inicjalizacja listy pól
            this.crops = new ArrayList<>(); // Inicjalizacja listy upraw
            this.totalAreaInHectares = 0.0;
        }

        public String getName() { // Akcesor do nazwy farmy
            return name;
        }

        public void setName(String name) { // Mutator do nazwy farmy
            this.name = name;
        }

        public String getLocation() { // Akcesor do lokalizacji farmy
            return location;
        }

        public void setLocation(String location) { // Mutator do lokalizacji farmy
            this.location = location;
        }

        public double getTotalAreaInHectares() { // Akcesor do całkowitej powierzchni farmy w hektarach
            return this.totalAreaInHectares;
        }

        public void changeTotalAreaInHectares(double areaInHectares) { // Metoda do zmiany całkowitej powierzchni farmy w hektarach
            this.totalAreaInHectares += areaInHectares;
        }

        public List<Field> getFields() { // Akcesor do listy pól na farmie
            return Collections.unmodifiableList(fields);
        }

        public void addField(Field field) { // Metoda do dodawania pola do farmy
            if (field == null) return;
            field.calculatePredictedYield();
            this.fields.add(field);
            changeTotalAreaInHectares(field.getAreaInHectares());
        }

        public void removeField(Field field) { // Metoda do usuwania pola z farmy
            if (field == null) return;
            if (this.fields.remove(field)) {
                changeTotalAreaInHectares(-field.getAreaInHectares());
            }
        }

        public void editField(Field field) { // Metoda do edytowania pola na farmie
            if (field == null) return; // Sprawdzenie, czy pole nie jest puste

            while (true){ // Pętla edycji pola
                System.out.println("What do you want to edit?");
                System.out.println("1. Change field name [" + field.getFieldName() + "]: ");
                System.out.println("2. Change field areaInHectares [" + String.format("%.2f", field.getAreaInHectares()) + "]: ");
                System.out.println("3. Change field classOfSoil [" + field.classOfSoil + "]: ");
                System.out.println("4. Change if field is fertilized [" + field.isFertilized + "]: ");
                System.out.println("5. Change if field is limed [" + field.isLimed + "]: ");
                if(field.isSeeded) // Sprawdzenie, czy pole jest zasiane, aby wyświetlić odpowiednią informację
                    System.out.println("6. Change if field is seeded [" + field.isSeeded + " " + field.crop.getName() + "]: ");
                else // Jeśli pole nie jest zasiane
                    System.out.println("6. Change if field is seeded [" + field.isSeeded + "]: ");
                System.out.println("0. Back to field management menu");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt(); // Odczyt wyboru użytkownika
                switch (choice) { // Obsługa wyboru użytkownika
                    case 1 -> { // Edycja nazwy pola gdy użytkownik wybierze opcję 1
                        System.out.print("Enter new field name: ");
                        String fieldName = scanner.next(); // Odczyt nowej nazwy pola
                        field.setFieldName(fieldName); // Ustawienie nowej nazwy pola
                        System.out.println("Field name updated successfully!");
                        System.out.println("");
                    }
                    case 2 -> { // Edycja powierzchni pola gdy użytkownik wybierze opcję 2
                        System.out.print("Enter new field area: ");
                        Double area = scanner.nextDouble(); // Odczyt nowej powierzchni pola
                        field.setAreaInHectares(area); // Ustawienie nowej powierzchni pola
                        System.out.println("Field area updated successfully!");
                        field.calculatePredictedYield(); // Przeliczenie przewidywanego plonu po zmianie powierzchni
                        System.out.println("");
                    }
                    case 3 -> { // Edycja klasy gleby pola gdy użytkownik wybierze opcję 3
                        System.out.print("Enter new class of soil (1 - best, 6 - worst): ");
                        int classOfSoil = scanner.nextInt(); // Odczyt nowej klasy gleby pola
                        field.setClassOfSoil(classOfSoil); // Ustawienie nowej klasy gleby pola
                        System.out.println("Field class of soil updated successfully!");
                        field.calculatePredictedYield(); // Przeliczenie przewidywanego plonu po zmianie klasy gleby
                        System.out.println("");
                    }
                    case 4 -> { // Edycja stanu nawożenia pola gdy użytkownik wybierze opcję 4
                        if(field.isFertilized){ // Sprawdzenie aktualnego stanu nawożenia pola
                            field.setIsFertilized(false); // Zmiana stanu nawożenia na fałsz
                            System.out.println("Field is now marked as not fertilized.");
                        } else { // Jeśli pole nie jest nawożone
                            field.setIsFertilized(true); // Zmiana stanu nawożenia na prawda
                            System.out.println("Field is now marked as fertilized.");
                        }
                        field.calculatePredictedYield(); // Przeliczenie przewidywanego plonu po zmianie stanu nawożenia
                        System.out.println("");
                    }
                    case 5 -> { // Edycja stanu wapnowania pola gdy użytkownik wybierze opcję 5
                        if(field.isLimed){ // Sprawdzenie aktualnego stanu wapnowania pola
                            field.setIsLimed(false); // Zmiana stanu wapnowania na fałsz
                            System.out.println("Field is now marked as not limed.");
                        } else {
                            field.setIsLimed(true); // Zmiana stanu wapnowania na prawda
                            System.out.println("Field is now marked as limed.");
                        }
                        field.calculatePredictedYield(); // Przeliczenie przewidywanego plonu po zmianie stanu wapnowania
                        System.out.println("");
                    }
                    case 6 -> { // Edycja stanu zasiania pola gdy użytkownik wybierze opcję 6
                        if(field.isSeeded){ // Sprawdzenie aktualnego stanu zasiania pola
                            field.setIsSeeded(false); // Zmiana stanu zasiania na fałsz
                            System.out.println("Field is now marked as not seeded.");
                        } else { // Jeśli pole nie jest zasiane
                            if (this.crops.isEmpty()) { // Sprawdzenie, czy lista upraw jest pusta
                                System.out.println("No crops available to seed.");
                            } else {
                                int cropChoice; // Zmienna do przechowywania wyboru uprawy
                                while (true) { // Pętla do wyboru uprawy
                                    System.out.println("What would you like to seed?");
                                    for (int i = 0; i < this.crops.size(); i++) { // Wyświetlenie dostępnych upraw
                                        System.out.println((i + 1) + ". " + this.crops.get(i).getName());
                                    }
                                    System.out.print("Your choice: ");
                                    if (!scanner.hasNextInt()) { // Sprawdzenie, czy wejście jest liczbą całkowitą
                                        System.out.println("Invalid input. Please enter a number.");
                                        scanner.next(); // consume invalid token
                                        continue;
                                    }
                                    cropChoice = scanner.nextInt(); // Odczyt wyboru uprawy
                                    if (cropChoice >= 1 && cropChoice <= this.crops.size()) break; // Sprawdzenie, czy wybór jest poprawny
                                    System.out.println("Invalid crop choice.");
                                }
                                field.setCrop(this.crops.get(cropChoice - 1)); // Ustawienie wybranej uprawy na polu
                                field.setIsSeeded(true); // Zmiana stanu zasiania na prawda
                                System.out.println("Field is now marked as seeded.");
                            }
                        }
                        field.calculatePredictedYield(); // Przeliczenie przewidywanego plonu po zmianie stanu zasiania
                        System.out.println("");
                    }
                    case 0 -> {
                        System.out.println("");
                        return; // Wyjście z pętli edycji pola gdy użytkownik wybierze opcję 0
                    }
                    default -> { // Obsługa niepoprawnego wyboru użytkownika
                        System.out.println("Invalid option.");
                        System.out.println("");
                    }
                }
            }
        }

        private void listAllFields() { // Metoda do wyświetlania wszystkich pól na farmie
            System.out.println("Fields in the farm:");
            // Iterowanie po wszystkich polach oraz wyświetlenie informacji o każdym polu
            for (Field field : this.getFields()) {
                System.out.println(field);
            }
            System.out.println();
        }

        @Override
        public String toString() { // Metoda do reprezentacji farmy jako łańcuch znaków
            return "Farm{" +
                    "name='" + name + '\'' +
                    ", location='" + location + '\'' +
                    ", totalAreaInHectares=" + String.format("%.2f", totalAreaInHectares) +
                    '}';
        }
    }

    private static void randomizeFarmInfo() { // Metoda do losowego generowania informacji o farmie
        String[] farmNames = {"Sloneczne Hektary", "Farma na wzgorzu", "Ranczo w dolinie", "Lesna ostoja", "Zielony Zakatek"};
        String[] locations = {"Mazowieckie", "Malopolskie", "Wielkopolskie", "Lubelskie", "Podlaskie"};

        String randomName = farmNames[(int) (Math.random() * farmNames.length)]; // Losowy wybór nazwy farmy
        String randomLocation = locations[(int) (Math.random() * locations.length)]; // Losowy wybór lokalizacji farmy

        currentFarm = new Farm(randomName, randomLocation); // Utworzenie nowej farmy z losowymi informacjami

        System.out.println("Farm information randomized successfully!");
    }

    private static void addDefaultCropsToFarm() { // Metoda do dodawania domyślnych upraw do farmy
        System.out.println("Adding crops to the farm.");
        // Dodanie domyślnych upraw do listy upraw farmy
        currentFarm.crops.add(new Crop("Pszenica", 5.2, 775.0, 150.0, 240.0, 220.0));
        currentFarm.crops.add(new Crop("Kukurydza", 7.3, 896.0, 225.0, 320.0, 350.0));
        currentFarm.crops.add(new Crop("Jeczmien", 5.0, 677.0, 150.0, 240.0, 200.0));
        currentFarm.crops.add(new Crop("Rzepak", 4.1, 2364.0, 480.0, 180.0, 1300.0));
        currentFarm.crops.add(new Crop("Trawa", 15.0, 400.0, 70.0, 100.0, 100.0));
    }

    private static void createNewFarm() { // Metoda do tworzenia nowej farmy
        System.out.println("Would you like to create farm manually or randomize it?");
        System.out.println("1. Manually");
        System.out.println("2. Randomly");
        System.out.println("0. Exit to farm managment menu");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt(); // Odczyt wyboru użytkownika
        switch (choice) { // Obsługa wyboru użytkownika
            case 0 -> { // Wyjście do menu zarządzania farmą gdy użytkownik wybierze opcję 0
                System.out.println("Returning to farm management menu.");
            }
            case 1 -> { // Tworzenie farmy ręcznie gdy użytkownik wybierze opcję 1
                System.out.print("Enter farm name: ");
                String name = scanner.next(); // Odczyt nazwy farmy
                System.out.print("Enter farm location: ");
                String location = scanner.next(); // Odczyt lokalizacji farmy
                currentFarm = new Farm(name, location);
                System.out.println("New farm created successfully!");
                addDefaultCropsToFarm(); // Dodanie domyślnych upraw do nowo utworzonej farmy
            }
            case 2 -> {
                randomizeFarmInfo(); // Losowe generowanie informacji o farmie gdy użytkownik wybierze opcję 2
                addDefaultCropsToFarm(); // Dodanie domyślnych upraw do nowo utworzonej farmy
            }
            default -> { // Obsługa niepoprawnego wyboru użytkownika
                System.out.println("Invalid option.");
            }
        }
    }

    private static void editFarmInfo() { // Metoda do edytowania informacji o farmie
        while (true){ // Pętla edycji informacji o farmie
            System.out.println("What information you want to change:");
            System.out.println("1. Change farm name [" + currentFarm.getName() + "]: ");
            System.out.println("2. Change farm location [" + currentFarm.getLocation() + "]: ");
            System.out.println("0. Back to farm management menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt(); // Odczyt wyboru użytkownika
            switch (choice) { // Obsługa wyboru użytkownika
                case 1 -> { // Edycja nazwy farmy gdy użytkownik wybierze opcję 1
                    System.out.print("Enter new farm name: ");
                    String name = scanner.next(); // Odczyt nowej nazwy farmy
                    currentFarm.setName(name); // Ustawienie nowej nazwy farmy
                    System.out.println("Farm name updated successfully!");
                }
                case 2 -> { // Edycja lokalizacji farmy gdy użytkownik wybierze opcję 2
                    System.out.print("Enter new farm location: ");
                    String location = scanner.next(); // Odczyt nowej lokalizacji farmy
                    currentFarm.setLocation(location); // Ustawienie nowej lokalizacji farmy
                    System.out.println("Farm location updated successfully!");
                }
                case 0 -> { // Wyjście do menu zarządzania farmą gdy użytkownik wybierze opcję 0
                    System.out.println("Returning to farm management menu.");
                    return; // Wyjście z pętli edycji informacji o farmie
                }
                default -> System.out.println("Invalid option."); // Obsługa niepoprawnego wyboru użytkownika
            }
        }
    }

    private static void handleFarmManagement() { // Metoda do obsługi zarządzania farmą
        if (currentFarm == null) { // Sprawdzenie, czy farma nie istnieje
            createNewFarm(); // Jesli nie istnieje utworzenie nowej farmy
        } else { // Jeśli farma istnieje
            while (true) { // Pętla zarządzania farmą
                System.out.println("Current farm: " + currentFarm.getName());
                System.out.println("1. Create new farm");
                System.out.println("2. Change farm information");
                System.out.println("0. Back to main menu");
                System.out.print("Your choice: ");

                int choice = scanner.nextInt(); // Odczyt wyboru użytkownika

                switch (choice) { // Obsługa wyboru użytkownika
                    case 1 -> createNewFarm(); // Tworzenie nowej farmy gdy użytkownik wybierze opcję 1
                    case 2 -> editFarmInfo(); // Edycja informacji o farmie gdy użytkownik wybierze opcję 2
                    case 0 -> { // Wyjście do głównego menu gdy użytkownik wybierze opcję 0
                        System.out.println("Returning to farm management menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again."); // Obsługa niepoprawnego wyboru użytkownika
                }
            }
        }
    }

    private static void handleFieldManagement() { // Metoda do obsługi zarządzania polami
        if (currentFarm == null) { // Sprawdzenie, czy farma nie istnieje
            System.out.println("Please create a farm first!");
            return;
        }

        while (true) { // Pętla zarządzania polami
            System.out.println("Field Management:");
            System.out.println("1. Add new field");
            System.out.println("2. Edit field");
            System.out.println("3. Remove field");
            System.out.println("4. View all fields");
            System.out.println("0. Back to main menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt(); // Odczyt wyboru użytkownika

            switch (choice) { // Obsługa wyboru użytkownika
                case 1 -> { // Dodawanie nowego pola gdy użytkownik wybierze opcję 1
                    String fieldName = ""; // Inicjalizacja nazwy pola
                    Double area = 0.0; // Inicjalizacja powierzchni pola

                    while (true) { // Pętla do wprowadzania unikalnej nazwy pola
                        System.out.print("Enter unique field name: ");
                        fieldName = scanner.next(); // Odczyt nazwy pola
                        boolean isDuplicate = false; // Flaga do sprawdzenia duplikatu nazwy pola

                        for (Field f : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                            if (f.getFieldName().equals(fieldName)) { // Sprawdzenie, czy nazwa pola już istnieje
                                System.out.println("Field name already exists. Please enter a unique field name.");
                                isDuplicate = true; // Ustawienie flagi duplikatu na prawda w momencie gdy znaleziono duplikat
                                break; // Przerwanie pętli po znalezieniu duplikatu
                            }
                        }

                        if (!isDuplicate) { // Jeśli nie znaleziono duplikatu, przerwij pętlę
                            break; // Przerwanie pętli gdy nazwa pola jest unikalna
                        }
                    }

                    while (true) { // Pętla do wprowadzania poprawnej powierzchni pola
                        System.out.print("Enter area in hectares: ");
                        area = scanner.nextDouble(); // Odczyt powierzchni pola
                        if (area > 0) { // Sprawdzenie, czy powierzchnia jest większa od 0
                            break; // Przerwanie pętli gdy powierzchnia jest poprawna
                        }
                        System.out.println("Area must be greater than 0. Please enter a valid area.");
                    }

                    System.out.print("Enter class of soil (1 - best, 6 - worst): ");
                    int classOfSoil = scanner.nextInt(); // Odczyt klasy gleby pola
                    if (classOfSoil < 1) { // Sprawdzenie, czy klasa gleby jest w zakresie 1-6
                        classOfSoil = 1; // Ustawienie klasy gleby na 1 jeśli jest mniejsza niż 1
                    } else if (classOfSoil > 6) {
                        classOfSoil = 6; // Ustawienie klasy gleby na 6 jeśli jest większa niż 6
                    }
                    
                    currentFarm.addField(new Field(fieldName, area, classOfSoil, currentFarm.crops.get(0))); // Dodanie nowego pola do farmy z domyślną uprawą
                    System.out.println("Field added successfully!");
                    System.out.println("");
                }
                case 2 -> {
                    currentFarm.listAllFields(); // Wyświetlenie wszystkich pól na farmie
                    System.out.print("Enter field name you want to edit: ");
                    String fieldName = scanner.next(); // Odczyt nazwy pola do edycji
                    Field fieldToEdit = null; // Inicjalizacja pola do edycji
                    for (Field f : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        if (f.getFieldName().equals(fieldName)) { // Sprawdzenie, czy nazwa pola pasuje do wprowadzonej nazwy
                            fieldToEdit = f; // Przypisanie pola do edycji
                            break; // Przerwanie pętli po znalezieniu pola
                        }
                    }
                    if (fieldToEdit == null) { // Sprawdzenie, czy pole do edycji zostało znalezione
                        System.out.println("Field not found.");
                    } else { // Jeśli pole zostało znalezione, wywołanie metody edycji pola
                        currentFarm.editField(fieldToEdit); // Wywołanie metody edycji pola
                        System.out.println("Field edited successfully!");
                    }
                    System.out.println("");
                }
                case 3 -> {
                    System.out.println("Fields:");
                    currentFarm.listAllFields(); // Wyświetlenie wszystkich pól na farmie
                    System.out.print("Enter field name you want to remove: ");
                    String fieldName = scanner.next(); // Odczyt nazwy pola do usunięcia
                    Field fieldToRemove = null; // Inicjalizacja pola do usunięcia
                    for (Field f : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        if (f.getFieldName().equals(fieldName)) { // Sprawdzenie, czy nazwa pola pasuje do wprowadzonej nazwy
                            fieldToRemove = f; // Przypisanie pola do usunięcia
                            break; // Przerwanie pętli po znalezieniu pola
                        }
                    }
                    if (fieldToRemove == null) { // Sprawdzenie, czy pole do usunięcia zostało znalezione
                        System.out.println("Field not found.");
                    } else {
                        currentFarm.removeField(fieldToRemove); // Usunięcie pola z farmy
                        System.out.println("Field removed successfully!");
                    }
                    System.out.println("");
                }
                case 4 -> {
                    currentFarm.listAllFields(); // Wyświetlenie wszystkich pól na farmie gdy użytkownik wybierze opcję 4
                }
                case 0 -> {
                    return; // Wyjście do głównego menu gdy użytkownik wybierze opcję 0
                }
                default -> System.out.println("Invalid option. Please try again."); // Obsługa niepoprawnego wyboru użytkownika
            }
        }
    }

    private static void showStatus() { // Metoda do wyświetlania statusu pól na farmie
        if (currentFarm == null) { // Sprawdzenie, czy farma nie istnieje
            System.out.println("Please create a farm first!");
            return; // Jeśli farma nie istnieje, wyjście z metody
        }
        while (true) { // Pętla do wyświetlania statusu pól

            System.out.println("What status do you want to see?");
            System.out.println("1. Get all fields ready to be seeded");
            System.out.println("2. Get all fields required to be fertilized");
            System.out.println("3. Get all fields required to be limed");
            System.out.println("4. Calculate required expenses");
            System.out.println("5. View predicted yield and earnings for all fields");
            System.out.println("0. Exit to main menu");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt(); // Odczyt wyboru użytkownika
            switch (choice) { // Obsługa wyboru użytkownika
                case 1 -> { // Wyświetlenie wszystkich pól gotowych do zasiania gdy użytkownik wybierze opcję 1
                    for (Field field : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        if (!field.isSeeded) { // Sprawdzenie, czy pole nie jest zasiane
                            System.out.println(field);
                        }
                    }
                    System.out.println();
                }
                case 2 -> { // Wyświetlenie wszystkich pól wymagających nawożenia gdy użytkownik wybierze opcję 2
                    Double totalExpenses = 0.0; // Inicjalizacja całkowitych wydatków na nawozy
                    for (Field field : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        if (!field.isFertilized) { // Sprawdzenie, czy pole nie jest nawożone
                            System.out.println(field);
                            totalExpenses += field.areaInHectares * field.crop.getFertilizerCostPerHectare(); // Obliczenie wydatków na nawozy dla pola i dodanie do całkowitych wydatków
                        }
                    }
                    System.out.println("Total fertilizer expenses for all unfertilized fields: " + String.format("%.2f", totalExpenses) + " PLN");
                    System.out.println();
                }
                case 3 -> { // Wyświetlenie wszystkich pól wymagających wapnowania gdy użytkownik wybierze opcję 3
                    Double totalExpenses = 0.0; // Inicjalizacja całkowitych wydatków na wapno
                    for (Field field : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        if (!field.isLimed) { // Sprawdzenie, czy pole nie jest wapnowane
                            System.out.println(field);
                            totalExpenses += field.areaInHectares * field.crop.getLimeCostPerHectare(); // Obliczenie wydatków na wapno dla pola i dodanie do całkowitych wydatków
                        }
                    }
                    System.out.println("Total lime expenses for all unlimed fields: " + String.format("%.2f", totalExpenses) + " PLN");
                    System.out.println();
                }
                case 4 -> { // Obliczenie wymaganych wydatków gdy użytkownik wybierze opcję 4
                    Double totalExpenses = 0.0; // Inicjalizacja całkowitych wydatków
                    for (Field field : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        Double fieldExpenses = 0.0; // Inicjalizacja wydatków dla pola
                        if (!field.isSeeded) { // Sprawdzenie, czy pole nie jest zasiane
                            fieldExpenses += field.areaInHectares * field.crop.getSeedCostPerHectare(); // Obliczenie wydatków na nasiona dla pola
                        }
                        if (!field.isFertilized) { // Sprawdzenie, czy pole nie jest nawożone
                            fieldExpenses += field.areaInHectares * field.crop.getFertilizerCostPerHectare(); // Obliczenie wydatków na nawozy dla pola
                        }
                        if (!field.isLimed) { // Sprawdzenie, czy pole nie jest wapnowane
                            fieldExpenses += field.areaInHectares * field.crop.getLimeCostPerHectare(); // Obliczenie wydatków na wapno dla pola
                        }
                        if (fieldExpenses > 0) { // Jeśli wydatki dla pola są większe niż 0, wyświetlenie informacji o wydatkach dla pola
                            System.out.println("Field: " + field.getFieldName() + " - Expenses: " + String.format("%.2f", fieldExpenses) + " PLN");
                            totalExpenses += fieldExpenses; // Dodanie wydatków dla pola do całkowitych wydatków
                        }
                    }
                    System.out.println("Total expenses for all fields: " + String.format("%.2f", totalExpenses) + " PLN");
                    System.out.println();
                }
                case 5 -> { // Wyświetlenie przewidywanego plonu i zarobków dla wszystkich pól gdy użytkownik wybierze opcję 5
                    for (Field field : currentFarm.getFields()) { // Pętla po wszystkich polach na farmie
                        Double expectedEarnings = field.predictedYield * field.crop.getPriceOnSell(); // Obliczenie oczekiwanych zarobków dla pola
                        System.out.println("Field: " + field.getFieldName() + " - Predicted Yield: " + String.format("%.2f", field.predictedYield) + " tons, Expected Earnings: " + String.format("%.2f", expectedEarnings) + " PLN");
                    }
                    System.out.println();

                }
                case 0 -> { // Wyjście do głównego menu gdy użytkownik wybierze opcję 0
                    System.out.println("Returning to main menu.");
                    return; // Wyjście do głównego menu gdy użytkownik wybierze opcję 0
                }
                default -> System.out.println("Invalid option. Please try again."); // Obsługa niepoprawnego wyboru użytkownika
            }
        }
    }

    private static void showFarmInformation() { // Metoda do wyświetlania informacji o farmie
        if (currentFarm == null) { // Sprawdzenie, czy farma nie istnieje
            System.out.println("Please create a farm first!");
            return; // Jeśli farma nie istnieje, wyjście z metody
        }

        System.out.println("Farm Information:");
        System.out.println("Name: " + currentFarm.getName());
        System.out.println("Location: " + currentFarm.getLocation());
        System.out.println("Total Area (hectares): " + String.format("%.2f", currentFarm.getTotalAreaInHectares()));
        System.out.println("Number of Fields: " + currentFarm.getFields().size());
        currentFarm.listAllFields(); // Wyświetlenie wszystkich pól na farmie
    }

    public static void main(String[] args) { // Główna metoda programu

        System.out.println("=== Farm Management Tool ===");
        System.out.println("=== 126031 ===");
        System.out.println("=== Adam Rajs ===");
        System.out.println();

        randomizeFarmInfo(); // Utworzenie losowej farmy na start
        addDefaultCropsToFarm(); // Dodanie domyślnych upraw do farmy

        // Dodanie przykładowych pól do farmy
        currentFarm.addField(new Field("Pole1", 100.0, 1, currentFarm.crops.get(0))); 
        currentFarm.addField(new Field("Pole2", 100.0, 2, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole3", 100.0, 3, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole4", 100.0, 4, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole5", 100.0, 5, currentFarm.crops.get(0)));
        currentFarm.addField(new Field("Pole6", 100.0, 6, currentFarm.crops.get(0)));

        while (true) { // Główna pętla menu programu
            System.out.println("Menu Options:");
            System.out.println("1. Farm management");
            System.out.println("2. Field management");
            System.out.println("3. Status menu");
            System.out.println("4. Show farm information");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt(); // Odczyt wyboru użytkownika

            switch (choice) { // Obsługa wyboru użytkownika
                case 1 -> { // Obsługa zarządzania farmą gdy użytkownik wybierze opcję 1
                    System.out.println();
                    System.out.println("==========");
                    handleFarmManagement(); // Wywołanie metody zarządzania farmą
                    System.out.println("==========");
                    System.out.println();
                }
                case 2 -> { // Obsługa zarządzania polami gdy użytkownik wybierze opcję 2
                    System.out.println(); 
                    System.out.println("==========");
                    handleFieldManagement(); // Wywołanie metody zarządzania polami
                    System.out.println("==========");
                    System.out.println();
                }
                case 3 -> { // Obsługa menu statusu gdy użytkownik wybierze opcję 3
                    System.out.println();
                    System.out.println("==========");
                    showStatus(); // Wywołanie metody wyświetlania statusu pól
                    System.out.println("==========");
                    System.out.println();
                }
                case 4 -> { // Wyświetlanie informacji o farmie gdy użytkownik wybierze opcję 4
                    System.out.println();
                    System.out.println("==========");
                    showFarmInformation(); // Wywołanie metody wyświetlania informacji o farmie
                    System.out.println("==========");
                    System.out.println();
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    scanner.close(); // Zamknięcie skanera przed zakończeniem programu
                    return; // Zakończenie programu gdy użytkownik wybierze opcję 0
                }
                default -> System.out.println("Invalid option. Please try again."); // Obsługa niepoprawnego wyboru użytkownika
            }
            System.out.println();
        }
    }
}
