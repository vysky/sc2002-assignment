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
    /**
     * Path to the CSV file where patient data is stored.
     */
    static final String CSV_FILE_PATH_PATIENT = "src/main/resources/csv/patient.csv";

    /**
     * Headers for the CSV file.
     */
    String[] PATIENT_HEADERS = {"Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Information", "Diagnoses", "Treatments", "Prescriptions", "Password", "Hash", "Active"};

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
                ArrayList<String> diagnoses;
                ArrayList<String> treatments;
                ArrayList<String> prescriptions;
                String password;
                String hash = record.get("Hash");
                boolean active;

                if (record.get("Active") == null || record.get("Active").isEmpty())
                {
                    active = true;
                }
                else
                {
                    active = Boolean.parseBoolean(record.get("Active"));
                }

                try
                {
                    String arrayListString = record.get("Diagnoses");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    diagnoses = trimmedString.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
                }
                catch (Exception e)
                {
                    diagnoses = new ArrayList<>();
                }

                try
                {
                    String arrayListString = record.get("Treatments");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    treatments = trimmedString.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
                }
                catch (Exception e)
                {
                    treatments = new ArrayList<>();
                }

                try
                {
                    String arrayListString = record.get("Prescriptions");
                    String trimmedString = arrayListString.substring(1, arrayListString.length() - 1);
                    prescriptions = trimmedString.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(trimmedString.split(", ")));
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
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email, diagnoses, treatments, prescriptions, active);
                }
                else
                {
                    patient = new Patient(id, name, "patient", dateOfBirth, gender, bloodType, email, diagnoses, treatments, prescriptions, password, hash, active);
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
                csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getDateOfBirth(), patient.getGender(), patient.getBloodType(), patient.getEmail(), patient.getDiagnoses(), patient.getTreatments(), patient.getPrescriptions(), patient.getPassword(), patient.getHash(), patient.getActive());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
