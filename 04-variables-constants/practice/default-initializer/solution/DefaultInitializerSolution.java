/**
 * Reference solution for DefaultInitializerSolution.
 * 
 * In Java:
 * - Instance and static variables are automatically initialized to their default values:
 *   - boolean: false
 *   - char: '\u0000' (null character)
 *   - byte, short, int: 0
 *   - long: 0L
 *   - float: 0.0f
 *   - double: 0.0
 *   - Objects/References: null
 * - Local variables are NOT initialized automatically. Attempting to use them before
 *   assignment is a compile-time error.
 */
public class DefaultInitializerSolution {

    // Instance fields declared without explicit initialization
    public boolean defaultBoolean;
    public byte defaultByte;
    public char defaultChar;
    public short defaultShort;
    public int defaultInt;
    public long defaultLong;
    public float defaultFloat;
    public double defaultDouble;
    public String defaultString;

    // Static fields declared without explicit initialization
    public static int defaultStaticInt;
    public static Object defaultStaticObject;

    public boolean getDefaultBoolean() {
        return defaultBoolean;
    }

    public byte getDefaultByte() {
        return defaultByte;
    }

    public char getDefaultChar() {
        return defaultChar;
    }

    public short getDefaultShort() {
        return defaultShort;
    }

    public int getDefaultInt() {
        return defaultInt;
    }

    public long getDefaultLong() {
        return defaultLong;
    }

    public float getDefaultFloat() {
        return defaultFloat;
    }

    public double getDefaultDouble() {
        return defaultDouble;
    }

    public String getDefaultString() {
        return defaultString;
    }

    public static int getDefaultStaticInt() {
        return defaultStaticInt;
    }

    public static Object getDefaultStaticObject() {
        return defaultStaticObject;
    }
}
