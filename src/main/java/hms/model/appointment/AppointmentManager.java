package hms.model.appointment;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import hms.model.user.Doctor;
import hms.model.user.Patient;

/**
 * The AppointmentManager class is responsible for managing appointments, including
 * retrieving doctor and appointment data, checking availability, scheduling, and
 * updating appointments and availability status.
 */

public class AppointmentManager {
    private static final String STAFF_FILE = "src\\main\\resources\\csv\\staff.csv";
    private static final String AVAILABILITY_FILE = "src\\main\\resources\\csv\\Timeslot.csv";
    private static final String APPOINTMENTS_FILE = "src\\main\\resources\\csv\\Appointment_Record.csv";

    /**
     * Retrieves all doctors from the staff file.
     * @return a list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(STAFF_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if ("doctor".equalsIgnoreCase(nextLine[2])) {
                    doctors.add(new Doctor(nextLine[0], nextLine[1], nextLine[2], nextLine[3], Integer.parseInt(nextLine[4])));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * Retrieves available timeslots for a specific doctor on a specific date.
     * @param doctorId the ID of the doctor
     * @param date the date to check for availability
     * @return a list of available timeslots
     */
    public List<Timeslot> getAvailableTimeslots(String doctorId, String date) {
        List<Timeslot> availableTimeslots = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(AVAILABILITY_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(doctorId) && nextLine[1].equals(date) && nextLine[3].equals("Available")) {
                    availableTimeslots.add(new Timeslot(nextLine[0], nextLine[1], nextLine[2], nextLine[3]));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return availableTimeslots;
    }

    /**
     * Checks if a specific timeslot is available for a doctor on a specific date.
     * @param doctorId the ID of the doctor
     * @param date the date to check
     * @param timeslot the timeslot to check
     * @return true if the timeslot is available, false otherwise
     */
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

    /**
     * Retrieves an existing appointment for a specific patient and doctor.
     * @param patientId the ID of the patient
     * @param doctorId the ID of the doctor
     * @return the existing appointment, or null if none found
     */
    public Appointment getExistingAppointment(String patientId, String doctorId) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Appointment appointment = new Appointment(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]);
                if (appointment.getPatientId().equals(patientId) && appointment.getDoctorId().equals(doctorId) &&
                        appointment.isPendingOrConfirmed()) {
                    return appointment;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all existing appointments for a specific patient.
     * @param patientId the ID of the patient
     * @return a list of existing appointments
     */
    public List<Appointment> getExistingAppointment(String patientId) {
        List<Appointment> appointment = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                appointment.add(new Appointment(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    /**
     * Reschedules an appointment.
     * @param appointmentId the ID of the appointment to reschedule
     * @return true if the appointment was successfully rescheduled, false otherwise
     */
    public boolean rescheduleAppointment(String appointmentId) {
        return false;
    }

    /**
     * Generates a new appointment ID.
     * @return the generated new appointment ID
     */
    public String generateAppointmentID() {
        String lastAppointmentID = getLastAppointmentID();
        if (lastAppointmentID == null) {
            return "A00001";
        }

        int numericPart = Integer.parseInt(lastAppointmentID.substring(1));
        int newNumericPart = numericPart + 1;
        return String.format("A%05d", newNumericPart);
    }

    /**
     * Retrieves the last appointment ID from the appointments file.
     * @return the last appointment ID, or null if none found
     */
    private String getLastAppointmentID() {
        String lastAppointmentID = null;

        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            List<String[]> allRecords = reader.readAll();
            if (!allRecords.isEmpty()) {
                lastAppointmentID = allRecords.get(allRecords.size() - 1)[0];
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return lastAppointmentID;
    }

    /**
     * Makes a new appointment.
     * @param patientId the ID of the patient
     * @param doctorId the ID of the doctor
     * @param date the date of the appointment
     * @param timeslot the timeslot of the appointment
     */
    public void makeAppointment(String patientId, String doctorId, String date, String timeslot) {

        updateAvailability(doctorId, date, timeslot, "Not Available");
        String appointmentID = generateAppointmentID();
        String appointmentStatus = "Pending";

        String[] appointment = {appointmentID, patientId, doctorId, date, timeslot, appointmentStatus};

        try (CSVWriter appointmentWriter = new CSVWriter(new FileWriter(APPOINTMENTS_FILE, true))) {
            appointmentWriter.writeNext(appointment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Appointment made successfully. Your appointment ID is " + appointmentID);
    }

    /**
     * Updates the availability of a timeslot for a specific doctor on a specific date.
     * @param doctorId the ID of the doctor
     * @param date the date of the timeslot
     * @param timeslot the timeslot to update
     * @param availability the new availability status
     */
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
