package hms.model.appointment;
import hms.model.user.Patient;
import hms.model.user.Doctor;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Appointment> getExistingAppointment(String patientId) {
        List<Appointment> appointment = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[5].equals("Pending") || (nextLine[5].equals("Confirmed"))) {
                appointment.add(new Appointment(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    public boolean rescheduleAppointment(String appointmentId) {
        List<Appointment> appointments = new ArrayList<>();

        try (CSVWriter writer = new CSVWriter(new FileWriter(APPOINTMENTS_FILE, true))) {
            for (Appointment appointment : appointments) {
                writer.writeNext(new String[]{appointment.getAppointmentId(), appointment.getPatientId(), appointment.getDoctorId(), appointment.getDate(), appointment.getTimeslot(), "Rescheduled"});
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
           e.printStackTrace();
        }
    }

    public void updateAppointmentStatus(String appointmentID, String Status) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {

            List<String[]> allElements = new ArrayList<>();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(appointmentID)) {
                    nextLine[5] = Status;
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
}
