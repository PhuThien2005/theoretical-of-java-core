package no06_control_flow.practice.fizzbuzz_extended;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for FizzBuzzExtendedSolution.
 * 
 * This exercise demonstrates:
 * - Loop control using conditional statements.
 * - `break` to exit the loop block immediately.
 * - `continue` to skip the remainder of the current iteration and proceed to the next iteration.
 */
public class FizzBuzzExtendedSolution {

    public static List<String> fizzBuzz(int start, int end, int skipDivisor, int stopNumber) {
        List<String> results = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            // Rule 2: Break condition. If we reach `stopNumber`, terminate immediately.
            if (i == stopNumber) {
                break;
            }

            // Rule 3: Continue condition. If divisible by `skipDivisor`, skip the rest of the loop block.
            if (skipDivisor != 0 && i % skipDivisor == 0) {
                continue;
            }

            // Rule 4: Standard FizzBuzz evaluation
            if (i % 3 == 0 && i % 5 == 0) {
                results.add("FizzBuzz");
            } else if (i % 3 == 0) {
                results.add("Fizz");
            } else if (i % 5 == 0) {
                results.add("Buzz");
            } else {
                results.add(String.valueOf(i));
            }
        }

        return results;
    }
}
