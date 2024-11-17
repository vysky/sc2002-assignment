package hms.service.user;

import hms.model.user.Pharmacist;
import hms.service.medicine.InventoryServiceImpl;

import java.util.Scanner;

// public class PharmacistServiceImpl implements Menu
public class PharmacistServiceImpl extends UserService
{
    private Pharmacist authenticatedPharmacist;
    private InventoryServiceImpl inventoryService;

    public PharmacistServiceImpl(Pharmacist pharmacist, InventoryServiceImpl inventoryService)
    {
        this.authenticatedPharmacist = pharmacist;
        this.inventoryService = inventoryService;
    }

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

    public void option1()
    {
        System.out.println("Option 1");
    }

    public void option2()
    {
        System.out.println("Option 2");
    }

    public void option3()
    {
        inventoryService.printMedicineList();
    }

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