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

    public String getId()
    {
        return this.id;
    }

    private void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    public String getRole()
    {
        return this.role;
    }

    private void setRole(String role)
    {
        this.role = role;
    }

    public String getGender()
    {
        return this.gender;
    }

    private void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}