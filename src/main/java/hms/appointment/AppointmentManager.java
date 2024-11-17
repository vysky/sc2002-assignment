package hms.appointment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private static final String AVAILABILITY_FILE = "src\\main\\resources\\Timeslot.csv";
    private static final String APPOINTMENTS_FILE = "src\\main\\resources\\Appointment_Record.csv";

    public List<String> getAvailableTimeslots(String doctorId, String date) {

        List<String> availableTimeslots = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(AVAILABILITY_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(doctorId) && nextLine[1].equals(date) && nextLine[3].equals("Available")) {
                    availableTimeslots.add(nextLine[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return availableTimeslots;
    }

     public boolean isAvailable(String doctorId, String date, String timeslot) {

        try (CSVReader reader = new CSVReader(new FileReader(AVAILABILITY_FILE))) {

            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(doctorId) && nextLine[1].equals(date) && nextLine[2].equals(timeslot)) {
                    return nextLine[3].equals("Available");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void makeAppointment(String doctorId, String date, String timeslot) {

        updateAvailability(doctorId, date, timeslot, "Not Available");

        String[] appointment = {doctorId, date, timeslot};

        try (CSVWriter appointmentWriter = new CSVWriter(new FileWriter(APPOINTMENTS_FILE, true))) {
            appointmentWriter.writeNext(appointment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Appointment made successfully.");
    }

    public void updateAvailability(String doctorId, String date, String timeslot, String availability) {

        try (CSVReader reader = new CSVReader(new FileReader(AVAILABILITY_FILE))) {

            List<String[]> allElements = new ArrayList<>();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(doctorId) && nextLine[1].equals(date) && nextLine[2].equals(timeslot)) {
                    nextLine[3] = availability;
                }
                allElements.add(nextLine);
            }

            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(AVAILABILITY_FILE))) {
                csvWriter.writeAll(allElements);
            }

            System.out.println("Availability updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
