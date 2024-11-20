package hms.model.user;

/**
 * The Staff class represents a staff member in the hospital management system (HMS).
 * It extends the User class and includes additional properties such as age, which are common to all hospital staff members.
 * This class serves as a base class for more specialized staff types (such as doctors, pharmacists, and administrators).
 */

public class Staff extends User
{
    private int age;

    /**
     * Default constructor that creates a new Staff object.
     * This constructor does not initialize any properties.
     */
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

   
   public Staff(String id, String name, String role, String gender, int age, boolean active)
   {
       super(id, name, role, gender, active);
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
    public Staff(String id, String name, String role, String gender, int age, String hash, boolean active)
    {
        super(id, name, role, gender, hash,active);
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
