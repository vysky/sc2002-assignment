package hms.service.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import hms.model.appointment.Appointment;
import hms.model.appointment.Timeslot;
import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.service.appointment.AppointmentManager;
import hms.service.appointment.RecordManager;
import hms.service.medicalRecord.MedicalRecordService;

/**
 * The PatientServiceImpl class provides an implementation for patient-specific functionalities
 * in the hospital management system (HMS). It extends the abstract UserService class and handles
 * operations such as viewing and managing medical records, scheduling appointments, and managing
 * personal information.
 */
public class PatientServiceImpl extends UserService
{
    private Patient authenticatedPatient;
    private SharedUserServiceImpl sharedUserServiceImpl;
    private AppointmentManager appointmentManager;
    private MedicalRecordService medicalRecordService;
    private RecordManager recordManager;

    /**
     * Constructs a PatientServiceImpl with the authenticated patient.
     *
     * @param patient The authenticated patient.
     */
    public PatientServiceImpl(Patient patient, SharedUserServiceImpl sharedUserServiceImpl, AppointmentManager appointmentManager , MedicalRecordService medicalRecordService, RecordManager recordManager)
    {
        this.authenticatedPatient = patient;
        this.sharedUserServiceImpl = sharedUserServiceImpl;
        this.appointmentManager = appointmentManager;
        this.medicalRecordService = medicalRecordService;
        this.recordManager = recordManager;
    }

