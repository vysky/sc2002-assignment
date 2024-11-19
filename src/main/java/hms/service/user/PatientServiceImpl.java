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
        System.out.println("Update Personal Information");
    }

    public void option3()
    {
        Scanner input = new Scanner(System.in);

        List<Doctor> doctors = appointmentManager.getAllDoctors();
        appointmentManager.displayDoctor(doctors);

        int doctorNumber = appointmentManager.selectDoctor(input, doctors.size());
        if (doctorNumber == -1) {
            System.out.println("Invalid selection.");
            return;
        }

        String selectedDoctorId = doctors.get(doctorNumber - 1).getId();
        String selectedDate = appointmentManager.inputDate(input);

        List<Timeslot> availableTimeslots = appointmentManager.getAvailableTimeslots(selectedDoctorId, selectedDate);
        appointmentManager.displayAvailableTimeslots(availableTimeslots);

        System.out.println("Enter any key to continue");
        input.next();
    }

    public void option4()
    {
        Scanner input = new Scanner(System.in);

        List<Doctor> doctors = appointmentManager.getAllDoctors();
        appointmentManager.displayDoctor(doctors);

        int doctorNumber = appointmentManager.selectDoctor(input, doctors.size());
        if (doctorNumber == -1) {
            System.out.println("Invalid selection.");
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

    public void option5()
    {
        Scanner input = new Scanner(System.in);

        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();
    
        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
            System.out.println("Enter any key to continue");
            input.next();
            return;
        }

        System.out.println("Select an appointment for rescheduling by number:");
        int appointmentNumber = input.nextInt();
        input.nextLine();

        if (appointmentNumber < 1 || appointmentNumber > existingAppointments.size()) {
            System.out.println("Invalid selection.");
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
            System.out.println("Enter any key to continue");
            input.nextLine();
        }

        if (!availableTimeslots.isEmpty()) {
            appointmentManager.selectTimeslotAndMakeAppointment(input, authenticatedPatient.getId(), doctorId, selectedDate, availableTimeslots);

            appointmentManager.updateAvailability(doctorId, oldDate, oldTimeslot, "Available");

            appointmentManager.updateAppointmentStatus(oldAppointmentId, "Rescheduled");
        }
    }

    public void option6()
    {
        Scanner input = new Scanner(System.in);

        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();
    
        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
            System.out.println("Enter any key to continue");
            input.next();
            return;
        }

        System.out.println("Select an appointment for canceling by number:");
        int appointmentNumber = input.nextInt();
        input.nextLine();

        if (appointmentNumber < 1 || appointmentNumber > existingAppointments.size()) {
            System.out.println("Invalid selection.");
            System.out.println("Enter any key to continue");
            input.next();
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
        input.next();
    }

    public void option7()
    {
        Scanner input = new Scanner(System.in);

        List<Appointment> existingAppointments = appointmentManager.getExistingAppointment(authenticatedPatient.getId());
        List<Doctor> doctors = appointmentManager.getAllDoctors();
        
        if (appointmentManager.displayExistingAppointment(existingAppointments, doctors) == -1) {
            System.out.println("There is no existing appointment.");
            System.out.println("Enter any key to continue");
            input.next();
            return;
        }
    }

    public void option8()
    {
        System.out.println("View Past Appointment Outcome Records");
    }
}