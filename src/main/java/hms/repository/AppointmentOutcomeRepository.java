package hms.repository;

import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentOutcome;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRepository implements CsvRepository<AppointmentOutcome> {
    
    static final String CSV_FILE_PATH_APPOINTMENT_OUTCOME = "src/main/resources/appointment_outcome.csv";

    String[] APPOINTMENT_OUTCOME_HEADERS = {"Appointment Outcome ID", "Appointment ID", "Service", "Medicine", "Notes", "Prescription Status"};

    @Override
    public List<AppointmentOutcome> importFromCsv() {
        ArrayList<AppointmentOutcome> appointmentOutcomeArrayList = new ArrayList<AppointmentOutcome>();

        try {
            Reader reader = new FileReader(CSV_FILE_PATH_APPOINTMENT_OUTCOME);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            for (CSVRecord record : records) {
                AppointmentOutcome appointmentOutcome;

                String appointmentOutcomeId = record.get("Appointment Outcome ID");
                String appointmentId = record.get("Appointment ID");
                String service = record.get("Service");
                String medicine = record.get("Medicine");
                String notes = record.get("Notes");
                String prescriptionStatus = record.get("Prescription Status");

                appointmentOutcome = new AppointmentOutcome(appointmentOutcomeId, appointmentId, service, medicine, notes, prescriptionStatus);
                appointmentOutcomeArrayList.add(appointmentOutcome);
            }

            return appointmentOutcomeArrayList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return null;
    }

    @Override
    public void exportToCsv(List<AppointmentOutcome> appointmentOutcomes) {
        try {
            Writer writer = new FileWriter(CSV_FILE_PATH_APPOINTMENT_OUTCOME);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(APPOINTMENT_OUTCOME_HEADERS));

            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                csvPrinter.printRecord(appointmentOutcome.getAppointmentOutcomeId(), appointmentOutcome.getAppointmentId(), appointmentOutcome.getService(), appointmentOutcome.getMedicine(), appointmentOutcome.getNotes(), appointmentOutcome.getPrescriptionStatus());
            }

            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

}
