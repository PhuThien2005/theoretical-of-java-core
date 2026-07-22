package no06_control_flow.practice.fizzbuzz_extended;

import java.util.ArrayList;
import java.util.List;

/**
 * An extended version of FizzBuzz incorporating break and continue jump
 * statements.
 */
public class FizzBuzzExtended {

    /**
     * Generates a List of FizzBuzz results from start to end (inclusive),
     * applying dynamic skip and stop conditions.
     * 
     * Rules:
     * 1. Loop from `start` to `end` (inclusive).
     * 2. If the current number is equal to `stopNumber`, terminate the loop
     * immediately (break).
     * Note: The stopNumber itself should NOT be evaluated or added to the results.
     * 3. If the current number is divisible by `skipDivisor`, skip the number
     * entirely (continue).
     * 4. For other numbers:
     * - If divisible by both 3 and 5, add "FizzBuzz".
     * - If divisible by 3, add "Fizz".
     * - If divisible by 5, add "Buzz".
     * - Otherwise, add the string representation of the number (e.g. "7").
     *
     * @param start       the starting number of the loop range
     * @param end         the ending number of the loop range
     * @param skipDivisor numbers divisible by this value will be skipped
     * @param stopNumber  reaching this number terminates the loop
     * @return the list of FizzBuzz strings
     */
    public static List<String> fizzBuzz(int start, int end, int skipDivisor, int stopNumber) {
        // TODO: Implement the extended FizzBuzz loop with break and continue
        // statements.
        var res = new ArrayList<String>();
        for (int i = start; i <= end; i++) {
            if (i == stopNumber) {
                break;
            }
            if (skipDivisor != 0 && i % skipDivisor == 0) {
                continue;
            } else {
                if (i % 3 == 0 && i % 5 == 0) {
                    res.add("FizzBuzz");
                } else if (i % 3 == 0) {
                    res.add("Fizz");
                } else if (i % 5 == 0) {
                    res.add("Buzz");
                } else {
                    res.add(String.valueOf(i));
                }
            }
        }
        return res;
    }
}
