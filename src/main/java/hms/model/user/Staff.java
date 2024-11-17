package hms.model.user;

public class Staff extends User
{
    private int age;

    public Staff()
    {
    }

    public Staff(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender);
        this.age = age;
    }

    public Staff(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, password);
        this.age = age;
    }

    
    public int getAge()
    {
        return this.age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}