package hms.service.user;

import hms.model.user.Doctor;

import java.util.Scanner;

public class DoctorServiceImpl extends UserService
{
    private Doctor authenticatedDoctor;
    private SharedUserServiceImpl sharedUserService;

    public DoctorServiceImpl(Doctor doctor, SharedUserServiceImpl sharedUserService)
    {
        this.authenticatedDoctor = doctor;
        this.sharedUserService = sharedUserService;
    }

    public void printMenu()
    {
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
            case 0 ->
            {
                System.out.printf("Goodbye %s!", authenticatedDoctor.getName());
            }
            default ->
            {
                System.out.println("Invalid option, please select a new option.");
            }
        }
    }

    public void option1()
    {
        System.out.println("Option 1");
    }

    public void option2()
    {
        System.out.println("Option 2");
    }

    public void option3()
    {
        System.out.println("Option 3");
    }

    public void option4()
    {
        System.out.println("Option 4");
    }

    public void option5()
    {
        System.out.println("Option 5");
    }

    public void option6()
    {
        System.out.println("Option 6");
    }

    public void option7()
    {
        System.out.println("Option 7");
    }
}