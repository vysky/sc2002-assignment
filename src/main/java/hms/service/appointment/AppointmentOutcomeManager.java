package hms.service.appointment;

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
    List<AppointmentOutcome> appointmentOutcomes;

    public AppointmentOutcomeManager(InventoryServiceImpl inventoryService) {
        this.inventoryService = inventoryService;
        this.appointmentOutcomeRepository = new AppointmentOutcomeRepository(inventoryService);
        this.appointmentOutcomes = appointmentOutcomeRepository.importFromCsv();
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
