import java.io.Console;
import java.util.Scanner;

import hms.model.shared.CredentialPair;
import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.user.Pharmacist;
import hms.model.user.User;
import hms.service.medicalRecord.MedicalRecordService;
import hms.service.medicine.InventoryServiceImpl;
import hms.service.user.AdministratorServiceImpl;
import hms.service.user.DoctorServiceImpl;
import hms.service.user.PatientServiceImpl;
import hms.service.user.PharmacistServiceImpl;
import hms.service.user.SharedUserServiceImpl;
import hms.service.user.UserAuthenticationServiceImpl;
import hms.service.user.UserService;

public class HMSApp
{
    // private static Console csl;
    private static Scanner input;
    private static User authenticatedUser;
    private static InventoryServiceImpl inventoryService;
    private static SharedUserServiceImpl sharedUserService;
    private static UserAuthenticationServiceImpl userAuthenticationService;
    private static UserService userService;
    private static MedicalRecordService medicalRecordService;

    public static void main(String[] args)
    {
        initializeInstances();
        runProgram();
    }

    // initialise instances
    private static void initializeInstances()
    {
        // csl = System.console();
        input = new Scanner(System.in);
        authenticatedUser = null;
        inventoryService = new InventoryServiceImpl();
        sharedUserService = new SharedUserServiceImpl();
        userAuthenticationService = new UserAuthenticationServiceImpl(sharedUserService.getPatientList(), sharedUserService.getStaffList());
        medicalRecordService = new MedicalRecordService(sharedUserService.getPatientList());
    }

    // todo: should set a login limit, if failed to login then kick user back to this menu
    private static void runProgram()
    {
        int option;
        int counter = 1;
        int maximumAttempt = 3;
        CredentialPair credentialPair = null;

        while (true)
        {
            do
            {
                System.out.println();
                System.out.print("""
                                         Welcome to Hospital Management System
                                         1. Login
                                         2. Forgot password
                                         3. (DEV) Login as Patient
                                         4. (DEV) Login as Administrator
                                         5. (DEV) Login as Doctor
                                         6. (DEV) Login as Pharmacist
                                         0. Exit program
                                         """);
                System.out.print("Enter an option: ");
                option = Integer.parseInt(input.nextLine());

                switch (option)
                {
                    case 1 ->
                    {
                        while (authenticatedUser == null && counter <= maximumAttempt)
                        {
                            credentialPair = printLoginDialog();

                            // use the credential pair to authenticate patient and staff, then return the authenticated user
                            performAuthentication(credentialPair, counter, maximumAttempt);

                            counter++;
                        }
                    }
                    case 2 ->
                    {
                        printForgetPasswordDialog();
                    }
                    case 3 ->
                    {
                        credentialPair = new CredentialPair("P1001", "password");
                    }
                    case 4 ->
                    {
                        credentialPair = new CredentialPair("A001", "password");
                    }
                    case 5 ->
                    {
                        credentialPair = new CredentialPair("D001", "password");
                    }
                    case 6 ->
                    {
                        credentialPair = new CredentialPair("P001", "password");
                    }
                    case 0 ->
                    {
                        System.out.println("Goodbye!");
                        System.exit(0); // todo: see if can remove this
                    }
                    default ->
                    {
                        System.out.println("Invalid option.");
                    }
                }
            } while (option == 2);

            if (authenticatedUser != null)
            {
                performChangePasswordOnFirstLogin();

                // downcast the authenticated user and get the relevant patient / admin / doctor / pharmacist user service
                userService = postLoginCreateService();

                // run the user service returned above
                runUserService();
            }

            counter = 1;
        }
    }

    private static CredentialPair printLoginDialog()
    {
        // todo: use hashmap if have time, probably need to refactor
        System.out.print("User id: ");
        String id = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        // char[] ch = csl.readPassword(
        //     "Enter password : ");
        // String password = new String(ch);

        return new CredentialPair(id, password);
    }

    private static void printForgetPasswordDialog()
    {
        boolean passwordVerified = false;
        int counter = 1;
        int maximumAttempt = 3;

        System.out.print("----- Reset Password -----\n");
        System.out.print("Enter your user id: ");
        String id = input.nextLine();

        while (!passwordVerified && counter <= maximumAttempt)
        {
            System.out.print("Enter new password: ");
            String password = input.nextLine();
            System.out.print("Enter new password again: ");

            if (password.equals(input.nextLine()))
            {
                if (userAuthenticationService.changePassword(id, password))
                {
                    System.out.println("Password changed successfully, please login using your new password.");
                    passwordVerified = true;
                }
                else
                {
                    System.out.println("Error changing password, please try again.");
                }
            }
            else
            {
                System.out.printf("Password does not match, please try again (Attempt %d/%d).\n", counter, maximumAttempt);
                counter++;
            }
        }

        if (counter >= maximumAttempt)
        {
            System.out.println("Attempt exceeded, exiting to main menu.");
        }
    }

    private static void performAuthentication(CredentialPair credentialPair, int counter, int maximumAttempt)
    {
        authenticatedUser = userAuthenticationService.authenticateUser(credentialPair);

        if (authenticatedUser == null)
        {
            System.out.printf("Invalid credentials (Attempt %d/%d).\n", counter, maximumAttempt);
        }

        if (authenticatedUser == null && counter >= maximumAttempt)
        {
            System.out.println("Attempt exceeded, exiting to main menu.");
        }
    }

    private static void performChangePasswordOnFirstLogin()
    {
        // todo: if have time, add a parameter in csv to track if login the first time instead of checking the default password
        if (authenticatedUser.getPassword().equals("password"))
        {
            System.out.print("Please change your password on your first login.\n");
            System.out.print("Enter new password: ");
            authenticatedUser.setPassword(input.nextLine());
        }
    }

    private static UserService postLoginCreateService()
    {
        switch (authenticatedUser.getRole())
        {
            case "patient" ->
            {
                assert authenticatedUser instanceof Patient;
                return new PatientServiceImpl((Patient) authenticatedUser);
            }
            case "administrator" ->
            {
                assert authenticatedUser instanceof Administrator;
                return new AdministratorServiceImpl((Administrator) authenticatedUser, inventoryService, sharedUserService);
            }
            case "doctor" ->
            {
                assert authenticatedUser instanceof Doctor;
                return new DoctorServiceImpl((Doctor) authenticatedUser, sharedUserService, medicalRecordService);
            }
            case "pharmacist" ->
            {
                assert authenticatedUser instanceof Pharmacist;
                return new PharmacistServiceImpl((Pharmacist) authenticatedUser, inventoryService);
            }
            default ->
            {
                return null;
            }
        }
    }

    private static void runUserService()
    {
        int option;
        System.out.printf("Welcome %s!\n", authenticatedUser.getName());

        do
        {
            userService.printMenu();
            option = Integer.parseInt(input.nextLine());
            userService.handleSelectedOption(input, option);
        } while (option != 0);
    }
}