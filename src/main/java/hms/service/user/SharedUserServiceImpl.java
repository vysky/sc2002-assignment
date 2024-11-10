package hms.service.user;

import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.repository.UserRepository;

import java.util.List;

public class SharedUserServiceImpl
{
    private static final UserRepository userRepository = new UserRepository();
    private final List<Patient> patientList;
    private final List<Staff> staffList;

    public SharedUserServiceImpl()
    {
        UserRepository userRepository = new UserRepository();
        this.patientList = userRepository.getPatientList();
        this.staffList = userRepository.getStaffList();
    }

    public List<Patient> getPatientList()
    {
        return this.patientList;
    }

    public List<Staff> getStaffList()
    {
        return this.staffList;
    }

    public void updatePatientList()
    {
        userRepository.updatePatientCsv(patientList);
    }

    public void updateStaffList()
    {
        userRepository.updateStaffCsv(staffList);
    }
}
