package hms.user;

import java.util.Scanner;
import java.lang.Math;
import java.util.Random;

public class Administrator extends User{
    private String StaffId;
    private String Name;
    private String Gender;
    private String hpNum;
    private double Age;
    private String role;
    public Administrator(String staffID, String name, String role, String gender, double age){
        super(staffID, staffID+gender.charAt(0)+Double.toString(age), true);
        this.StaffId = staffID;
        this.Name = name;
        this.role = role;
        this.Gender = gender;
        this.Age = age;
        
    }
}