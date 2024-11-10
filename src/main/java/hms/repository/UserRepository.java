package hms.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import hms.model.medicine.*;
import hms.model.user.*;

public class UserRepository
{
    static final String CSV_FILE_PATH_PATIENT = "src/main/resources/csv/patient.csv";
    static final String CSV_FILE_PATH_STAFF = "src/main/resources/csv/staff.csv";

    String[] PATIENT_HEADERS = {"Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information"};
    String[] STAFF_HEADERS = {"Staff ID", "Name", "Role", "Gender", "Age"};

    public List<Patient> getPatientList()
    {
        ArrayList<Patient> patientArrayList = new ArrayList<Patient>();

        try
        {
            Reader in = new FileReader(CSV_FILE_PATH_PATIENT);
            // Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);

            for (CSVRecord record : records)
            {
                Patient patient;

                String id = record.get("Patient ID");
                String name = record.get("Name");
                String dateOfBirth = record.get("Date of Birth");
                String gender = record.get("Gender");
                String bloodType = record.get("Blood Type");
                String email = record.get("Contact Information");
                String password = "";

                try
                {
                    password = record.get("Password");
                }
                catch (IllegalArgumentException e)
                {
                    password = null;
                }

                if (password == null || password.isEmpty())
                {
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email);
                }
                else
                {
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email, password);
                }

                patientArrayList.add(patient);
            }

            return patientArrayList;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }

        return null;
    }

    public List<Staff> getStaffList()
    {
        ArrayList<Staff> staffArrayList = new ArrayList<Staff>();

        try
        {
            Reader in = new FileReader(CSV_FILE_PATH_STAFF);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);

            for (CSVRecord record : records)
            {
                Staff staff = new Staff();
                String id = record.get("Staff ID");
                String name = record.get("Name");
                String role = record.get("Role").toLowerCase(); // User.Role role = User.Role.valueOf(record.get("Role").toUpperCase());
                String gender = record.get("Gender");
                int age = Integer.parseInt(record.get("Age"));
                String password = "";

                try
                {
                    password = record.get("Password");
                }
                catch (IllegalArgumentException e)
                {
                    password = null;
                }

                switch (role)
                {
                    case "administrator" ->
                    {
                        if (password == null || password.isEmpty())
                        {
                            staff = new Administrator(id, name, role, gender, age);
                        }
                        else
                        {
                            staff = new Administrator(id, name, role, gender, age, password);
                        }
                    }
                    case "doctor" ->
                    {
                        if (password == null || password.isEmpty())
                        {
                            staff = new Doctor(id, name, role, gender, age);
                        }
                        else
                        {
                            staff = new Doctor(id, name, role, gender, age, password);
                        }
                    }
                    case "pharmacist" ->
                    {
                        if (password == null || password.isEmpty())
                        {
                            staff = new Pharmacist(id, name, role, gender, age);
                        }
                        else
                        {
                            staff = new Pharmacist(id, name, role, gender, age, password);
                        }
                    }
                    default -> System.out.printf("Unknown role, skipping user %s.", staff.getRole());
                }

                staffArrayList.add(staff);
            }

            return staffArrayList;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }

        return null;
    }

    public void updatePatientCsv(List<Patient> patientList)
    {
        try
        {
            Writer out = new FileWriter(CSV_FILE_PATH_PATIENT);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(PATIENT_HEADERS).build();

            final CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);

            for (Patient patient : patientList)
            {
                csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getDateOfBirth(), patient.getGender(), patient.getBloodType(), patient.getEmail());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }
    }

    public void updateStaffCsv(List<Staff> staffList)
    {
        try
        {
            Writer out = new FileWriter(CSV_FILE_PATH_STAFF);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(STAFF_HEADERS).build();

            final CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);

            for (Staff staff : staffList)
            {
                csvPrinter.printRecord(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }
    }
}
