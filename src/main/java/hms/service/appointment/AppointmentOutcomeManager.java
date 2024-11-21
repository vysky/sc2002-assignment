package hms.service.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hms.model.appointment.AppointmentOutcome;
import hms.model.medicine.Medicine;
import hms.model.medicine.Prescription;
import hms.model.shared.MedicineQuantityPair;
import hms.repository.AppointmentOutcomeRepository;
import hms.service.medicine.InventoryServiceImpl;

/**
 * The AppointmentOutcomeManager class handles operations related to appointment outcomes,
 * including creation, retrieval, and management of appointment outcome records.
 */
public class AppointmentOutcomeManager {
    InventoryServiceImpl inventoryService;
    AppointmentOutcomeRepository appointmentOutcomeRepository;
    private static int counter = 1;
    List<AppointmentOutcome> appointmentOutcomes;

    /**
     * Constructs an AppointmentOutcomeManager with the provided InventoryServiceImpl.
     *
     * @param inventoryService the inventory service implementation
     */
    public AppointmentOutcomeManager(InventoryServiceImpl inventoryService) {
        this.inventoryService = inventoryService;
        this.appointmentOutcomeRepository = new AppointmentOutcomeRepository(inventoryService);
        this.appointmentOutcomes = appointmentOutcomeRepository.importFromCsv();
        // Initialize the counter based on existing records
        if (appointmentOutcomes != null && !appointmentOutcomes.isEmpty()) {
            counter = appointmentOutcomes.size() + 1;
        } else {
            appointmentOutcomes = new ArrayList<>(); // Ensure the list is initialized
        }
    }

    /**
     * Creates a new appointment outcome based on user input.
     *
     * @param appointmentId the ID of the appointment
     * @param input         the scanner for user input
     */
    public void createAppointmentOutcome(String appointmentId, Scanner input) {

        System.out.print("Enter service: ");
        String service = input.nextLine();

        System.out.print("Enter medicine: ");
        String medicine = input.nextLine();

        System.out.print("Enter quantity: ");
        String quantity = input.nextLine();

        System.out.print("Enter notes: ");
        String notes = input.nextLine();

        Medicine medicineObject = this.inventoryService.getMedicineList().stream().filter(uObject -> uObject.getMedicineName().equals(medicine)).findFirst().orElse(null);
        MedicineQuantityPair medicineQuantityPair = new MedicineQuantityPair(medicineObject, Integer.parseInt(quantity));
        Prescription prescription = new Prescription(medicineQuantityPair, false); // false = pending, will always be pending as it is newly created, thus i removed the input

        AppointmentOutcome newOutcome = new AppointmentOutcome(generateAppointmentID(), appointmentId, service, notes, prescription);
        appointmentOutcomes.add(newOutcome);
    }

    /**
     * Prints the appointment history for a list of appointment IDs.
     *
     * @param appointmentIds the list of appointment IDs
     */
    public void printPatientHistory(List<String> appointmentIds) {
        if (appointmentIds.isEmpty()) {
            System.out.println("No appointment history found for the patient.");
            return;
        }

        System.out.println("Patient Appointment History:");
        for (AppointmentOutcome outcome : appointmentOutcomes) {
            if (appointmentIds.contains(outcome.getAppointmentId())) {
                System.out.println("Appointment ID: " + outcome.getAppointmentId());
                System.out.println("Service: " + outcome.getService());
                System.out.println("Notes: " + outcome.getNotes());
                System.out.println("Medicine: " + outcome.getPrescription().getMedicineQuantityPair().medicine().getMedicineName());
                System.out.println("Quantity: " + outcome.getPrescription().getMedicineQuantityPair().quantity());
                String status = outcome.getPrescription().getStatus() ? "Dispensed" : "Pending";
                System.out.println("Prescription Status: " + status);
                System.out.println();
            }
        }
    }

    /**
     * Generates a new appointment outcome ID.
     *
     * @return the newly generated appointment outcome ID
     */
    public String generateAppointmentID() {
        String lastAppointmentID = getLastAppointmentID();
        if (lastAppointmentID != null && lastAppointmentID.length() > 1) {
            String numericPart = lastAppointmentID.substring(1); // Extract the numeric part
            try {
                int id = Integer.parseInt(numericPart);
                id++;
                return "O" + String.format("%05d", id); // Format the new ID
            } catch (NumberFormatException e) {
                // Handle the exception if the numeric part is not a valid integer
                e.printStackTrace();
            }
        }
        return "O00001"; // Default ID if no valid last ID is found
    }

    private String getLastAppointmentID() {
        String lastAppointmentID = null;
            if (!appointmentOutcomes.isEmpty()) {
                lastAppointmentID = appointmentOutcomes.get(appointmentOutcomes.size() - 1).getAppointmentOutcomeId();
            }

        return lastAppointmentID;
    }

    /**
     * Retrieves the list of appointment outcomes.
     *
     * @return the list of appointment outcomes
     */
    public List<AppointmentOutcome> getAppointmentOutcomes()
    {
        return this.appointmentOutcomes;
    }

    /**
     * Prints all appointment outcomes.
     */
    public void printAppointmentOutcomes() {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-15s %-20s %-20s %-20s %-10s %-10s%n", "No", "Appointment ID", "Service", "Notes", "Medicine", "Quantity", "Status");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        int i = 1;
        for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
            String status = appointmentOutcome.getPrescription().getStatus() ? "Dispensed" : "Pending";
            System.out.printf("%-5d %-15s %-20s %-20s %-20s %-10d %-10s%n", i, appointmentOutcome.getAppointmentId(), appointmentOutcome.getService(), appointmentOutcome.getNotes(), appointmentOutcome.getPrescription().getMedicineQuantityPair().medicine().getMedicineName(), appointmentOutcome.getPrescription().getMedicineQuantityPair().quantity(), status);
            i++;
        }
    }

    /**
     * Updates the appointment outcome list by exporting it to a CSV file.
     */
    public void updateAppointmentOutcomeList() {
        appointmentOutcomeRepository.exportToCsv(appointmentOutcomes);
    }
}
