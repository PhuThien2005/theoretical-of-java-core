package no26_io.practice.object_graph_serializer;

import java.io.*;

/**
 * Reference solution for ObjectGraphSerializerSolution.
 * 
 * Serialization concept:
 * - A class must implement `java.io.Serializable` to be serialized.
 * - `transient` fields are skipped during serialization. They receive default JVM values upon restoration
 *   (e.g., `null` for references, `0` for numeric primitives, `false` for booleans).
 */
public class ObjectGraphSerializerSolution {

    public static byte[] serialize(Object obj) throws IOException {
        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data cannot be empty");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        }
    }
}

class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String sessionId;
    
    // Mark sensitive metadata as transient to protect from exposure/unwanted persistence
    private final transient String securityToken;
    private final transient long loginTimeMillis;

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
