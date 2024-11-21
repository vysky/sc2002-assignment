package hms.model.medicine;

import hms.model.shared.MedicineQuantityPair;

/**
 * The Prescription class represents a prescription for a specific medicine with a specified quantity.
 * It contains the details of the medicine prescribed to a patient and the quantity of the medicine.
 */

public class Prescription
{
    // private List<MedicineQuantityPair> medicineQuantityPairList;

    /**
     * The medicine and quantity pair prescribed.
     */
    private MedicineQuantityPair medicineQuantityPair;

    /**
     * The status of the prescription order. False = pending, True = dispensed.
     */
    private boolean status = false; // false = pending, true = dispensed

    /**
     * Constructor with parameters.
     *
     * @param medicineQuantityPair Medicine and quantity pair prescribed
     * @param status Status of the prescription order
     */
    public Prescription(MedicineQuantityPair medicineQuantityPair, boolean status)
    {
        this.medicineQuantityPair = medicineQuantityPair;
        this.status = status;
    }

    /**
     * Gets the medicine and quantity pair prescribed.
     * @return Medicine and quantity pair prescribed
     */
    public MedicineQuantityPair getMedicineQuantityPair()
    {
        return this.medicineQuantityPair;
    }

    /**
     * Sets the medicine and quantity pair prescribed.
     * @param medicineQuantityPair Medicine and quantity pair prescribed
     */
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
