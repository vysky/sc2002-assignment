package hms.model.appointment;

/**
 * The Timeslot class represents a specific time slot for a doctor's appointment.
 * It contains information about the doctor, date, time, and availability status of the slot.
 */

public class Timeslot {
    private String doctorId;
    private String date;
    private String time;
    private String status;

    /**
     * Constructor for Timeslot.
     * @param doctorId the ID of the doctor
     * @param date the date of the timeslot
     * @param time the time of the timeslot
     * @param status the availability status of the timeslot
     */
    public Timeslot(String doctorId, String date, String time, String status) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
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
     * Gets the time.
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time.
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
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
     * Returns a string representation of the Timeslot.
     * @return a string representation of the Timeslot
     */
    @Override
    public String toString() {
        return "Timeslot{" +
                "doctorId='" + doctorId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
