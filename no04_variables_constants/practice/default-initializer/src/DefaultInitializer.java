package no04_variables_constants.practice.default_initializer;

/**
 * A class to explore default initializations of instance and static variables in Java.
 */
public class DefaultInitializer {

    // Instance variables (declare but do not initialize)
    public boolean defaultBoolean;
    public byte defaultByte;
    public char defaultChar;
    public short defaultShort;
    public int defaultInt;
    public long defaultLong;
    public float defaultFloat;
    public double defaultDouble;
    public String defaultString;

    // Static variables (declare but do not initialize)
    public static int defaultStaticInt;
    public static Object defaultStaticObject;

    /**
     * Gets the default value of the boolean instance variable.
     */
    public boolean getDefaultBoolean() {
        // TODO: Return defaultBoolean
        return true;
    }

    /**
     * Gets the default value of the byte instance variable.
     */
    public byte getDefaultByte() {
        // TODO: Return defaultByte
        return -1;
    }

    /**
     * Gets the default value of the char instance variable.
     */
    public char getDefaultChar() {
        // TODO: Return defaultChar
        return 'X';
    }

    /**
     * Gets the default value of the short instance variable.
     */
    public short getDefaultShort() {
        // TODO: Return defaultShort
        return -1;
    }

    /**
     * Gets the default value of the int instance variable.
     */
    public int getDefaultInt() {
        // TODO: Return defaultInt
        return -1;
    }

    /**
     * Gets the default value of the long instance variable.
     */
    public long getDefaultLong() {
        // TODO: Return defaultLong
        return -1L;
    }

    /**
     * Gets the default value of the float instance variable.
     */
    public float getDefaultFloat() {
        // TODO: Return defaultFloat
        return -1.0f;
    }

    /**
     * Gets the default value of the double instance variable.
     */
    public double getDefaultDouble() {
        // TODO: Return defaultDouble
        return -1.0;
    }

    /**
     * Gets the default value of the String reference instance variable.
     */
    public String getDefaultString() {
        // TODO: Return defaultString
        return "not-null";
    }

    /**
     * Gets the default value of the static int variable.
     */
    public static int getDefaultStaticInt() {
        // TODO: Return defaultStaticInt
        return -1;
    }

    /**
     * Gets the default value of the static Object reference.
     */
    public static Object getDefaultStaticObject() {
        // TODO: Return defaultStaticObject
        return new Object();
    }
}
