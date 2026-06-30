/**
 * Reference solution for BitwiseBaseConverterSolution.
 * 
 * Using bitwise operators for base conversion:
 * - `>>>` represents unsigned right shift. This fills the leftmost bits with 0s even for negative numbers,
 *   which is critical because integers are stored in two's complement.
 * - `&` is a bitwise AND. We mask the lowest bits (e.g. `& 1` for binary, `& 0xF` for hex) to isolate them.
 */
public class BitwiseBaseConverterSolution {

    /**
     * Converts an integer to a binary string representation using bitwise shifts and masks.
     */
    public static String toBinary(int value) {
        if (value == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;

        // Loop through all 32 bits from left (most significant, index 31) to right (index 0)
        for (int i = 31; i >= 0; i--) {
            // Shift the bit at index i to the units place and mask out all other bits
            int bit = (value >>> i) & 1;

            // Only start appending once we find our first non-zero bit (to prevent leading zeros)
            if (bit != 0 || !leadingZero) {
                sb.append(bit);
                leadingZero = false;
            }
        }

        return sb.toString();
    }

    /**
     * Converts an integer to a hexadecimal string representation.
     */
    public static String toHex(int value) {
        if (value == 0) {
            return "0";
        }

        char[] hexChars = "0123456789abcdef".toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;

        // An int has 32 bits, which is 8 hex digits (each digit is 4 bits / 1 nibble)
        for (int i = 7; i >= 0; i--) {
            // Shift the nibble at index i * 4 to the unit position and mask with 0xF (15, which is 1111 in binary)
            int nibble = (value >>> (i * 4)) & 0xF;

            // Prevent leading zeros
            if (nibble != 0 || !leadingZero) {
                sb.append(hexChars[nibble]);
                leadingZero = false;
            }
        }

        return sb.toString();
    }
}
