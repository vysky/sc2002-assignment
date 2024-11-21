package hms.repository;

import hms.model.appointment.Appointment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The AppointmentRepository class provides methods to import and export appointment data
 * from and to a CSV file. It implements the CsvRepository interface for handling appointment objects.
 */

public class AppointmentRepository implements CsvRepository<Appointment>
{
    /**
     * Path to the CSV file where appointments are stored.
     */
    static final String CSV_FILE_PATH_APPOINTMENT = "src/main/resources/appointment.csv";

    /**
     * Headers for the CSV file.
     */
    String[] APPOINTMENT_HEADERS = {"Appointment ID", "Patient", "Doctor", "Date", "Timeslot", "Appointment_Status", "Service", "Medication", "Notes", "Prescription_Status"};

    /**
     * Imports appointment data from a CSV file.
     *
     * @return a list of appointments
     */
    public List<Appointment> importFromCsv()
    {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();

        try
        {
            // Create a reader for the CSV file
            Reader reader = new FileReader(CSV_FILE_PATH_APPOINTMENT);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            //Loop through each record and create an Appointment object
            for (CSVRecord record : records)
            {
                String appointmentID = record.get("Appointment ID");
                String patientID = record.get("Patient");
                String doctorID = record.get("Doctor");
                String date = record.get("Date");
                String timeslot = record.get("Timeslot");
                String status = record.get("Appointment_Status");

                // Create a new appointment object
                Appointment appointment = new Appointment(appointmentID, patientID, doctorID, date, timeslot, status);
                appointmentArrayList.add(appointment);
            }

            return appointmentArrayList;
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
     * Exports appointment data to a CSV file.
     *
     * @param appointmentList the list of appointments to export
     */
    public void exportToCsv(List<Appointment> appointmentList)
    {
        try
        {
            // Create a writer for the CSV file
            Writer writer = new FileWriter(CSV_FILE_PATH_APPOINTMENT);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(APPOINTMENT_HEADERS).build();

            // Create a CSVPrinter to write data
            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

             // Loop through each appointment and write its data to the CSV
            for (Appointment appointment : appointmentList)
            {
                csvPrinter.printRecord(appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getDate(), appointment.getTimeslot(), appointment.getStatus());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
