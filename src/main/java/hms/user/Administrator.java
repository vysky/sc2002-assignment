package hms.user;

import java.util.List;
import java.util.Scanner;

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

    public void defaultSort(List<Staff> staffs){
        int i=1;
        System.out.println("Index\t|ID: \t\t\t|Name\t\t\t|Role\t\t\t|Gender: \t\t|Age:");
        for(Staff perstaff : staffs){
            String id = String.format("%-10s",perstaff.getHospitalId());
            String name = String.format("%-10s",perstaff.getName());
            String role = String.format("%-10s",perstaff.getrole());
            String gender = String.format("%-10s",perstaff.getGender());
            int age = (int)perstaff.getAge();
            System.out.println(i+".\t|"+id+"\t\t|"+name+"\t\t|"+role+"\t\t|"+gender+"\t\t|Age: "+age);
            i++;
        }
    }

    public void idSort(List<Staff> staffs){
        
    }
}