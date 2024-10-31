package hms.user;

import java.util.Scanner;

public class Administrator extends Staff{
    private String StaffId;
    private String Name;
    private String Gender;
    private String hpNum;
    private double Age;
    private String Role;
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
}