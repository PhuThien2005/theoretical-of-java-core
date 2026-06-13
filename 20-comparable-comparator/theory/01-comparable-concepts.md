# Comparable and Comparator - Part 1

## Learning Goal

This file covers a focused slice of **Comparable and Comparator**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Comparable` | Comparable defines natural ordering inside the class being compared. |
| `compareTo` |compareTo is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Comparator` | Comparator defines external custom ordering for objects. |
| `compare` |compare is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Natural ordering` |Natural ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Custom ordering` |Custom ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Sort List object` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Sort by multiple criteria` |Sort by multiple criteria is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Comparator.comparing` | Comparator defines external custom ordering for objects. |
| `thenComparing` |thenComparing is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Comparable

Comparable defines natural ordering inside the class being compared.

#### Enriched Explanation
`Comparable<T>` is a generic interface (`java.lang.Comparable`) implemented by a class to define its **natural ordering**. When a class implements `Comparable`, objects of that class can be sorted automatically by collections utilities like `Collections.sort()` or `Arrays.sort()`, and can be used as keys in sorted maps (`TreeMap`) or elements in sorted sets (`TreeSet`) without providing an explicit comparator.

#### Code Example
```java
public class User implements Comparable<User> {
    private final String username;
    private final int id;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
    }

    @Override
    public int compareTo(User other) {
        // Natural ordering based on ID ascending
        return Integer.compare(this.id, other.id);
    }
}
```

#### Gotchas & Failure Modes
- **ClassCastException**: If you attempt to sort a list of objects that do not implement `Comparable` (and do not provide a `Comparator`), Java will throw a `ClassCastException` at runtime (if using raw types) or fail to compile (with generic types).
- **Consistency with Equals**: It is strongly recommended (though not strictly required) that natural ordering be consistent with `equals`. That is, `(x.compareTo(y) == 0) == (x.equals(y))`. Collections like `TreeSet` and `TreeMap` use `compareTo` (not `equals`) to determine uniqueness; if they are inconsistent, the set/map will violate the general contract of `Set`/`Map` and behave unexpectedly.

### compareTo

compareTo is a specific method in Comparable used to define natural ordering rules.

#### Enriched Explanation
The `compareTo(T o)` method is the single abstract method of the `Comparable` interface. It compares the current object (`this`) with the specified object `o`.
- Returns a **negative integer** if `this` is less than `o`.
- Returns **zero** if `this` is equal to `o`.
- Returns a **positive integer** if `this` is greater than `o`.

#### Code Example
```java
// String's compareTo implementation compares characters lexicographically
int result = "apple".compareTo("banana"); // returns a negative number (< 0)
```

#### Gotchas & Failure Modes
- **Integer Subtraction Overflow Bug**: A classic mistake is implementing `compareTo` using subtraction:
  ```java
  public int compareTo(User other) {
      return this.id - other.id; // DANGER: can overflow!
  }
  ```
  If `this.id` is `Integer.MIN_VALUE` and `other.id` is `1`, the subtraction results in `Integer.MAX_VALUE` (a positive number), incorrectly indicating that `this` is greater than `other`. Always use `Integer.compare(a, b)` instead.
- **NullPointerException**: `x.compareTo(null)` should always throw a `NullPointerException`.

### Comparator

Comparator defines external custom ordering for objects.

#### Enriched Explanation
`Comparator<T>` is a functional interface (`java.util.Comparator`) used to define an **external, custom ordering** for a class. Unlike `Comparable`, which is embedded in the class itself, a `Comparator` can be defined as separate classes, anonymous classes, or lambda expressions. This allows multiple different sorting strategies for the same class (e.g., sorting employees by salary, then by department).

#### Code Example
```java
import java.util.Comparator;

