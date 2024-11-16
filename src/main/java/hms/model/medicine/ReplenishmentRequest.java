package hms.model.medicine;

public class ReplenishmentRequest
{
    private Medicine medicine;
    private int requestedQuantity;

    public ReplenishmentRequest()
    {
    }

    public ReplenishmentRequest(Medicine medicine, int requestedQuantity)
    {
        this.medicine = medicine;
        this.requestedQuantity = requestedQuantity;
    }

    public Medicine getMedicine()
    {
        return this.medicine;
    }

    public void setMedicine(Medicine medicine)
    {
        this.medicine = medicine;
    }

    public int getRequestedQuantity()
    {
        return this.requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity)
    {
        this.requestedQuantity = requestedQuantity;
    }
}
