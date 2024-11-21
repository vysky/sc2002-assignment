package hms.service.appointment;

import java.util.Scanner;

public class RecordManager {

    private AppointmentManager appointmentManager;
    private AppointmentOutcomeManager appointmentOutcomeManager;

    public RecordManager(AppointmentManager appointmentManager, AppointmentOutcomeManager appointmentOutcomeManager) {
        this.appointmentManager = appointmentManager;
        this.appointmentOutcomeManager = appointmentOutcomeManager;
    }

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
}
