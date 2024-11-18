package hms.repository;

import hms.model.medicine.Medicine;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepository implements CsvRepository<Medicine>
{
    static final String CSV_FILE_PATH_MEDICINE = "src/main/resources/csv/medicine.csv";

    String[] MEDICINE_HEADERS = {"Medicine Name", "Initial Stock", "Low Stock Level Alert"};

    public List<Medicine> importFromCsv()
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

            // todo: delete, for dev only
            for (Medicine medicine : medicineArrayList)
            {
                System.out.println(medicine.getMedicineName());
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

    public void exportToCsv(List<Medicine> medicineList)
    {
        try
        {
            Writer out = new FileWriter(CSV_FILE_PATH_MEDICINE);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(MEDICINE_HEADERS).build();

            final CSVPrinter csvPrinter = new CSVPrinter(out, csvFormat);

            for (Medicine medicine : medicineList)
            {
                csvPrinter.printRecord(medicine.getMedicineName(), medicine.getInitialStock(), medicine.getLowStockAlert());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
