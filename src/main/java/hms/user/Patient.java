package hms.user;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String hpNumber = ""; // Storing hp as string prevents unintentional int operations
    private String email;
    private String bloodType;
    private List<String> diagnoses = new ArrayList<>();
    private List<String> treatments = new ArrayList<>();

    public Patient(String patientId, String name, String dateOfBirth, String gender, String bloodType, String email) {
        super(patientId, dateOfBirth, false);
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.email = email;
    }

    // GETTERS
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getHpNumber() {
        return hpNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getDiagnoses() {
        StringBuilder resultStr = new StringBuilder();
        diagnoses.forEach(diag -> resultStr.append(diag).append(", "));
        return resultStr.toString().trim();
    }

    public String getTreatments() {
        StringBuilder resultStr = new StringBuilder();
        treatments.forEach(treat -> resultStr.append(treat).append(", "));
        return resultStr.toString().trim();
    }

    // SETTERS
    public void setHpNumber(String newNum) {
        hpNumber = newNum;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setDiagnoses(String newDiag) {
        diagnoses.add(newDiag);
    }

    public void setTreatments(String newTreat) {
        treatments.add(newTreat);
    }

}