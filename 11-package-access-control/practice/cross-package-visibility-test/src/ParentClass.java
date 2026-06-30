package alpha;

/**
 * A parent class containing fields with different access modifiers.
 */
public class ParentClass {
    
    // public - visible everywhere
    public String publicField = "public-value";

    // protected - visible in same package AND subclasses in different packages
    protected String protectedField = "protected-value";

    // package-private (default) - visible only in the same package
    String defaultField = "default-value";

    // private - visible only within this class
    private String privateField = "private-value";

    public String getPrivateField() {
        // TODO: Return privateField
        return null;
    }
}
