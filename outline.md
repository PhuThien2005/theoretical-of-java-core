Java Core is usually the foundation of Java. It covers everything from basic syntax to OOP, Collections, Exceptions, IO, Threads, Generics, Lambdas, and more. If you are studying for an intern/fresher Java Backend role, you should understand the following parts fairly well.

1. Overview of Java

- What is Java?
- Characteristics of Java:
- Object-Oriented
- Platform Independent
- Simple
- Secure
- Robust
- Multithreaded
- Distributed
- High Performance Through JIT
- JVM, JRE, JDK
- The "Write once, run anywhere" mechanism
- The process of compiling and running a Java program:
- `.java`
- `.class`
- bytecode
- JVM executes bytecode
- JIT Compiler
- Garbage Collection
- Java SE, Java EE/Jakarta EE, Java ME
- Popular Java versions:
- Java 8
- Java 11
- Java 17
- Java 21

---

2. Basic Syntax

- Java program structure
- main function
- Comment
- Package
- Import
- Naming conventions:
- class
- method
- variable
- constant
- package
- Keywords in Java
- Variable scope
- Code block `{}`

---

3. Data Types

Primitive types

- `byte`
- `short`
- `int`
- `long`
- `float`
- `double`
- `char`
- `boolean`

Reference types

- Class
- Object
- Array
- String
- Interface
- Enum
- Wrapper class

Related concepts

- Primitive type vs Reference type
- Literal
- Type casting:
- widening casting
- narrowing casting
- Autoboxing
- Unboxing
- Wrapper classes:
- Integer
- Long
- Double
- Boolean
- Character
- ...
- `null`
- Comparing `==` and `.equals()`

---

4. Variables and Constants

- Local variable
- Instance variable
- Static variable
- Final variable
- Constant
- `var` in Java 10+
- Default values of variables
- Variables in stack and heap
- Variable lifetime

---

5. Operators

- Arithmetic operators:
- `+`
- `-`
- `*`
- `/`
- `%`
- Assignment operators:
- `=`
- `+=`
- `-=`
- `*=`
- `/=`
- `%=`
- Comparison operators:
- `==`
- `!=`
- `>`
- `<`
- `>=`
- `<=`
- Logical operators:
- `&&`
- `||`
- `!`
- Bitwise operators:
- `&`
- `|`
- `^`
- `~`
- `<<`
- `>>`
- `>>>`
- Increment/decrement operators:
- `++`
- `--`
- Ternary operator:
- condition ? valueIfTrue : valueIfFalse
- `instanceof`
- Operator precedence

---

6. Control Flow

- `if`
- `if else`
- `else if`
- nested if
- `switch`
- switch expression Java 12+
- for
- enhanced for
- while
- `do while`
- `break`
- `continue`
- labeled break
- labeled continue
- `return`

---

7. Arrays

- Array declaration
- Array initialization
- One-dimensional array
- Two-dimensional array
- Multidimensional array
- Traversing arrays
- Accessing elements
- `length`
- Array of objects
- Copy array:
- loop
- `System.arraycopy`
- `Arrays.copyOf`
- Sorting arrays:
- `Arrays.sort`
- Searching in arrays
- Comparing arrays:
- `Arrays.equals`
- `Arrays.deepEquals`

---

8. String

- String
- String literal
- String pool
- Immutable String
- Comparing String:
- `==`
- `.equals()`
- `.equalsIgnoreCase()`
- Common methods:
- `length`
- `charAt`
- `substring`
- `contains`
- `indexOf`
- `lastIndexOf`
- `replace`
- `trim`
- `strip`
- `toLowerCase`
- `toUpperCase`
- `split`
- `startsWith`
- `endsWith`
- StringBuilder
- StringBuffer
- String concatenation
- Format String:
- `String.format`
- `printf`
- Text Block Java 15+

---

9. Object-Oriented Programming OOP

This is an extremely important part.

Class and Object

- Class
- Object
- Attribute / Field
- Method
- Constructor
- Default constructor
- Parameterized constructor
- Constructor overloading
- `this`
- Object reference
- Anonymous object

4 OOP principles

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

Encapsulation

- Access modifier:
- private
- default/package-private
- protected
- public
- Getter
- Setter
- Data hiding

Inheritance

- `extends`
- Superclass
- Subclass
- Single inheritance
- Multilevel inheritance
- Hierarchical inheritance
- Java does not support multiple inheritance through classes
- `super`
- Constructor in inheritance
- Method overriding
- Field hiding

