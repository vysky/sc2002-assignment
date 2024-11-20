package hms.service.medicalRecord;

import java.util.ArrayList;
import java.util.List;

import hms.model.appointment.AppointmentManager;
import hms.model.user.Doctor;
import hms.model.user.Patient;

/**
 * The MedicalRecordService class provides functionality to manage and retrieve medical records
 * for patients in the hospital management system. It allows retrieving patient information,
 * setting new diagnoses, and setting new treatments.
 */

public class MedicalRecordService {
    private AppointmentManager appointmentManager = new AppointmentManager();
    private List<Patient> patients, currentPatients;

    public MedicalRecordService(List<Patient> listPatients) {
        this.patients = listPatients;
    }

    public List<Patient> getPatientsUnderDoctor(String doctorId) {
        currentPatients = new ArrayList<Patient>();
        List<String> patientIds = appointmentManager.getPatientsUnderDoctor(doctorId);
        
        for (String patientId : patientIds) {
            Patient patient = getPatientById(patientId);
            if (patient != null) {
                currentPatients.add(patient);
            }
        }

        return currentPatients;
    }

    public void printPatients(Doctor doctor) {
        System.out.println("\nThe following patients are under Dr " + doctor.getName());

        for (Patient p : getPatientsUnderDoctor(doctor.getId())) {
            System.out.println(p.getId());
        }
    }

    public boolean isPatientUnderDoctor(String patientId) {
        for (Patient patient : currentPatients) {
            if (patient.getId().equals(patientId)) {
                return true;
            }
        }
        return false;
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

        for (Patient patient : currentPatients) {
            if (patient.getId().equals(patientId)) {
                p = patient;

                System.out.println("\n-----MEDICAL RECORD OF " + patientId + "----");
                System.out.println("Name: " + p.getName());
                System.out.println("Date of Birth: " + p.getDateOfBirth());
                System.out.println("Gender: " + p.getGender());
                //System.out.println("Contact Number: " + p.getHpNumber());
                System.out.println("Email: " + p.getEmail());
                System.out.println("Blood Type: " + p.getBloodType());
                System.out.println("Past Diagnoses: " + p.getDiagnoses());
                System.out.println("Past Treatments: " + p.getTreatments());

                return;
            }
        }
        
        System.out.println("Patient of ID: " + patientId + " is not under the care of the doctor.");
    }

    public void patientGetMedicalRecord(String patientId) {
        Patient p = getPatientById(patientId);

        System.out.println("\n-----MEDICAL RECORD OF " + patientId + "----");
        System.out.println("Name: " + p.getName());
        System.out.println("Date of Birth: " + p.getDateOfBirth());
        System.out.println("Gender: " + p.getGender());
        //System.out.println("Contact Number: " + p.getHpNumber());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Blood Type: " + p.getBloodType());
        System.out.println("Past Diagnoses: " + p.getDiagnoses());
        System.out.println("Past Treatments: " + p.getTreatments());

        return;
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

    /**
     * Retrieves a patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return the patient with the specified ID
     */
    private Patient getPatientById(String patientId) {
        return this.patients.stream().filter(uObject -> uObject.getId().equals(patientId)).findFirst().orElse(null);
    }
}
