package no26_io.practice.object_graph_serializer;

import java.io.*;

/**
 * Starter template for transient object serialization.
 */
public class ObjectGraphSerializer {

    /**
     * Serializes an object to a byte array.
     */
    public static byte[] serialize(Object obj) throws IOException {
        // TODO: Implement serialization using ByteArrayOutputStream and ObjectOutputStream
        return null;
    }

    /**
     * Deserializes a byte array back to an object.
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        // TODO: Implement deserialization using ByteArrayInputStream and ObjectInputStream
        return null;
    }
}

/**
 * Represents a User Session. Must be serializable.
 */
class UserSession implements Serializable {
    // TODO: Add serialVersionUID

    private final String username;
    private final String sessionId;
    
    // TODO: Mark these two fields as transient to exclude them from serialization
    private final String securityToken;
    private final long loginTimeMillis;

    public UserSession(String username, String sessionId, String securityToken, long loginTimeMillis) {
        this.username = username;
        this.sessionId = sessionId;
        this.securityToken = securityToken;
        this.loginTimeMillis = loginTimeMillis;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public long getLoginTimeMillis() {
        return loginTimeMillis;
    }
}
