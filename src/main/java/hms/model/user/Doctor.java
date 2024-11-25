package hms.model.user;

/**
 * The Doctor class represents a doctor in the hospital management system (HMS).
 * It extends the Staff class, inheriting basic staff properties like id, name, role, gender, and age,
 * and may include additional functionalities specific to doctors, such as viewing patient records or managing appointments.
 */

public class Doctor extends Staff
{
    /**
     * Default constructor that creates a new Doctor object.
     * This constructor does not initialize any properties.
     */
    public Doctor()
    {
    }

    /**
     * Constructor with parameters.
     *
     * @param id     Doctor ID.
     * @param name   Doctor name.
     * @param role   Doctor role.
     * @param gender Doctor gender.
     * @param age    Doctor age.
     * @param active Doctor active status.
     */
    public Doctor(String id, String name, String role, String gender, int age, boolean active)
    {
        super(id, name, role, gender, age, active);
    }

    /**
     * Constructor with parameters including password.
     *
     * @param id       Doctor ID.
     * @param name     Doctor name.
     * @param role     Doctor role.
     * @param gender   Doctor gender.
     * @param age      Doctor age.
     * @param password Doctor password.
     * @param hash     Doctor hashed password.
     * @param active   Doctor active status.
     */
    public Doctor(String id, String name, String role, String gender, int age, String password, String hash, boolean active)
    {
        super(id, name, role, gender, age, password, hash, active);
    }
}
