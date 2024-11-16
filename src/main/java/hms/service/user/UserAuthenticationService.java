package hms.service.user;

import hms.model.shared.CredentialPair;
import hms.model.user.User;

interface UserAuthenticationService
{
    public User authenticateUser(CredentialPair credentialPair);
}
