package hms.model.medicine;

import java.util.List;

public class PrescriptionOrder
{
    private List<Prescription> prescriptionList;
    private boolean status; // 0 = pending, 1 = dispensed

    /**
     * Constructor with parameters.
     * @param prescriptionList List of prescriptions
     */
    public PrescriptionOrder(List<Prescription> prescriptionList)
    {
        this.prescriptionList = prescriptionList;
    }

    /**
     * Gets the list of prescriptions.
     * @return List of prescriptions
     */
    public List<Prescription> getPrescriptionList()
    {
        return this.prescriptionList;
    }

    /**
     * Sets the list of prescriptions.
     * @param prescriptionList List of prescriptions
     */
    public void setPrescriptionList(List<Prescription> prescriptionList)
    {
        this.prescriptionList = prescriptionList;
    }

    /**
     * Gets the status of the prescription order.
     * @return Status of the prescription order
     */
    public Boolean getStatus()
    {
        return this.status;
    }

    /**
     * Sets the status of the prescription order.
     * @param status Status of the prescription order
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }
}