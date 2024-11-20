package hms.service.user;

import hms.model.appointment.*;
import hms.model.medicine.Medicine;
import hms.model.user.*;
import hms.service.medicine.InventoryServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * The AdministratorServiceImpl class provides an implementation for administrator-specific
 * functionalities in the hospital management system (HMS). It extends the abstract UserService
 * class and handles operations such as managing staff, appointments, and medication inventory.
 */
public class AdministratorServiceImpl extends UserService
{
    private AppointmentManager appointmentManager;
    private Administrator authenticatedAdministrator;
    private InventoryServiceImpl inventoryService;
    private SharedUserServiceImpl sharedUserService;
    

    /**
     * Constructs an AdministratorServiceImpl with the specified administrator, inventory service, and shared user service.
     *
     * @param administrator the authenticated administrator
     * @param inventoryService the inventory service
     * @param sharedUserService the shared user service
     */
    public AdministratorServiceImpl(Administrator administrator, InventoryServiceImpl inventoryService, SharedUserServiceImpl sharedUserService)
    {
        this.authenticatedAdministrator = administrator;
        this.inventoryService = inventoryService;
        this.sharedUserService = sharedUserService;
    }

    /**
     * Prints the menu options for the administrator.
     */
    public void printMenu()
    {   
        // todo: for logout, want to try logout back to main menu. may be an option to logout and an option to end program?
        System.out.print("""
                                 ========== Administrator's Menu ==========
                                 (1) View and Manage Hospital Staff
                                 (2) View Appointments details
                                 (3) View and Manage Medication Inventory
                                 (4) Approve Replenishment Requests
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    /**
     * Handles the selected option from the administrator.
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
                viewManage(input);
            }
            case 2 ->
            {
                apptDetails(input);
            }
            case 3 ->
            {
                // View and Manage Medication Inventory
                medInven(input);
            }
            case 4 ->
            {
                // Approve Replenishment Requests
                medTopup(input);
            }
            case 0 ->
            {
                sharedUserService.setStaffList();
                System.out.printf("Goodbye %s!", authenticatedAdministrator.getName());
            }
            default ->
            {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    /**
     * Handles option 1: View and Manage Hospital Staff.
     *
     * @param input the Scanner object to read user input
     */
    private void viewManage(Scanner input)
    {
        int opt;
        while(true){
            try{
                System.out.println("""
                        1. Create new staff
                        2. View all staff
                        """);
                opt = Integer.parseInt(input.nextLine());
                break;
            }catch(NumberFormatException e) { 
                System.out.println("Invalid Option! ");
                
            } catch(NullPointerException e) {
                System.out.println("Invalid Option! ");
            }
        }
        if(opt==1){
            newStaff(input);
        }
        else{
            sortMenu(sharedUserService.getStaffList(),input);
            return;
        }
    }
    
    /**
     * Creates new staff.
     *
     * @param input the Scanner object to read user input.
     */
    private void newStaff(Scanner input){
        String id="",i,name,role,gender,oopt,c="";
        int nid,age;
        System.out.println("Welcome to Staff Maker!");
        while(true){
            System.out.println("Enter New Staff Name");
            name = input.nextLine();
            System.out.println("Confirm " + name + " is correct?");
            System.out.println("Key 1 to confirm, 0 to edit, any other key to return to menu");
            oopt = input.nextLine();
            if(oopt.equals("1")) {
                oopt="0";
                break;
            }
            else if(oopt.equals("0")){

            }
            else {
                c = "0";break;
            }
        }
        if(c=="0"){
            return;
        }
        while(true){
            System.out.println("Enter New Staff Role");
            role = input.nextLine();
            if(role.equals("doctor")||role.equals("administrator")||role.equals("pharmacist")){
                System.out.println("Confirm " + role + " is correct?");
                System.out.println("Key 1 to confirm, 0 to edit, any other key to return to menu");
                oopt = input.nextLine();
                if(oopt.equals("1")) {
                    oopt="0";break;
                }
                else if(oopt.equals("0")){}
                else {
                    c = "0";
                    break;
                }
            }
            else{
                System.out.println("Invalid role entered!");
            }
        }
        if(c=="0"){
            return;
        }
        while(true){
            System.out.println("Enter New Staff Gender");
            gender = input.nextLine();
            if(gender.equals("male")||gender.equals("female")){
                System.out.println("Confirm " + gender + " is correct?");
                System.out.println("Key 1 to confirm, 0 to edit, any other key to return to menu");
                oopt = input.nextLine();
                if(oopt.equals("1")) {
                    oopt="0";break;
                }
                else if(oopt.equals("0")){}
                else {
                    c = "0";
                    break;
                }
            }
            else{
                System.out.println("Invalid gender entered!");
            }
        }
        if(c=="0"){
            return;
        }
        gender = gender.substring(0, 1).toUpperCase()+gender.substring(1);
        while(true){
            try{
                System.out.println("Enter New Staff age");
                age = Integer.parseInt(input.nextLine());
                
                System.out.println("Confirm age "+ age + " is correct?");
                System.out.println("Key 1 to confirm, 0 to edit, any other key to return to menu");
                oopt = input.nextLine();
                if(oopt.equals("1")) {
                    oopt="0";break;
                }
                else if(oopt.equals("0")){}
                else {
                    c = "0";
                    break;
                }
                if(age<1||age>99){
                    System.out.println("Invalid age");
                }
                else break;
            }catch(NumberFormatException e) { 
                System.out.println("Invalid Option! ");
            
            }catch(NullPointerException e) {
                System.out.println("Invalid Option! ");
            }
        }
        if(c=="0"){
            return;
        }
        nid = checkIDSeq(role)+1;
        i=role.substring(0, 1).toUpperCase();
        if(nid<10){
            id = String.format("%03d",nid);
        }
        id = i+id;
        boolean active = true;
        Staff staff = new Staff();
        switch (role)
                {
                    case "administrator" ->
                    {
                        
                            staff = new Administrator(id, name, role, gender, age, active);
                        
                    }
                    case "doctor" ->
                    {
                            staff = new Doctor(id, name, role, gender, age, active);
                        
                    }
                    case "pharmacist" ->
                    {
                            staff = new Pharmacist(id, name, role, gender, age, active);
                    }
                    default -> System.out.printf("Unknown role, skipping user %s.", staff.getRole());
                }
        List<Staff> staffList = sharedUserService.getStaffList();
        staffList.add(staff);
        
    }

    /**
     * Check existing staff number sequence based on role.
     *
     * @param role the selected role
     */
    public int checkIDSeq(String role){
        int nid=-1;
        if(role.equals(role)){
            List<Staff> theList = sharedUserService.getStaffList();
            String tempid ="";
            for(Staff s : theList){
                if(s.getRole().equalsIgnoreCase(role)){
                    tempid.compareToIgnoreCase(tempid);
                    if(tempid.compareToIgnoreCase(s.getId())<0){
                        tempid = s.getId();
                    }
                }
            }
            nid = Integer.parseInt(tempid.substring(1));
        }
        return nid;
    }

    /**
     * Handles option 2: View Appointments details.
     */
    public void apptDetails(Scanner input)
    {
        int i;
        i = showPatient(sharedUserService.getPatientList(),input);
        if(i== -1){
            return;
        }
        selectPatient(sharedUserService.getPatientList(),i,input);
    }

    /**
     * Check existing staff number sequence based on role.
     *
     * @param patientList the List of the staff objects
     * @param input the Scanner object to read user input.
     */
    private int showPatient(List<Patient> patientList,Scanner input){
        System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender:");
        int i=1;
        for(Patient s : patientList){
            String id = String.format("%-10s",s.getId());
            String name = String.format("%-10s",s.getName());
            String role = String.format("%-10s",s.getRole());
            String gender = String.format("%-10s",s.getGender());
            System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender);
            i++;
        }
        return i;
    }

    /**
     * Check existing staff number sequence based on role.
     *
     * @param patientList the List of the staff objects
     * @param i the maximum index of list
     * @param input the Scanner object to read user input.
     */
    private void selectPatient(List<Patient> patientList,int i,Scanner input){
        int oopt;
        while(true){
            try{
                System.out.println("""
                    Which patient's appointment details would you like to see?
                    Otherwise Enter '0' (without quotes) to exit to Admin Menu
                        """);
                oopt = Integer.parseInt(input.nextLine());
                break;
            }catch(NumberFormatException e) { 
                System.out.println("Invalid Option! Exiting...");
            }catch(NullPointerException e) {
                System.out.println("Invalid Option! Exiting...");
            }
        }
        if((oopt <0 || oopt>(i-1))){
            System.out.println("Invalid entry! Returning to Admin Menu.");
            return ;
        }
        else if(oopt ==0){
            System.out.println("Returning to Admin Menu...");
            return ;
        }
        List<Patient> list = new ArrayList<Patient>(patientList);
        System.out.println("Viewing Patient "+ list.get(oopt-1).getName() + " appointment details.");
        System.out.println("Key 1 to confirm, otherwise any other key to exit.");
        String choice = input.nextLine();
        switch (choice) {
            case "1":
                listByDoc(list.get(oopt-1).getId());
                break;
            default:
                System.out.println("Exiting..");
                return;
        }
        return;
    }


    /**
     * Check all appointment with all doctors.
     *
     * @param patientID the List of the staff objects
     */
    private void listByDoc(String patientID){
        appointmentManager = new AppointmentManager();
        List<Doctor> doctors = appointmentManager.getAllDoctors();
        Appointment existingAppointment;
        int count = 0;
        for (int i = 0; i < doctors.size(); i++) {
            existingAppointment = appointmentManager.getExistingAppointment(patientID, doctors.get(i).getId());
            //System.out.println("(" + (i + 1) + ") Dr. " + doctors.get(i).getName());
            if (existingAppointment != null) {
                System.out.println("Patient have an appointment with Dr. " + doctors.get(i).getName() + " on " +
                    existingAppointment.getDate() + " at " + existingAppointment.getTimeslot());
                count++;
            }
        }
        if(count == 0){
            System.out.println("This patient currently do not have any appointment booked! ");
        }
    }


    /**
     * Print out medication list
     *
     *  @param input the Scanner object to read user input.
     */
    private int printMedList(Scanner input){
        int c;
        while(true){
            try{
                inventoryService.printMedicineList();
                System.out.print("""
                                        ----- Manage Medication Inventory -----
                                        (1) Add new medicine
                                        (2) Remove existing medicine
                                        (3) Update stock level of existing medicine
                                        (0) Go back main menu
                                        """);
                System.out.print("Select an option: ");
                c = Integer.parseInt(input.nextLine());
                break;
            }catch(NumberFormatException e) { 
                System.out.println("Non Numberical values entered!");
            } catch(NullPointerException e) {
                System.out.println("Invalid Option!");
            }
        }
        return c;
    }
    
    /**
     * Add new medication to list
     *
     *  @param input the Scanner object to read user input.
     */
    private void addNewMed(Scanner input){
        String opt,medicineName;
        boolean test=false;
        while(true){
            System.out.print("Enter medicine name: ");
            medicineName = input.nextLine();
            if(1==inventoryService.checkDuplicateMedications(medicineName)){
                System.out.println("""
                    Medicine exist in system!
                    Key 1 to enter the name again, otherwise any key to exit""");
                opt = input.nextLine();
                switch (opt) {
                    case "1":  break;                          
                            default:
                                test = true;
                                break;
                        }
                        if(test) break;
                    }
                    else{
                        break;
                    }
                }
            if(test){return;}
            int stockLevel,lowStockLevel;
            while (true) {        
                    try{
                        System.out.print("Enter stock level: ");
                        stockLevel = Integer.parseInt(input.nextLine());
                        if(stockLevel <= 0){
                            System.out.println("Negative or zero values entered!");
                        }
                        else  break;
                    }catch(NumberFormatException e) { 
                        System.out.println("Non Numberical values entered!");
                    } catch(NullPointerException e) {
                        System.out.println("Invalid Option!");
                    }
                }
                    
                while (true) {
                    try{
                            System.out.print("Enter low stock level alert: ");
                            lowStockLevel = Integer.parseInt(input.nextLine());
                            if(lowStockLevel < 0){
                                System.out.println("Negative or zero values entered!");
                            }
                            else if(lowStockLevel == 0){
                                System.out.println("""
                                    NO alert will be set for this medicine.
                                    To CONFIRM press 1, Otherwise any key to return.""");
                                opt = input.nextLine();
                                switch (opt) {
                                    case "1":
                                        System.out.println("No alert is set for this medicine.");
                                        test=true;
                                        break;
                                    default: break;
                                }
                                if(test){
                                    break;
                                }
                            }
                            else  break;
                        }catch(NumberFormatException e) { 
                            System.out.println("Non Numberical values entered!");
                        } catch(NullPointerException e) {
                            System.out.println("Invalid Option!");
                        }
                    }
                    Medicine newMedicine = new Medicine(medicineName, stockLevel, lowStockLevel);
                    inventoryService.getMedicineList().add(newMedicine);
                    System.out.println("New medicine added!");
    }
    
    /**
     * Remove existing medication from list
     *
     *  @param input the Scanner object to read user input.
     */
    private void removeMed(Scanner input){
        int max,index;
        while(true){
            try{
            max = inventoryService.printMedicineList()-1 ;
            System.out.print("""
                Select medicine to remove (Enter the No. of medicine)
                Otherwise key 0 to exit
                """);
            index = Integer.parseInt(input.nextLine());
            if(index > max || index < 0){
                System.out.println("Invalid index!");
            }
            else if(index==0){
                System.out.println("Exiting..");
                break;
            }
            else{
                inventoryService.removeMedicine(index);
                System.out.println("Medicine removed!");
                break;
            }
            }catch(NumberFormatException e) { 
                System.out.println("Non Numberical values entered!");
            }catch(NullPointerException e) {
                System.out.println("Invalid Option!");
            }
        }
    }
    
    /**
     * update existing medication stock on the list
     *
     *  @param input the Scanner object to read user input.
     */
    private void updateStock(Scanner input){
        int max,index,newcount=-1;
        String c;
        while(true){
            try{
                max = inventoryService.printMedicineList() -1;
                System.out.print("""
                    Select medicine to update stock level (Enter the No. of medicine): 
                    Otherwise Key 0 to exit.
                    """);
                index = Integer.parseInt(input.nextLine());
                if(index>max || index < 0){
                    System.out.println("Invalid index!");
                }
                else if(index==0){
                    break;
                }
                else{
                    System.out.print("""
                        Enter new stock: 
                        Enter 0 to clear existing stocks, -1 to exit.
                        """);
                    newcount = Integer.parseInt(input.nextLine());
                    if(newcount<-1){
                        System.out.println("Invalid amount!");
                    }
                    else if(newcount== 0){
                        System.out.println("""
                            Confirm to clear stock amount to ZERO?
                            Key 1 to confirm, any key to exit.
                            """);
                        c = input.nextLine();
                        switch (c) {
                            case "1":
                                System.out.println("Stock amount cleared to 0.");
                                inventoryService.updateMedicineStock(index, input.nextInt());
                                break;
                        
                            default:
                                break;
                        }
                    }
                    else if(newcount==-1){
                        break;
                    }
                    else{
                        inventoryService.updateMedicineStock(index, input.nextInt());
                        System.out.println("Stock updated!");
                        break;
                    }
                }
                
            }catch(NumberFormatException e) { 
                System.out.println("Non Numberical values entered!");
            } catch(NullPointerException e) {
                System.out.println("Invalid Option!");
            }
        }

    }
    
    /**
     * Handles option 3: View and Manage Medication Inventory.
     *
     * @param input the Scanner object to read user input
     */
    // View and Manage Medication Inventory
    public void medInven(Scanner input)
    {
        int option;

        do
        {
            option = printMedList(input);
            switch (option)
            {
                case 1 ->
                {
                    addNewMed(input);
                }
                case 2 ->
                {
                    removeMed(input);
                }
                case 3 ->
                {
                    updateStock(input);
                }
                case 0 ->
                {
                    System.out.print("Main menu");
                }
                default ->
                {
                    System.out.println("Invalid option, please select a new option.");
                }
            }
        } while (option != 0);
    }

    /**
     * Handles option 4: Approve Replenishment Requests.
     *
     * @param input the Scanner object to read user input
     */
    public void medTopup(Scanner input)
    {
        int option, max;
        
        while (true) {        
            try{
                max = inventoryService.printReplenishmentRequestList()-1;
                System.out.print("Enter replenishment request No. to approve (Enter 0 to go back main menu): ");
                option = Integer.parseInt(input.nextLine());
                if(option>max || option < 0){
                    System.out.println("Invalid Option!");
                }
                else  break;
            }catch(NumberFormatException e) { 
                System.out.println("Invalid Option!");
            } catch(NullPointerException e) {
                System.out.println("Invalid Option!");
            }
        }
        //option = Integer.parseInt(input.nextLine());
        if(option == 0) return;
        inventoryService.approveReplenishmentRequest(option);
    }

    /**
     * Displays the sorting menu and allows the user to sort the staff list.
     *
     * @param staffs   the list of staff
     * @param tempscan the Scanner object to read user input
     */
    private void sortMenu(List<Staff> staffs, Scanner tempscan){
        String opt;
        System.out.println("""
                Select the sorting order:
                1. Default Sort (ID)
                2. Role Sort
                3. Gender Sort
                4. Age Sort
                5. Name Sort
                """);
        opt = tempscan.nextLine();
        System.out.println("'"+opt+"'");
        if(!opt.equals("1")&&!opt.equals("2")&&!opt.equals("3")&&!opt.equals("4")&&!opt.equals("5")){
            System.out.println("Invalid option. Exiting...");
            return;
        }
        List<Staff> copystaff = new ArrayList<>(staffs);
        //Staff temp;
        int index,i;
        switch (opt) {
            case "1":
                Collections.sort(copystaff, new idComparator());
                i=printStaff(copystaff);
                index = editMenu(tempscan,i);
                if(index == -1) break;
                //temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "2":
                Collections.sort(copystaff, new roleComparator());
                i=printStaff(copystaff);
                index = editMenu(tempscan,i);
                if(index == -1) break;
                //temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "3":
                Collections.sort(copystaff, new genderComparator());
                i=printStaff(copystaff);
                index = editMenu(tempscan,i);
                if(index == -1) break;
                //temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "4":
                Collections.sort(copystaff, new ageComparator());
                i=printStaff(copystaff);
                index = editMenu(tempscan,i);
                if(index == -1) break;
                //temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "5":
                Collections.sort(copystaff, new nameComparator());
                i=printStaff(copystaff);
                index = editMenu(tempscan,i);
                if(index == -1) break;
                //temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            default:
                break;
        }
    }

    /**
     * Prints the staff list.
     *
     * @param theList the list of staff
     * @return the number of staff
     */
    private int printStaff(List<Staff> theList){
        int i=1;
        System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
        for(Staff s : theList){
            String id = String.format("%-10s",s.getId());
            String name = String.format("%-10s",s.getName());
            String role = String.format("%-10s",s.getRole());
            String gender = String.format("%-10s",s.getGender());
            int age = s.getAge();
            System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
            i++;
        }
        return i-1;
    }

    /**
     * Displays the edit menu and allows the user to edit staff details.
     *
     * @param tempScanner the Scanner object to read user input
     * @param maxPeep     the maximum number of staff
     * @return the index of the selected staff
     */
    private int editMenu(Scanner tempScanner,int maxPeep){
        String tempopt;
        System.out.println("Press 1 to edit the details of a staff or any other key to quit.");
        tempopt = tempScanner.nextLine();
        if(!tempopt.equals("1")){
            System.out.println("Invalid Option! Exiting...");
            return -1;
        }
        else{
            int index;
            while(true){
                System.out.println("Choose the index of the staff you want to edit.");
                try{
                    index = Integer.parseInt(tempScanner.nextLine());
                }catch(NumberFormatException e) {
                    System.out.println("Invalid Option! Exiting...");
                    return -1;
                } catch(NullPointerException e) {
                    System.out.println("Invalid Option! Exiting...");
                    return -1;
                }
                System.out.println(index);
                if(index>maxPeep || index <1){
                    System.out.println("Invalid selection of selected staff index!!");
                    return -1;
                }
                break;
            }
            return index;
        }
    }

    /**
     * Edits the details of the selected staff.
     *
     * @param theList    the list of staff
     * @param index      the index of the selected staff
     * @param tempScanner the Scanner object to read user input
     */
    private void editDetails(List<Staff> theList,int index,Scanner tempScanner){
        System.out.println("""
            What do you want to edit
            1) Name
            2) Role
            3) Gender
            4) Age
            5) Password
            6) Active
            7) Stored Hash
        """);
        String tempopt = tempScanner.nextLine();
        if(!tempopt.equals("1")&&!tempopt.equals("2")&&!tempopt.equals("3")&&!tempopt.equals("4")&&!tempopt.equals("5")&&!tempopt.equals("6")&&!tempopt.equals("7"))
        {
            System.out.println("Invalid option! Exiting...");
            return;
        }
        switch (tempopt) {
            case "1":
                System.out.println("Enter new name: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setName(tempopt);
                break;

            case "2":
                System.out.println("Enter new role: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setRole(tempopt);          
                break;

            case "3":
                System.out.println("Enter new gender: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setGender(tempopt);
                break;

            case "4":
                System.out.println("Enter new age: ");
                int newAge = tempScanner.nextInt();
                theList.get(index-1).setAge(newAge);
                if(tempScanner.hasNextLine()){
                tempScanner.nextLine();}
                break;

            case "5":
                System.out.println("Enter new password: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setPassword(tempopt);
                break;

            case "6":
                while(true){
                    System.out.println("""
                        Enter account active status:
                        (Enter T for true/enable, F for false/disable)
                        """);
                    tempopt = tempScanner.nextLine();
                    if(tempopt.equals("t")||tempopt.equals("T")){
                        theList.get(index-1).setActive(true);
                        break;
                    }
                    else if(tempopt.equals("f")||tempopt.equals("F")){
                        theList.get(index-1).setActive(false);
                        break;
                    }else{

                    }
                }
                break;
            case "7":
                theList.get(index-1).setHash("password");;
                System.out.println("Hash removed!");
                break;
            default:
                break;
        }
        return;
    }

    /**
     * Comparator for sorting staff by ID.
     */
    private class idComparator implements Comparator<Staff> {
        // Method
        // Sorting in ascending order of name
        public int compare(Staff a, Staff b)
        {

            return a.getId().compareToIgnoreCase(b.getId());
        }
    }

    /**
     * Comparator for sorting staff by name.
     */
    private class nameComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int nameCompare = a.getName().compareToIgnoreCase(b.getName());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (nameCompare==0) ? hospitalId:nameCompare;
        }
    }

    /**
     * Comparator for sorting staff by gender.
     */
    private class genderComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int genderCompare = a.getGender().compareTo(b.getGender());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (genderCompare==0) ? hospitalId:genderCompare;
        }
    }

    /**
     * Comparator for sorting staff by age.
     */
    private class ageComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int ageCompare = Double.compare(a.getAge(), b.getAge());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (ageCompare==0) ? hospitalId:ageCompare;
        }
    }

    /**
     * Comparator for sorting staff by role.
     */
    private class roleComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int roleCompare = a.getRole().compareToIgnoreCase(b.getRole());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (roleCompare==0) ? hospitalId:roleCompare;
        }
    }
}
