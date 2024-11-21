package hms.service.user;

import java.util.List;

import hms.model.shared.CredentialPair;
import hms.model.user.Patient;
import hms.model.user.Staff;
import hms.model.user.User;
import org.springframework.security.crypto.bcrypt.*;

/**
 * The UserAuthenticationServiceImpl class provides an implementation for the
 * UserAuthenticationService interface. It handles user authentication and password
 * management for patients and staff in the hospital management system (HMS).
 */
public class UserAuthenticationServiceImpl implements UserAuthenticationService
{
    private final List<Patient> patientList;
    private final List<Staff> staffList;

    /**
     * Constructor for UserAuthenticationServiceImpl.
     *
     * @param patientList List of patients.
     * @param staffList   List of staff.
     */
    public UserAuthenticationServiceImpl(List<Patient> patientList, List<Staff> staffList)
    {
        this.patientList = patientList;
        this.staffList = staffList;
    }

    /**
     * Prints the list of patients and staff with their names and passwords.
     */
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

    /**
     * Authenticates a user based on their credentials.
     *
     * @param credentialPair The credential pair containing id and password.
     * @return The authenticated user, or null if authentication fails.
     */
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
            System.out.println("Invalid credentials or inactive account.");
        }

        return null;
    }

    /**
     * Changes the password for a user.
     *
     * @param id          The id of the user.
     * @param newPassword The new password to set.
     * @return true if the password was successfully changed, false otherwise.
     */
    public boolean changePassword(String id, String newPassword)
    {
        try
        {
            if (isIdFromPatient(id))
            {
                getPatientById(id).setPassword(newPassword);
                getPatientById(id).setHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                return true;
            }

            if (isIdFromStaff(id))
            {
                getStaffById(id).setPassword(newPassword);
                getStaffById(id).setHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                return true;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Invalid user id.");
        }

        return false;
    }

    /**
     * Checks if the given id belongs to a patient.
     *
     * @param id The id to check.
     * @return true if the id belongs to a patient, false otherwise.
     */
    private boolean isIdFromPatient(String id)
    {
        return this.patientList.stream().anyMatch(patient -> patient.getId().equals(id));
    }

    /**
     * Checks if the given id belongs to a staff member.
     *
     * @param id The id to check.
     * @return true if the id belongs to a staff member, false otherwise.
     */
    private boolean isIdFromStaff(String id)
    {
        return this.staffList.stream().anyMatch(staff -> staff.getId().equals(id));
    }

    /**
     * Checks if the given credentials belong to a patient.
     *
     * @param id       The id to check.
     * @param password The password to check.
     * @return true if the credentials belong to a patient, false otherwise.
     */
    private boolean isCredentialFromPatient(String id, String password)
    {
        return this.patientList.stream().anyMatch(uObject -> uObject.getId().equals(id) && BCrypt.checkpw(password, uObject.getHash()));
    }

    /**
     * Checks if the given credentials belong to a staff member.
     *
     * @param id       The id to check.
     * @param password The password to check.
     * @return true if the credentials belong to a staff member, false otherwise.
     */
    private boolean isCredentialFromStaff(String id, String password)
    {
        return this.staffList.stream().anyMatch(uObject -> uObject.getId().equals(id) && BCrypt.checkpw(password, uObject.getHash()));
    }

    /**
     * Retrieves a patient by their id.
     *
     * @param id The id of the patient.
     * @return The patient with the given id, or null if no such patient exists.
     */
    private Patient getPatientById(String id)
    {
        return this.patientList.stream().filter(uObject -> uObject.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves a staff member by their id.
     *
     * @param id The id of the staff member.
     * @return The staff member with the given id, or null if no such staff member exists.
     */
    private Staff getStaffById(String id)
    {
        return this.staffList.stream().filter(uObject -> uObject.getId().equals(id)).findFirst().orElse(null);
    }
}
