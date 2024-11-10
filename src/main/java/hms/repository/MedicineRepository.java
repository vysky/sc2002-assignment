package hms.repository;

import hms.model.medicine.Medicine;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepository
{
    static final String CSV_FILE_PATH_MEDICINE = "src/main/resources/csv/medicine.csv";

    public List<Medicine> getMedicineList()
    {
        ArrayList<Medicine> medicineArrayList = new ArrayList<Medicine>();

        try
        {
            Reader in = new FileReader(CSV_FILE_PATH_MEDICINE);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);

            for (CSVRecord record : records)
            {
                String medicineName = record.get("Medicine Name");
                int initialStock = Integer.parseInt(record.get("Initial Stock"));
                int lowStockAlert = Integer.parseInt(record.get("Low Stock Level Alert"));

                Medicine medicine = new Medicine(medicineName, initialStock, lowStockAlert);
                medicineArrayList.add(medicine);
            }

            return medicineArrayList;
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

    private void updateMedicineCsv(List<Medicine> medicineList)
    {

    }
}
