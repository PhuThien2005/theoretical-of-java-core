import java.util.Arrays;

/**
 * Reference solution for MatrixTransposerSolution.
 */
public class MatrixTransposerSolution {

    /**
     * Transposes an N x N square matrix in-place.
     * 
     * We iterate through the upper triangle of the matrix (where j > i)
     * and swap matrix[i][j] with matrix[j][i]. We only iterate over j > i
     * to avoid double-swapping elements back to their original positions.
     */
    public static void transpose(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }

        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            if (matrix[i] == null) {
                throw new IllegalArgumentException("Matrix rows cannot be null");
            }
            if (matrix[i].length != n) {
                throw new IllegalArgumentException("Matrix must be a square N x N matrix. Row " + i + " has length " + matrix[i].length + " instead of " + n);
            }
        }

        for (int i = 0; i < n; i++) {
            // Swap elements across the diagonal: only swap when column index j is greater than row index i
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    /**
     * Sorts each row of the 2D matrix in ascending order.
     * Demonstrates how to access and manipulate 1D arrays inside a 2D array container.
     */
    public static void sortRows(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] != null) {
                Arrays.sort(matrix[i]);
            }
        }
    }
}
