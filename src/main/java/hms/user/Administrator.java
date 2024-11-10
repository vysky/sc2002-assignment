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
        
        int i=1;
        switch (opt) {
            case "1":
                Collections.sort(copystaff, new idComparator());
                System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
                for(Staff s : copystaff){
                    String id = String.format("%-10s",s.getHospitalId());
                    String name = String.format("%-10s",s.getName());
                    String role = String.format("%-10s",s.getrole());
                    String gender = String.format("%-10s",s.getGender());
                    int age = (int)s.getAge();
                    System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
                    i++;
                }
                
                break;
            case "2":
                Collections.sort(copystaff, new roleComparator());
                System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
                for(Staff s : copystaff){
                    String id = String.format("%-10s",s.getHospitalId());
                    String name = String.format("%-10s",s.getName());
                    String role = String.format("%-10s",s.getrole());
                    String gender = String.format("%-10s",s.getGender());
                    int age = (int)s.getAge();
                    System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
                    i++;
                }
                break;
            case "3":
                Collections.sort(copystaff, new genderComparator());
                System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
                for(Staff s : copystaff){
                    String id = String.format("%-10s",s.getHospitalId());
                    String name = String.format("%-10s",s.getName());
                    String role = String.format("%-10s",s.getrole());
                    String gender = String.format("%-10s",s.getGender());
                    int age = (int)s.getAge();
                    System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
                    i++;
                }
                break;
            case "4":
                Collections.sort(copystaff, new ageComparator());
                System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
                for(Staff s : copystaff){
                    String id = String.format("%-10s",s.getHospitalId());
                    String name = String.format("%-10s",s.getName());
                    String role = String.format("%-10s",s.getrole());
                    String gender = String.format("%-10s",s.getGender());
                    int age = (int)s.getAge();
                    System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
                    i++;
                }
                break;
            case "5":
                Collections.sort(copystaff, new nameComparator());
                System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
                for(Staff s : copystaff){
                    String id = String.format("%-10s",s.getHospitalId());
                    String name = String.format("%-10s",s.getName());
                    String role = String.format("%-10s",s.getrole());
                    String gender = String.format("%-10s",s.getGender());
                    int age = (int)s.getAge();
                    System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
                    i++;
                }
                break;
            default:
                break;
        }
    }

    /*public void defaultSort(List<Staff> staffs){

        int i=1;
        System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
        for(Staff perstaff : staffs){
            String id = String.format("%-10s",perstaff.getHospitalId());
            String name = String.format("%-10s",perstaff.getName());
            String role = String.format("%-10s",perstaff.getrole());
            String gender = String.format("%-10s",perstaff.getGender());
            int age = (int)perstaff.getAge();
            System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|"+age);
            i++;
        }
    }

    public void idSort(List<Staff> staffs){
        
    }*/

    class idComparator implements Comparator<Staff> {
        // Method
        // Sorting in ascending order of name
        public int compare(Staff a, Staff b)
        {

            return a.getHospitalId().compareToIgnoreCase(b.getHospitalId());
        }
    }

    /*class idComparator implements java.util.Comparator<Staff> {
        @Override
        public int compare(Staff a, Staff b) {
            int ageCompare = Double.compare(a.getAge(), b.getAge());
            int hospitalId = a.getHospitalId().compareTo(b.getHospitalId());
            //Double ageCompare = a.getAge().compare(b.getAge());
            return (hospitalId==0) ? ageCompare:hospitalId;
        }
    }*/

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
            return (hospitalId==0) ? ageCompare:hospitalId;
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