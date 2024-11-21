package hms.service.appointment;

import java.util.List;
import java.util.Scanner;

/**
 * The RecordManager class manages recording appointment outcomes and printing patient histories.
 */
public class RecordManager {

    private AppointmentManager appointmentManager;
    private AppointmentOutcomeManager appointmentOutcomeManager;

    /**
     * Constructs a RecordManager with the specified AppointmentManager and AppointmentOutcomeManager.
     *
     * @param appointmentManager        the appointment manager
     * @param appointmentOutcomeManager the appointment outcome manager
     */
    public RecordManager(AppointmentManager appointmentManager, AppointmentOutcomeManager appointmentOutcomeManager) {
        this.appointmentManager = appointmentManager;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
    }

    /**
     * Records the outcome of an appointment for a given doctor.
     *
     * @param doctorId the ID of the doctor
     * @param input    the scanner for user input
     */
    public void recordAppointmentOutcome(String doctorId, Scanner input) {
        String appointmentID = appointmentManager.getAppointmentId(doctorId, input);
        if (appointmentID != null) {
            // set appointment status as completed
            appointmentManager.updateAppointmentStatus(appointmentID, "Completed");

            // open new entry in appointment outcome
            appointmentOutcomeManager.createAppointmentOutcome(appointmentID, input);

            // write appointment outcome to CSV
            appointmentOutcomeManager.updateAppointmentOutcomeList();
        }
    }

    /**
     * Prints the appointment history of a patient.
     *
     * @param patientId the ID of the patient
     */
    public void printPatientHistory(String patientId) {
        List<String> appointmentIds = appointmentManager.getPatientAppointmentIds(patientId);
        if (appointmentIds.isEmpty()) {
            System.out.println("No appointment history found for the patient.");
            return;
        }
        appointmentOutcomeManager.printPatientHistory(appointmentIds);
    }
}
