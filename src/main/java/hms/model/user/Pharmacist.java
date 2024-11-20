package hms.model.user;

/**
 * The Pharmacist class represents a pharmacist in the hospital management system (HMS).
 * It extends the Staff class, inheriting basic staff properties like id, name, role, gender, and age,
 * and may include additional functionalities specific to pharmacists, such as managing medication inventory, processing prescriptions, etc.
 */

public class Pharmacist extends Staff
{
    /**
     * Default constructor that creates a new Pharmacist object.
     * This constructor does not initialize any properties.
     */
    
    public Pharmacist()
    {
    }


    public Pharmacist(String id, String name, String role, String gender, int age, boolean active)
    {
        super(id, name, role, gender, age, active);
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
    public Pharmacist(String id, String name, String role, String gender, int age, String hash, boolean active)
    {
        super(id, name, role, gender, age, hash, active);
    }
}
