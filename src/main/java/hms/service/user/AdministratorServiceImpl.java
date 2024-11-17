package hms.service.user;

import hms.model.medicine.Medicine;
import hms.model.user.*;
import hms.service.medicine.InventoryServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AdministratorServiceImpl extends UserService
{
    private Administrator authenticatedAdministrator;
    private InventoryServiceImpl inventoryService;
    private SharedUserServiceImpl sharedUserService;

    public AdministratorServiceImpl(Administrator administrator, InventoryServiceImpl inventoryService, SharedUserServiceImpl sharedUserService)
    {
        this.authenticatedAdministrator = administrator;
        this.inventoryService = inventoryService;
        this.sharedUserService = sharedUserService;
    }

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

    @Override
    public void handleSelectedOption(Scanner input, int option)
    {
        switch (option)
        {
            case 1 ->
            {
                option1(input);
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

    public void option1(Scanner input)
    {
        sortMenu(sharedUserService.getStaffList(),input);
        return;
    }

    public void option2()
    {
        System.out.println("Option 2");
    }

    // View and Manage Medication Inventory
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

    // Approve Replenishment Requests
    public void option4(Scanner input)
    {
        int option;
        inventoryService.printReplenishmentRequestList();
        System.out.print("Enter replenishment request No. to approve (Enter 0 to go back main menu): ");
        option = input.nextInt();
        inventoryService.approveReplenishmentRequest(option);
    }

    public void printStaff(List<Staff> theList){
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
    }

    public int editMenu(Scanner tempScanner){
        String tempopt;
        System.out.println("Press 1 to edit the details of a staff or any other key to quit.");
        tempopt = tempScanner.nextLine();
        if(!tempopt.equals("1")){
            return -1;
        }
        else{
            System.out.println("Choose the index of the staff you want to edit.");
            tempopt = tempScanner.nextLine();
            return Integer.parseInt(tempopt);
        }
    }

    public void editDetails(List<Staff> theList,int index,Scanner tempScanner){
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
                System.out.println("Enter new password: ");
                tempopt = tempScanner.nextLine();
                theList.get(index-1).setPassword(tempopt);
                break;
            default:
                break;
        }
        return;
    }

    public void sortMenu(List<Staff> staffs, Scanner tempscan){
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
        Staff temp;
        int index;
        switch (opt) {
            case "1":
                Collections.sort(copystaff, new idComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "2":
                Collections.sort(copystaff, new roleComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "3":
                Collections.sort(copystaff, new genderComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "4":
                Collections.sort(copystaff, new ageComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "5":
                Collections.sort(copystaff, new nameComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getRole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            default:
                break;
        }
    }

    class idComparator implements Comparator<Staff> {
        // Method
        // Sorting in ascending order of name
        public int compare(Staff a, Staff b)
        {

            return a.getId().compareToIgnoreCase(b.getId());
        }
    }

    class nameComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int nameCompare = a.getName().compareToIgnoreCase(b.getName());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (nameCompare==0) ? hospitalId:nameCompare;
        }
    }

    class genderComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int genderCompare = a.getGender().compareTo(b.getGender());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (genderCompare==0) ? hospitalId:genderCompare;
        }
    }

    class ageComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int ageCompare = Double.compare(a.getAge(), b.getAge());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (ageCompare==0) ? hospitalId:ageCompare;
        }
    }

    class roleComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int roleCompare = a.getRole().compareToIgnoreCase(b.getRole());
            int hospitalId = a.getId().compareTo(b.getId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (roleCompare==0) ? hospitalId:roleCompare;
        }
    }
}