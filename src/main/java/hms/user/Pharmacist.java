package hms.user;
import java.util.ArrayList;
import java.util.List;

class Pharmacist extends User{
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String hpNumber = ""; // Storing hp as string prevents unintentional int operations
    private String email;
    private String bloodType;
    private List<String> diagnoses = new ArrayList<>();
    private List<String> treatments = new ArrayList<>();

}