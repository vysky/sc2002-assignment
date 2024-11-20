package hms.repository;

import java.util.List;

public interface CsvRepository<T>
{
    /**
     * Imports data from a CSV file.
     *
     * @return a list of objects
     */
    public List<T> importFromCsv();

    /**
     * Exports data to a CSV file.
     *
     * @param list the list of objects to export
     */
    public void exportToCsv(List<T> list);
}
