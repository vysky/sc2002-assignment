package hms.service.user;

import java.util.Scanner;

import hms.model.user.Doctor;
import hms.service.medicalRecord.MedicalRecordService;

public class DoctorServiceImpl extends UserService
{
    private Doctor authenticatedDoctor;
    private SharedUserServiceImpl sharedUserService;
    private MedicalRecordService medicalRecordService;

    public DoctorServiceImpl(Doctor doctor, SharedUserServiceImpl sharedUserService, MedicalRecordService medicalRecordService)
    {
        this.authenticatedDoctor = doctor;
        this.sharedUserService = sharedUserService;
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Prints the menu options for the doctor.
     */
    public void printMenu()
    {
        System.out.print("""
                                 ========== Doctor's Menu ==========
                                 (1) View Patient Medical Records
                                 (2) Update Patient Medical Records
                                 (3) View Personal Schedule
                                 (4) Set Availability for Appointments
                                 (5) Accept or Decline Appointment Requests
                                 (6) View Upcoming Appointments
                                 (7) Record Appointment Outcome
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    /**
     * Handles the selected option from the doctor.
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
                option1(medicalRecordService, input);
            }
            case 2 ->
            {
                option2(medicalRecordService, input);
            }
            case 3 ->
            {
                option3();
            }
            case 4 ->
            {
                option4();
            }
            case 5 ->
            {
                option5();
            }
            case 6 ->
            {
                option6();
            }
            case 7 ->
            {
                option7();
            }
            case 0 ->
            {
                System.out.printf("Goodbye %s!", authenticatedDoctor.getName());
            }
            default ->
            {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    /**
     * Handles option 1: View Patient Medical Records.
     *
     * @param mr    the MedicalRecordService object
     * @param input the Scanner object to read user input
     */
    public void option1(MedicalRecordService mr, Scanner input)
    {
        String pId;

        // Actual method needs to find patients under the doctor's care
        while (true) {
            System.out.println("""
                \nPlease enter Patient ID:
                (Input 0 to exit)
                """);
            pId = input.next();
            if (pId.equals("0")) {
                return;
            }
            mr.getMedicalRecord(pId);
        }
    }

    /**
     * Handles option 2: Update Patient Medical Records.
     *
     * @param mr    the MedicalRecordService object
     * @param input the Scanner object to read user input
     */
    public void option2(MedicalRecordService mr, Scanner input)
    {
        String pId = "";
        String description;
        boolean loop = true;

        while (pId.equals("")) {
            System.out.println("""
                \nPlease enter Patient ID:
                (Input 0 to exit)
                """);
            pId = input.next();
            if (pId.equals("0")) {
                return;
            }
        }

        do {
            System.out.println("""
                        Select record to update:
                        (Input 0 to exit)
                        (1) Diagnosis
                        (2) Treatment
                        (3) Prescription
                            """);

            int choice = input.nextInt();

            switch (choice) {
                case 0 -> {
                    loop = false;
                }
                case 1 -> {
                    System.out.println("Please enter diagnosis description:");
                    input.nextLine();
                    description = input.nextLine();
                    mr.setNewDiagnosis(pId, description);
                    System.out.println("Successfully added diagnosis");
                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                case 2 -> {
                    System.out.println("Please enter treatment description:");
                    input.nextLine();
                    description = input.nextLine();
                    mr.setNewTreatment(pId, description);
                    System.out.println("Successfully added treatment");
                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                case 3 -> {
                    // Supposed to send a request to pharmacist
                    // Pharmacist will then fulfill the order
                }
                default -> {
                    System.out.println("Invalid choice, please try again");
                }
            }
        } while (loop);
    }

    /**
     * Handles option 3: View Personal Schedule.
     */
    public void option3()
    {
        System.out.println("Option 3");
    }

    /**
     * Handles option 4: Set Availability for Appointments.
     */
    public void option4()
    {
        System.out.println("Option 4");
    }

    /**
     * Handles option 5: Accept or Decline Appointment Requests.
     */
    public void option5()
    {
        System.out.println("Option 5");
    }

    /**
     * Handles option 6: View Upcoming Appointments.
     */
    public void option6()
    {
        System.out.println("Option 6");
    }

    /**
     * Handles option 7: Record Appointment Outcome.
     */
    public void option7()
    {
        System.out.println("Option 7");
    }
}