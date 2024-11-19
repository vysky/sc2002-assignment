package hms.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import hms.model.medicine.Medicine;
import hms.model.user.Administrator;
import hms.model.user.Staff;
import hms.service.medicine.InventoryServiceImpl;
/**
 * The AdministratorServiceImpl class provides an implementation for administrator-specific
 * functionalities in the hospital management system (HMS). It extends the abstract UserService
 * class and handles operations such as managing staff, appointments, and medication inventory.
 */
public class AdministratorServiceImpl extends UserService
{
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
                option2();
            }
            case 3 ->
            {
                // View and Manage Medication Inventory
                option3(input);
            }
            case 4 ->
            {
                // Approve Replenishment Requests
                option4(input);
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

    /**
     * Handles option 1: View and Manage Hospital Staff.
     *
     * @param input the Scanner object to read user input
     */
    private void viewManage(Scanner input)
    {
        sortMenu(sharedUserService.getStaffList(),input);
        return;
    }

    /**
     * Handles option 2: View Appointments details.
     */
    public void option2()
    {
        System.out.println("Option 2");
    }

    /**
     * Handles option 3: View and Manage Medication Inventory.
     *
     * @param input the Scanner object to read user input
     */
    public void option3(Scanner input)
    {
        int option;

        do
        {
            inventoryService.printMedicineList();
            System.out.print("""
                                       ----- Manage Medication Inventory -----
                                       (1) Add new medicine
                                       (2) Remove existing medicine
                                       (3) Update stock level of existing medicine
                                       (0) Go back main menu
                                       """);
            System.out.print("Select an option: ");
            option = input.nextInt();

            switch (option)
            {
                case 1 ->
                {
                    System.out.print("Enter medicine name: ");
                    String medicineName = input.next();
                    System.out.print("Enter stock level: ");
                    int stockLevel = input.nextInt();
                    System.out.print("Enter low stock level alert: ");
                    int lowStockLevel = input.nextInt();
                    Medicine newMedicine = new Medicine(medicineName, stockLevel, lowStockLevel);
                    inventoryService.getMedicineList().add(newMedicine);
                    System.out.println("New medicine added!");
                }
                case 2 ->
                {
                    inventoryService.printMedicineList();
                    System.out.print("Select medicine to remove (Enter the No. of medicine): ");
                    inventoryService.removeMedicine(input.nextInt());
                    System.out.println("Medicine removed!");
                }
                case 3 ->
                {
                    inventoryService.printMedicineList();
                    System.out.print("Select medicine to update stock level (Enter the No. of medicine): ");
                    int index = input.nextInt();
                    System.out.print("Enter new stock: ");
                    inventoryService.updateMedicineStock(index, input.nextInt());
                    System.out.println("Stock updated!");
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
    public void option4(Scanner input)
    {
        int option;
        inventoryService.printReplenishmentRequestList();
        System.out.print("Enter replenishment request No. to approve (Enter 0 to go back main menu): ");
        option = input.nextInt();
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
        System.out.println(i-1);
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
        """);
        String tempopt = tempScanner.nextLine();
        if(!tempopt.equals("1")&&!tempopt.equals("2")&&!tempopt.equals("3")&&!tempopt.equals("4")&&!tempopt.equals("5"))
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
                //staff.setrole(tempopt);           #set role function
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
                System.out.println("Enter new age: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setPassword(tempopt);
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
