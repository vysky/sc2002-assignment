package hms.user;

public class Patient extends User {
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String hpNumber = ""; // Storing hp as string prevents unintentional int operations
    private String email;
    private String bloodType;
    private String diagnoses = "";
    private String treatments = "";

    public Patient(String patientId, String name, String dateOfBirth, String gender, String bloodType, String email) {
        super("from excel sheet", "from excel sheet", false);
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.email = email;
    }

    // Create respective get and set methods
}