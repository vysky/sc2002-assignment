import java.util.List;
import java.util.Scanner;

import hms.user.Administrator;
import hms.user.Patient;
import hms.user.Staff;
import hms.user.User;
import hms.user.helpers.ExcelReader;

public class Main
{
    public static void main(String[] args)
    {
        int option;
        Scanner input = new Scanner(System.in);
        

        List<Patient> patients = ExcelReader.readPatientData("Patient_List.xlsx");
        List<Staff> listOfStaffs = ExcelReader.readstaffData("Staff_List.xlsx");
        do
        {
            menuLogin();
            option = input.nextInt();

            switch(option) {
                case 1:
                    menuPatient(patients);
                    break;
                case 2:
                    menuDoctor();
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
        } while(option != 0);
        //input.close();
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

    public static void menuPatient(List<Patient> patients)
    {
        int option;
        Scanner input = new Scanner(System.in);

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
                    patientOption1(patients);
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

    public static void menuDoctor()
    {
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
                Administrator currAdmin= null;;
                for(int i=0;i<staffs.size();i++){
                    //System.out.println(staffs.get(i).getStaffId()+"a");
                    //System.out.println(staffs.get(i).getPassword()+"a");
                    //System.out.println(staffs.get(i).getrole());
                    if(staffs.get(i).getStaffId().compareTo(id)==0 && staffs.get(i).getPassword().compareTo(pw)==0){
                        System.out.println("Login successful");
                        currAdmin = new Administrator(staffs.get(i).getStaffId(), staffs.get(i).getPassword(), staffs.get(i).getrole(), staffs.get(i).getGender(), staffs.get(i).getAge());
                        role = staffs.get(i).getrole();
                        //System.out.println(role);
                    }
                }
                
                // this section will check if the user is authorized to access this menu
                if(!role.equals("Administrator")){
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
                /*if(admininput.hasNextLine()){
                admininput.nextLine();}*/
        
                do
                {
                    //admininput.nextLine();
        
                    switch(option)
                    {
                        case "1":
                            //admininput.nextLine();
                            AdminOption1(staffs,currAdmin,admininput);
                            option = showAdminMenu(admininput);
                            //admininput.nextLine();
                            continue;
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

    

    public static void patientOption1(List<Patient> patients) // Not final implementation. For testing only
    {
        Patient user = new Patient(patients.get(0).getPatientId(), patients.get(0).getName(), patients.get(0).getDateOfBirth(), patients.get(0).getGender(), patients.get(0).getBloodType(), patients.get(0).getEmail());
        user.setDiagnoses("flu");
        user.setDiagnoses("cold");
        user.setDiagnoses("back pain");

        System.out.println("\n-----MEDICAL RECORD OF " + user.getPatientId() + "----");
        System.out.println("Name: " + user.getName());
        System.out.println("Date of Birth: " + user.getDateOfBirth());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Contact Number: " + user.getHpNumber());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Blood Type: " + user.getBloodType());
        System.out.println("Past Diagnoses: " + user.getDiagnoses());
        System.out.println("Past Treatments" + user.getTreatments());
        // for (int i = 0; i < patients.size(); i++) {
        //     System.out.println("\n-----MEDICAL RECORD OF " + patients.get(i).getPatientId() + "----");
        //     System.out.println("Name: " + patients.get(i).getName());
        //     System.out.println("Date of Birth: " + patients.get(i).getDateOfBirth());
        //     System.out.println("Gender: " + patients.get(i).getGender());
        //     System.out.println("Contact Number: " + patients.get(i).getHpNumber());
        //     System.out.println("Email: " + patients.get(i).getEmail());
        //     System.out.println("Blood Type: " + patients.get(i).getBloodType());
        //     System.out.println("Past Diagnoses: " + patients.get(i).getDiagnoses());
        //     System.out.println("Past Treatments" + patients.get(i).getTreatments());
        // }
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

    public static void AdminOption1(List<Staff> staffs,Administrator curAdm,Scanner tempScanner){
        int opt=0;
        String tempopt;
        curAdm.sortMenu(staffs,tempScanner);
        //tempScanner.nextLine();

        System.out.println("Enter 1 to choose a staff to manage otherwise any key to exit");
        //tempScanner.nextLine();
        tempopt = tempScanner.nextLine();
        
        
        //tempScanner.nextLine();
        if(tempopt.equals("1")){
            tempopt = "";
            System.out.println("Enter the index of the staff to manage. (No.#)");
            opt = tempScanner.nextInt();
            
            if(tempScanner.hasNextLine()){
                tempScanner.nextLine();}
            //tempScanner.skip("\n");
            //System.out.println(staffs.get(opt-1).getName());
            System.out.println("Confirm to edit the following Staff: "+staffs.get(opt-1).getName());
            System.out.println("Enter 1 to confirm, otherwise any key to cancel");
            //tempScanner.nextLine();
            tempopt = tempScanner.nextLine();

            if(tempopt.equals("1")){
                System.out.println("""
                    Which attribute do you want to edit (ID is not changable!)
                    Enter 0 to exit.
                    1. Name
                    2. Gender
                    3. Age
                    4. Password
                    0. Exit                
                                            """);
                tempopt = tempScanner.nextLine();
                switch (tempopt) {
                    case "1":
                        
                        System.out.println("Enter New Name value:");
                        //tempScanner.nextLine();
                        staffs.get(opt-1).setName(tempScanner.nextLine());
                        break;
                        
                    case "2":
                        String getCap;
                        
                        while(true){
                            System.out.println("Enter New Gender value: (Male or Female only)");
                            String newName = tempScanner.nextLine();
                            if(newName.equalsIgnoreCase("male") || newName.equalsIgnoreCase("female")){
                                getCap = newName.substring(0,1).toUpperCase()+newName.substring(1);
                                staffs.get(opt-1).setGender(getCap);
                                break;
                            }
                            else
                                System.out.println("Enter Valid Gender");
                            }
                        break;
                        
                    case "3":
                            double age;
                            while(true){
                            try{
                                System.out.println("Enter New Age value:");
                                age = tempScanner.nextDouble();
                                
                                staffs.get(opt-1).setAge(age);
                                break;
                            }
                            catch(Exception e){
                                System.out.println("Invalid input! Try again!");
                            }
                            //tempScanner.nextLine();
                            }
                            break;
                        
                    case "4":
                            
                        System.out.println("Enter New Password:");
                        //tempScanner.nextLine();
                        staffs.get(opt-1).setPassword(tempScanner.nextLine());
                        break;
                    
                    case "0":
                            
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option!");
                        System.out.println("Exiting...");
                        //tempScanner.nextLine();
                        break;
                }
                //tempopt = tempScanner.nextLine();
            }
            //tempScanner.nextLine();
            return;
        }
        else{
            return;
        }

    }

    public static void AdminOption2(){
        System.err.println("nothing here");
    }

    public static String showAdminMenu(Scanner tempScanner){
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