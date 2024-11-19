package hms.service.medicine;

import java.util.ArrayList;
import java.util.List;

import hms.model.medicine.Medicine;
import hms.model.medicine.ReplenishmentRequest;
import hms.repository.MedicineRepository;

public class InventoryServiceImpl
{
    private static final MedicineRepository medicineRepository = new MedicineRepository();
    private final List<Medicine> medicineList;
    private final List<ReplenishmentRequest> replenishmentRequestList = new ArrayList<>();

    public InventoryServiceImpl()
    {
        this.medicineList = medicineRepository.importFromCsv();

        // test data
        ReplenishmentRequest replenishmentRequest1 = new ReplenishmentRequest(medicineList.get(0), 1000);
        ReplenishmentRequest replenishmentRequest2 = new ReplenishmentRequest(medicineList.get(1), 2000);
        ReplenishmentRequest replenishmentRequest3 = new ReplenishmentRequest(medicineList.get(2), 3000);

        this.replenishmentRequestList.add(replenishmentRequest1);
        this.replenishmentRequestList.add(replenishmentRequest2);
        this.replenishmentRequestList.add(replenishmentRequest3);
    }

    // ====================================================================================================
    // medicine
    // ====================================================================================================

    /**
     * Retrieves the list of medicines.
     *
     * @return the list of medicines
     */
    public List<Medicine> getMedicineList()
    {
        return this.medicineList;
    }

    /**
     * Updates the medicine list by exporting it to a CSV file.
     */
    public void updateMedicineList()
    {
        medicineRepository.exportToCsv(this.medicineList);
    }

    /**
     * Prints the list of medicines.
     */
    public void printMedicineList()
    {
        int i = 1;
        System.out.println("---------- CURRENT INVENTORY ----------");
        System.out.println("No.\tMedicine Name\tCurrent Stock\tLow Stock Level Alert");
        for (Medicine medicine : this.medicineList)
        {
            System.out.printf("%d\t%s\t%d\t%d\n", i, medicine.getMedicineName(), medicine.getInitialStock(), medicine.getLowStockAlert());
            i++;
        }
    }

    /**
     * Removes a medicine from the list.
     *
     * @param index the index of the medicine to remove
     */
    public void removeMedicine(int index)
    {
        medicineList.remove(index - 1);
    }

    /**
     * Updates the stock level of a medicine.
     *
     * @param index    the index of the medicine to update
     * @param newStock the new stock level
     */
    public void updateMedicineStock(int index, int newStock)
    {
        medicineList.get(index - 1).setInitialStock(newStock);
    }

    // ====================================================================================================
    // should we split the replenishment request to another service? in terms of oop and mvc design
    // ====================================================================================================

    /**
     * Retrieves the list of replenishment requests.
     *
     * @return the list of replenishment requests
     */
    public List<ReplenishmentRequest> getReplenishmentRequestList()
    {
        return this.replenishmentRequestList;
    }

    /**
     * Prints the list of replenishment requests.
     */
    public void printReplenishmentRequestList()
    {
        int i = 1;

        for (ReplenishmentRequest replenishmentRequest : this.replenishmentRequestList)
        {
            System.out.printf("%d\t%s\t%d\n", i, replenishmentRequest.getMedicine().getMedicineName(), replenishmentRequest.getRequestedQuantity());
            i++;
        }
    }

    // todo
    //  i think we may need to add id for the medicine
    //  if we use index, if there is any changes to the medicine list, example a medicine is removed
    //  the index in replenishment request will be wrong, example the last medicine is removed
    //  the new length of the medicine list will be -1 which will caused error
    /**
     * Creates a new replenishment request.
     *
     * @param index             the index of the medicine to replenish
     * @param requestedQuantity the quantity to replenish
     */
    public void createReplenishmentRequest(int index, int requestedQuantity)
    {
        ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(medicineList.get(index - 1), requestedQuantity);
        replenishmentRequestList.add(replenishmentRequest);
    }

    /**
     * Approves a replenishment request.
     *
     * @param index the index of the replenishment request to approve
     */
    public void approveReplenishmentRequest(int index)
    {
        ReplenishmentRequest replenishmentRequest = replenishmentRequestList.get(index - 1);
        Medicine medicine = replenishmentRequest.getMedicine();
        medicine.setInitialStock(medicine.getInitialStock() + replenishmentRequest.getRequestedQuantity());
        System.out.printf("Replenishment Request approved. New Quantity : %d", medicine.getInitialStock());
        replenishmentRequestList.remove(index - 1);
    }
}
