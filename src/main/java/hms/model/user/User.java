package hms.model.user;

import org.springframework.security.crypto.bcrypt.*;

/**
 * The User class represents a user in the hospital management system (HMS).
 * This class holds basic user details such as ID, name, role, gender, and password.
 * It is a base class for different types of users, such as staff members, patients, doctors, etc.
 */

public class User
{
    private String id;
    private String name;
    private String role;
    private String gender;
    private String password = "password";
    private boolean active = true;
    private String hash;

    /**
     * Default constructor that creates a new User object.
     * This constructor does not initialize any properties.
     */
    public User()
    {
    }
    
    /**
     * Constructs a new User object with the specified details.
     * This constructor initializes the user properties excluding the password.
     * 
     * @param id The unique identifier for the user.
     * @param name The name of the user.
     * @param role The role of the user (e.g., "Patient", "Doctor", "Administrator").
     * @param gender The gender of the user.
     */
    public User(String id, String name, String role, String gender)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
    }
        /**
     * Constructs a new User object with the specified details, including a password.
     * This constructor initializes the user properties, including the password.
     * 
     * @param id The unique identifier for the user.
     * @param name The name of the user.
     * @param role The role of the user (e.g., "Patient", "Doctor", "Administrator").
     * @param gender The gender of the user.
     * @param password The password of the user.
     */
    public User(String id, String name, String role, String gender, String hash)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.hash = hash;
    }

    public User(String id, String name, String role, String gender, boolean active)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.active = active;
        this.hash = BCrypt.hashpw("password" , BCrypt.gensalt());
    }

    public User(String id, String name, String role, String gender, String hash, boolean active)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.hash = hash;
        this.active = active;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the user name.
     *
     * @param name the user name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the user role.
     *
     * @return the user role
     */
    public String getRole()
    {
        return this.role;
    }

    /**
     * Sets the user role.
     *
     * @param role the user role
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Gets the user gender.
     *
     * @return the user gender
     */
    public String getGender()
    {
        return this.gender;
    }

    /**
     * Sets the user gender.
     *
     * @param gender the user gender
     */
    public void setGender(String gender)
    {
        this.gender = gender;
    }

    /**
     * Gets the user password.
     *
     * @return the user password
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Sets the user password.
     *
     * @param password the user password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getHash(){
        return this.hash;
    }

    public void setHash(String hash){
         this.hash = hash;
    }

    public boolean getActive(){
        return this.active;
    }

    public void setActive(boolean tf){
        this.active = tf;
    }
}
