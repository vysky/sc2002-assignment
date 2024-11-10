package hms.service.medicine;

import hms.model.medicine.Medicine;
import hms.repository.MedicineRepository;

import java.util.List;

public class MedicineServiceImpl
{
    private final List<Medicine> medicineList;

    public MedicineServiceImpl()
    {
        MedicineRepository medicineRepository = new MedicineRepository();
        this.medicineList = medicineRepository.getMedicineList();
    }

    public void printList()
    {
        for (Medicine medicine : this.medicineList)
        {
            System.out.println(medicine.getMedicineName());
        }
    }
}
