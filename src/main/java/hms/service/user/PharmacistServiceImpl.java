package hms.service.user;

import hms.model.user.Pharmacist;

// public class PharmacistServiceImpl implements Menu
public class PharmacistServiceImpl extends UserService
{
    Pharmacist authenticatedPharmacist;

    public PharmacistServiceImpl(Pharmacist pharmacist)
    {
        this.authenticatedPharmacist = pharmacist;
    }

    public void printMenu()
    {
        System.out.print("""
                                 (1) View Appointment Outcome Record
                                 (2) Update Prescription Status
                                 (3) View Medication Inventory
                                 (4) Submit Replenishment Request
                                 (0) Logout
                                 Select an option:\s
                                 """);
    }

    @Override
    public void handleSelectedOption(int option, SharedUserServiceImpl sharedUserService)
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
                option4();
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
        System.out.println("Option 3");
    }

    public void option4()
    {
        System.out.println("Option 4");
    }
}