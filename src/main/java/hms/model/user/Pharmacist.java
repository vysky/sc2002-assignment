package hms.model.user;

public class Pharmacist extends Staff
{
    public Pharmacist()
    {
    }

    /**
     * Constructor with parameters.
     * @param id Pharmacist ID
     * @param name Pharmacist name
     * @param role Pharmacist role
     * @param gender Pharmacist gender
     * @param age Pharmacist age
     */
    public Pharmacist(String id, String name, String role, String gender, int age)
    {
        super(id, name, role, gender, age);
    }

    /**
     * Constructor with parameters including password.
     * @param id Pharmacist ID
     * @param name Pharmacist name
     * @param role Pharmacist role
     * @param gender Pharmacist gender
     * @param age Pharmacist age
     * @param password Pharmacist password
     */
    public Pharmacist(String id, String name, String role, String gender, int age, String password)
    {
        super(id, name, role, gender, age, password);
    }
}