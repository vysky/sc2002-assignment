package hms.service.user;

import hms.model.medicine.Medicine;
import hms.model.user.*;
import hms.service.medicine.InventoryServiceImpl;

import java.util.Scanner;

public class AdministratorServiceImpl extends UserService
{
    private Administrator authenticatedAdministrator;
    private InventoryServiceImpl inventoryService;
    private SharedUserServiceImpl sharedUserService;

    public AdministratorServiceImpl(Administrator administrator, InventoryServiceImpl inventoryService, SharedUserServiceImpl sharedUserService)
    {
        this.authenticatedAdministrator = administrator;
        this.inventoryService = inventoryService;
        this.sharedUserService = sharedUserService;
    }

    public void printMenu()
    {
        // todo: for logout, want to try logout back to main menu. may be an option to logout and an option to end program?
        System.out.print("""
                                 ========== Administrator's Menu ==========
                                 (1) View and Manage Hospital Staff
                                 (2) View Appointments details
                                 (3) View and Manage Medication Inventory
                                 (4) Approve Replenishment Requests
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
                // View and Manage Medication Inventory
                option3(input);
            }
            case 4 ->
            {
                // Approve Replenishment Requests
                option4(input);
            }
            case 0 ->
            {
                System.out.printf("Goodbye %s!", authenticatedAdministrator.getName());
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

    // View and Manage Medication Inventory
    public void option3(Scanner input)
    {
        int option;

        do
        {
            inventoryService.printMedicineList();
            System.out.print("""
                                       ----- Manage Medication Inventory -----
                                       (1) Add new medicine
                                       (2) Remove existing medicine
                                       (3) Update stock level of existing medicine
                                       (0) Go back main menu
                                       """);
            System.out.print("Select an option: ");
            option = input.nextInt();

            switch (option)
            {
                case 1 ->
                {
                    System.out.print("Enter medicine name: ");
                    String medicineName = input.next();
                    System.out.print("Enter stock level: ");
                    int stockLevel = input.nextInt();
                    System.out.print("Enter low stock level alert: ");
                    int lowStockLevel = input.nextInt();
                    Medicine newMedicine = new Medicine(medicineName, stockLevel, lowStockLevel);
                    inventoryService.getMedicineList().add(newMedicine);
                    System.out.println("New medicine added!");
                }
                case 2 ->
                {
                    inventoryService.printMedicineList();
                    System.out.print("Select medicine to remove (Enter the No. of medicine): ");
                    inventoryService.removeMedicine(input.nextInt());
                    System.out.println("Medicine removed!");
                }
                case 3 ->
                {
                    inventoryService.printMedicineList();
                    System.out.print("Select medicine to update stock level (Enter the No. of medicine): ");
                    int index = input.nextInt();
                    System.out.print("Enter new stock: ");
                    inventoryService.updateMedicineStock(index, input.nextInt());
                    System.out.println("Stock updated!");
                }
                case 0 ->
                {
                    System.out.print("Main menu");
                }
                default ->
                {
                    System.out.println("Invalid option, please select a new option.");
                }
            }
        } while (option != 0);
    }

    // Approve Replenishment Requests
    public void option4(Scanner input)
    {
        int option;
        inventoryService.printReplenishmentRequestList();
        System.out.print("Enter replenishment request No. to approve (Enter 0 to go back main menu): ");
        option = input.nextInt();
        inventoryService.approveReplenishmentRequest(option);
    }
}