package hms.model.medicine;

import java.util.List;

public class PrescriptionOrder
{
    private List<Prescription> prescriptionList;
    private boolean status; // 0 = pending, 1 = dispensed

    public PrescriptionOrder(List<Prescription> prescriptionList)
    {
        this.prescriptionList = prescriptionList;
    }

    public List<Prescription> getPrescriptionList()
    {
        return this.prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList)
    {
        this.prescriptionList = prescriptionList;
    }

    public Boolean getStatus()
    {
        return this.status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }
}