public class EmployeeSalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return Double.compare(e1.getSalary(), e2.getSalary());
    }
}
```

#### Gotchas & Failure Modes
- **Concurrent Modification / Mutable Fields**: If you sort a collection and then modify the fields of an object that the `Comparator` uses to sort, the sorting state becomes inconsistent. The collection (like `TreeSet` or `TreeMap`) will fail to retrieve, delete, or correctly order the modified elements.

### compare

compare is the abstract method in Comparator used to evaluate two objects.

#### Enriched Explanation
The `compare(T o1, T o2)` method is the primary abstract method of `Comparator`.
- Returns a **negative integer** if `o1` is less than `o2`.
- Returns **zero** if `o1` is equal to `o2`.
- Returns a **positive integer** if `o1` is greater than `o2`.

#### Code Example
```java
Comparator<String> lengthComparator = (s1, s2) -> Integer.compare(s1.length(), s2.length());
int result = lengthComparator.compare("short", "extremelyLong"); // negative
```

#### Gotchas & Failure Modes
- **Null Safety**: Unlike `compareTo` (where `x.compareTo(null)` throws NPE by contract), `compare(o1, o2)` may receive `null` for either or both parameters. Implementations must decide how to handle `null` (e.g., using `Comparator.nullsFirst()`) to avoid throwing unexpected `NullPointerException`s.
- **Asymmetry Bug**: The implementation must satisfy asymmetry: `signum(compare(x, y)) == -signum(compare(y, x))`. If not met, sorting algorithms can loop infinitely or produce wrong results.

### Natural ordering

Natural ordering is the default order defined by the class's compareTo implementation.

#### Enriched Explanation
**Natural ordering** refers to the default sort order defined within a class by implementing `Comparable`. For built-in Java classes, natural ordering is pre-defined:
- `String` uses lexicographic order (Unicode value comparisons).
- Numeric wrappers (`Integer`, `Double`, etc.) use ascending numerical order.
- `LocalDate` and `LocalDateTime` use chronological order.

#### Code Example
```java
List<String> fruits = new ArrayList<>(List.of("Orange", "Apple", "Banana"));
Collections.sort(fruits); // Uses String's natural ordering
System.out.println(fruits); // [Apple, Banana, Orange]
```

#### Gotchas & Failure Modes
- **Case Sensitivity**: The natural ordering of `String` is case-sensitive (lexicographical), meaning uppercase letters come before lowercase letters (e.g., `"Zebra"` comes before `"apple"` because `'Z'` has ASCII value 90 and `'a'` has 97).

### Custom ordering

Custom ordering is an alternative order provided externally by a Comparator.

#### Enriched Explanation
**Custom ordering** allows sorting objects in a sequence different from their natural ordering, or sorting objects of a class that does not implement `Comparable`. It is achieved by providing a `Comparator`.

#### Code Example
```java
List<String> fruits = new ArrayList<>(List.of("Orange", "Apple", "Banana"));
// Case-insensitive custom ordering
fruits.sort(String.CASE_INSENSITIVE_ORDER);
System.out.println(fruits); // [Apple, Banana, Orange]
```

#### Gotchas & Failure Modes
- **Contract Violation**: In Java 7 and later, sorting algorithms use TimSort, which strictly enforces the `Comparator` mathematical contract (reflexivity, transitivity, and symmetry). If a custom comparator violates these rules, the JVM will throw a runtime `IllegalArgumentException: Comparison method violates its general contract!`.

### Sort List object

Sorting list objects via Collections.sort or List.sort.

#### Enriched Explanation
Sorting a list can be done using:
1. `Collections.sort(List<T> list)`: Sorts based on natural ordering.
2. `Collections.sort(List<T> list, Comparator<? super T> c)`: Sorts using the specified comparator.
3. `List.sort(Comparator<? super T> c)`: (Java 8+) Instance method on the `List` interface. To sort a list using natural ordering, pass `null` or `Comparator.naturalOrder()`.

`List.sort()` is generally preferred over `Collections.sort()` as it is an instance method and can be overridden by specific list implementations for optimal performance.

#### Code Example
```java
List<Integer> numbers = new ArrayList<>(List.of(3, 1, 4, 1, 5));
// Using List.sort with natural ordering
numbers.sort(Comparator.naturalOrder()); // [1, 1, 3, 4, 5]
// Using Collections.sort
Collections.sort(numbers, Comparator.reverseOrder()); // [5, 4, 3, 1, 1]
```

#### Gotchas & Failure Modes
- **Immutable/Fixed-Size Lists**: Attempting to sort an unmodifiable list (e.g., created via `List.of()`, `List.copyOf()`, or `Collections.unmodifiableList()`) throws `UnsupportedOperationException` at runtime. Note that `Arrays.asList()` returns a fixed-size but mutable list, so sorting it is allowed, but it directly mutates the underlying array.

### Sort by multiple criteria

Sorting objects by a chain of keys using thenComparing.

#### Enriched Explanation
Sorting by multiple criteria involves ordering objects by a primary key, and then resolving ties using secondary, tertiary keys, etc. This is easily achieved in Java 8+ by chaining `Comparator` instances using static methods like `Comparator.comparing` and default methods like `thenComparing`.

#### Code Example
```java
List<Employee> list = getEmployees();
// Sort by department, then by salary (ascending)
list.sort(Comparator.comparing(Employee::getDepartment)
                    .thenComparingDouble(Employee::getSalary));
```

#### Gotchas & Failure Modes
- **Type Inference Issues**: Sometimes the compiler fails to infer types when chaining comparator methods if the types are not explicitly declared or if method references are overloaded. Providing explicit types in generic arguments (e.g., `Comparator.<Employee, String>comparing(...)`) resolves this.
- **NPE on Chained Methods**: If any of the intermediate key extractors return `null`, the chained comparator will throw a `NullPointerException`.

### Comparator.comparing

Static helper method to create a Comparator from a key extractor function.

#### Enriched Explanation
`Comparator.comparing` is a static factory method introduced in Java 8. It takes a key extractor function and returns a `Comparator` that compares objects based on that extracted key. Overloaded versions allow specifying a custom comparator for the extracted key. There are also primitive-specialized versions: `comparingInt`, `comparingLong`, and `comparingDouble` to avoid autoboxing overhead.

#### Code Example
```java
// Avoids boxing from double to Double:
Comparator<Employee> salaryComp = Comparator.comparingDouble(Employee::getSalary);
```

#### Gotchas & Failure Modes
- **Null Values**: If the key extractor function returns `null`, calling `compare` will result in a `NullPointerException`. To handle null keys, wrap the extractor or the key comparator with `Comparator.nullsFirst` or `Comparator.nullsLast`.

### thenComparing

Default method on Comparator to chain a secondary comparison criteria.

#### Enriched Explanation
`thenComparing` is a default method in the `Comparator` interface. It returns a lexicographic-order comparator with another comparator. If the first comparator considers two elements equal (returns 0), the second comparator is invoked to break the tie.

#### Code Example
```java
Comparator<Employee> comp = Comparator.comparing(Employee::getLastName)
                                      .thenComparing(Employee::getFirstName);
