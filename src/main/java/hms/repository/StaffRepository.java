package hms.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Pharmacist;
import hms.model.user.Staff;

/**
 * The StaffRepository class provides methods to import and export staff data
 * from and to a CSV file. It implements the CsvRepository interface for handling staff objects.
 */
public class StaffRepository implements CsvRepository<Staff>
{
    static final String CSV_FILE_PATH_STAFF = "src/main/resources/csv/staff.csv";

    String[] STAFF_HEADERS = {"Staff ID", "Name", "Role", "Gender", "Age","Active"};

    /**
     * Imports staff data from a CSV file.
     *
     * @return a list of staff members
     */
    public List<Staff> importFromCsv()
    {
        ArrayList<Staff> staffArrayList = new ArrayList<>();

        try
        {
            Reader reader = new FileReader(CSV_FILE_PATH_STAFF);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            for (CSVRecord record : records)
            {
                Staff staff = new Staff();
                String id = record.get("Staff ID");
                String name = record.get("Name");
                String role = record.get("Role").toLowerCase(); // User.Role role = User.Role.valueOf(record.get("Role").toUpperCase());
                String gender = record.get("Gender");
                boolean active = false;
                int age = Integer.parseInt(record.get("Age"));
                if(Integer.parseInt(record.get("Active"))==1){
                    active = true;}
                if(Integer.parseInt(record.get("Active"))==0){
                    active = false;}
                String password = "";
                // boolean changedDefaultPassword = record.get("Changed Default Password") != null;

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
                            staff = new Administrator(id, name, role, gender, age, active);
                        }
                        else
                        {
                            staff = new Administrator(id, name, role, gender, age, password, active);
                        }
                    }
                    case "doctor" ->
                    {
                        if (password == null || password.isEmpty())
                        {
                            staff = new Doctor(id, name, role, gender, age, active);
                        }
                        else
                        {
                            staff = new Doctor(id, name, role, gender, age, password, active);
                        }
                    }
                    case "pharmacist" ->
                    {
                        if (password == null || password.isEmpty())
                        {
                            staff = new Pharmacist(id, name, role, gender, age, active);
                        }
                        else
                        {
                            staff = new Pharmacist(id, name, role, gender, age, password, active);
                        }
                    }
                    default -> System.out.printf("Unknown role, skipping user %s.", staff.getRole());
                }

                staffArrayList.add(staff);
            }

            // todo: delete, for dev only
            for (Staff staff : staffArrayList)
            {
                System.out.println(staff.getName());
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

    /**
     * Exports staff data to a CSV file.
     *
     * @param staffList the list of staff members to export
     */
    public void exportToCsv(List<Staff> staffList)
    {
        try
        {
            Writer writer = new FileWriter(CSV_FILE_PATH_STAFF);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(STAFF_HEADERS).build();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            for (Staff staff : staffList)
            {
                if(staff.getActive())
                    csvPrinter.printRecord(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), 1);
                else if(!staff.getActive())
                    csvPrinter.printRecord(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), 0);
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
