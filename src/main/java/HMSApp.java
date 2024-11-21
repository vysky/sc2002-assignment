import java.util.Scanner;
import javax.swing.*;
import java.io.Console;

import hms.model.appointment.AppointmentManager;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Main application class for the Hospital Management System (HMS).
 * This class manages user authentication and services for different user roles
 * like patient, administrator, doctor, and pharmacist.
 */
public class HMSApp
{
    private static Console csl;
    private static Scanner input;
    private static User authenticatedUser;
    private static InventoryServiceImpl inventoryService;
    private static SharedUserServiceImpl sharedUserService;
    private static UserAuthenticationServiceImpl userAuthenticationService;
    private static UserService userService;
    private static MedicalRecordService medicalRecordService;
    private static AppointmentManager appointmentManager;

    /**
     * Main method to start the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        initializeInstances();
        runProgram();
    }

    /**
     * Initializes required instances for services and variables.
     * This includes user authentication, inventory service, and medical record service.
     */
    private static void initializeInstances()
    {
        csl = System.console();
        input = new Scanner(System.in);
        authenticatedUser = null;
        inventoryService = new InventoryServiceImpl();
        sharedUserService = new SharedUserServiceImpl();
        userAuthenticationService = new UserAuthenticationServiceImpl(sharedUserService.getPatientList(), sharedUserService.getStaffList());
        medicalRecordService = new MedicalRecordService(sharedUserService.getPatientList());
        appointmentManager = new AppointmentManager();
    }

    /**
     * Runs the main program loop, presenting options to the user for login, password reset, and role-based login.
     * After successful authentication, the relevant user service is run.
     */
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
                                         ----- Welcome to Hospital Management System -----
                                         1. Login
                                         2. Forgot password
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
                    case 33 ->
                    {
                        credentialPair = new CredentialPair("P1001", "password");
                        performAuthentication(credentialPair, counter, maximumAttempt);
                    }
                    case 44 ->
                    {
                        credentialPair = new CredentialPair("A001", "password");
                        performAuthentication(credentialPair, counter, maximumAttempt);
                    }
                    case 55 ->
                    {
                        credentialPair = new CredentialPair("D001", "password");
                        performAuthentication(credentialPair, counter, maximumAttempt);
                    }
                    case 66 ->
                    {
                        credentialPair = new CredentialPair("P001", "password");
                        performAuthentication(credentialPair, counter, maximumAttempt);
                    }
                    case 0 ->
                    {
                        exitProgram();
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

    /**
     * Prompts the user to enter their login credentials.
     *
     * @return a CredentialPair containing the entered user id and password
     */
    private static CredentialPair printLoginDialog()
    {
        // todo: use hashmap if have time, probably need to refactor
        System.out.println("----- Enter your user id and password to login -----");
        System.out.print("User id: ");
        String id = input.nextLine();
        String password;

        password = enterPassword(1);

        return new CredentialPair(id, password);
    }


    /**
     * Prompts the user to enter their login password while hiding input.
     *
     * @return a CredentialPair containing the entered user id and password
     */
    private static String enterPassword(int c){
        String password;

        if(c==1){
            if (csl == null)
            {
                password = getPasswordWithoutConsole("Enter password: ");
            }
            else
            {
                char[] ch = csl.readPassword("Enter password : ");
                password = new String(ch);
            }
            return password;
        }
        else {
            if (csl == null)
            {
                password = getPasswordWithoutConsole("Enter password again: ");
            }
            else
            {
                char[] ch = csl.readPassword("Enter password again: ");
                password = new String(ch);
            }
            return password;
        }
    }

    /**
     * Prompts the user to enter their login credentials.
     *
     * @return a CredentialPair containing the entered user id and password
     */
    public static String getPasswordWithoutConsole(String prompt)
    {
        final JPasswordField passwordField = new JPasswordField();
        return JOptionPane.showConfirmDialog(null, passwordField, prompt, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(passwordField.getPassword()) : "";
    }

    /**
     * Handles the process of resetting a user's password.
     * Allows up to 3 attempts to successfully change the password
     */
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
            
            String password = enterPassword(1);
            String password2 = enterPassword(2);

            if (password.equals(password2))
            {
                if (userAuthenticationService.changePassword(id, password))
                {
                    System.out.println("Password changed successfully, please login using your new password.");
                    sharedUserService.setPatientList();
                    sharedUserService.setStaffList();
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

    /**
     * Authenticates the user based on the provided credentials.
     * Increases the counter for failed login attempts and exits if the maximum attempts are exceeded.
     *
     * @param credentialPair the credentials entered by the user
     * @param counter        the current attempt number
     * @param maximumAttempt the maximum number of allowed attempts
     */
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

    /**
     * Prompts the user to change their password on first login.
     * Allows up to 3 attempts to change the password successfully.
     */
    private static void performChangePasswordOnFirstLogin()
    {
        boolean passwordVerified = false;
        int counter = 1;
        int maximumAttempt = 3;

        // todo: if have time, add a parameter in csv to track if login the first time instead of checking the default password
        if (authenticatedUser.getPassword().equals("password"))
        {
            while (!passwordVerified && counter <= maximumAttempt)
            {
                System.out.print("Please change your password on your first login.\n");
                String password = enterPassword(1);
                String password2 = enterPassword(2);

                if (password.equals(password2))
                {
                    if (userAuthenticationService.changePassword(authenticatedUser.getId(), password))
                    {
                        System.out.println("Password changed successfully.");
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
    }

    /**
     * Creates the appropriate user service based on the authenticated user's role.
     *
     * @return the user service corresponding to the authenticated user's role, or null if role is unknown
     */
    private static UserService postLoginCreateService()
    {
        switch (authenticatedUser.getRole())
        {
            case "patient" ->
            {
                assert authenticatedUser instanceof Patient;
                return new PatientServiceImpl((Patient) authenticatedUser, appointmentManager, medicalRecordService, sharedUserService);
            }
            case "administrator" ->
            {
                assert authenticatedUser instanceof Administrator;
                return new AdministratorServiceImpl((Administrator) authenticatedUser, inventoryService, sharedUserService,userAuthenticationService);
            }
            case "doctor" ->
            {
                assert authenticatedUser instanceof Doctor;
                return new DoctorServiceImpl((Doctor) authenticatedUser, medicalRecordService, appointmentManager);
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

    /**
     * Runs the user service, displaying the menu and handling selected options.
     */
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

        saveData();
        clearUser();
    }

    private static void saveData()
    {
        sharedUserService.setPatientList();
        sharedUserService.setStaffList();
    }

    private static void clearUser()
    {
        authenticatedUser = null;
    }

    private static void exitProgram()
    {
        System.out.println("Goodbye!");
        System.exit(0); // todo: see if can remove this
    }
}
