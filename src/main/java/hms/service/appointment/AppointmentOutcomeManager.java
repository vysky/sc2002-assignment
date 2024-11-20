package hms.service.appointment;

import java.util.List;
import java.util.Scanner;

import hms.model.appointment.AppointmentOutcome;
import hms.repository.AppointmentOutcomeRepository;

public class AppointmentOutcomeManager {
    List<AppointmentOutcome> appointmentOutcomes;
    AppointmentOutcomeRepository appointmentOutcomeRepository = new AppointmentOutcomeRepository();

    public AppointmentOutcomeManager() {
        this.appointmentOutcomes = appointmentOutcomeRepository.importFromCsv();
    }

    public void createAppointmentOutcome(String appointmentId, Scanner input) {

        System.out.print("Enter service: ");
        String service = input.nextLine();

        System.out.print("Enter medicine: ");
        String medicine = input.nextLine();

        System.out.print("Enter notes: ");
        String notes = input.nextLine();

        System.out.print("Enter prescription status: ");
        String prescriptionStatus = input.nextLine();

        AppointmentOutcome newOutcome = new AppointmentOutcome(generateAppointmentID(), appointmentId, service, medicine, notes, prescriptionStatus);
        appointmentOutcomes.add(newOutcome);
    }

    public String generateAppointmentID() {
        String lastAppointmentID = getLastAppointmentID();
        if (lastAppointmentID == null) {
            return "AO00001";
        }

        int numericPart = Integer.parseInt(lastAppointmentID.substring(1));
        int newNumericPart = numericPart + 1;
        return String.format("AO%05d", newNumericPart);
    }

    private String getLastAppointmentID() {
        String lastAppointmentID = null;
            if (!appointmentOutcomes.isEmpty()) {
                lastAppointmentID = appointmentOutcomes.get(appointmentOutcomes.size() - 1).getAppointmentOutcomeId();
            }

        return lastAppointmentID;
    }

    public void updateAppointmentOutcomeList() {
        appointmentOutcomeRepository.exportToCsv(appointmentOutcomes);
    }
}
