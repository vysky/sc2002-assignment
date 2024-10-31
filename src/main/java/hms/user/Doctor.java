package hms.user;

public class Doctor extends Staff {

    public Doctor(String staffId, String name, String role, String gender, int age) {
        super(staffId, name, role, gender, age);
    }

    // MEDICAL RECORD MANAGEMENT

    public void setPatientDiagnosis(Patient p, String diag) {
        p.setDiagnoses(diag);
    }

    public void setPatientTreatment(Patient p, String treat) {
        p.setTreatments(treat);
    }

    // There will be a method to set patient prescriptions too
}
