package hms.service.user;

import hms.model.user.Doctor;
import hms.model.user.Patient;
import hms.model.appointment.Appointment;
import hms.model.appointment.AppointmentManager;
import hms.model.appointment.Timeslot;

import java.util.List;
import java.util.Scanner;

public class PatientServiceImpl extends UserService
{
    private static AppointmentManager appointmentManager = new AppointmentManager();
    private Patient authenticatedPatient;

    public PatientServiceImpl(Patient patient)
    {
        this.authenticatedPatient = patient;
    }

    public void printMenu()
    {
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

    @Override
    public void handleSelectedOption(Scanner input, int option)
    {
        switch (option)
        {
            case 1 ->
            {
                option1();
            }
            case 2 ->
            {
                option2();
            }
            case 3 ->
            {
                option3();
            }
            case 4 ->
            {
                option4();
            }
            case 5 ->
            {
                option5();
            }
            case 6 ->
            {
                option6();
            }
            case 7 ->
            {
                option7();
            }
            case 8 ->
            {
                option8();
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

    public void option1()
    {
        System.out.println("View Medical Record");
    }

    public void option2()
    {
        //(String id, String name, String role, String dateOfBirth, String gender, String bloodType, String email, String password)
        System.out.println("Update Personal Information");

    }

    public void option3()
    {
        Scanner input = new Scanner(System.in);

        List<Doctor> doctors = appointmentManager.getAllDoctors();

        System.out.println("List of doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            System.out.println("(" + (i + 1) + ") Dr. " + doctor.getName());
        }

        System.out.println("Select a doctor by numbers:");
        int doctorNumber = input.nextInt();
        input.nextLine();

        if (doctorNumber < 1 || doctorNumber > doctors.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String doctorId = doctors.get(doctorNumber - 1).getId();

        System.out.println("Enter Date (e.g., 01 - 31):");
        String date = input.nextLine();
        System.out.println("""
            Enter Date 
            (e.g., Jan, Mar)
            Jan - 01, Feb - 02, Mar - 03, 
            Apr - 04, May - 05, Jun - 06, 
            Jul - 07, Aug - 08, Sep - 09, 
            Oct - 10, Nov - 11, Dec - 12:
                """);
        String month = input.nextLine();
        System.out.println("Enter Date (e.g., 04 Nov 2024):");
        String year = input.nextLine();
        String fullDate = date + " " + month + " " + year;

        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(doctorId, fullDate);
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
        } else {
            System.out.println("Available Timeslots:");
            for (int i = 0; i < availableTimeslots.size(); i++) {
                System.out.println(availableTimeslots.get(i).getTime());
            }
        }

        System.out.println("Enter any key to continue");
        if (input.nextInt() == 1) {}
    }

    public void option4()
    {
        Scanner input = new Scanner(System.in);

        List<Doctor> doctors = appointmentManager.getAllDoctors();

        System.out.println("List of doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            System.out.println("(" + (i + 1) + ") Dr. " + doctor.getName());
        }

        System.out.println("Select a doctor by numbers:");
        int doctorNumber = input.nextInt();
        input.nextLine();

        if (doctorNumber < 1 || doctorNumber > doctors.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String doctorId = doctors.get(doctorNumber - 1).getId();

        Appointment existingAppointment = appointmentManager.getExistingAppointment(authenticatedPatient.getId(), doctorId);
        if (existingAppointment != null) {
            System.out.println("You already have an existing appointment with Dr. " + doctors.get(doctorNumber).getName() + " on " +
                existingAppointment.getDate() + " at " + existingAppointment.getTimeslot());

            System.out.println("Enter any key to continue");
            input.next();
            return;
        }

        System.out.println("Enter Date (e.g., 04 Nov 2024):");
        String date = input.nextLine();

        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(doctorId, date);
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
        } else {
            System.out.println("Available Timeslots:");
            for (int i = 0; i < availableTimeslots.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + availableTimeslots.get(i).getTime());
            }

            System.out.println("Select a timeslot by number:");
            int timeslotNumber = input.nextInt();
            input.nextLine();

            if (timeslotNumber < 1 || timeslotNumber > availableTimeslots.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String timeslot = availableTimeslots.get(timeslotNumber - 1).getTime();

            if (appointmentManager.isAvailable(doctorId, date, timeslot)) {
                appointmentManager.makeAppointment(authenticatedPatient.getId(), doctorId, date, timeslot);
            } else {
                System.out.println("The selected timeslot is not available.");
            }
        }
    }

    public void option5()
    {
        Scanner input = new Scanner(System.in);

        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();

        System.out.println("Your existing appointment:");
        for (int i = 0; i < existingAppointments.size(); i++) {
            Appointment appointment = existingAppointments.get(i);
            String doctorId = appointment.getDoctorId(); 

            for (Doctor doctor : doctors) {
                if (doctor.getId() == doctorId) {
                    String doctorName = doctor.getName();

                    System.out.println("(" + (i + 1) + ") " + doctorName + ", " + appointment.getDate() + ", " + appointment.getTimeslot() + ", " + appointment.getStatus());
                }
            }
        }

        System.out.println("Select a timeslot for rescheduling by number:");
        int appointmentNumber = input.nextInt();
        input.nextLine();

        if (appointmentNumber < 1 || appointmentNumber > existingAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String doctorId = existingAppointments.get(appointmentNumber - 1).getDoctorId();

        System.out.println("Enter Date (e.g., 04 Nov 2024):");
        String date = input.nextLine();
        
        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(doctorId, date);
        if (availableTimeslots.isEmpty()) {
            System.out.println("No available timeslots for the selected date.");
        } else {
            System.out.println("Available Timeslots:");
            for (int i = 0; i < availableTimeslots.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + availableTimeslots.get(i).getTime());
            }

            System.out.println("Select a timeslot by number:");
            int timeslotNumber = input.nextInt();
            input.nextLine();

            if (timeslotNumber < 1 || timeslotNumber > availableTimeslots.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String timeslot = availableTimeslots.get(timeslotNumber - 1).getTime();

            if (appointmentManager.isAvailable(doctorId, date, timeslot)) {
                appointmentManager.makeAppointment(authenticatedPatient.getId(), doctorId, date, timeslot);
            } else {
                System.out.println("The selected timeslot is not available.");
            }
        }

        String appointmentId = existingAppointments.get(appointmentNumber - 1).getAppointmentId();



        //rescheduleAppointment(existingAppointments.getAppointmentId());
    }

    public void option6()
    {
        System.out.println("Cancel an Appointment");
    }

    public void option7()
    {
        System.out.println("View Scheduled Appointments");
        Scanner input = new Scanner(System.in);

        List<Doctor> doctors = appointmentManager.getAllDoctors();
        Doctor doctor;
        String doctorId;
        Appointment existingAppointment;
        System.out.println("List of doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            doctor = doctors.get(i);
            doctorId = doctors.get(i).getId();
            existingAppointment = appointmentManager.getExistingAppointment(authenticatedPatient.getId(), doctorId);
            System.out.println("(" + (i + 1) + ") Dr. " + doctor.getName());
            if (existingAppointment != null) {
                System.out.println("You already have an existing appointment with Dr. " + doctors.get(i).getName() + " on " +
                    existingAppointment.getDate() + " at " + existingAppointment.getTimeslot());
    
                System.out.println("Enter any key to continue");
                input.next();
                return;
            }   

        
        
        }
    }

    public void option8()
    {
        System.out.println("View Past Appointment Outcome Records");
    }
}