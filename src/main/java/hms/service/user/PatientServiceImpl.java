package hms.service.user;

import hms.model.user.*;

// public class PatientServices implements Menu
public class PatientServiceImpl extends UserService
{
    Patient authenticatedPatient;

    public PatientServiceImpl(Patient patient)
    {
        this.authenticatedPatient = patient;
    }

    public void printMenu()
    {
        System.out.print("""
                                 (1) View Medical Record
                                 (2) Update Personal Information
                                 (3) View Available Appointment Slots
                                 (4) Schedule an Appointment
                                 (5) Reschedule an Appointment
                                 (6) Cancel an Appointment
                                 (7) View Scheduled Appointments
                                 (8) View Past Appointment Outcome Records
                                 (0) Logout
                                 Select an option:\s
                                 """);
    }

    @Override
    public void handleSelectedOption(int option, SharedUserServiceImpl sharedUserServices)
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
        System.out.println("View Available Appointment Slots");
    }

    public void option4()
    {
        System.out.println("Schedule an Appointment");
    }

    public void option5()
    {
        System.out.println("Reschedule an Appointment");
    }

    public void option6()
    {
        System.out.println("Cancel an Appointment");
    }

    public void option7()
    {
        System.out.println("View Scheduled Appointments");
    }

    public void option8()
    {
        System.out.println("View Past Appointment Outcome Records");
    }
}