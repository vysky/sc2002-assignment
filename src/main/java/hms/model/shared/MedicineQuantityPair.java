package hms.model.shared;

import hms.model.medicine.Medicine;

public record MedicineQuantityPair(Medicine medicine, int quantity)
{
}
