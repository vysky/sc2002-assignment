package hms.service.user;

import hms.model.shared.CredentialPair;
import hms.model.user.User;

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
