package hms.service.medicine;

import hms.model.medicine.Medicine;
import hms.model.medicine.ReplenishmentRequest;
import hms.repository.MedicineRepository;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class InventoryServiceImpl
{
    private static final MedicineRepository medicineRepository = new MedicineRepository();
    private final List<Medicine> medicineList;
    private final List<ReplenishmentRequest> replenishmentRequestList = new ArrayList<>();

    public InventoryServiceImpl()
    {
        this.medicineList = medicineRepository.getMedicineList();

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

    public List<Medicine> getMedicineList()
    {
        return this.medicineList;
    }

    public void updateMedicineList()
    {
        medicineRepository.updateMedicineCsv(this.medicineList);
    }

    public int printMedicineList()
    {
        int i = 1, maxNL=-1,k=1;
        System.out.println("---------- CURRENT INVENTORY ----------");
        System.out.println("No.\tMedicine Name\tCurrent Stock\tLow Stock Level Alert");
        
        for (Medicine medicine : this.medicineList)
        {
            if(medicine.getMedicineName().length()>maxNL){
                maxNL = medicine.getMedicineName().length();
            }
            k++;
        }
        for (Medicine medicine : this.medicineList)
        {
            String tabs = "";
            String ntabs ="\t";
            int count=0 ;
            if(medicine.getMedicineName().length() % 9 > 1){
                count = Math.round((float)medicine.getMedicineName().length()/9);
            }
            for(int j=0;i<count;i++){
                tabs += "\t";
            }
            String name = String.format("%-10s",medicine.getMedicineName())+tabs;
            String initstock = String.format("%-10s",medicine.getInitialStock());
            String stockalert = String.format("%-10s",medicine.getLowStockAlert());
            if(medicine.getLowStockAlert()==0){
                stockalert = "No Alert";
            }
            System.out.println(i +ntabs+name+ntabs+initstock+ntabs+stockalert);
            //System.out.printf("%d\t%s\t\t%d\t\t%d\n", i, name, initstock, stockalert);
            i++;
        }
        return k;
    }

    public void removeMedicine(int index)
    {
        medicineList.remove(index - 1);
    }

    public void updateMedicineStock(int index, int newStock)
    {
        medicineList.get(index - 1).setInitialStock(newStock);
    }

    // ====================================================================================================
    // should we split the replenishment request to another service? in terms of oop and mvc design
    // ====================================================================================================

    public List<ReplenishmentRequest> getReplenishmentRequestList()
    {
        return this.replenishmentRequestList;
    }

    public int printReplenishmentRequestList()
    {
        int i = 1;

        for (ReplenishmentRequest replenishmentRequest : this.replenishmentRequestList)
        {
            System.out.printf("%d\t%s\t%d\n", i, replenishmentRequest.getMedicine().getMedicineName(), replenishmentRequest.getRequestedQuantity());
            i++;
        }
        return i;
    }

    // todo
    //  i think we may need to add id for the medicine
    //  if we use index, if there is any changes to the medicine list, example a medicine is removed
    //  the index in replenishment request will be wrong, example the last medicine is removed
    //  the new length of the medicine list will be -1 which will caused error
    public void createReplenishmentRequest(int index, int requestedQuantity)
    {
        ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(medicineList.get(index - 1), requestedQuantity);
        replenishmentRequestList.add(replenishmentRequest);
    }

    public void approveReplenishmentRequest(int index)
    {
        ReplenishmentRequest replenishmentRequest = replenishmentRequestList.get(index - 1);
        Medicine medicine = replenishmentRequest.getMedicine();
        medicine.setInitialStock(medicine.getInitialStock() + replenishmentRequest.getRequestedQuantity());
        System.out.printf("Replenishment Request approved. New Quantity : %d\n", medicine.getInitialStock());
        replenishmentRequestList.remove(index - 1);
    }

    public int checkDuplicateMedications(String a){
        for(Medicine s: medicineList){
            if(s.getMedicineName().equals(a)){
                return 1;
            }
        }
        return 0;
    }
}
