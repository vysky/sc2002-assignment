package hms.model.user;

public class Staff extends User
{
    private int age;

    public Staff()
    {
    }

    /**
     * Constructor with parameters.
     * @param id Staff ID
     * @param name Staff name
     * @param role Staff role
     * @param gender Staff gender
     * @param age Staff age
     */
    public Staff(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender);
        this.age = age;
    }

    /**
     * Constructor with parameters including password.
     * @param id Staff ID
     * @param name Staff name
     * @param role Staff role
     * @param gender Staff gender
     * @param age Staff age
     * @param password Staff password
     */
    public Staff(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, password);
        this.age = age;
    }

    /**
     * Gets the age of the staff.
     * @return Staff age
     */
    public int getAge()
    {
        return this.age;
    }

    /**
     * Sets the age of the staff.
     * @param age Staff age
     */
    public void setAge(int age)
    {
        this.age = age;
    }
}