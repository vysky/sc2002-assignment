package hms.model.user;


/**
 * The Administrator class represents an administrator in the hospital management system (HMS).
 * It extends the Staff class, inheriting the basic properties of a staff member (e.g., id, name, role, gender, and age),
 * and may also include additional administrator-specific functionalities.
 */

public class Administrator extends Staff
{
    /**
     * Default constructor that creates a new Administrator object.
     * This constructor does not initialize any properties.
     */

    public Administrator()
    {
    }

    /**
     * Constructor with parameters.
     *
     * @param id     Administrator ID
     * @param name   Administrator name
     * @param role   Administrator role
     * @param gender Administrator gender
     * @param age    Administrator age
     */
    public Administrator(String id, String name, String role, String gender, int age, boolean active)
    {
        super(id, name, role, gender, age, active);
    }

    /**
     * Constructor with parameters including password.
     *
     * @param id       Administrator ID
     * @param name     Administrator name
     * @param role     Administrator role
     * @param gender   Administrator gender
     * @param age      Administrator age
     * @param password Administrator password
     */
    public Administrator(String id, String name, String role, String gender, int age, String password, String hash, boolean active)
    {
        super(id, name, role, gender, age, password, hash, active);
    }
}
