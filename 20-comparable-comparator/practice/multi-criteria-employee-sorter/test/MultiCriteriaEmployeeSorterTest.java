import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test runner for MultiCriteriaEmployeeSorter.
 */
public class MultiCriteriaEmployeeSorterTest {

    public static void main(String[] args) {
        try {
            testEmployeeSorting();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testEmployeeSorting() {
        Employee e1 = new Employee("Alice", "Sales", 50000.0, 25);
        Employee e2 = new Employee("Bob", "Sales", 60000.0, 30); // Higher salary than Alice
        Employee e3 = new Employee("Charlie", "HR", 45000.0, 28);   // HR department (alphabetically first)
        Employee e4 = new Employee("David", "Sales", 50000.0, 22); // Same salary as Alice, but younger

        List<Employee> list = new ArrayList<>(Arrays.asList(e1, e2, e3, e4));

        MultiCriteriaEmployeeSorter.sortEmployees(list);

        // Expected Order:
        // 1. Charlie (HR - department comes first)
        // 2. Bob (Sales, $60k - highest salary in Sales)
        // 3. David (Sales, $50k, age 22 - younger than Alice)
        // 4. Alice (Sales, $50k, age 25)
        assertEquals(e3, list.get(0), "Index 0: HR");
        assertEquals(e2, list.get(1), "Index 1: Sales, 60k");
        assertEquals(e4, list.get(2), "Index 2: Sales, 50k, Age 22");
        assertEquals(e1, list.get(3), "Index 3: Sales, 50k, Age 25");
    }
}
