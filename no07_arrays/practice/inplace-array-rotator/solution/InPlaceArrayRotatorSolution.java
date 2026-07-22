package no07_arrays.practice.inplace_array_rotator;

/**
 * Reference solution for InPlaceArrayRotatorSolution.
 * 
 * To rotate an array [1, 2, 3, 4, 5] right by k=2 in-place:
 * 1. Normalize k to keep it within [0, length - 1].
 * 2. Reverse the entire array -> [5, 4, 3, 2, 1]
 * 3. Reverse the first k elements -> [4, 5, 3, 2, 1] (elements 0 to k-1)
 * 4. Reverse the remaining length - k elements -> [4, 5, 1, 2, 3] (elements k to length-1)
 * 
 * Space complexity: O(1) (requires only a few temp swap variables).
 * Time complexity: O(N) (reverses segments of array linearly).
 */
public class InPlaceArrayRotatorSolution {

    public static void rotate(int[] arr, int k) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        // Normalize k
        k = k % n;
        if (k < 0) {
            k = k + n; // Negative rotation equivalent to positive right rotation
        }

        if (k == 0) {
            return;
        }

        // Step 1: Reverse the whole array
        reverse(arr, 0, n - 1);
        
        // Step 2: Reverse the first k elements
        reverse(arr, 0, k - 1);
        
        // Step 3: Reverse the remaining elements
        reverse(arr, k, n - 1);
    }

    /**
     * Helper method to reverse a subsegment of an array in-place.
     */
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
