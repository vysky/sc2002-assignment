package hms.model.user;

import java.util.ArrayList;

public class Patient extends User
{
    private String dateOfBirth;
    private String email;
    private String bloodType;

    private ArrayList<String> diagnoses = new ArrayList<>();
    private ArrayList<String> treatments = new ArrayList<>();
    private ArrayList<String> prescriptions = new ArrayList<>();

    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, String diagnoses, String treatments, String prescriptions)
    {
        super(id, name, role, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnoses.add(diagnoses);
        this.diagnoses.remove(0);
        this.treatments.add(treatments);
        this.treatments.remove(0);
        this.prescriptions.add(prescriptions);
        this.prescriptions.remove(0);
    }

    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, String diagnoses, String treatments, String prescriptions, String password)
    {
        super(id, name, role, gender, password);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnoses.add(diagnoses);
        this.diagnoses.remove(0);
        this.treatments.add(treatments);
        this.treatments.remove(0);
        this.prescriptions.add(prescriptions);
        this.prescriptions.remove(0);
        
    }
    
    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
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

    public void setEmail(String email)
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

    public String getPrescriptions()
    {
        StringBuilder resultStr = new StringBuilder();
        prescriptions.forEach(presc -> resultStr.append(presc).append(", "));
        return resultStr.toString().trim();
    }

    public void setDiagnoses(String newDiag) {
        diagnoses.add(newDiag);
    }

    public void setTreatments(String newTreat) {
        treatments.add(newTreat);
    }

    public void setPrescriptions(String newPresc) {
        prescriptions.add(newPresc);
    }
}