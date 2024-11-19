package hms.repository;

import java.util.List;

public interface CsvRepository<T>
{
    public List<T> importFromCsv();

    public void exportToCsv(List<T> list);
}