Polymorphism

- Compile-time polymorphism
- method overloading
- Runtime polymorphism
- method overriding
- Upcasting
- Downcasting
- Dynamic method dispatch
- `instanceof`

Abstraction

- Abstract class
- Abstract method
- Interface
- Difference between abstract class and interface
- Default method in interface
- Static method in interface
- Private method in interface Java 9+

---

10. Modifiers in Java

- Access modifier:
- public
- protected
- default
- private
- Non-access modifier:
- static
- final
- abstract
- synchronized
- volatile
- transient
- native
- strictfp

static

- Static variable
- Static method
- Static block
- Static nested class
- Static import

final

- Final variable
- Final method
- Final class
- Final parameter
- Blank final variable

---

11. Package and Access Control

- What is a package?
- Create package
- Import package
- import static
- Default package
- Package naming convention
- Access between packages
- Classpath
- Basic module path

---

12. Exception Handling

- What is an exception?
- Error vs Exception
- Checked exception
- Unchecked exception
- Runtime exception
- `try`
- `catch`
- multiple catch
- `finally`
- `throw`
- `throws`
- try-with-resources
- Custom exception
- Exception propagation
- Common exceptions:
- `NullPointerException`
- `ArrayIndexOutOfBoundsException`
- `StringIndexOutOfBoundsException`
- `ClassCastException`
- `NumberFormatException`
- `ArithmeticException`
- `IllegalArgumentException`
- `IllegalStateException`
- `IOException`
- `FileNotFoundException`
- `SQLException`
- Best practices when handling exceptions

---

13. Java Memory Management

- Stack
- Heap
- Method Area / Metaspace
- PC Register
- Native Method Stack
- Object lifecycle
- Reference variable
- Strong reference
- Weak reference
- Soft reference
- Phantom reference
- Garbage Collection
- Conditions for an object to be GC'd
- `System.gc()`
- Finalization, `finalize()` deprecated
- Memory leak in Java
- `OutOfMemoryError`
- `StackOverflowError`

---

14. Object class

All classes in Java inherit from Object.

Important methods:

- `toString()`
- `equals()`
- `hashCode()`
- `getClass()`
- `clone()`
- `finalize()` deprecated
- `wait()`
- `notify()`
- `notifyAll()`

Parts to understand deeply:

- Why overriding `equals()` means you should also override `hashCode()`
- Contract of `equals()`
- Contract of `hashCode()`
- Comparing objects by reference and by value

---

15. Inner Class and Nested Class

- Nested class
- Static nested class
- Inner class
- Local inner class
- Anonymous inner class
- Access variables outside the class
- Use case of inner class
- Anonymous class in event handler, thread, comparator

---

16. Enum

- What is an enum?
- Declare enum
- Enum constructor
- Enum field
- Enum method
- `values()`
- `valueOf()`
- `ordinal()`
- `name()`
- Enum in switch
- Enum implements interface
- Enum Singleton pattern

---

17. Annotation

- What is an annotation?
- Built-in annotations:
- `@Override`
- `@Deprecated`
- `@SuppressWarnings`
- `@FunctionalInterface`
- `@SafeVarargs`
- Meta-annotations:
- `@Target`
- `@Retention`
- `@Documented`
- `@Inherited`
- `@Repeatable`
- Custom annotation
- Runtime annotation
- Basic annotation processing

---

18. Generics

- Generic class
- Generic method
- Generic interface
- Type parameter
- Multiple type parameters
- Bounded type parameter:
- `<T extends Number>`
- Wildcard:
- `<?>`
- `<? extends T>`
- `<? super T>`
- PECS:
- Producer Extends
- Consumer Super
- Generic with Collection
- Type erasure
- Raw type
- Generic limitations

---

19. Collections Framework

Extremely important when learning Java Core.

Overview

- What is the Collection Framework?
- Iterable
- Collection
- List
- Set
- Queue
- Deque
- Map

List

- ArrayList
- LinkedList
- Vector
- Stack
- Comparing ArrayList and LinkedList
- When to use List?

Set

- HashSet
- LinkedHashSet
- TreeSet
- SortedSet
- NavigableSet
- When to use Set?
- Duplicate removal mechanism
- Role of `equals()` and `hashCode()`

Queue / Deque

- Queue
- Deque
- PriorityQueue
- ArrayDeque
- LinkedList as Queue
- FIFO
- LIFO
- Priority queue

