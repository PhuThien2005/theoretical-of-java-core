import java.util.Arrays;

/**
 * Test runner for MatrixTransposer.
 */
public class MatrixTransposerTest {

    public static void main(String[] args) {
        try {
            testTranspose();
            testSortRows();
            testInvalidMatrixTranspose();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertMatrixEquals(int[][] expected, int[][] actual, String message) {
        if (!Arrays.deepEquals(expected, actual)) {
            throw new AssertionError(message + "\nExpected: " + Arrays.deepToString(expected) + "\nActual:   " + Arrays.deepToString(actual));
        }
    }

    private static void testTranspose() {
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[][] expected = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9}
        };

        MatrixTransposer.transpose(matrix);
        assertMatrixEquals(expected, matrix, "Transpose 3x3 square matrix");

        // 1x1 matrix
        int[][] matrix1 = {{42}};
        int[][] expected1 = {{42}};
        MatrixTransposer.transpose(matrix1);
        assertMatrixEquals(expected1, matrix1, "Transpose 1x1 matrix");
    }

    private static void testSortRows() {
        int[][] matrix = {
            {5, 2, 9},
            {1, 8, 4},
            {7, 3, 6}
        };
        int[][] expected = {
            {2, 5, 9},
            {1, 4, 8},
            {3, 6, 7}
        };

        MatrixTransposer.sortRows(matrix);
        assertMatrixEquals(expected, matrix, "Sort rows in ascending order");
    }

    private static void testInvalidMatrixTranspose() {
        // Jagged/non-square matrix transpose should throw IllegalArgumentException
        int[][] jaggedMatrix = {
            {1, 2, 3},
            {4, 5}
        };
        try {
            MatrixTransposer.transpose(jaggedMatrix);
            throw new AssertionError("Should throw IllegalArgumentException for jagged matrix");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Rectangular non-square matrix transpose should throw IllegalArgumentException
        int[][] rectMatrix = {
            {1, 2, 3},
            {4, 5, 6}
        };
        try {
            MatrixTransposer.transpose(rectMatrix);
            throw new AssertionError("Should throw IllegalArgumentException for rectangular matrix");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
