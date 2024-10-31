package hms.user.helpers;

import java.util.List;

import hms.user.Patient;

public class MedicalRecord {
    private List<Patient> patients;

    public MedicalRecord(List<Patient> p) {
        patients = p;
    }

    public Patient getPatientById(String patientId) {
        for (Patient p : patients) {
            if (patientId.equals(p.getPatientId())) {
                return p;
            }
        }
        return null;
    }

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
        System.out.println("Contact Number: " + p.getHpNumber());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Blood Type: " + p.getBloodType());
        System.out.println("Past Diagnoses: " + p.getDiagnoses());
        System.out.println("Past Treatments" + p.getTreatments());
    }
}
