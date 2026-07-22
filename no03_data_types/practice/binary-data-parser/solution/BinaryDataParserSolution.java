package no03_data_types.practice.binary_data_parser;

/**
 * Reference solution for BinaryDataParserSolution.
 * 
 * In binary network protocols or file formats, values are typically stored as a sequence of bytes.
 * "Big-endian" means the most significant bytes are stored first (lowest memory address / lowest index).
 * 
 * We use the bitwise AND operator `& 0xFF` on each byte. In Java, byte operations are promoted to
 * int. Because bytes are signed, casting a negative byte to int causes sign-extension (fills the top
 * 24 bits with 1s). Masking with `& 0xFF` ensures we only keep the lowest 8 bits as an unsigned value.
 */
public class BinaryDataParserSolution {

    public static short parseShort(byte[] data, int offset) {
        if (data == null) {
            throw new NullPointerException("Data array cannot be null");
        }
        if (offset < 0 || offset + 2 > data.length) {
            throw new IllegalArgumentException("Insufficient bytes to parse a 2-byte short (offset=" + offset + ")");
        }
        return (short) (((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF));
    }

    public static int parseInt(byte[] data, int offset) {
        if (data == null) {
            throw new NullPointerException("Data array cannot be null");
        }
        if (offset < 0 || offset + 4 > data.length) {
            throw new IllegalArgumentException("Insufficient bytes to parse a 4-byte int (offset=" + offset + ")");
        }
        return ((data[offset] & 0xFF) << 24) |
               ((data[offset + 1] & 0xFF) << 16) |
               ((data[offset + 2] & 0xFF) << 8) |
               (data[offset + 3] & 0xFF);
    }

    public static long parseLong(byte[] data, int offset) {
        if (data == null) {
            throw new NullPointerException("Data array cannot be null");
        }
        if (offset < 0 || offset + 8 > data.length) {
            throw new IllegalArgumentException("Insufficient bytes to parse an 8-byte long (offset=" + offset + ")");
        }
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (data[offset + i] & 0xFF);
        }
        return value;
    }

    public static boolean parseBoolean(byte[] data, int offset) {
        if (data == null) {
            throw new NullPointerException("Data array cannot be null");
        }
        if (offset < 0 || offset >= data.length) {
            throw new IllegalArgumentException("Offset out of bounds: " + offset);
        }
        return data[offset] != 0;
    }
}
