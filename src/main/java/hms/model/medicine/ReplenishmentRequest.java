package hms.model.medicine;

/**
 * The ReplenishmentRequest class represents a request to replenish stock for a specific medicine.
 * It contains details about the medicine that needs to be replenished and the quantity requested.
 */

public class ReplenishmentRequest
{
    private Medicine medicine;
    private int requestedQuantity;

    /**
     * Constructor with parameters.
     * @param medicine Medicine to be replenished
     * @param requestedQuantity Quantity requested
     */
    public ReplenishmentRequest(Medicine medicine, int requestedQuantity)
    {
        this.medicine = medicine;
        this.requestedQuantity = requestedQuantity;
    }

    /**
     * Gets the medicine to be replenished.
     * @return Medicine to be replenished
     */
    public Medicine getMedicine()
    {
        return this.medicine;
    }

    /**
     * Sets the medicine to be replenished.
     * @param medicine Medicine to be replenished
     */
    private void setMedicine(Medicine medicine)
    {
        this.medicine = medicine;
    }

    /**
     * Gets the requested quantity.
     * @return Requested quantity
     */
    public int getRequestedQuantity()
    {
        return this.requestedQuantity;
    }

    /**
     * Sets the requested quantity.
     * @param requestedQuantity Quantity requested
     */
    private void setRequestedQuantity(int requestedQuantity)
    {
        this.requestedQuantity = requestedQuantity;
    }
}
