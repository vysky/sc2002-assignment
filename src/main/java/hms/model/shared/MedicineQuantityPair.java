package hms.model.shared;

import hms.model.medicine.Medicine;

/**
 * Represents a pair of a medicine and its quantity.
 *
 * @param medicine the Medicine object
 * @param quantity the quantity of the medicine
 */
public record MedicineQuantityPair(Medicine medicine, int quantity)
{
}
