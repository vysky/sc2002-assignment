package hms.model.appointment;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String timeslot;
    private String status;

    /**
     * Constructor for Appointment.
     * @param appointmentId the ID of the appointment
     * @param patientId the ID of the patient
     * @param doctorId the ID of the doctor
     * @param date the date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param status the status of the appointment
     */
    public Appointment(String appointmentId, String patientId, String doctorId, String date, String timeslot, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeslot = timeslot;
        this.status = status;
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
     * Gets the patient ID.
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient ID.
     * @param patientId the patient ID to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the doctor ID.
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the doctor ID.
     * @param doctorId the doctor ID to set
     */
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the date.
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date.
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the timeslot.
     * @return the timeslot
     */
    public String getTimeslot() {
        return timeslot;
    }

    /**
     * Sets the timeslot.
     * @param timeslot the timeslot to set
     */
    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Gets the status.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the appointment status is pending or confirmed.
     * @return true if the status is pending or confirmed, false otherwise
     */
    public boolean isPendingOrConfirmed() {
        return "Pending".equalsIgnoreCase(this.status) || "Confirmed".equalsIgnoreCase(this.status);
    }

    /**
     * Returns a string representation of the Appointment.
     * @return a string representation of the Appointment
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", date='" + date + '\'' +
                ", timeslot='" + timeslot + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
