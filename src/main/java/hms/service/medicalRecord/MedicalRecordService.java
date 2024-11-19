package hms.service.medicalRecord;

import java.util.List;

import hms.model.user.Patient;
import hms.model.user.User;

/**
 * The MedicalRecordService class provides functionality to manage and retrieve medical records
 * for patients in the hospital management system. It allows retrieving patient information,
 * setting new diagnoses, and setting new treatments.
 */

public class MedicalRecordService {
    private List<Patient> patients;

    public MedicalRecordService(List<Patient> p) {
        patients = p;
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return the patient with the specified ID
     */
    public Patient getPatientById(String patientId) {
        for (Patient p : patients) {
            User temp = p;
            if (patientId.equals(temp.getId())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Retrieves and prints the medical record of a patient.
     *
     * @param patientId the ID of the patient
     */
    public void getMedicalRecord(String patientId) {
        Patient p = getPatientById(patientId);

        if (p == null) {
            System.out.println("Patient of ID: " + patientId + " does not exist.");
            return;
        }

        System.out.println("\n-----MEDICAL RECORD OF " + patientId + "----");
        System.out.println("Name: " + p.getName());
        System.out.println("Date of Birth: " + p.getDateOfBirth());
        System.out.println("Gender: " + p.getGender());
        //System.out.println("Contact Number: " + p.getHpNumber());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Blood Type: " + p.getBloodType());
        System.out.println("Past Diagnoses: " + p.getDiagnoses());
        System.out.println("Past Treatments: " + p.getTreatments());
    }

    /**
     * Sets a new diagnosis for a patient.
     *
     * @param patientId the ID of the patient
     * @param diagnosis the diagnosis to set
     */
    public void setNewDiagnosis(String patientId, String diagnosis) {
        Patient p = getPatientById(patientId);
        p.setDiagnoses(diagnosis);
    }

    /**
     * Sets a new treatment for a patient.
     *
     * @param patientId the ID of the patient
     * @param treatment the treatment to set
     */
    public void setNewTreatment(String patientId, String treatment) {
        Patient p = getPatientById(patientId);
        p.setTreatments(treatment);
    }

    // Prescription method goes here too
}
