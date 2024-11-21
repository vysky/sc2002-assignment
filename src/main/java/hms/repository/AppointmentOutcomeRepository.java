package hms.repository;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;

import hms.model.medicine.Medicine;
import hms.model.medicine.Prescription;
import hms.model.shared.MedicineQuantityPair;
import hms.service.medicine.InventoryServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class for managing appointment outcomes.
 */
public class AppointmentOutcomeRepository implements CsvRepository<AppointmentOutcome>
{
    /**
     * Inventory service used to manage medicine data.
     */
    private InventoryServiceImpl inventoryService;

    /**
     * Path to the CSV file where appointment outcomes are stored.
     */
    static final String CSV_FILE_PATH_APPOINTMENT_OUTCOME = "src/main/resources/csv/appointment_outcome.csv";

    /**
     * Headers for the CSV file.
     */
    String[] APPOINTMENT_OUTCOME_HEADERS = {"Appointment Outcome ID", "Appointment ID", "Service", "Notes", "Medicine", "Medicine Quantity", "Prescription Status"};

    /**
     * Constructs an AppointmentOutcomeRepository with the specified inventory service.
     * @param inventoryService the inventory service implementation
     */
    public AppointmentOutcomeRepository(InventoryServiceImpl inventoryService)
    {
        this.inventoryService = inventoryService;
    }

    /**
     * Imports appointment outcomes from a CSV file.
     * @return a list of appointment outcomes
     */
    @Override
    public List<AppointmentOutcome> importFromCsv()
    {
        ArrayList<AppointmentOutcome> appointmentOutcomeArrayList = new ArrayList<AppointmentOutcome>();

        try
        {
            Reader reader = new FileReader(CSV_FILE_PATH_APPOINTMENT_OUTCOME);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            for (CSVRecord record : records)
            {
                AppointmentOutcome appointmentOutcome;

                String appointmentOutcomeId = record.get("Appointment Outcome ID");
                String appointmentId = record.get("Appointment ID");
                String service = record.get("Service");
                String notes = record.get("Notes");
                String medicine = record.get("Medicine");
                Medicine medicineObject;
                int medicineQuantity = 0; // Default value

                if (record.isMapped("Medicine Quantity")) {
                    medicineQuantity = Integer.parseInt(record.get("Medicine Quantity"));
                }

                MedicineQuantityPair medicineQuantityPair;
                boolean prescriptionStatus;

                if (record.get("Prescription Status") == null || record.get("Prescription Status").isEmpty())
                {
                    prescriptionStatus = false;
                }
                else
                {
                    prescriptionStatus = Boolean.parseBoolean(record.get("Prescription Status"));
                }

                if (medicine == null || medicine.isEmpty())
                {
                    medicineObject = new Medicine("Paracetamol", 100, 20);
                }
                else
                {
                    try
                    {
                        medicineObject = this.inventoryService.getMedicineList().stream().filter(uObject -> uObject.getMedicineName().equals(medicine)).findFirst().orElse(null);
                    }
                    catch (Exception e)
                    {
                        medicineObject = new Medicine("Paracetamol", 100, 20);
                    }
                }

                medicineQuantityPair = new MedicineQuantityPair(medicineObject, medicineQuantity);
                Prescription prescription = new Prescription(medicineQuantityPair, prescriptionStatus);

                appointmentOutcome = new AppointmentOutcome(appointmentOutcomeId, appointmentId, service, notes, prescription);
                appointmentOutcomeArrayList.add(appointmentOutcome);
            }

            return appointmentOutcomeArrayList;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }

        return null;
    }

    /**
     * Exports appointment outcomes to a CSV file.
     * @param appointmentOutcomes the list of appointment outcomes to export
     */
    @Override
    public void exportToCsv(List<AppointmentOutcome> appointmentOutcomes)
    {
        try
        {
            Writer writer = new FileWriter(CSV_FILE_PATH_APPOINTMENT_OUTCOME);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(APPOINTMENT_OUTCOME_HEADERS).build();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes)
            {
                csvPrinter.printRecord(appointmentOutcome.getAppointmentOutcomeId(), appointmentOutcome.getAppointmentId(), appointmentOutcome.getService(), appointmentOutcome.getNotes(), appointmentOutcome.getPrescription().getMedicineQuantityPair().medicine().getMedicineName(), appointmentOutcome.getPrescription().getMedicineQuantityPair().quantity(), appointmentOutcome.getPrescription().getStatus());
            }

            csvPrinter.flush();
            csvPrinter.close();
        }
        catch (IOException e)
        {
            System.out.println("Error writing to file.");
        }
    }

}
