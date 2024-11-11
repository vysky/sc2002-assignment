package hms.user;

import java.util.List;
import java.util.Scanner;
import hms.user.helpers.ExcelReaderPharmacist;

public class Pharmacist extends User {
    private String staffId;
    private String name;
    private String role;
    private String gender;
    private int age;

    private List<String> medicationNames;
    private List<Integer> initialStocks;
    private List<Integer> lowStockAlerts;

    // Constructor
    public Pharmacist(String hospitalId, String password, String name, String gender, int age) {
        super(hospitalId, password, true); // Call User constructor with hospitalId, password, and isStaff=true
        this.name = name;
        this.gender = gender;
        this.age = age;
        
        // Load inventory from Excel file
        loadInventory();
    }

    private void loadInventory() {
        // Read data from the Excel file
        ExcelReaderPharmacist reader = new ExcelReaderPharmacist();
        reader.readInventoryData("Medicine_List.xlsx");

        this.medicationNames = reader.getMedicationNames();
        this.initialStocks = reader.getInitialStocks();
        this.lowStockAlerts = reader.getLowStockAlerts();
    }

    // (1) View Appointment Outcome Record
    public void viewAppointmentOutcomeRecord() {
        // Implementation to view appointment outcome records
        System.out.println("Viewing appointment outcome record...");
        // Add actual logic here
    }

    // (2) Update Prescription Status
    public void updatePrescriptionStatus() {
        // Implementation to update prescription status
        System.out.println("Updating prescription status...");
        // Add actual logic here
    }

    // (3) View Medication Inventory
    public void viewMedicationInventory() {
        System.out.println("\n--- Medication Inventory ---");
        for (int i = 0; i < medicationNames.size(); i++) {
            System.out.println("Medication: " + medicationNames.get(i));
            System.out.println("Initial Stock: " + initialStocks.get(i));
            System.out.println("Low Stock Alert: " + lowStockAlerts.get(i));
            System.out.println();
        }
    }

    // (4) Submit Replenishment Request
    public void submitReplenishmentRequest() {
        Scanner input = new Scanner(System.in);

        System.out.println("\n--- Submit Replenishment Request ---");
        System.out.print("Enter the name of the medication to replenish: ");
        String medication = input.nextLine();

        if (medicationNames.contains(medication)) {
            System.out.print("Enter quantity to request: ");
            int quantity = input.nextInt();
            System.out.println("Replenishment request for " + medication + " (Quantity: " + quantity + ") has been submitted.");
            // Add actual logic here to process the request
        } else {
            System.out.println("Medication not found in inventory.");
        }
    }

    // Main menu for pharmacist
    public void displayMenu() {
        Scanner input = new Scanner(System.in);
        int option;

        do {
            System.out.println("""
                    (1) View Appointment Outcome Record
                    (2) Update Prescription Status
                    (3) View Medication Inventory
                    (4) Submit Replenishment Request
                    (0) Logout
                    """);
            System.out.print("Select an option: ");
            option = input.nextInt();
            input.nextLine();  // Consume newline

            switch (option) {
                case 1 -> viewAppointmentOutcomeRecord();
                case 2 -> updatePrescriptionStatus();
                case 3 -> viewMedicationInventory();
                case 4 -> submitReplenishmentRequest();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);
    }
}