package hms.user.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hms.user.Administrator;
import hms.user.Patient;
import hms.user.User;

public class ExcelReader {
    public static List<Patient> readPatientData(String fileName) {
        List<Patient> patients = new ArrayList<>();

        try {
            ClassLoader classLoader = ExcelReader.class.getClassLoader();
            InputStream file = classLoader.getResourceAsStream(fileName);
            
            if (file == null) {
                throw new FileNotFoundException("File " + fileName + " not found in resources");
            }


            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    String patientId = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String dob = row.getCell(2).getStringCellValue();
                    String gender = row.getCell(3).getStringCellValue();
                    String bloodType = row.getCell(4).getStringCellValue();
                    String email = row.getCell(5).getStringCellValue();

                    Patient patient = new Patient(patientId, name, dob, gender, bloodType, email);
                    patients.add(patient);
                }
            }
            
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static List<User> readstaffData(String fileName) {
        List<User> staffs = new ArrayList<>();

        try {
            ClassLoader classLoader = ExcelReader.class.getClassLoader();
            InputStream file = classLoader.getResourceAsStream(fileName);
            
            if (file == null) {
                throw new FileNotFoundException("File " + fileName + " not found in resources");
            }


            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    String staffID = row.getCell(0).getStringCellValue();
                    String name = row.getCell(1).getStringCellValue();
                    String role = row.getCell(2).getStringCellValue();
                    String gender = row.getCell(3).getStringCellValue();
                    double age = row.getCell(4).getNumericCellValue();
                    if(role == "Administrator"){
                        User Administrator = new Administrator(staffID, name, role, gender, age);
                        staffs.add(Administrator);
                    }
                }
            }
            
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffs;
    }
}