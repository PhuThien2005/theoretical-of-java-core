import java.util.Comparator;
import java.util.List;

/**
 * Starter template for multi-criteria sorting using Comparator chains.
 */
public class MultiCriteriaEmployeeSorter {

    /**
     * Sorts the given list of employees in-place.
     * 
     * Sorting Criteria (chained):
     * 1. Department name: ascending (alphabetical order).
     * 2. Salary: descending (highest salary first).
     * 3. Age: ascending (youngest first).
     *
     * @param employees the list of employees to sort
     */
    public static void sortEmployees(List<Employee> employees) {
        // TODO: Implement sorting using a Comparator chain.
        // Hint: Use List.sort(Comparator) along with Comparator.comparing() and .thenComparing()
    }
}

/**
 * Represents an Employee.
 */
class Employee {
    private final String name;
    private final String department;
    private final double salary;
    private final int age;

    public Employee(String name, String department, double salary, int age) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, $%.2f, %d)", name, department, salary, age);
    }
}
