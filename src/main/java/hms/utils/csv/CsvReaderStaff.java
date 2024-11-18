//package hms.utils.csv;
//
//import main.java.hms.model.user.*;
//
//import main.java.hms.model.user.User;
//import org.apache.commons.hms.utils.csv.*;
//
//import java.io.*;
//import java.util.List;
//
//public class CsvReaderStaff extends CsvReader
//{
//    protected String csvFilePath;
//
//    public CsvReaderStaff(String csvFilePath)
//    {
//        super(csvFilePath);
//    }
//
//    @Override
//    public List<? extends User> readData()
//    {
//        List<main.java.hms.model.user.Staff> staffList = new ArrayList<>();
//
//        try {
//            Reader in = new FileReader(this.csvFilePath);
//
//            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
//                    .setHeader()
//                    .setSkipHeaderRecord(true)
//                    .build()
//                    .parse(in);
//
//            for (CSVRecord record : records) {
//                String id = record.get("Staff ID");
//                String name = record.get("Name");
//                String role = record.get("Role");
//                String gender = record.get("Gender");
//                int age = Integer.parseInt(record.get("Age"));
//
//                main.java.hms.model.user.Staff staff = new Staff(id, name, gender, age);
//                staffList.add(staff);
//            }
//
//            return staffList;
//        }
//        catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        }
//        catch (IOException e) {
//            System.out.println("Error reading file");
//        }
//        return null;
//    }
//}