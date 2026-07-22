package no10_modifiers.practice.immutable_user_session;

import java.util.List;

/**
 * A class representing an immutable user session.
 * 
 * TODO: Make this class final to prevent subclassing.
 */
public class ImmutableUserSession {

    // TODO: Declare all fields private and final to ensure they cannot be modified.
    private String sessionId;
    private String username;
    private List<String> permissions;

    /**
     * Initializes the user session.
     * 
     * TODO: Perform defensive copying on mutable inputs (like permissions)
     * to prevent external modifications after object construction.
     */
    public ImmutableUserSession(String sessionId, String username, List<String> permissions) {
        this.sessionId = sessionId;
        this.username = username;
        this.permissions = permissions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Returns the list of permissions.
     * 
     * TODO: Ensure the returned list is unmodifiable so that callers
     * cannot modify the list contents.
     */
    public List<String> getPermissions() {
        return permissions;
    }
}