Map

- HashMap
- LinkedHashMap
- TreeMap
- Hashtable
- ConcurrentHashMap
- WeakHashMap
- IdentityHashMap
- SortedMap
- NavigableMap
- When to use Map?

Iterator

- Iterator
- ListIterator
- Fail-fast iterator
- Fail-safe iterator
- ConcurrentModificationException

Collections utility

- `Collections.sort`
- `Collections.reverse`
- `Collections.shuffle`
- `Collections.max`
- `Collections.min`
- `Collections.unmodifiableList`
- `Collections.synchronizedList`

Arrays utility

- `Arrays.sort`
- `Arrays.binarySearch`
- `Arrays.asList`
- `Arrays.copyOf`
- `Arrays.equals`
- `Arrays.deepEquals`

---

20. Comparable and Comparator

- Comparable
- `compareTo`
- Comparator
- `compare`
- Natural ordering
- Custom ordering
- Sort List object
- Sort by multiple criteria
- `Comparator.comparing`
- `thenComparing`
- Reverse order
- Null handling:
- `nullsFirst`
- `nullsLast`

---

21. Lambda Expression

Java 8 onward is very important.

- What is a lambda?
- Lambda syntax
- Functional interface
- `@FunctionalInterface`
- Method reference:
- static method reference
- instance method reference
- constructor reference
- Variable capture
- Effectively final
- Lambda with Collection
- Lambda with Thread
- Lambda with Comparator

---

22. Functional Interface

Commonly used interfaces:

- `Predicate<T>`
- `Function<T, R>`
- `Consumer<T>`
- `Supplier<T>`
- `UnaryOperator<T>`
- `BinaryOperator<T>`
- `BiPredicate<T, U>`
- `BiFunction<T, U, R>`
- `BiConsumer<T, U>`

Should understand:

- What is the input?
- What is the output?
- When to use which interface?

---

23. Stream API

Very important in modern Java.

- What is Stream?
- Stream vs Collection
- Create Stream:
- from List
- from Array
- from Map
- `Stream.of`
- `IntStream`
- `LongStream`
- `DoubleStream`
- Intermediate operations:
- `filter`
- `map`
- `flatMap`
- `distinct`
- `sorted`
- `peek`
- `limit`
- `skip`
- Terminal operations:
- `forEach`
- `collect`
- `toList`
- `count`
- `min`
- `max`
- `reduce`
- `anyMatch`
- `allMatch`
- `noneMatch`
- `findFirst`
- `findAny`
- Lazy evaluation
- Short-circuiting
- Parallel stream
- Collectors:
- `toList`
- `toSet`
- `toMap`
- `joining`
- `groupingBy`
- `partitioningBy`
- `counting`
- `summarizingInt`
- `mapping`
- `reducing`

---

24. Optional

- What is `Optional<T>`?
- Avoid NullPointerException
- `Optional.of`
- `Optional.ofNullable`
- `Optional.empty`
- `isPresent`
- `ifPresent`
- `orElse`
- `orElseGet`
- `orElseThrow`
- `map`
- `flatMap`
- `filter`
- Do not overuse Optional
- Optional in return type

---

25. Date and Time API

Old API

- Date
- Calendar
- SimpleDateFormat

New API Java 8

- LocalDate
- LocalTime
- LocalDateTime
- ZonedDateTime
- OffsetDateTime
- Instant
- Duration
- Period
- DateTimeFormatter
- ZoneId
- Parse date/time
- Format date/time
- Compare date/time
- Add/subtract date/time
- Timezone

---

26. IO in Java

File

- File
- Create file
- Delete file
- Check existence
- Read file metadata
- Create directory

Byte Stream

- InputStream
- OutputStream
- FileInputStream
- FileOutputStream
- BufferedInputStream
- BufferedOutputStream

Character Stream

- Reader
- Writer
- FileReader
- FileWriter
- BufferedReader
- BufferedWriter

Object Stream

- ObjectInputStream
- ObjectOutputStream
- Serialization
- Deserialization
- Serializable
- serialVersionUID
- transient

Console IO

- Scanner
- BufferedReader
- System.in
- System.out
- System.err

---

27. NIO / NIO.2

- Path
- Paths
- Files
- StandardOpenOption
- Read/write file using Files
- Walk file tree
- Copy/move/delete file
- Channel
- Buffer
- ByteBuffer
- FileChannel
- Basic Selector
- Basic Asynchronous IO

