package hms.model.user;

import java.util.ArrayList;

/**
 * The Patient class represents a patient in the hospital management system (HMS).
 * It extends the User class and contains additional information such as date of birth, email, blood type,
 * and medical details like diagnoses and treatments.
 */
public class Patient extends User
{
    private String dateOfBirth;
    private String email;
    private String bloodType;

    private ArrayList<String> diagnoses = new ArrayList<>();
    private ArrayList<String> treatments = new ArrayList<>();
    private ArrayList<String> prescriptions = new ArrayList<>();

    public Patient()
    {
    }

    /**
     * Constructor with parameters.
     *
     * @param id            Patient ID.
     * @param name          Patient name.
     * @param role          Patient role.
     * @param dateOfBirth   Patient date of birth.
     * @param gender        Patient gender.
     * @param bloodType     Patient blood type.
     * @param email         Patient email.
     * @param diagnoses     Patient diagnoses.
     * @param treatments    Patient treatments.
     * @param prescriptions Patient prescriptions.
     * @param active        Patient active status.
     */
    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, ArrayList<String> diagnoses, ArrayList<String> treatments, ArrayList<String> prescriptions, boolean active)
    {
        super(id, name, role, gender, active);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
        this.prescriptions = prescriptions;
    }

    /**
     * Constructor with parameters including password.
     *
     * @param id            Patient ID.
     * @param name          Patient name.
     * @param role          Patient role.
     * @param dateOfBirth   Patient date of birth.
     * @param gender        Patient gender.
     * @param bloodType     Patient blood type.
     * @param email         Patient email.
     * @param diagnoses     Patient diagnoses.
     * @param treatments    Patient treatments.
     * @param prescriptions Patient prescriptions.
     * @param password      Patient password.
     * @param hash          Patient hashed password.
     * @param active        Patient active status.
     */
    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, ArrayList<String> diagnoses, ArrayList<String> treatments, ArrayList<String> prescriptions, String password, String hash, boolean active)
    {
        super(id, name, role, gender, password, hash, active);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnoses = diagnoses;
        this.treatments = treatments;
        this.prescriptions = prescriptions;
    }

    /**
     * Gets the date of birth of the patient.
     *
     * @return Patient date of birth
     */
    public String getDateOfBirth()
    {
        return this.dateOfBirth;
    }

    /**
     * Sets the date of birth of the patient.
     *
     * @param dateOfBirth Patient date of birth
     */
    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the blood type of the patient.
     *
     * @return Patient blood type
     */
    public String getBloodType()
    {
        return this.bloodType;
    }

    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType Patient blood type
     */
    private void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    /**
     * Gets the email of the patient.
     *
     * @return Patient email
     */
    public String getEmail()
    {
        return this.email;
    }

    /**
     * Sets the email of the patient.
     *
     * @param email Patient email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the list of diagnoses for the patient.
     *
     * @return the list of diagnoses.
     */
    public ArrayList<String> getDiagnoses()
    {
        return this.diagnoses;
    }

    /**
     * Adds a new diagnosis to the patient's list of diagnoses.
     *
     * @param newDiagnosis the new diagnosis to add.
     */
    public void setDiagnoses(String newDiagnosis)
    {
        this.diagnoses.add(newDiagnosis);
    }

    /**
     * Gets the list of treatments for the patient.
     *
     * @return the list of treatments.
     */
    public ArrayList<String> getTreatments()
    {
        return this.treatments;
    }

    /**
     * Adds a new treatment to the patient's list of treatments.
     *
     * @param newTreatment the new treatment to add.
     */
    public void setTreatments(String newTreatment)
    {
        this.treatments.add(newTreatment);
    }

    /**
     * Gets the list of prescriptions for the patient.
     *
     * @return the list of prescriptions.
     */
    public ArrayList<String> getPrescriptions()
    {
        return this.prescriptions;
    }

    /**
     * Adds a new prescription to the patient's list of prescriptions.
     *
     * @param newPrescription the new prescription to add.
     */
    public void setPrescriptions(String newPrescription)
    {
        this.prescriptions.add(newPrescription);
    }
}