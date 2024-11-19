package hms.service.user;

import java.util.Scanner;

import hms.model.appointment.AppointmentManager;
import hms.model.user.Doctor;
import hms.service.medicalRecord.MedicalRecordService;

public class DoctorServiceImpl extends UserService {

    private Doctor authenticatedDoctor;
    private MedicalRecordService medicalRecordService;
    private AppointmentManager appointmentManager;

    public DoctorServiceImpl(Doctor doctor, MedicalRecordService medicalRecordService, AppointmentManager appointmentManager) {
        this.authenticatedDoctor = doctor;
        this.medicalRecordService = medicalRecordService;
        this.appointmentManager = appointmentManager;
    }

    public void printMenu() {
        System.out.print("""
                                 ========== Doctor's Menu ==========
                                 (1) View Patient Medical Records
                                 (2) Update Patient Medical Records
                                 (3) View Personal Schedule
                                 (4) Set Availability for Appointments
                                 (5) Accept or Decline Appointment Requests
                                 (6) View Upcoming Appointments
                                 (7) Record Appointment Outcome
                                 (0) Logout
                                 """);
        System.out.print("Select an option: ");
    }

    @Override
    public void handleSelectedOption(Scanner input, int option) {
        switch (option) {
            case 1 -> {
                option1(medicalRecordService, input);
            }
            case 2 -> {
                option2(medicalRecordService, input);
            }
            case 3 -> {
                option3(appointmentManager, input);
            }
            case 4 -> {
                option4(appointmentManager, input);
            }
            case 5 -> {
                option5(appointmentManager, input);
            }
            case 6 -> {
                option6(appointmentManager, input);
            }
            case 7 -> {
                option7();
            }
            case 0 -> {
                System.out.printf("Goodbye %s!", authenticatedDoctor.getName());
            }
            default -> {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    public void option1(MedicalRecordService mr, Scanner input) {
        String pId;

        while (true) {
            mr.printPatients(authenticatedDoctor);
            System.out.println("""
                \nPlease enter Patient ID:
                (Input 0 to exit)
                """);
            pId = input.nextLine();
            if (pId.equals("0")) {
                return;
            }
            mr.getMedicalRecord(pId);
        }
    }

    public void option2(MedicalRecordService mr, Scanner input) {
        String pId = "";
        String description;
        boolean loop = true;

        while (pId.equals("")) {
            mr.printPatients(authenticatedDoctor);
            System.out.println("""
                \nPlease enter Patient ID:
                (Input 0 to exit)
                """);
            pId = input.nextLine();
            if (pId.equals("0")) {
                return;
            }

            if (!mr.isPatientUnderDoctor(pId)) {
                System.out.println("Patient is not under your care, please try again");
                pId = "";
            }
        }

        do {
            System.out.println("""
                        Select record to update:
                        (Input 0 to exit)
                        (1) Diagnosis
                        (2) Treatment
                        (3) Prescription
                            """);

            String strChoice = input.nextLine();
            int choice = Integer.parseInt(strChoice);

            switch (choice) {
                case 0 -> {
                    loop = false;
                }
                case 1 -> {
                    System.out.println("Please enter diagnosis description:");
                    description = input.nextLine();
                    mr.setNewDiagnosis(pId, description);
                    System.out.println("Successfully added diagnosis");
                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                case 2 -> {
                    System.out.println("Please enter treatment description:");
                    description = input.nextLine();
                    mr.setNewTreatment(pId, description);
                    System.out.println("Successfully added treatment");
                    // for dev only
                    mr.getMedicalRecord(pId);
                }
                case 3 -> {
                    // Supposed to send a request to pharmacist
                    // Pharmacist will then fulfill the order
                }
                default -> {
                    System.out.println("Invalid choice, please try again");
                }
            }
        } while (loop);
    }

    public void option3(AppointmentManager am, Scanner input) {
        while (true) {
            System.out.println("Enter date to view schedule (eg 01 Jan 2000): \n(Input 0 to exit)");
            String date = input.nextLine();
            if (date.equals("0")) {
                return;
            }
            System.out.println("Available timeslots for " + date + ": ");
            am.printAvailableTimeslots(authenticatedDoctor.getId(), date);
        }
    }

    public void option4(AppointmentManager am, Scanner input) { // Set availability for appointments
        System.out.println("Select a date (eg 01 Jan 2000): ");
        String date = input.nextLine();
        // System.out.println("Select a time (eg 09:00): ");
        // String time = input.nextLine();
        am.setAvailability(authenticatedDoctor.getId(), date, input);
    }

    public void option5(AppointmentManager am, Scanner input) { // Accept or Decline Appointment Requests
        am.acceptAppointment(authenticatedDoctor.getId(), input);
    }

    public void option6(AppointmentManager am, Scanner input) { // View upcoming appointments
        am.getAppointments(authenticatedDoctor.getId());
    }

    public void option7() {
        System.out.println("Option 7");
    }
}
