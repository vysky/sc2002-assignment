package hms.service.user;

import hms.model.shared.CredentialPair;
import hms.model.user.User;
/**
 * The UserAuthenticationService interface defines the contract for user authentication
 * in the hospital management system (HMS). Implementing classes should provide methods
 * to authenticate users based on their credentials.
 */
interface UserAuthenticationService
{
    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param credentialPair the credentials of the user
     * @return the authenticated user
     */
    public User authenticateUser(CredentialPair credentialPair);
}