---

28. Multithreading

Very commonly asked in interviews.

- Process vs Thread
- Create thread using:
- extends Thread
- implements Runnable
- implements Callable
- ExecutorService
- Lifecycle of Thread:
- New
- Runnable
- Running
- Blocked
- Waiting
- Timed Waiting
- Terminated
- `start()` vs `run()`
- sleep
- join
- yield
- interrupt
- Daemon thread
- User thread
- Thread priority
- Race condition
- Critical section
- Thread safety
- Immutable object
- Atomic operation

---

29. Synchronization and Concurrency

- synchronized method
- synchronized block
- Object lock
- Class lock
- Monitor
- wait
- notify
- notifyAll
- Deadlock
- Livelock
- Starvation
- Volatile
- Atomic classes:
- AtomicInteger
- AtomicLong
- AtomicBoolean
- AtomicReference
- Lock API:
- Lock
- ReentrantLock
- ReadWriteLock
- StampedLock
- Semaphore
- CountDownLatch
- CyclicBarrier
- Phaser
- BlockingQueue
- Concurrent collections:
- ConcurrentHashMap
- CopyOnWriteArrayList
- ConcurrentLinkedQueue
- Executor Framework:
- Executor
- ExecutorService
- ScheduledExecutorService
- ThreadPoolExecutor
- Executors
- Future
- Callable
- CompletableFuture
- ForkJoinPool
- Parallel Stream

---

30. Regular Expression

- What is Regex?
- Pattern
- Matcher
- matches
- find
- group
- Character classes
- Quantifiers
- Capturing group
- Non-capturing group
- Basic lookahead / lookbehind
- Validate email, phone, password
- Replace using regex
- Split using regex

---

31. Reflection

- What is Reflection?
- `Class<?>`
- Get class information
- Get field
- Get method
- Get constructor
- Invoke method using reflection
- Create object using reflection
- Access private field/method
- Annotation + reflection
- Advantages and disadvantages of reflection
- Reflection in frameworks such as Spring

---

32. ClassLoader

- Class loading process
- Bootstrap ClassLoader
- Platform/Extension ClassLoader
- Application ClassLoader
- Parent delegation model
- Dynamic class loading
- `Class.forName`
- Classpath
- Basic JAR loading

---

33. Java Module System

From Java 9 onward.

- What is a module?
- `module-info.java`
- `requires`
- `exports`
- `opens`
- Named module
- Unnamed module
- Automatic module
- Module-level encapsulation
- Module path vs Classpath

---

34. JDBC

Usually considered advanced Java Core or Java SE.

- What is JDBC?
- Driver
- DriverManager
- Connection
- Statement
- PreparedStatement
- CallableStatement
- ResultSet
- Transaction:
- commit
- rollback
- setAutoCommit
- Batch processing
- SQL Injection
- Basic Connection Pool
- DataSource
- CRUD using JDBC

---

35. Networking

- Socket programming
- TCP socket
- UDP socket
- Socket
- ServerSocket
- DatagramSocket
- InetAddress
- URL
- URI
- Basic HTTP request
- HttpURLConnection
- Java 11 HttpClient
- Client-server model

---

36. Basic Security

- Basic secure coding
- Hashing
- MessageDigest
- SHA-256
- Base64
- Basic encryption/decryption
- KeyStore
- Basic SSL/TLS
- Input validation
- Avoid SQL Injection
- Avoid insecure deserialization

---

37. Advanced JVM

This part is usually for advanced Java Core/interviews.

- JVM architecture
- Class Loader Subsystem
- Runtime Data Areas:
- Heap
- Stack
- Method Area / Metaspace
- PC Register
- Native Method Stack
- Execution Engine
- Interpreter
- JIT Compiler
- Garbage Collector
- Native Interface
- Heap generation:
- Young Generation
- Eden
- Survivor
- Old Generation
- GC algorithms:
- Serial GC
- Parallel GC
- old CMS
- G1 GC
- ZGC
- Shenandoah
- Stop-the-world
- Minor GC
- Major GC
- Full GC
- Basic JVM tuning:
- `-Xms`
- `-Xmx`
- `-XX`
- Basic profiling
- Memory dump
- Thread dump

---

38. Build, Compile, Run

