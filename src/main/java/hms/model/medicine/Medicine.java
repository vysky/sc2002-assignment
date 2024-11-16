package hms.model.medicine;

public class Medicine {
    private String medicineName;
    private int initialStock;
    private int lowStockAlert;

    public Medicine(String medicineName, int initialStock, int lowStockAlert)
    {
        this.medicineName = medicineName;
        this.initialStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    public String getMedicineName()
    {
        return this.medicineName;
    }

    public void setMedicineName(String medicineName)
    {
        this.medicineName = medicineName;
    }

    public int getInitialStock()
    {
        return this.initialStock;
    }

    public void setInitialStock(int initialStock)
    {
        this.initialStock = initialStock;
    }

    public int getLowStockAlert()
    {
        return this.lowStockAlert;
    }

    public void setLowStockAlert(int lowStockAlert)
    {
        this.lowStockAlert = lowStockAlert;
    }
}
