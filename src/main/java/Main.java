import java.util.List;
import java.util.Scanner;

import hms.user.Doctor;
import hms.user.Patient;
import hms.user.helpers.ExcelReader;
import hms.user.helpers.MedicalRecord;

public class Main
{
    public static void main(String[] args)
    {
        int option;
        Scanner input = new Scanner(System.in);

        List<Patient> patients = ExcelReader.readPatientData("Patient_List.xlsx");
        MedicalRecord mr = new MedicalRecord(patients);
        Doctor doc1 = new Doctor("D0001", "John", "Doctor", "Male", 42);

        do
        {
            menuLogin();
            option = input.nextInt();

            switch(option) {
                case 1:
                    menuPatient(patients, input);
                    break;
                case 2:
                    menuDoctor(doc1, mr, input);
                    break;
                case 3:
                    menuPharmacist();
                    break;
                case 4:
                    menuAdministrator();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                default:
                    continue;
            }
        } while(option != 0);
    }

    public static void menuLogin()
    {
        System.out.print("Username: \n");
        System.out.print("Password: \n"); // need mask password

        // for development only
        System.out.println("""
                View menu of
                (1) Patient
                (2) Doctor
                (3) Pharmacist
                (4) Administrator
                """);
    }

    public static void menuPatient(List<Patient> patients, Scanner input)
    {
        int option;

        System.out.println("""
                (1) View Medical Record
                (2) Update Personal Information
                (3) View Available Appointment Slots
                (4) Schedule an Appointment
                (5) Reschedule an Appointment
                (6) Cancel an Appointment
                (7) View Scheduled Appointments
                (8) View Past Appointment Outcome Records
                (0) Logout
                """);

        do
        {
            option = input.nextInt();

            switch(option)
            {
                case 1:
                    patientOption1();
                    break;
                case 2:
                    patientOption2();
                    break;
                case 3:
                    patientOption3();
                    break;
                case 4:
                    patientOption4();
                    break;
                case 5:
                    patientOption5();
                    break;
                case 6:
                    patientOption6();
                    break;
                case 7:
                    patientOption7();
                    break;
                case 8:
                    patientOption8();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                default:
                    continue;
            }
        } while (option != 0);
    }

    public static void menuDoctor(Doctor doc, MedicalRecord mr, Scanner input) {
        
        int option;

        System.out.println("""
                (1) View Patient Medical Records
                (2) Update Patient Medical Records
                (3) View Personal Schedule
                (4) Set Availability for Appointments
                (5) Accept or Decline Appointment Requests
                (6) View Upcoming Appointments
                (7) Record Appointment Outcome
                (0) Logout
                """);

        do {
            option = input.nextInt();

            switch (option) {
                case 1:
                    doctorOption1(mr, input);
                    break;
                case 2:
                    patientOption2();
                    break;
                case 3:
                    patientOption3();
                    break;
                case 4:
                    patientOption4();
                    break;
                case 5:
                    patientOption5();
                    break;
                case 6:
                    patientOption6();
                    break;
                case 7:
                    patientOption7();
                    break;
                case 8:
                    patientOption8();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                default:
                    continue;
            }
        } while (option != 0);
    }

    public static void menuPharmacist()
    {
        System.out.println("""
                (1) View Appointment Outcome Record
                (2) Update Prescription Status
                (3) View Medication Inventory
                (4) Submit Replenishment Request
                (0) Logout
                """);
    }

    public static void menuAdministrator()
    {
        System.out.println("""
                (1) View and Manage Hospital Staff
                (2) View Appointments details
                (3) View and Manage Medication Inventory
                (4) Approve Replenishment Requests
                (0) Logout
                """);
    }

    public static void patientOption1() // Not final implementation. For testing only
    {
        System.out.println("View Medical Record");
    }

    public static void patientOption2()
    {
        System.out.println("Update Personal Information");
    }

    public static void patientOption3()
    {
        System.out.println("View Available Appointment Slots");
    }

    public static void patientOption4()
    {
        System.out.println("Schedule an Appointment");
    }

    public static void patientOption5()
    {
        System.out.println("Reschedule an Appointment");
    }

    public static void patientOption6()
    {
        System.out.println("Cancel an Appointment");
    }

    public static void patientOption7()
    {
        System.out.println("View Scheduled Appointments");
    }

    public static void patientOption8()
    {
        System.out.println("View Past Appointment Outcome Records");
    }

    public static void doctorOption1(MedicalRecord mr, Scanner input) // Not final implementation. For testing only
    {
        String pId;

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
}