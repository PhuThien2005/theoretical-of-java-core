import java.util.Comparator;
import java.util.List;

/**
 * Reference solution for MultiCriteriaEmployeeSorterSolution.
 * 
 * Comparator chains:
 * - `Comparator.comparing` creates a comparator based on a key extractor function.
 * - `.thenComparing` adds secondary sort keys.
 * - `reversed()` reverses the comparison logic (useful for descending order).
 */
public class MultiCriteriaEmployeeSorterSolution {

    public static void sortEmployees(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return;
        }

        // Sort in-place using a Comparator chain:
        // 1. Department (ascending)
        // 2. Salary (descending - using reversed())
        // 3. Age (ascending)
        employees.sort(
            Comparator.comparing(Employee::getDepartment)
                      .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                      .thenComparingInt(Employee::getAge)
        );
    }
}

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
