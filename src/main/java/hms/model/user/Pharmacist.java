package hms.model.user;

import hms.service.user.PharmacistServiceImpl;

public class Pharmacist extends Staff
{
    public Pharmacist()
    {
    }

    public Pharmacist(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    public Pharmacist(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}