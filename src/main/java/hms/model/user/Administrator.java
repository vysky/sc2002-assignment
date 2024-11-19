package hms.model.user;

public class Administrator extends Staff
{
    public Administrator()
    {
    }

    /**
     * Constructor with parameters.
     * @param id Administrator ID
     * @param name Administrator name
     * @param role Administrator role
     * @param gender Administrator gender
     * @param age Administrator age
     */
    public Administrator(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    /**
     * Constructor with parameters including password.
     * @param id Administrator ID
     * @param name Administrator name
     * @param role Administrator role
     * @param gender Administrator gender
     * @param age Administrator age
     * @param password Administrator password
     */
    public Administrator(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}