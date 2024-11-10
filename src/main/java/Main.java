import java.util.Scanner;

import hms.model.shared.CredentialPair;
import hms.model.user.*;
import hms.service.medicine.MedicineServiceImpl;
import hms.service.user.*;

public class Main
{
    // ============================================================
    // Stored Properties
    // ============================================================

    private static Scanner input;

    // private static MedicineServiceImpl medicineService;

    private static SharedUserServiceImpl sharedUserService;
    private static UserAuthenticationServiceImpl userAuthenticationService;

    // private static CredentialPair credentialPair;
    private static User authenticatedUser;
    private static UserService userService;

    // private static PatientServiceImpl patientService;
    // private static AdministratorServiceImpl administratorService;
    // private static DoctorServiceImpl doctorService;
    // private static PharmacistServiceImpl pharmacistService;

    // ============================================================
    // Private Methods
    // ============================================================

    public static void main(String[] args)
    {
        // initialize instances
        initializeInstances();
        CredentialPair credentialPair = printLoginDialog();
        authenticatedUser = performAuthentication(credentialPair);
        userService = postLoginCreateService();
        runUserService();
    }

    private static void initializeInstances()
    {
        input = new Scanner(System.in);
        // medicineService = new MedicineServiceImpl(); // rename to inventory services??
        sharedUserService = new SharedUserServiceImpl();
        userAuthenticationService = new UserAuthenticationServiceImpl(sharedUserService.getPatientList(), sharedUserService.getStaffList());
    }

    private static CredentialPair printLoginDialog()
    {
        // todo: use hashmap if have time, probably need to refactor

        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        return new CredentialPair(username, password);
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

        return authenticatedUser;
    }

    public static UserService postLoginCreateService()
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
                return new AdministratorServiceImpl((Administrator) authenticatedUser);
            }
            case "doctor" ->
            {
                assert authenticatedUser instanceof Doctor;
                return new DoctorServiceImpl((Doctor) authenticatedUser);
            }
            case "pharmacist" ->
            {
                assert authenticatedUser instanceof Pharmacist;
                return new PharmacistServiceImpl((Pharmacist) authenticatedUser);
            }
            default ->
            {
                return null;
            }
        }
    }

    public static void runUserService()
    {
        int option;
        System.out.printf("Welcome %s!\n", authenticatedUser.getName());

        do
        {
            userService.printMenu();
            option = input.nextInt();
            userService.handleSelectedOption(option, sharedUserService);
        } while (option != 0);
    }
}