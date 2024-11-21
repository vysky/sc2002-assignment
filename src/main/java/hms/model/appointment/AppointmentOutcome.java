package hms.model.appointment;

import hms.model.medicine.Prescription;

public class AppointmentOutcome {
    private String appointmentOutcomeId;
    private String appointmentId;
    private String service;
    private String notes;
    private Prescription prescription;
    // private String medicine;
    // private String prescriptionStatus;

    // public AppointmentOutcome(String appointmentOutcomeId, String appointmentId, String service, String medicine, String notes, String prescriptionStatus) {
    public AppointmentOutcome(String appointmentOutcomeId, String appointmentId, String service, String notes, Prescription prescription) {
        this.appointmentOutcomeId = appointmentOutcomeId;
        this.appointmentId = appointmentId;
        this.service = service;
        this.notes = notes;
        this.prescription = prescription;
        // this.medicine = medicine;
        // this.prescriptionStatus = prescriptionStatus;
    }

    public String getAppointmentOutcomeId() {
        return appointmentOutcomeId;
    }

    public void setAppointmentOutcomeId(String appointmentOutcomeId) {
        this.appointmentOutcomeId = appointmentOutcomeId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Prescription getPrescription()
    {
        return this.prescription;
    }

    public void setPrescription(Prescription prescription)
    {
        this.prescription = prescription;
    }

    // public String getMedicine() {
    //     return medicine;
    // }
    //
    // public void setMedicine(String medicine) {
    //     this.medicine = medicine;
    // }

    // public String getPrescriptionStatus() {
    //     return prescriptionStatus;
    // }
    //
    // public void setPrescriptionStatus(String prescriptionStatus) {
    //     this.prescriptionStatus = prescriptionStatus;
    // }

    @Override public String toString() {
        return "AppointmentOutcome{" +
                "appointmentOutcomeId='" + appointmentOutcomeId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", service='" + service + '\'' +
                ", notes='" + notes + '\'' +
                ", medicine='" + this.prescription.getMedicineQuantityPair() + '\'' +
                ", prescriptionStatus='" + this.prescription.getStatus() + '\'' +
                '}';
    }
}
