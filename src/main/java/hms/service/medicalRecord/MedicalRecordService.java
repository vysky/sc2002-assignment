package hms.service.medicalRecord;

import java.util.ArrayList;
import java.util.List;

import hms.model.user.Patient;
import hms.model.user.Doctor;
import hms.model.appointment.AppointmentManager;

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

    public void setNewDiagnosis(String patientId, String diagnosis) {
        Patient p = getPatientById(patientId);
        p.setDiagnoses(diagnosis);
    }

    public void setNewTreatment(String patientId, String treatment) {
        Patient p = getPatientById(patientId);
        p.setTreatments(treatment);
    }

    // Prescription method goes here too

    private Patient getPatientById(String patientId) {
        return this.patients.stream().filter(uObject -> uObject.getId().equals(patientId)).findFirst().orElse(null);
    }
}