package hms.model.user;

import java.util.ArrayList;

public class Patient extends User
{
    private String dateOfBirth;
    private String email;
    private String bloodType;

    private ArrayList<String> diagnoses = new ArrayList<>();
    private ArrayList<String> treatments = new ArrayList<>();

    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email)
    {
        super(id, name, role, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
    }

    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, String password)
    {
        super(id, name, role, gender, password);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    private void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodType()
    {
        return bloodType;
    }

    private void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    public String getEmail()
    {
        return email;
    }

    private void setEmail(String email)
    {
        this.email = email;
    }

    public String getDiagnoses()
    {
        StringBuilder resultStr = new StringBuilder();
        diagnoses.forEach(diag -> resultStr.append(diag).append(", "));
        return resultStr.toString().trim();
    }

    public String getTreatments()
    {
        StringBuilder resultStr = new StringBuilder();
        treatments.forEach(treat -> resultStr.append(treat).append(", "));
        return resultStr.toString().trim();
    }

    public void setDiagnoses(String newDiag) {
        diagnoses.add(newDiag);
    }

    public void setTreatments(String newTreat) {
        treatments.add(newTreat);
    }
}