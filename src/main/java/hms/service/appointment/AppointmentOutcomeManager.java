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

public class AppointmentOutcomeManager {
    InventoryServiceImpl inventoryService;
    AppointmentOutcomeRepository appointmentOutcomeRepository;
    private static int counter = 1;
    List<AppointmentOutcome> appointmentOutcomes;

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
        Prescription prescription = new Prescription(medicineQuantityPair, false); // false = pending, will always be pending as it is newly created, thus i removed the inout

        AppointmentOutcome newOutcome = new AppointmentOutcome(generateAppointmentID(), appointmentId, service, notes, prescription);
        appointmentOutcomes.add(newOutcome);
    }

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

    public void updateAppointmentOutcomeList() {
        appointmentOutcomeRepository.exportToCsv(appointmentOutcomes);
    }
}
