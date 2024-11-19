package hms.model.appointment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import hms.model.user.Doctor;

public class AppointmentManager {

    private static final String STAFF_FILE = "src\\main\\resources\\csv\\staff.csv";
    private static final String AVAILABILITY_FILE = "src\\main\\resources\\csv\\Timeslot.csv";
    private static final String APPOINTMENTS_FILE = "src\\main\\resources\\csv\\Appointment_Record.csv";

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

    public Timeslot timeSlotSelector(String doctorId, String date, Scanner input) {
        List<Timeslot> availableTimeslots = getAvailableTimeslots(doctorId, date);
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
            return null;
        }

        System.out.println("Available timeslots for " + doctorId + " on " + date + ":");
        for (int i = 0; i < availableTimeslots.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimeslots.get(i).getTime());
        }

        System.out.print("Select a timeslot: ");
        int selectedTimeslotIndex = Integer.parseInt(input.nextLine()) - 1;
        System.out.println("You have selected " + availableTimeslots.get(selectedTimeslotIndex).getTime());
        return availableTimeslots.get(selectedTimeslotIndex);
    }

    public void printAvailableTimeslots(String doctorId, String date) {
        List<Timeslot> availableTimeslots = getAvailableTimeslots(doctorId, date);
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
        } else {
            for (Timeslot timeslot : availableTimeslots) {
                System.out.println(timeslot.getTime());
            }
        }
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

    public void setAvailability(String doctorId, String date, Scanner input) {
        try (CSVReader reader = new CSVReader(new FileReader(AVAILABILITY_FILE))) {
            Timeslot timeslot = timeSlotSelector(doctorId, date, input);
            while (true) {
                System.out.println("Set " + timeslot.getTime() + " on " + date + " as Unavailable? (Y/N)\n(Input 0 to exit)");
                String choice = input.nextLine();
                if (choice.equals("0")) {
                    break;
                }
                if (choice.equalsIgnoreCase("Y")) {
                    updateAvailability(doctorId, date, timeslot.getTime(), "Not Available");
                    System.out.println("You are now unavailable at " + timeslot.getTime() + " on " + date);
                    break;
                } else if (choice.equalsIgnoreCase("N")) {
                    System.out.println("Transaction cancelled");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter Y or N");
                }
            }

            System.out.println("Availability updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Appointment getExistingAppointment(String patientId, String doctorId) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Appointment appointment = new Appointment(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]);
                if (appointment.getPatientId().equals(patientId) && appointment.getDoctorId().equals(doctorId)
                        && appointment.isPendingOrConfirmed()) {
                    return appointment;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return null;
    }

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

    public List<String> getPatientsUnderDoctor(String doctorId) {
        List<String> patientIds = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[2].equals(doctorId) && nextLine[5].equals("Confirmed")) {
                    patientIds.add(nextLine[1]);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return patientIds;
    }

    // public boolean rescheduleAppointment(String appointmentId) {
    // }
    public String generateAppointmentID() {
        String lastAppointmentID = getLastAppointmentID();
        if (lastAppointmentID == null) {
            return "A00001";
        }

        int numericPart = Integer.parseInt(lastAppointmentID.substring(1));
        int newNumericPart = numericPart + 1;
        return String.format("A%05d", newNumericPart);
    }

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

    public void acceptAppointment(String doctorId, Scanner input) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {

            List<String[]> allElements = new ArrayList<>();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals("Pending") && nextLine[2].equals(doctorId)) {
                    boolean loop = true;
                    while (loop) {
                        System.out.println("Appointment with " + nextLine[1] + " on " + nextLine[3] + " at " + nextLine[4]);
                        System.out.println("Do you want to accept this appointment? (Y/N)");
                        String choice = input.nextLine();
                        if (choice.equalsIgnoreCase("Y")) {
                            nextLine[5] = "Confirmed";
                            updateAvailability(nextLine[2], nextLine[3], nextLine[4], "Not Available");
                            System.out.println("Appointment accepted.");
                            loop = false;
                        } else if (choice.equalsIgnoreCase("N")) {
                            nextLine[5] = "Rejected";
                            updateAvailability(nextLine[2], nextLine[3], nextLine[4], "Available");
                            System.out.println("Appointment rejected.");
                            loop = false;

                        } else {
                            System.out.println("Invalid input. Please enter Y or N.");
                        }
                    }
                }
                allElements.add(nextLine);
            }

            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(APPOINTMENTS_FILE))) {
                csvWriter.writeAll(allElements);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
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

    public void getAppointments(String doctorId) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[2].equals(doctorId)) {
                    System.out.println("Appointment ID: " + nextLine[0]);
                    System.out.println("Patient ID: " + nextLine[1]);
                    System.out.println("Date: " + nextLine[3]);
                    System.out.println("Timeslot: " + nextLine[4]);
                    System.out.println();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}
