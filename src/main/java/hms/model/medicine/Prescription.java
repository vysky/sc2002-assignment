package hms.model.medicine;

public class Prescription
{
    private Medicine medicine;
    private int quantity;

    /**
     * Constructor with parameters.
     * @param medicine Medicine prescribed
     * @param quantity Quantity prescribed
     */
    public Prescription(Medicine medicine, int quantity)
    {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    /**
     * Gets the medicine prescribed.
     * @return Medicine prescribed
     */
    public Medicine getMedicine()
    {
        return this.medicine;
    }

    /**
     * Sets the medicine prescribed.
     * @param medicine Medicine prescribed
     */
    private void setMedicine(Medicine medicine)
    {
        this.medicine = medicine;
    }

    /**
     * Gets the quantity prescribed.
     * @return Quantity prescribed
     */
    public int getQuantity()
    {
        return this.quantity;
    }

    /**
     * Sets the quantity prescribed.
     * @param quantity Quantity prescribed
     */
    private void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
