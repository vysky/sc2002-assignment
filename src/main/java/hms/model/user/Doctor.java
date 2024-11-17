package hms.model.user;

import hms.service.user.DoctorServiceImpl;

public class Doctor extends Staff
{
    public Doctor()
    {
    }

    public Doctor(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    public Doctor(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}