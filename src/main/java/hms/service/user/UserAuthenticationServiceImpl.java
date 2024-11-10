package hms.service.user;

import hms.model.shared.CredentialPair;
import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.model.user.User;
import hms.repository.UserRepository;

import java.util.List;

public class UserAuthenticationServiceImpl implements UserAuthenticationService
{
    // ============================================================
    // Stored Properties
    // ============================================================

    private final List<Patient> patientList;
    private final List<Staff> staffList;

    // ============================================================
    // Public Methods
    // ============================================================

    public UserAuthenticationServiceImpl(List<Patient> patientList, List<Staff> staffList)
    {
        this.patientList = patientList;
        this.staffList = staffList;
    }

    public void printList()
    {
        for (Patient patient : this.patientList)
        {
            System.out.println("Name: " + patient.getName() + ", Password: " + patient.getPassword());
        }

        for (Staff staff : this.staffList)
        {
            System.out.println("Name: " + staff.getName() + ", Password: " + staff.getPassword());
        }
    }

    public User authenticateUser(CredentialPair credentialPair)
    {
        try
        {
            if (isCredentialFromPatient(credentialPair.id(), credentialPair.password()))
            {
                return this.getPatientById(credentialPair.id());
            }

            if (isCredentialFromStaff(credentialPair.id(), credentialPair.password()))
            {
                return this.getStaffById(credentialPair.id());
            }
        }
        catch (Exception ex)
        {
            System.out.println("Invalid credentials.");
        }

        return null;
    }

    // ============================================================
    // Private Methods
    // ============================================================

    private boolean isCredentialFromPatient(String id, String password)
    {
        return this.patientList.stream().anyMatch(uObject -> uObject.getId().equals(id) && uObject.getPassword().equals(password));
    }

    private boolean isCredentialFromStaff(String id, String password)
    {
        return this.staffList.stream().anyMatch(uObject -> uObject.getId().equals(id) && uObject.getPassword().equals(password));
    }

    private Patient getPatientById(String id)
    {
        return this.patientList.stream().filter(uObject -> uObject.getId().equals(id)).findFirst().orElse(null);
    }

    private Staff getStaffById(String id)
    {
        return this.staffList.stream().filter(uObject -> uObject.getId().equals(id)).findFirst().orElse(null);
    }
}