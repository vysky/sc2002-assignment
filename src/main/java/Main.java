import java.util.List;
import java.util.Scanner;

import hms.user.Administrator;
import hms.user.Doctor;
import hms.user.Patient;
import hms.user.Staff;
import hms.user.helpers.ExcelReader;
import hms.user.helpers.MedicalRecord;

public class Main {

    public static void main(String[] args) {
        int option;
        Scanner input = new Scanner(System.in);

        List<Patient> patients = ExcelReader.readPatientData("Patient_List.xlsx");
        MedicalRecord mr = new MedicalRecord(patients);
        Doctor doc1 = new Doctor("D0001", "John", "Doctor", "Male", 42);

        do {
            List<Staff> listOfStaffs = ExcelReader.readstaffData("Staff_List.xlsx");
            menuLogin();
            option = input.nextInt();

            switch (option) {
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
                    String user = "A001";
                    menuAdministrator(listOfStaffs/*,user,"Administrator"*/);   // Should check if 'User' is allowed to access this menu
                    break;
                case 0:
                    System.out.println("Goodbye!");
                default:
                    continue;
            }
        } while (option != 0);
        //input.close();
    }

    public static void menuLogin() {
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

    public static void menuPatient(List<Patient> patients, Scanner input) {
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

        do {
            option = input.nextInt();

            switch (option) {
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
                case 1 -> {
                    doctorOption1(mr, input);
                }
                case 2 -> {
                    doctorOption2(mr, input);
                }
                case 3 -> {
                    //doctorOption1(mr, input);
                }
                case 4 -> {
                    //doctorOption1(mr, input);
                }
                case 5 -> {
                    //doctorOption1(mr, input);
                }
                case 6 -> {
                    //doctorOption1(mr, input);
                }
                case 7 -> {
                    //doctorOption1(mr, input);
                }
                case 8 -> {
                    //doctorOption1(mr, input);
                }
                case 0 -> {
                    System.out.println("Goodbye!");
                }
                default -> {
                    continue;
                }
            }
        } while (option != 0);
    }

    public static void menuPharmacist() {
        System.out.println("""
                (1) View Appointment Outcome Record
                (2) Update Prescription Status
                (3) View Medication Inventory
                (4) Submit Replenishment Request
                (0) Logout
                """);
    }

    public static void menuAdministrator(List<Staff> staffs /*Administrator user,String role*/) //If role is administrator, User will be allowed to use options within menu
    {
        /*System.out.println("""
                (1) View and Manage Hospital Staff
                (2) View Appointments details
                (3) View and Manage Medication Inventory
                (4) Approve Replenishment Requests
                (0) Logout
                """);*/
        Scanner admininput = new Scanner(System.in);
        System.out.println("Enter your ID");
        String id = admininput.nextLine();
        System.out.println("Enter your Password");
        String pw = admininput.nextLine();
        String role = "";
        Administrator currAdmin = null;;
        for (int i = 0; i < staffs.size(); i++) {
            //System.out.println(staffs.get(i).getStaffId()+"a");
            //System.out.println(staffs.get(i).getPassword()+"a");
            //System.out.println(staffs.get(i).getrole());
            if (staffs.get(i).getStaffId().compareTo(id) == 0 && staffs.get(i).getPassword().compareTo(pw) == 0) {
                System.out.println("Login successful");
                currAdmin = new Administrator(staffs.get(i).getStaffId(), staffs.get(i).getPassword(), staffs.get(i).getrole(), staffs.get(i).getGender(), staffs.get(i).getAge());
                role = staffs.get(i).getrole();
                //System.out.println(role);
            }
        }

        // this section will check if the user is authorized to access this menu
        if (!role.equals("Administrator")) {
            System.out.println("You DO NOT have access to this menu options!");
            return;
        }

        String option;

        System.out.println("""
                        (1) View and Manage Hospital Staff
                        (2) View Appointments details
                        (3) View and Manage Medication Inventory
                        (4) Approve Replenishment Requests
                        (0) Logout
                        """);
        option = admininput.nextLine();

        do {
            //admininput.nextLine();

            switch (option) {
                case "1":
                    //admininput.nextLine();
                    AdminOption1(staffs, currAdmin, admininput);
                    option = showAdminMenu(admininput);
                    //admininput.nextLine();
                    break;
                case "2":
                    AdminOption2();
                    break;
                case "3":
                    patientOption3();
                    break;
                case "4":
                    patientOption4();
                    break;
                case "5":
                    patientOption5();
                    break;
                case "6":
                    patientOption6();
                    break;
                case "7":
                    patientOption7();
                    break;
                case "8":
                    patientOption8();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    break;
                default:
                    continue;
            }
        } while (!option.equals("0"));
        //admininput.close();
    }

    public static void patientOption1() // Not final implementation. For testing only
    {
        System.out.println("View Medical Record");
    }

    public static void patientOption2() {
        System.out.println("Update Personal Information");
    }

    public static void patientOption3() {
        System.out.println("View Available Appointment Slots");
    }

    public static void patientOption4() {
        System.out.println("Schedule an Appointment");
    }

    public static void patientOption5() {
        System.out.println("Reschedule an Appointment");
    }

    public static void patientOption6() {
        System.out.println("Cancel an Appointment");
    }

    public static void patientOption7() {
        System.out.println("View Scheduled Appointments");
    }

    public static void patientOption8() {
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

    public static void doctorOption2(MedicalRecord mr, Scanner input) {
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

                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                case 2 -> {
                    System.out.println("Please enter treatment description:");
                    input.nextLine();
                    description = input.nextLine();
                    mr.setNewTreatment(pId, description);

                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                default -> {
                    System.out.println("Invalid choice, please try again");
                }
            }
        } while (loop);
    }

    public static void AdminOption1(List<Staff> staffs, Administrator curAdm, Scanner tempScanner) {
        curAdm.sortMenu(staffs, tempScanner);
    }

    public static void AdminOption2() {
        System.err.println("nothing here");
    }

    public static String showAdminMenu(Scanner tempScanner) {
        String opt;
        System.out.println("""
                        (1) View and Manage Hospital Staff
                        (2) View Appointments details
                        (3) View and Manage Medication Inventory
                        (4) Approve Replenishment Requests
                        (0) Logout
                        """);
        opt = tempScanner.nextLine();
        //tempScanner.nextLine();

        return opt;
    }

}
