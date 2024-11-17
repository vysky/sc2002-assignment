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

public class Main
{
    // ============================================================
    // Stored Properties
    // ============================================================
    private static Console csl;
    private static Scanner input;

    private static InventoryServiceImpl inventoryService;

    private static SharedUserServiceImpl sharedUserService;
    private static UserAuthenticationServiceImpl userAuthenticationService;

    // private static CredentialPair credentialPair;
    private static User authenticatedUser;
    private static UserService userService;

    private static MedicalRecordService medicalRecordService;

    // private static PatientServiceImpl patientService;
    // private static AdministratorServiceImpl administratorService;
    // private static DoctorServiceImpl doctorService;
    // private static PharmacistServiceImpl pharmacistService;

    // ============================================================
    // Private Methods
    // ============================================================

    public static void main(String[] args)
    {

        initializeInstances();
        // CredentialPair credentialPair = printLoginDialog();
        CredentialPair credentialPair = printMainMenu();
        authenticatedUser = performAuthentication(credentialPair);
        userService = postLoginCreateService();
        runUserService();
    }

    // initialize instances
    private static void initializeInstances()
    {
        csl = System.console();
        input = new Scanner(System.in);
        inventoryService = new InventoryServiceImpl();
        sharedUserService = new SharedUserServiceImpl();
        userAuthenticationService = new UserAuthenticationServiceImpl(sharedUserService.getPatientList(), sharedUserService.getStaffList());
        medicalRecordService = new MedicalRecordService(sharedUserService.getPatientList());
    }

    // todo: should set a login limit, if failed to login then kick user back to this menu
    private static CredentialPair printMainMenu()
    {
        int option;
        int currentAttempt = 1;
        int maximumAttempt = 3;

        do
        {
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
                    return printLoginDialog();
                }
                case 2 ->
                {
                    printForgetPasswordDialog();
                }
                case 3 ->
                {
                    return new CredentialPair("P1001", "password");
                }
                case 4 ->
                {
                    return new CredentialPair("A001", "password");
                }
                case 5 ->
                {
                    return new CredentialPair("D001", "password");
                }
                case 6 ->
                {
                    return new CredentialPair("P001", "password");
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
        } while (option != 0);

        return null;
    }

    private static CredentialPair printLoginDialog()
    {
        // todo: use hashmap if have time, probably need to refactor

        System.out.print("Username: ");
        String username = input.nextLine();
        char[] ch = csl.readPassword( 
            "Enter password : "); 
        //System.out.print("Password: ");
        String password = new String(ch);

        return new CredentialPair(username, password);
    }

    private static void printForgetPasswordDialog()
    {
        System.out.println("----- Reset Password -----");
        System.out.print("Enter your username (hospital ID): ");
        String username = input.nextLine();
        System.out.print("Enter new password: ");
        String password = input.nextLine();

        if (userAuthenticationService.changePassword(username, password))
        {
            System.out.println("Password changed successfully.");
        }
        else
        {
            System.out.println("Error changing password, please try again.");
        }
    }

    private static User performAuthentication(CredentialPair credentialPair)
    {
        var authenticatedUser = userAuthenticationService.authenticateUser(credentialPair);

        if (authenticatedUser == null)
        {
            System.out.println("Invalid credentials.");
            credentialPair = printLoginDialog();
            authenticatedUser = performAuthentication(credentialPair);
        }

        if (authenticatedUser.getPassword().equals("password"))
        {
            String newPassword = performFirstTimeLoginChangePassword();
            authenticatedUser.setPassword(newPassword);
        }

        return authenticatedUser;
    }

    private static String performFirstTimeLoginChangePassword()
    {
        System.out.print("Please change your password on your first login.\n");
        System.out.print("Enter new password: ");
        return input.nextLine();
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