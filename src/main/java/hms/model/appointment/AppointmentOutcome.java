package hms.model.appointment;

import hms.model.medicine.Prescription;

/**
 * The AppointmentOutcome class represents the outcome of an appointment in the hospital management system.
 * It contains details such as outcome ID, appointment ID, service provided, notes, and prescription information.
 */
public class AppointmentOutcome {

    private String appointmentOutcomeId;
    private String appointmentId;
    private String service;
    private String notes;
    private Prescription prescription;
    // private String medicine;
    // private String prescriptionStatus;

    /**
     * Constructor for AppointmentOutcome.
     * @param appointmentOutcomeId the ID of the appointment outcome
     * @param appointmentId the ID of the associated appointment
     * @param service the service provided during the appointment
     * @param notes any additional notes regarding the appointment outcome
     * @param prescription the prescription details associated with the appointment outcome
     */
    public AppointmentOutcome(String appointmentOutcomeId, String appointmentId, String service, String notes, Prescription prescription) {
        this.appointmentOutcomeId = appointmentOutcomeId;
        this.appointmentId = appointmentId;
        this.service = service;
        this.notes = notes;
        this.prescription = prescription;
        // this.medicine = medicine;
        // this.prescriptionStatus = prescriptionStatus;
    }

    /**
     * Gets the appointment outcome ID.
     * @return the appointment outcome ID
     */
    public String getAppointmentOutcomeId() {
        return appointmentOutcomeId;
    }

    /**
     * Sets the appointment outcome ID.
     * @param appointmentOutcomeId the appointment outcome ID to set
     */
    public void setAppointmentOutcomeId(String appointmentOutcomeId) {
        this.appointmentOutcomeId = appointmentOutcomeId;
    }

    /**
     * Gets the appointment ID.
     * @return the appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment ID.
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the service.
     * @return the service
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service.
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the notes.
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the prescription.
     * @return the prescription
     */
    public Prescription getPrescription() {
        return this.prescription;
    }

    /**
     * Sets the prescription.
     * @param prescription the prescription to set
     */
    public void setPrescription(Prescription prescription) {
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

    /**
     * Returns a string representation of the AppointmentOutcome.
     * @return a string representation of the AppointmentOutcome
     */
    @Override
    public String toString() {
        return "Appointment Outcome ID'" + appointmentOutcomeId + '\''
                + ", Appointment Id='" + appointmentId + '\''
                + ", Service='" + service + '\''
                + ", Notes='" + notes + '\''
                + ", Medicine='" + this.prescription.getMedicineQuantityPair() + '\''
                + ", PrescriptionStatus='" + this.prescription.getStatus();
    }
}
