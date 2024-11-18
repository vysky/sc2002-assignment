package hms.service.user;

import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.repository.PatientRepository;
import hms.repository.StaffRepository;

import java.util.List;

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

    public List<Patient> getPatientList()
    {
        return this.patientList;
    }

    public List<Staff> getStaffList()
    {
        return this.staffList;
    }

    // write patient list to csv
    public void setPatientList()
    {
        patientRepository.exportToCsv(this.patientList);
    }

    // write staff list to csv
    public void setStaffList()
    {
        staffRepository.exportToCsv(this.staffList);
    }
}
