package hms.repository;

import hms.model.user.Administrator;
import hms.model.user.Doctor;
import hms.model.user.Pharmacist;
import hms.model.user.Staff;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository implements CsvRepository<Staff>
{
    static final String CSV_FILE_PATH_STAFF = "src/main/resources/csv/staff.csv";

    String[] STAFF_HEADERS = {"Staff ID", "Name", "Role", "Gender", "Age"};

    public List<Staff> importFromCsv()
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

    public void exportToCsv(List<Staff> staffList)
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
            System.out.println("Error writing file.");
        }
    }
}
