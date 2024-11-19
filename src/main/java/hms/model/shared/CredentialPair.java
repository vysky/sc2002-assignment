package hms.model.shared;

/**
 * Record to store credential pairs.
 * @param id User ID
 * @param password User password
 */
public record CredentialPair(String id, String password)
{
}
