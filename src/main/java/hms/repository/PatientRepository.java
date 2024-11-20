package hms.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import hms.model.user.Patient;

/**
 * The PatientRepository class provides methods to import and export patient data
 * from and to a CSV file. It implements the CsvRepository interface for handling patient objects.
 */
public class PatientRepository implements CsvRepository<Patient>
{
    static final String CSV_FILE_PATH_PATIENT = "src/main/resources/csv/patient.csv";

    String[] PATIENT_HEADERS = {"Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information", "Diagnoses", "Treatments", "Prescriptions", "Password"};

    /**
     * Imports patient data from a CSV file.
     *
     * @return a list of patients
     */
    public List<Patient> importFromCsv()
    {
        ArrayList<Patient> patientArrayList = new ArrayList<>();

        try
        {
            Reader reader = new FileReader(CSV_FILE_PATH_PATIENT);
            // Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            for (CSVRecord record : records)
            {
                Patient patient;

                String id = record.get("Patient ID");
                String name = record.get("Name");
                String dateOfBirth = record.get("Date of Birth");
                String gender = record.get("Gender");
                String bloodType = record.get("Blood Type");
                String email = record.get("Contact Information");
                ArrayList<String> diagnoses = null;
                ArrayList<String> treatments = null;
                ArrayList<String> prescriptions = null;
                String password = "";
                // boolean changedDefaultPassword = record.get("Changed Default Password") != null;

                try
                {
                    String arrayListString = record.get("Diagnoses");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    diagnoses = new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
                }
                catch (Exception e)
                {
                    diagnoses = new ArrayList<>();
                }

                try
                {
                    String arrayListString = record.get("Treatments");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    treatments = new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
                }
                catch (Exception e)
                {
                    treatments = new ArrayList<>();
                }

                try
                {
                    String arrayListString = record.get("Prescriptions");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    prescriptions = new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
                }
                catch (Exception e)
                {
                    prescriptions = new ArrayList<>();
                }

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
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email, diagnoses, treatments, prescriptions);
                }
                else
                {
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email, diagnoses, treatments, prescriptions, password);
                }

                patientArrayList.add(patient);
            }

            // todo: delete, for dev only
            for (Patient patient : patientArrayList)
            {
                System.out.print(patient.getName());
                System.out.print("\tD: " + patient.getDiagnoses());
                System.out.print("\tT: " + patient.getTreatments());
                System.out.print("\tP: " + patient.getPrescriptions());
                System.out.println();
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

    /**
     * Exports patient data to a CSV file.
     *
     * @param patientList the list of patients to export
     */
    public void exportToCsv(List<Patient> patientList)
    {
        try
        {
            Writer writer = new FileWriter(CSV_FILE_PATH_PATIENT);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(PATIENT_HEADERS).build();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            for (Patient patient : patientList)
            {
                csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getDateOfBirth(), patient.getGender(), patient.getBloodType(), patient.getEmail(), patient.getDiagnoses(), patient.getTreatments(), patient.getPrescriptions(), patient.getPassword());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
