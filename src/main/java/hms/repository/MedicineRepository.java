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

import hms.model.medicine.Medicine;

public class MedicineRepository implements CsvRepository<Medicine>
{
    static final String CSV_FILE_PATH_MEDICINE = "src/main/resources/csv/medicine.csv";

    String[] MEDICINE_HEADERS = {"Medicine Name", "Initial Stock", "Low Stock Level Alert"};

    /**
     * Imports medicine data from a CSV file.
     *
     * @return a list of medicines
     */
    public List<Medicine> importFromCsv()
    {
        ArrayList<Medicine> medicineArrayList = new ArrayList<>();

        try
        {
            Reader reader = new FileReader(CSV_FILE_PATH_MEDICINE);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

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

    /**
     * Exports medicine data to a CSV file.
     *
     * @param medicineList the list of medicines to export
     */
    public void exportToCsv(List<Medicine> medicineList)
    {
        try
        {
            Writer writer = new FileWriter(CSV_FILE_PATH_MEDICINE);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(MEDICINE_HEADERS).build();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

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
