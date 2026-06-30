import java.util.Arrays;

/**
 * Test runner for InPlaceArrayRotator.
 */
public class InPlaceArrayRotatorTest {

    public static void main(String[] args) {
        try {
            testRotateRight();
            testRotateNegative();
            testRotateLargerK();
            testEdgeCases();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertArrayEquals(int[] expected, int[] actual, String message) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(message + "\nExpected: " + Arrays.toString(expected) + "\nActual:   " + Arrays.toString(actual));
        }
    }

    private static void testRotateRight() {
        int[] arr = {1, 2, 3, 4, 5};
        InPlaceArrayRotator.rotate(arr, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, arr, "Rotate right by 2");

        int[] arr2 = {1, 2, 3, 4};
        InPlaceArrayRotator.rotate(arr2, 1);
        assertArrayEquals(new int[]{4, 1, 2, 3}, arr2, "Rotate right by 1");
    }

    private static void testRotateNegative() {
        int[] arr = {1, 2, 3, 4, 5};
        InPlaceArrayRotator.rotate(arr, -1); // equivalent to rotate right by 4
        assertArrayEquals(new int[]{2, 3, 4, 5, 1}, arr, "Rotate left by 1 (k=-1)");
    }

    private static void testRotateLargerK() {
        int[] arr = {1, 2, 3, 4, 5};
        InPlaceArrayRotator.rotate(arr, 7); // 7 % 5 = 2. Same as rotating by 2
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, arr, "Rotate right by 7 (k > length)");
    }

    private static void testEdgeCases() {
        // Null check
        int[] arrNull = null;
        InPlaceArrayRotator.rotate(arrNull, 3); // should not throw Exception
        
        // Single element
        int[] arrSingle = {10};
        InPlaceArrayRotator.rotate(arrSingle, 5);
        assertArrayEquals(new int[]{10}, arrSingle, "Single element array");

        // Empty array
        int[] arrEmpty = {};
        InPlaceArrayRotator.rotate(arrEmpty, 2);
        assertArrayEquals(new int[]{}, arrEmpty, "Empty array");
    }
}
