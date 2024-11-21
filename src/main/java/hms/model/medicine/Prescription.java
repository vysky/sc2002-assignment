package hms.model.medicine;

import hms.model.shared.MedicineQuantityPair;

import java.util.List;

/**
 * The Prescription class represents a prescription for a specific medicine with a specified quantity.
 * It contains the details of the medicine prescribed to a patient and the quantity of the medicine.
 */

public class Prescription
{
    // private List<MedicineQuantityPair> medicineQuantityPairList;
    private MedicineQuantityPair medicineQuantityPair;
    private boolean status = false; // false = pending, true = dispensed

    /**
     * Constructor with parameters.
     *
     * @param medicine Medicine prescribed
     * @param quantity Quantity prescribed
     */
    public Prescription(MedicineQuantityPair medicineQuantityPair, boolean status)
    {
        this.medicineQuantityPair = medicineQuantityPair;
        this.status = status;
    }

    public MedicineQuantityPair getMedicineQuantityPair()
    {
        return this.medicineQuantityPair;
    }

    public void setMedicineQuantityPair(MedicineQuantityPair medicineQuantityPair)
    {
        this.medicineQuantityPair = medicineQuantityPair;
    }

    // public Prescription(List<MedicineQuantityPair> medicineQuantityPairList, boolean status)
    // {
    //     this.medicineQuantityPairList = medicineQuantityPairList;
    //     this.status = status;
    // }

    // public List<MedicineQuantityPair> getMedicineQuantityPairList()
    // {
    //     return this.medicineQuantityPairList;
    // }
    //
    // public void setMedicineQuantityPairList(List<MedicineQuantityPair> medicineQuantityPairList)
    // {
    //     this.medicineQuantityPairList = medicineQuantityPairList;
    // }

    /**
     * Gets the status of the prescription order.
     *
     * @return Status of the prescription order
     */
    public Boolean getStatus()
    {
        return this.status;
    }

    /**
     * Sets the status of the prescription order.
     *
     * @param status Status of the prescription order
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }
}
