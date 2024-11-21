package hms.service.appointment;

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

import hms.model.appointment.Appointment;
import hms.model.appointment.Timeslot;
import hms.model.user.Doctor;
import hms.model.user.Patient;

/**
 * The AppointmentManager class is responsible for managing appointments, including
 * retrieving doctor and appointment data, checking availability, scheduling, and
 * updating appointments and availability status.
 */
public class AppointmentManager {

    private List<Patient> patients;
    private static final String STAFF_FILE = "src/main/resources/csv/staff.csv";
    private static final String AVAILABILITY_FILE = "src/main/resources/csv/Timeslot.csv";
    private static final String APPOINTMENTS_FILE = "src/main/resources/csv/Appointment_Record.csv";
    private static final String PATIENT_FILE = "src/main/resources/csv/patient.csv";

//General
    /**
     * Prompts the user to input a date.
     *
     * @param input the scanner for user input
     * @return the date entered by the user
     */
    public String inputDate (Scanner input) {
        System.out.println("Enter Date (e.g., 04 Nov 2024):");
        return input.nextLine();
    }

//Patient

    /**
     * Updates patient information based on user input.
     *
     * @param patientId   the ID of the patient
     * @param allPatients the list of all patients
     * @param input       the scanner for user input
     */
    public void updateInformation (String patientId, List<Patient> allPatients, Scanner input) {

        Patient currentPatient = null;

        for (int i = 0; i < allPatients.size(); i++)
        {
            if (allPatients.get(i).getId().equals(patientId)) {
                currentPatient = allPatients.get(i);
                break;
            }
        }

        String newName = currentPatient.getName();
        String newDob = currentPatient.getDateOfBirth();
        String newEmail = currentPatient.getEmail();

        while (true) {
            System.out.println("Select the information you want to update:");
            System.out.println("(1) Name");
            System.out.println("(2) Date Of Birth");
            System.out.println("(3) Email");
            System.out.println("(4) Confirm the changes");
            System.out.println("(5) Discard changes and exit");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    newName = input.nextLine();
                    currentPatient.setName(newName);
                    break;

                case 2:
                    System.out.print("Enter new date of birth: ");
                    newDob = input.nextLine();
                    currentPatient.setDateOfBirth(newDob);
                    break;

                case 3:
                    System.out.print("Enter new email: ");
                    newEmail = input.nextLine();
                    currentPatient.setEmail(newEmail);
                    break;

                case 4:

                    try (CSVWriter writer = new CSVWriter(new FileWriter(PATIENT_FILE))) {
                    // Write header
                        String[] header = {"Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information", "Diagnoses", "Treatments", "Prescriptions", "Password", "Hash", "Active"}; // Replace with actual field names
                        writer.writeNext(header);

                    // Write patient data
                        for (Patient patient : allPatients) {
                            String[] patientData = convertPatientToStringArray(patient);
                            writer.writeNext(patientData);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Changes confirmed and saved.");
                    return;

                case 5:
                    System.out.println("No changes has been made");
                    System.out.println("Enter any key to continue");
                    input.nextLine();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again");
                    break;
            }
        }
    }

    private String[] convertPatientToStringArray(Patient patient) {
        // Convert patient fields to a String array
        // Replace getId(), getName(), getDateOfBirth(), getEmail() with actual getter methods
        ArrayList<String> diagnoses = patient.getDiagnoses();
        ArrayList<String> treatments = patient.getTreatments();
        ArrayList<String> prescriptions = patient.getPrescriptions();
        boolean active = patient.getActive();
        String activeStatus = "";

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < diagnoses.size(); i++)
        {
            stringBuilder.append(diagnoses.get(i));
            if (i < diagnoses.size() - 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");
        String diagnosesString = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("[");
        for (int i = 0; i < treatments.size(); i++)
        {
            stringBuilder.append(treatments.get(i));
            if (i < treatments.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        String treatmentsString = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("[");
        for (int i = 0; i < prescriptions.size(); i++)
        {
            stringBuilder.append(prescriptions.get(i));
            if (i < prescriptions.size() - 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("]");
        String prescriptionsString = stringBuilder.toString();

        stringBuilder.setLength(0);

        if (active == true){
            activeStatus = "true";
        }

        else {
            activeStatus = "false";
        }

        return new String[]{patient.getId(), patient.getName(), patient.getDateOfBirth(), patient.getGender(), patient.getBloodType(), patient.getEmail(),
        diagnosesString, treatmentsString, prescriptionsString, patient.getPassword(), patient.getHash(), activeStatus};
    }


//Related to Doctor
    /**
     * Retrieves all doctors from the staff file.
     *
     * @return a list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(STAFF_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if ("doctor".equalsIgnoreCase(nextLine[2])) {
                    doctors.add(new Doctor(nextLine[0], nextLine[1], nextLine[2], nextLine[3], Integer.parseInt(nextLine[4]), nextLine[5], nextLine[6], Boolean.parseBoolean(nextLine[7])));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * Displays a list of doctors.
     *
     * @param doctors the list of doctors to display
     */
    public void displayDoctor(List<Doctor> doctors) {
        System.out.println("List of doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            System.out.println("(" + (i + 1) + ") Dr. " + doctor.getName());
        }
    }

    /**
     * Allows the user to select a doctor.
     *
     * @param input      the scanner for user input
     * @param doctorSize the number of doctors available
     * @return the selected doctor's index, or -1 if invalid
     */
    public int selectDoctor(Scanner input, int doctorSize) {
        System.out.println("Select a doctor by numbers:");
        int doctorNumber = input.nextInt();
        input.nextLine();

        if (doctorNumber < 1 || doctorNumber > doctorSize) {
            return -1;
        }

        return doctorNumber;
    }


//Related to Timeslot
    /**
     * Retrieves available timeslots for a specific doctor on a specific date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to check for availability
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
     * Displays available timeslots.
     *
     * @param availableTimeslots the list of available timeslots
     * @return 1 if timeslots are available, -1 otherwise
     */
    public int displayAvailableTimeslots(List<Timeslot> availableTimeslots) {
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
            return -1;
        } else {
            System.out.println("Available Timeslots:");
            for (int i = 0; i < availableTimeslots.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + availableTimeslots.get(i).getTime());
            }
        }
        return 1;
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

    /**
     * Checks if a specific timeslot is available for a doctor on a date.
     *
     * @param doctorId the ID of the doctor
     * @param date     the date to check
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
     * Updates the availability of a timeslot for a doctor.
     *
     * @param doctorId    the ID of the doctor
     * @param date        the date
     * @param timeslot    the timeslot
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
           e.printStackTrace();
        }
    }

//Timeslot & Appointment
    public void selectTimeslotAndMakeAppointment(Scanner input, String patientId, String doctorId, String date, List<Timeslot> availableTimeslots) {
        System.out.println("Select a timeslot by number:");
        int timeslotNumber = input.nextInt();
        input.nextLine();

        if (timeslotNumber < 1 || timeslotNumber > availableTimeslots.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String timeslot = availableTimeslots.get(timeslotNumber - 1).getTime();

        if (isAvailable(doctorId, date, timeslot)) {
            makeAppointment(patientId, doctorId, date, timeslot);
        } else {
            System.out.println("The selected timeslot is not available.");
        }
    }

//Related to Appointment
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
                if (nextLine[1].equals(patientId)) {
                    if (nextLine[5].equals("Pending") || nextLine[5].equals("Confirmed")) {
                        appointment.add(new Appointment(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]));
                    }
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    public int displayExistingAppointment(List<Appointment> existingAppointments, List<Doctor> doctors) {
        String doctorName = "";

        if (existingAppointments.isEmpty()) {
            return -1;
        } else {
            System.out.println("Your existing appointment:");
            for (int i = 0; i < existingAppointments.size(); i++) {
                Appointment appointment = existingAppointments.get(i);
                String doctorId = existingAppointments.get(i).getDoctorId();

                for (int j = 0; j < doctors.size(); j++) {
                    if (doctors.get(j).getId().equals(doctorId)) {
                        doctorName = doctors.get(j).getName();
                    }
                }

                System.out.println("(" + (i + 1) + ") Dr. " + doctorName + ", " + appointment.getDate() + ", " + appointment.getTimeslot() + ", " + appointment.getStatus());
            }

            return 1;
        }
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
     * Makes a new appointment for a patient with a doctor at a specified date and timeslot.
     *
     * @param patientId the ID of the patient
     * @param doctorId  the ID of the doctor
     * @param date      the date of the appointment
     * @param timeslot  the timeslot of the appointment
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

    /**
     * Updates the status of an appointment.
     *
     * @param appointmentID the ID of the appointment
     * @param status        the new status of the appointment
     */
    public void updateAppointmentStatus(String appointmentID, String status) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {

            List<String[]> allElements = new ArrayList<>();
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals(appointmentID)) {
                    nextLine[5] = status;
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

    public void getAppointments(String doctorId) {
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            int index = 1;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[2].equals(doctorId) && nextLine[5].equals("Pending")) {
                    System.out.println((index++) + ":");
                    System.out.println("Appointment ID: " + nextLine[0]);
                    System.out.println("Patient ID: " + nextLine[1]);
                    System.out.println("Date: " + nextLine[3]);
                    System.out.println("Timeslot: " + nextLine[4]);
                    System.out.println();
                }
            }
            if (index == 1) {
                System.out.println("No pending appointments.");
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPatientAppointmentIds(String patientId) {
        List<String> appointmentIds = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[1].equals(patientId)) {
                    appointmentIds.add(nextLine[0]);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return appointmentIds;
    }

    public Appointment chooseAppointment(Scanner input, String doctorId) {
        Appointment selectedAppointment = null;

        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            List<Appointment> appointments = new ArrayList<>();

            if (appointments.isEmpty()) {
                System.out.println("No pending appointments.");
                return null;
            }

            getAppointments(doctorId);
            System.out.println("Select an appointment by number:");

            int appointmentNumber = Integer.parseInt(input.nextLine());
            selectedAppointment =  appointments.get(appointmentNumber - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return selectedAppointment;
    }

    /**
     * Retrieves the appointment ID based on doctor ID and user input.
     *
     * @param doctorId the ID of the doctor
     * @param input    the scanner for user input
     * @return the appointment ID, or null if not found
     */
    public String getAppointmentId(String doctorId, Scanner input) {
        List<String> appointmentIds = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(APPOINTMENTS_FILE))) {
            String[] nextLine;
            int index = 1;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[2].equals(doctorId) && nextLine[5].equals("Confirmed")) {
                    System.out.println((index++) + ":");
                    System.out.println("Appointment ID: " + nextLine[0]);
                    System.out.println("Patient ID: " + nextLine[1]);
                    System.out.println("Date: " + nextLine[3]);
                    System.out.println("Timeslot: " + nextLine[4]);
                    System.out.println();
                    appointmentIds.add(nextLine[0]);
                }
            }

            if (!appointmentIds.isEmpty()) {
                System.out.print("Enter the index of the appointment: ");
                int userIndex = Integer.parseInt(input.nextLine());
                if (userIndex > 0 && userIndex <= appointmentIds.size()) {
                    return appointmentIds.get(userIndex - 1);
                } else {
                    System.out.println("Invalid index.");
                    return null;
                }
            } else {
                System.out.println("No pending appointments found for the given doctor ID.");
                return null;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