    /**
     * Prints the menu options for the patient.
     */
    @Override
    public void printMenu()
    {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = dateTime.format(myFormatObj);
        System.out.println("Welcome to HMS Hospital "+ authenticatedPatient.getName() + "!");
        System.out.println("Today is " + formatDateTime);
        System.out.print("""
                                 ========== Patient's Menu ==========
                                 (1) View Medical Record
                                 (2) Update Personal Information
                                 (3) View Available Appointment Slots
                                 (4) Schedule an Appointment
                                 (5) Reschedule an Appointment
                                 (6) Cancel an Appointment
                                 (7) View Scheduled Appointments
                                 (8) View Past Appointment Outcome Records
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    /**
     * Handles the selected option from the patient.
     *
     * @param input  the Scanner object to read user input
     * @param option the selected option
     */
    @Override
    public void handleSelectedOption(Scanner input, int option)
    {
        switch (option)
        {
            case 1 ->
            {
                option1(input);
            }
            case 2 ->
            {
                option2(input);
            }
            case 3 ->
            {
                option3(input);
            }
            case 4 ->
            {
                option4(input);
            }
            case 5 ->
            {
                option5(input);
            }
            case 6 ->
            {
                option6(input);
            }
            case 7 ->
            {
                option7(input);
            }
            case 8 ->
            {
                option8(recordManager, input);
            }
            case 11 ->
            {
                authenticatedPatient.setDiagnoses("test diagnoses");
                authenticatedPatient.setTreatments("test treatments");
                authenticatedPatient.setPrescriptions("test prescriptions");
            }
            case 0 ->
            {
                System.out.printf("Goodbye %s!", authenticatedPatient.getName());
            }
            default ->
            {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    /**
     * Retrieves and displays the patient's medical records.
     *
     * @param input the Scanner object to read user input
     */
    public void option1(Scanner input)
    {
        medicalRecordService.patientGetMedicalRecord(authenticatedPatient.getId());
    }

    /**
     * Updates the patient's personal information.
     *
     * @param input the Scanner object to read user input
     */
    public void option2(Scanner input)
    {
        List<Patient> allPatients = sharedUserServiceImpl.getPatientList();

        appointmentManager.updateInformation(authenticatedPatient.getId(), allPatients, input);
    }

    /**
     * Handles option 3: View Available Appointment Slots.
     *
     * @param input the Scanner object to read user input
     */
    public void option3(Scanner input)
    {
        List<Doctor> doctors = appointmentManager.getAllDoctors();
        appointmentManager.displayDoctor(doctors);

        int doctorNumber = appointmentManager.selectDoctor(input, doctors.size());
        if (doctorNumber == -1) {
            System.out.println("Invalid selection.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        String selectedDoctorId = doctors.get(doctorNumber - 1).getId();
        String selectedDate = appointmentManager.inputDate(input);


        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(selectedDoctorId, selectedDate);
        appointmentManager.displayAvailableTimeslots(availableTimeslots);

        System.out.println("Enter any key to continue");
        input.nextLine();
    }

    /**
     * Schedules a new appointment for the patient.
     *
     * @param input the Scanner object to read user input
     */
    public void option4(Scanner input)
    {
        List<Doctor> doctors = appointmentManager.getAllDoctors();
        appointmentManager.displayDoctor(doctors);

        int doctorNumber = appointmentManager.selectDoctor(input, doctors.size());
        if (doctorNumber == -1) {
            System.out.println("Invalid selection.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        String selectedDoctorId = doctors.get(doctorNumber - 1).getId();

        Appointment existingAppointment = appointmentManager.getExistingAppointment(authenticatedPatient.getId(), selectedDoctorId);

        if (existingAppointment != null) {
            System.out.println("You already have an existing appointment with Dr. " + doctors.get(doctorNumber - 1).getName() + " on " +
                existingAppointment.getDate() + " at " + existingAppointment.getTimeslot());
                System.out.println("Enter any key to continue");
                input.nextLine();
                return;
        }

        String selectedDate = appointmentManager.inputDate(input);

        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(selectedDoctorId, selectedDate);
        if (appointmentManager.displayAvailableTimeslots(availableTimeslots) == -1) {
            System.out.println("Enter any key to continue");
            input.nextLine();
        }

        if(!availableTimeslots.isEmpty()) {
            appointmentManager.selectTimeslotAndMakeAppointment(input, authenticatedPatient.getId(), selectedDoctorId, selectedDate, availableTimeslots);
        }
    }

    /**
     * Reschedules an existing appointment for the patient.
     *
     * @param input the Scanner object to read user input
     */
    public void option5(Scanner input)
    {
        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();

        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        System.out.println("Select an appointment for rescheduling by number:");
        int appointmentNumber = input.nextInt();
        input.nextLine();

        if (appointmentNumber < 1 || appointmentNumber > existingAppointments.size()) {
            System.out.println("Invalid selection.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        String oldAppointmentId = existingAppointments.get(appointmentNumber - 1).getAppointmentId();
        String doctorId = existingAppointments.get(appointmentNumber - 1).getDoctorId();
        String oldDate = existingAppointments.get(appointmentNumber - 1).getDate();
        String oldTimeslot = existingAppointments.get(appointmentNumber - 1).getTimeslot();

        System.out.println("Enter a new appointment date (e.g., 04 Nov 2024):");
        String selectedDate = input.nextLine();

        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(doctorId, selectedDate);
        if (appointmentManager.displayAvailableTimeslots(availableTimeslots) == -1) {

        }

        if (!availableTimeslots.isEmpty()) {
            appointmentManager.selectTimeslotAndMakeAppointment(input, authenticatedPatient.getId(), doctorId, selectedDate, availableTimeslots);

            appointmentManager.updateAvailability(doctorId, oldDate, oldTimeslot, "Available");

            appointmentManager.updateAppointmentStatus(oldAppointmentId, "Rescheduled");
        }

        System.out.println("Enter any key to continue");
        input.nextLine();
    }

    /**
     * Cancels an existing appointment for the patient.
     *
     * @param input the Scanner object to read user input
     */
    public void option6(Scanner input)
    {
        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();

        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        System.out.println("Select an appointment for canceling by number:");
        int appointmentNumber = input.nextInt();
        input.nextLine();

        if (appointmentNumber < 1 || appointmentNumber > existingAppointments.size()) {
            System.out.println("Invalid selection.");
            System.out.println("Enter any key to continue");
            input.nextLine();
            return;
        }

        String oldAppointmentId = existingAppointments.get(appointmentNumber - 1).getAppointmentId();
        String doctorId = existingAppointments.get(appointmentNumber - 1).getDoctorId();
        String oldDate = existingAppointments.get(appointmentNumber - 1).getDate();
        String oldTimeslot = existingAppointments.get(appointmentNumber - 1).getTimeslot();

        appointmentManager.updateAvailability(doctorId, oldDate, oldTimeslot, "Available");

        appointmentManager.updateAppointmentStatus(oldAppointmentId, "Cancelled");

        System.out.println("Your appointment has been cancelled!");

        System.out.println("Enter any key to continue");
        input.nextLine();
    }

    /**
     * Displays all scheduled appointments for the patient.
     *
     * @param input the Scanner object to read user input
     */
    public void option7(Scanner input)
    {
        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();

        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
        }

        System.out.println("Enter any key to continue");
        input.nextLine();
    }

    /**
     * Displays past appointment outcome records for the patient.
     *
     * @param rm    the RecordManager object
     * @param input the Scanner object to read user input
     */
    public void option8(RecordManager rm, Scanner input)
    {
        rm.printPatientHistory(authenticatedPatient.getId());
    }
}
