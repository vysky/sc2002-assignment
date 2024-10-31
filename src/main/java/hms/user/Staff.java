package hms.user;

public class Staff extends User {
    
    private String staffId;
    private String name;
    private String role;
    private String gender;
    private int age;

    public Staff(String staffId, String name, String role, String gender, int age) {
        super(staffId, "password", role);
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    // GETTERS

    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    // SETTERS

    public void setStaffId(String id) {
        staffId = id;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setGender(String gen) {
        gender = gen;
    }

    public void setAge(int a) {
        age = a;
    }
}
