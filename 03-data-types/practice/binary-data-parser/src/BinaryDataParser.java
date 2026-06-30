/**
 * A utility class to parse primitive data types from a big-endian byte array (byte stream).
 */
public class BinaryDataParser {

    /**
     * Parses a 2-byte signed short from the byte array at the given offset.
     * The bytes are stored in big-endian order (most significant byte first).
     *
     * @param data the raw byte array
     * @param offset the index where the short starts
     * @return the parsed short value
     * @throws NullPointerException if data is null
     * @throws IllegalArgumentException if the array does not have at least 2 bytes remaining starting from offset
     */
    public static short parseShort(byte[] data, int offset) {
        // TODO: Implement big-endian short parsing. Remember to handle sign expansion and bit masking (e.g. data[i] & 0xFF).
        return 0;
    }

    /**
     * Parses a 4-byte signed int from the byte array at the given offset.
     * The bytes are stored in big-endian order.
     *
     * @param data the raw byte array
     * @param offset the index where the int starts
     * @return the parsed int value
     * @throws NullPointerException if data is null
     * @throws IllegalArgumentException if the array does not have at least 4 bytes remaining starting from offset
     */
    public static int parseInt(byte[] data, int offset) {
        // TODO: Implement big-endian int parsing.
        return 0;
    }

    /**
     * Parses an 8-byte signed long from the byte array at the given offset.
     * The bytes are stored in big-endian order.
     *
     * @param data the raw byte array
     * @param offset the index where the long starts
     * @return the parsed long value
     * @throws NullPointerException if data is null
     * @throws IllegalArgumentException if the array does not have at least 8 bytes remaining starting from offset
     */
    public static long parseLong(byte[] data, int offset) {
        // TODO: Implement big-endian long parsing.
        return 0L;
    }

    /**
     * Parses a 1-byte boolean from the byte array at the given offset.
     * A value of 0 represents false; any non-zero value represents true.
     *
     * @param data the raw byte array
     * @param offset the index where the boolean is stored
     * @return the parsed boolean value
     * @throws NullPointerException if data is null
     * @throws IllegalArgumentException if offset is out of bounds
     */
    public static boolean parseBoolean(byte[] data, int offset) {
        // TODO: Implement boolean parsing.
        return false;
    }
}
