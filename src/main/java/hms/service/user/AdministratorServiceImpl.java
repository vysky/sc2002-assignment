package hms.service.user;

import hms.model.user.*;

public class AdministratorServiceImpl extends UserService
{
    Administrator authenticatedAdministrator;

    public AdministratorServiceImpl(Administrator administrator)
    {
        this.authenticatedAdministrator = administrator;
    }

    public void printMenu()
    {
        System.out.print("""
                                 (1) View and Manage Hospital Staff
                                 (2) View Appointments details
                                 (3) View and Manage Medication Inventory
                                 (4) Approve Replenishment Requests
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    @Override
    public void handleSelectedOption(int option, SharedUserServiceImpl sharedUserServices)
    {
        switch (option)
        {
            case 1 ->
            {
                // option1();
                Administrator administrator = new Administrator("AAA", "No Name", "Super Admin", "---", 18);
                sharedUserServices.getStaffList().add(administrator);
                sharedUserServices.updateStaffList();

                for (Staff staff : sharedUserServices.getStaffList())
                {
                    System.out.println(staff.getName());
                }
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

    public void option3()
    {
        System.out.println("Option 3");
    }

    public void option4()
    {
        System.out.println("Option 4");
    }
}