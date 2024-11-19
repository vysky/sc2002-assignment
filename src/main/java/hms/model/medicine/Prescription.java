package hms.model.medicine;

public class Prescription
{
    private Medicine medicine;
    private int quantity;

    public Prescription(Medicine medicine, int quantity)
    {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public Medicine getMedicine()
    {
        return this.medicine;
    }

    private void setMedicine(Medicine medicine)
    {
        this.medicine = medicine;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    private void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
