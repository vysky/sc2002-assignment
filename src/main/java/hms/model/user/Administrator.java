package hms.model.user;

import hms.service.user.AdministratorServiceImpl;

public class Administrator extends Staff
{
    public Administrator()
    {
    }

    public Administrator(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    public Administrator(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}