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

    /**
     * Constructor with parameters.
     *
     * @param id          Patient ID
     * @param name        Patient name
     * @param role        Patient role
     * @param dateOfBirth Patient date of birth
     * @param gender      Patient gender
     * @param bloodType   Patient blood type
     * @param email       Patient email
     */
    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, ArrayList<String> diagnoses, ArrayList<String> treatments, ArrayList<String> prescriptions)
    {
        super(id, name, role, gender);
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
     * @param id          Patient ID
     * @param name        Patient name
     * @param role        Patient role
     * @param dateOfBirth Patient date of birth
     * @param gender      Patient gender
     * @param bloodType   Patient blood type
     * @param email       Patient email
     * @param password    Patient password
     */

    public Patient(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, ArrayList<String> diagnoses, ArrayList<String> treatments, ArrayList<String> prescriptions, String password)
    {
        super(id, name, role, gender, password);
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
    private void setDateOfBirth(String dateOfBirth)
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
    private void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the diagnoses of the patient.
     *
     * @return Patient diagnoses as a comma-separated string
     */
    public ArrayList<String> getDiagnoses()
    {
        return this.diagnoses;
    }

    /**
     * Gets the treatments of the patient.
     *
     * @return Patient treatments as a comma-separated string
     */
    public ArrayList<String> getTreatments()
    {
        return this.treatments;
    }

    /**
     * Gets the prescriptions of the patient.
     *
     * @return Patient prescriptions as a comma-separated string
     */
    public ArrayList<String> getPrescriptions()
    {
        return this.prescriptions;
    }

    /**
     * Adds a new diagnosis to the patient's diagnoses.
     *
     * @param newDiagnosis New diagnosis
     */
    public void setDiagnoses(String newDiagnosis)
    {
        this.diagnoses.add(newDiagnosis);
    }

    /**
     * Adds a new treatment to the patient's treatments.
     *
     * @param newTreatment New treatment
     */
    public void setTreatments(String newTreatment)
    {
        this.treatments.add(newTreatment);
    }

    /**
     * Adds a new treatment to the patient's treatments.
     *
     * @param newPrescription New treatment
     */
    public void setPrescriptions(String newPrescription)
    {
        prescriptions.add(newPrescription);
    }

    public void setPrescriptions(String newPresc) {
        prescriptions.add(newPresc);
    }
}