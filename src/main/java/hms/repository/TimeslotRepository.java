package hms.repository;

import hms.model.appointment.Timeslot;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimeslotRepository implements CsvRepository<Timeslot>
{
    static final String CSV_FILE_PATH_TIMESLOT = "src/main/resources/timeslot.csv";

    String[] TIMESLOT_HEADERS = {"ID", "Date", "Timeslot", "Status"};

    /**
     * Imports timeslot data from a CSV file.
     *
     * @return a list of timeslots
     */
    public List<Timeslot> importFromCsv()
    {
        ArrayList<Timeslot> timeslotArrayList = new ArrayList<Timeslot>();

        try
        {
            Reader reader = new FileReader(CSV_FILE_PATH_TIMESLOT);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);

            for (CSVRecord record : records)
            {
                String id = record.get("ID");
                String date = record.get("Date");
                String timeslotField = record.get("Timeslot");
                String status = record.get("Status");

                Timeslot timeslot = new Timeslot(id, date, timeslotField, status);
                timeslotArrayList.add(timeslot);
            }

            return timeslotArrayList;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch (IOException e)
        {
            System.out.println("Error reading file.");
        }

        return null;
    }

    /**
     * Exports timeslot data to a CSV file.
     *
     * @param timeslotList the list of timeslots to export
     */
    public void exportToCsv(List<Timeslot> timeslotList)
    {
        try
        {
            Writer writer = new FileWriter(CSV_FILE_PATH_TIMESLOT);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(TIMESLOT_HEADERS).build();

            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

            for (Timeslot timeslot : timeslotList)
            {
                csvPrinter.printRecord(timeslot.getDoctorId(), timeslot.getDate(), timeslot.getTime(), timeslot.getStatus());
            }

            csvPrinter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error writing file.");
        }
    }
}
