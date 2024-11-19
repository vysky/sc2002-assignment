package hms.model.user;

public class Doctor extends Staff
{
    public Doctor()
    {
    }

    /**
     * Constructor with parameters.
     * @param id Doctor ID
     * @param name Doctor name
     * @param role Doctor role
     * @param gender Doctor gender
     * @param age Doctor age
     */
    public Doctor(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    /**
     * Constructor with parameters including password.
     * @param id Doctor ID
     * @param name Doctor name
     * @param role Doctor role
     * @param gender Doctor gender
     * @param age Doctor age
     * @param password Doctor password
     */
    public Doctor(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}