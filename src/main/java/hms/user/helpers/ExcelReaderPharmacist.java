package hms.user.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderPharmacist {

    private static final String FILE_PATH = "resources/Medicine_List.xlsx";

    public static List<Medicine> readInventoryData() {
        List<Medicine> medicines = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String name = row.getCell(0).getStringCellValue();
                int initialStock = (int) row.getCell(1).getNumericCellValue();
                int lowStockAlert = (int) row.getCell(2).getNumericCellValue();

                medicines.add(new Medicine(name, initialStock, lowStockAlert));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return medicines;
    }

    public static void updateInventoryData(List<Medicine> medicines) {
        try (FileInputStream file = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowIndex = 1;

            for (Medicine medicine : medicines) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }
                row.createCell(0).setCellValue(medicine.getName());
                row.createCell(1).setCellValue(medicine.getInitialStock());
                row.createCell(2).setCellValue(medicine.getLowStockAlert());

                rowIndex++;
            }

            try (FileOutputStream outFile = new FileOutputStream(FILE_PATH)) {
                workbook.write(outFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}