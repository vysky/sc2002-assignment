package hms.model.medicine;

/**
 * The Medicine class represents a medicine item in the hospital's inventory.
 * It contains details about the medicine's name, initial stock, and low stock alert level.
 */

public class Medicine {
    private String medicineName;
    private int initialStock;
    private int lowStockAlert;

    /**
     * Constructor with parameters.
     * @param medicineName Name of the medicine
     * @param initialStock Initial stock of the medicine
     * @param lowStockAlert Low stock alert threshold
     */
    public Medicine(String medicineName, int initialStock, int lowStockAlert)
    {
        this.medicineName = medicineName;
        this.initialStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    /**
     * Gets the name of the medicine.
     * @return Medicine name
     */
    public String getMedicineName()
    {
        return this.medicineName;
    }

    /**
     * Sets the name of the medicine.
     * @param medicineName Medicine name
     */
    public void setMedicineName(String medicineName)
    {
        this.medicineName = medicineName;
    }

    /**
     * Gets the initial stock of the medicine.
     * @return Initial stock
     */
    public int getInitialStock()
    {
        return this.initialStock;
    }

    /**
     * Sets the initial stock of the medicine.
     * @param initialStock Initial stock
     */
    public void setInitialStock(int initialStock)
    {
        this.initialStock = initialStock;
    }

    /**
     * Gets the low stock alert threshold.
     * @return Low stock alert threshold
     */
    public int getLowStockAlert()
    {
        return this.lowStockAlert;
    }

    /**
     * Sets the low stock alert threshold.
     * @param lowStockAlert Low stock alert threshold
     */
    public void setLowStockAlert(int lowStockAlert)
    {
        this.lowStockAlert = lowStockAlert;
    }
}
