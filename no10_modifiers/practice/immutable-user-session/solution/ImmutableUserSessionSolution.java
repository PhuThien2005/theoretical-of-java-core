package no10_modifiers.practice.immutable_user_session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reference solution for ImmutableUserSessionSolution.
 * 
 * Rules for Immutability:
 * 1. Declare class as `final` so it cannot be extended (prevents overriding method behaviors).
 * 2. Declare all fields `private` and `final` (ensures fields are initialized once in the constructor and cannot be reassigned).
 * 3. Provide no setters.
 * 4. Defensive Copying:
 *    - In constructor: create a new collection wrapping the input parameter to prevent external changes from updating our class.
 *    - In getter: return the collection wrapped in `Collections.unmodifiableList(...)` to prevent direct edits.
 */
public final class ImmutableUserSessionSolution {

    private final String sessionId;
    private final String username;
    private final List<String> permissions;

    public ImmutableUserSessionSolution(String sessionId, String username, List<String> permissions) {
        if (sessionId == null || username == null) {
            throw new IllegalArgumentException("Session ID and username cannot be null");
        }
        this.sessionId = sessionId;
        this.username = username;
        
        // Defensive copy of the mutable List:
        if (permissions == null) {
            this.permissions = new ArrayList<>();
        } else {
            this.permissions = new ArrayList<>(permissions);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getPermissions() {
        // Return an unmodifiable view of the list to prevent external modification
        return Collections.unmodifiableList(permissions);
    }
}
