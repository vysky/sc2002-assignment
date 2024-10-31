package hms.user;

public class User{

    enum Role {
        PATIENT,
        DOCTOR,
        PHARMACIST,
        ADMIN,
        UNASSIGNED,
    };

    private String hospitalId = "";
    private String password = "";
    private Role userRole = Role.UNASSIGNED;

    public User(String id, String pwd, String role) {
        hospitalId = id;
        pwd = password;

        String curRole = role;
        switch (curRole) {
            case "Pharmacist": { // Since there are 2 'P's, use staff role to differentiate the 2
                userRole = Role.PHARMACIST;
                break;
            }

            case "Doctor": {
                userRole = Role.DOCTOR;
                break;
            }

            case "Administrator": {
                userRole = Role.ADMIN;
                break;
            }

            case "Patient": {
                userRole = Role.PATIENT;
                break;
            }

            default: {
                userRole = Role.UNASSIGNED;
            }
        }
    }

    public void changePassword(String newPwd) {
        password = newPwd;
        System.out.println("Password changed!\n");
    }
    
}