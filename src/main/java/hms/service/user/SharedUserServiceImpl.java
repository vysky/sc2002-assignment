package hms.service.user;

import java.util.List;

import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.repository.PatientRepository;
import hms.repository.StaffRepository;

public class SharedUserServiceImpl
{
    // private static final UserRepository userRepository = new UserRepository();
    private static final PatientRepository patientRepository = new PatientRepository();
    private static final StaffRepository staffRepository = new StaffRepository();
    private final List<Patient> patientList;
    private final List<Staff> staffList;

    public SharedUserServiceImpl()
    {
        this.patientList = patientRepository.importFromCsv();
        this.staffList = staffRepository.importFromCsv();
    }

    /**
     * Retrieves the list of patients.
     *
     * @return the list of patients
     */
    public List<Patient> getPatientList()
    {
        return this.patientList;
    }

    /**
     * Retrieves the list of staff.
     *
     * @return the list of staff
     */
    public List<Staff> getStaffList()
    {
        return this.staffList;
    }

    /**
     * Writes the patient list to a CSV file.
     */
    public void setPatientList()
    {
        patientRepository.exportToCsv(this.patientList);
    }

    /**
     * Writes the staff list to a CSV file.
     */
    public void setStaffList()
    {
        staffRepository.exportToCsv(this.staffList);
    }

    /**
     * Adds a new patient to the patient list.
     *
     * @param patient the patient to add
     */
    public void addPatient(Patient patient) {
        this.patientList.add(patient);
    }

    /**
     * Adds a new staff member to the staff list.
     *
     * @param staff the staff member to add
     */
    public void addStaff(Staff staff) {
        this.staffList.add(staff);
    }

    /**
     * Removes a patient from the patient list.
     *
     * @param patient the patient to remove
     */
    public void removePatient(Patient patient) {
        this.patientList.remove(patient);
    }

    /**
     * Removes a staff member from the staff list.
     *
     * @param staff the staff member to remove
     */
    public void removeStaff(Staff staff) {
        this.staffList.remove(staff);
    }
}
