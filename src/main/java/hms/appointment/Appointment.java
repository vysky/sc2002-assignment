package hms.appointment;

import hms.user.Patient;

public class Appointment {
    private Patient patient;
    //private Doctor doctor;
    private String timeSlot;
    private String status;

    public Appointment(Patient patient, String timeSlot,String status){
        this.patient = patient;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public String[] toRecord(){
        return new String[]{patient.getName(), status};
    }
}
