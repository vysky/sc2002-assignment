package hms.user;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Administrator extends Staff{
    private String StaffId;
    private String Name;
    private String Gender;
    private String hpNum;
    private double Age;
    private String Role;
    private String Password;
    public Administrator(String staffID, String name, String role, String gender, double age){
        //super(staffID, staffID+gender.charAt(0)+Double.toString(age), true);
        super(staffID,name,role,gender,age);
        this.StaffId = staffID;
        this.Name = name;
        this.Role = role;
        this.Gender = gender;
        this.Age = age;
    }

    public String getStaffID(){
        return StaffId;
    }

    public String getName(){
        return Name;
    }

    public String getGender(){
        return Gender;
    }

    public String getHPNum(){
        return hpNum;
    }

    public double getAge(){
        return Age;
    }

    public void updateStaffName(String newname){
        Name = newname;
    }   
    
    public void updateStaffRole(String newRole){
        Role = newRole;
    }

    public void updateStaffHp(String newHp){
        hpNum = newHp;
    }

    public void updateStaffRole(int newAge){
        Age = newAge;
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
                temp = new Staff(copystaff.get(index-1).getHospitalId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getrole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "2":
                Collections.sort(copystaff, new roleComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getHospitalId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getrole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "3":
                Collections.sort(copystaff, new genderComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getHospitalId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getrole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "4":
                Collections.sort(copystaff, new ageComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getHospitalId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getrole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            case "5":
                Collections.sort(copystaff, new nameComparator());
                printStaff(copystaff);
                index = editMenu(tempscan);
                if(index == -1) break;
                temp = new Staff(copystaff.get(index-1).getHospitalId(), copystaff.get(index-1).getName(), copystaff.get(index-1).getrole(), copystaff.get(index-1).getGender(), copystaff.get(index-1).getAge());
                editDetails(copystaff, index,tempscan);
                break;
            default:
                break;
        }


    }

    public void printStaff(List<Staff> theList){
        int i=1;
        System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
        for(Staff s : theList){
            String id = String.format("%-10s",s.getHospitalId());
            String name = String.format("%-10s",s.getName());
            String role = String.format("%-10s",s.getrole());
            String gender = String.format("%-10s",s.getGender());
            int age = (int)s.getAge();
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
        """);
        String tempopt = tempScanner.nextLine();
        if(!tempopt.equals("1")&&!tempopt.equals("2")&&!tempopt.equals("3")&&!tempopt.equals("4"))
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
                double newAge = tempScanner.nextDouble();
                theList.get(index-1).setAge(newAge);
                            
                if(tempScanner.hasNextLine()){
                tempScanner.nextLine();}
                break;
            default:
                break;
        }
        return;
    }

    class idComparator implements Comparator<Staff> {
        // Method
        // Sorting in ascending order of name
        public int compare(Staff a, Staff b)
        {

            return a.getHospitalId().compareToIgnoreCase(b.getHospitalId());
        }
    }

    class nameComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int nameCompare = a.getName().compareToIgnoreCase(b.getName());
            int hospitalId = a.getHospitalId().compareTo(b.getHospitalId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (nameCompare==0) ? hospitalId:nameCompare;
        }
    }

    class genderComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int genderCompare = a.getGender().compareTo(b.getGender());
            int hospitalId = a.getHospitalId().compareTo(b.getHospitalId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (genderCompare==0) ? hospitalId:genderCompare;
        }
    }

    class ageComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int ageCompare = Double.compare(a.getAge(), b.getAge());
            int hospitalId = a.getHospitalId().compareTo(b.getHospitalId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (ageCompare==0) ? hospitalId:ageCompare;
        }
    }

    class roleComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int roleCompare = a.getrole().compareToIgnoreCase(b.getrole());
            int hospitalId = a.getHospitalId().compareTo(b.getHospitalId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (roleCompare==0) ? hospitalId:roleCompare;
        }
    }
}