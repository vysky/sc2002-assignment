package hms.model.user;

public class User
{
    private String id;
    private String name;
    private String role;
    private String gender;
    private String password = "password";

    public User()
    {
    }

    public User(String id, String name, String role, String gender)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
    }

    public User(String id, String name, String role, String gender, String password)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.password = password;
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
}