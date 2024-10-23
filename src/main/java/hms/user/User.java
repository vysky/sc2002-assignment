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
    private boolean isStaff = false;

    public User(String id, String pwd, boolean staff) {
        hospitalId = id;
        pwd = password;
        isStaff = staff;

        char roleChar = id.charAt(0);
        switch (roleChar) {
            case 'P': { // Since there are 2 'P's, use staff role to differentiate the 2
                if (staff) {
                    userRole = Role.PHARMACIST;
                }
                else {
                    userRole = Role.PATIENT;
                }
                break;
            }

            case 'D': {
                userRole = Role.DOCTOR;
                break;
            }

            case 'A': {
                userRole = Role.ADMIN;
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