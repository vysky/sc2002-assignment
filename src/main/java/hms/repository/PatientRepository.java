package hms.repository;

import hms.model.user.Patient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements CsvRepository<Patient>
{
    static final String CSV_FILE_PATH_PATIENT = "src/main/resources/csv/patient.csv";

    String[] PATIENT_HEADERS = {"Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information"};

    public List<Patient> importFromCsv()
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

            // todo: delete, for dev only
            for (Patient patient : patientArrayList)
            {
                System.out.println(patient.getName());
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

    public void exportToCsv(List<Patient> patientList)
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
            System.out.println("Error writing file.");
        }
    }
}
