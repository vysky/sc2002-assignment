package hms.service.user;

import java.util.Scanner;

import hms.model.user.Pharmacist;
import hms.service.appointment.AppointmentOutcomeManager;
import hms.service.medicine.InventoryServiceImpl;

/**
 * The PharmacistServiceImpl class provides an implementation for pharmacist-specific functionalities
 * in the hospital management system (HMS). It extends the abstract UserService class and handles
 * operations such as managing the medication inventory, updating prescription statuses, and submitting
 * replenishment requests.
 */

// public class PharmacistServiceImpl implements Menu
public class PharmacistServiceImpl extends UserService
{
    private Pharmacist authenticatedPharmacist;
    private InventoryServiceImpl inventoryService;
    private AppointmentOutcomeManager appointmentOutcomeManager;

    /**
     * Constructs a PharmacistServiceImpl with the authenticated pharmacist and inventory service.
     *
     * @param pharmacist       The authenticated pharmacist.
     * @param inventoryService The inventory service for managing medicines and replenishment requests.
     */
    public PharmacistServiceImpl(Pharmacist pharmacist, InventoryServiceImpl inventoryService, AppointmentOutcomeManager appointmentOutcomeManager)
    {
        this.authenticatedPharmacist = pharmacist;
        this.inventoryService = inventoryService;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
    }

    /**
     * Prints the menu options for the pharmacist.
     */
    public void printMenu()
    {
        System.out.print("""
                                 ========== Pharmacist's Menu ==========
                                 (1) View Appointment Outcome Record
                                 (2) Update Prescription Status (use inventory service, and inventory service should contain appointment list, which should contain prescription order)
                                 (3) View Medication Inventory
                                 (4) Submit Replenishment Request
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    /**
     * Handles the selected option from the pharmacist.
     *
     * @param input  the Scanner object to read user input
     * @param option the selected option
     */
    @Override
    public void handleSelectedOption(Scanner input, int option)
    {
        switch (option)
        {
            case 1 ->
            {
                option1();
            }
            case 2 ->
            {
                option2();
            }
            case 3 ->
            {
                option3();
            }
            case 4 ->
            {
                option4(input);
            }
            case 0 ->
            {
                System.out.printf("Goodbye %s!", authenticatedPharmacist.getName());
            }
            default ->
            {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    /**
     * Handles option 1: View Appointment Outcome Record.
     */
    public void option1()
    {
        System.out.println("Option 1");
    }

    /**
     * Handles option 2: Update Prescription Status.
     */
    public void option2()
    {
        System.out.println("Option 2");
    }

    /**
     * Handles option 3: View Medication Inventory.
     */
    public void option3()
    {
        inventoryService.printMedicineList();
    }

    /**
     * Handles option 4: Submit Replenishment Request.
     *
     * @param input the Scanner object to read user input
     */
    public void option4(Scanner input)
    {
        inventoryService.printMedicineList();

        System.out.print("Enter medicine to replenish: ");
        int index = input.nextInt();

        System.out.print("Enter the amount to replenish: ");
        int amount = input.nextInt();

        inventoryService.createReplenishmentRequest(index, amount);
        inventoryService.printReplenishmentRequestList();
    }
}