- `javac`
- `java`
- `jar`
- Create JAR file
- Executable JAR
- Classpath
- Manifest file
- Basic Maven
- Basic Gradle
- Dependency management
- Standard project structure
- Basic unit test with JUnit

---

39. Some Common Utility APIs

- Math
- Random
- BigInteger
- BigDecimal
- UUID
- Objects
- Optional
- System
- Runtime
- ProcessBuilder
- Properties
- ResourceBundle
- Locale
- Currency
- Formatter
- Scanner

---

40. Modern Java Concepts To Know

Depending on how deeply you study, you can learn more:

- `var`
- Records
- Sealed class
- Pattern matching for instanceof
- Switch expression
- Text blocks
- Enhanced NullPointerException message
- Virtual Threads
- Basic Structured Concurrency
- Pattern matching for switch
- Sequenced Collections
- String templates were once preview; currently they should not be used as a stable feature

---

41. Best Practices in Java

- Name variables, functions, and classes clearly
- Code according to convention
- Do not overuse static
- Do not overuse inheritance
- Prefer composition over inheritance
- Override equals/hashCode correctly
- Use StringBuilder when concatenating strings many times
- Use BigDecimal for money
- Use try-with-resources
- Do not catch overly broad Exception if unnecessary
- Do not swallow exceptions
- Use interface type when declaring Collection:
- `List<String> list = new ArrayList<>();`
- Avoid raw type
- Avoid null when possible
- Write testable code
- Separate class/method responsibilities
- Immutability when appropriate

---

42. Basic Design Principles Often Paired With Java Core

Not purely Java Core, but worth knowing when studying Java:

- SOLID
- DRY
- KISS
- YAGNI
- Composition over inheritance
- Coupling
- Cohesion
- Basic Dependency Injection
- Defensive programming
- Basic Clean Code

---

43. Basic Design Patterns Commonly Seen in Java

Also not purely Java Core, but commonly paired with it.

- Singleton
- Factory Method
- Abstract Factory
- Builder
- Prototype
- Adapter
- Decorator
- Facade
- Proxy
- Strategy
- Observer
- Template Method
- Command
- Iterator
- State
- MVC
- DAO
- DTO
- Repository
- Service Layer

---

44. Basic Unit Testing

- JUnit
- Test case
- Assertion
- `@Test`
- `@BeforeEach`
- `@AfterEach`
- `@BeforeAll`
- `@AfterAll`
- Basic Mockito
- Mock object
- Test exception
- Test private logic indirectly
- Basic code coverage

---

45. Common Java Core Interview Questions

- How are JVM, JDK, and JRE different?
- Does Java pass references?
- What is the difference between `==` and `.equals()`?
- Why is String immutable?
- How are String, StringBuilder, and StringBuffer different?
- How does HashMap work?
- What improvements did HashMap have in Java 8?
- How are ArrayList and LinkedList different?
- How does HashSet remove duplicates?
- How are final, finally, and finalize different?
- How are checked and unchecked exceptions different?
- How are abstract class and interface different?
- How are overload and override different?
- Can static methods be overridden?
- Are constructors inherited?
- How are `this` and `super` different?
- How are Comparable and Comparator different?
- How are fail-fast and fail-safe iterators different?
- How are volatile and synchronized different?
- What is deadlock?
- How are Thread `start()` and `run()` different?
- How are `sleep()` and `wait()` different?
- How are `notify()` and `notifyAll()` different?
- Is Stream API lazy?
- How are `map` and `flatMap` different?
- How are `orElse` and `orElseGet` different?
- How are HashMap, Hashtable, and ConcurrentHashMap different?
- Why must overriding `equals()` also override `hashCode()`?
- How does Garbage Collection work?
- How are Stack and Heap different?

---

Suggested reasonable study order

If studying for Java Backend, I think you should go in this order:

1. Basic syntax, data types, operators, control flow
2. Learn OOP very solidly
3. String, Array, Exception
4. Collection Framework
5. Generics
6. Comparable, Comparator
7. Lambda, Functional Interface, Stream API
8. Optional, Date Time API
9. IO/NIO
10. Basic Multithreading + Concurrency
11. JDBC
12. JVM, Memory, GC
13. Basic design principles + patterns
14. Maven/Gradle + JUnit
15. Then move to Spring Boot

In short, the most important Java Core topics for intern/fresher are:

OOP, String, Exception, Collection, Generics, Lambda/Stream, basic Multithreading, JDBC, JVM Memory, equals/hashCode, HashMap.