```

#### Gotchas & Failure Modes
- **Performance**: Chaining too many object-based `thenComparing` calls can cause excessive object allocation (lambda instances) and method invocation overhead. For high-performance sorting, consider custom primitive comparison in a single block rather than chaining multiple functional extractors.

## Case Study: Sorting a List of Employees by Department then Salary

In real-world applications, sorting records by multiple criteria is extremely common. Below is a complete case study showing how to sort a list of `Employee` objects first by department (lexicographically) and then by salary (descending) in case of department ties.

### Step 1: Define the Employee Class
```java
public class Employee {
    private final String name;
    private final String department;
    private final double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("%s (%s: $%.2f)", name, department, salary);
    }
}
```

### Step 2: Sorting Logic (Java 8 Chaining)
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "HR", 50000));
        employees.add(new Employee("Bob", "IT", 80000));
        employees.add(new Employee("Charlie", "IT", 90000));
        employees.add(new Employee("David", "HR", 60000));

        // Comparator chaining: 
        // 1. Sort by department ascending (natural order of String)
        // 2. Sort by salary descending (using reversed() on double comparing)
        Comparator<Employee> deptThenSalaryComp = Comparator
            .comparing(Employee::getDepartment)
            .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed());

        employees.sort(deptThenSalaryComp);

        // Output:
        // David (HR: $60000.00)
        // Alice (HR: $50000.00)
        // Charlie (IT: $90000.00)
        // Bob (IT: $80000.00)
        employees.forEach(System.out::println);
    }
}
```

## Common Mistakes

Here are critical traps and errors developers make with Comparable and Comparator:

### 1. Subtraction Overflow Bug (Integer.compare vs Subtraction)
Using subtraction to implement comparison is a dangerous anti-pattern:
```java
// BUGGY: Do not do this!
public int compareTo(Product other) {
    return this.id - other.id; 
}
```
If `this.id = Integer.MIN_VALUE` (-2147483648) and `other.id = 1`, `-2147483648 - 1` overflows to `2147483647` (a positive number). Java will incorrectly treat `this` as greater than `other`.
**Fix:** Always use primitive helper methods:
```java
public int compareTo(Product other) {
    return Integer.compare(this.id, other.id);
}
```

### 2. Sorting Immutable Collections
`Collections.sort()` and `List.sort()` mutate the collection in place. If the collection is immutable, they throw a runtime exception.
```java
List<Integer> list = List.of(3, 1, 2); // Immutable
list.sort(Comparator.naturalOrder()); // Throws UnsupportedOperationException!
```
**Fix:** Create a mutable copy first, or use streams:
```java
List<Integer> mutableList = new ArrayList<>(list);
mutableList.sort(Comparator.naturalOrder()); // Works!

// Or using Stream API (returns a new list):
List<Integer> sortedList = list.stream().sorted().toList();
```

### 3. Inconsistency between compareTo and equals
If `x.compareTo(y) == 0` but `x.equals(y)` returns `false`, inserting both into a `TreeSet` or `TreeMap` will treat them as duplicate elements, and only the first one will be stored.
```java
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

BigDecimal d1 = new BigDecimal("1.0");
BigDecimal d2 = new BigDecimal("1.00");

// HashSet uses equals() -> d1 and d2 are NOT equal, so size is 2
HashSet<BigDecimal> hashSet = new HashSet<>();
hashSet.add(d1);
hashSet.add(d2); // size = 2

// TreeSet uses compareTo() -> d1.compareTo(d2) is 0, so size is 1
TreeSet<BigDecimal> treeSet = new TreeSet<>();
treeSet.add(d1);
treeSet.add(d2); // size = 1 (d2 is rejected as a duplicate!)
```

### 4. NullPointerException with default comparators
Passing lists with `null` elements to standard sorting functions results in `NullPointerException`:
```java
List<String> names = Arrays.asList("Alice", null, "Bob");
Collections.sort(names); // Throws NullPointerException!
```
**Fix:** Wrap the comparison with `nullsFirst` or `nullsLast`:
```java
names.sort(Comparator.nullsFirst(Comparator.naturalOrder())); // [null, Alice, Bob]
```


## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
