
public class StringPool {
    public static void main(String[] args) {
        String name = null;

        // Case 1: Safe short-circuit evaluation
        boolean isNotEmptySafe = (name != null && name.length() > 0);
        System.out.println(isNotEmptySafe); // false (LHS is false, RHS is ignored)

        // Case 2: Unsafe non-short-circuit evaluation
        try {
            boolean isNotEmptyUnsafe = (name != null & name.length() > 0);
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException!"); // Prints: Caught NullPointerException!
        }

    }
}