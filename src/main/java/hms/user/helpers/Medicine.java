package hms.user.helpers;

public class Medicine {
    private String name;
    private int initialStock;
    private int lowStockAlert;

    public Medicine(String name, int initialStock, int lowStockAlert) {
        this.name = name;
        this.initialStock = initialStock;
        this.lowStockAlert = lowStockAlert;
    }

    public String getName() {
        return name;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }
}