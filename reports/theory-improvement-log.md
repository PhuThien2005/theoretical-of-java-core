# 📝 Theory Improvement Log

This log tracks the improvements made to the theory files across the 45 Java Core topics to enhance explanation depth and align them with Self-Check questions.

## no42_design_principles — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion).
- `theory/01-solid-concepts.md`: Appended 5 new Why sections: "Why Single Responsibility Promotes High Cohesion", "Why Open/Closed Principle Protects Existing Code", "Why Liskov Substitution Principle Enforces Behavioral Contracts", "Why Interface Segregation Prevents Fat Interface Coupling", and "Why Dependency Inversion Decouples Modules" (each including JVM/language mechanism details, ASCII mental models, runnable code examples with output, and cause-effect chains).
- `terms/01-key-terms.md`: Completely rewrote all key terms (SOLID, Single Responsibility, Open Closed, Liskov Substitution, Interface Segregation, Dependency Inversion, DRY, KISS, YAGNI, coupling, cohesion, dependency injection) with detailed explanations, why they matter, common confusions, and code examples.
- `anki/basic.tsv`: Replaced template placeholders with 16 high-quality concept-specific basic cards.
- `anki/basic-extra.tsv`: Replaced template placeholders with 12 basic-extra cards containing detailed explanations.
- `anki/cloze.tsv`: Replaced template placeholders with 12 cloze deletion cards.
- `anki/code-question.tsv`: Replaced template placeholders with 12 code challenges and answers.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, card quality audit warnings.
- After: 5/5 PASS, 0 card quality audit warnings, all audit checks cleared.

---

## no41_best_practices — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (descriptive naming, exception swallowing, checked vs unchecked custom exceptions, constant compile-time inlining, and early return guard clauses).
- `theory/01-name-variables-functions-and-classes-clearly-concepts.md`: Added three Why sections: "Why Descriptive Naming Matters" (explaining intent-revealing code, JVM local variable name discarding, cognitive overload prevention, cognitive load mental model, NamingDemo code, and name discarding cause-effect chain), "Why Constants Prevent Magic Numbers" (explaining magic numbers anti-pattern, compile-time constant inlining, getstatic avoidance, constant inlining mental model, ConstantsDemo code, and compilation dependency cause-effect chain), and "Why Guard Clauses Simplify Control Flow" (explaining return-early fail-fast pattern, nested conditional deep tracking, flat CFG JIT optimizations, linear branch mental model, GuardClauseDemo code, and JIT prediction cause-effect chain).
- `theory/02-do-not-swallow-exceptions-concepts.md`: Added two Why sections: "Why Exception Swallowing Is Dangerous" (explaining swallowed exceptions, Exception Table bytecode search, stack-unwinding mechanism, trace loss mental model, ExceptionSwallowingDemo code, and trace loss cause-effect chain) and "Why Custom Exceptions Group by Recovery Rationale" (explaining recoverable checked exceptions, compile-time throws constraints, unchecked RuntimeExceptions, thread abort fast failure, recovery mental model, RecoveryExceptionDemo code, and separation of concerns cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote all 11 key terms (descriptive naming, exception swallowing, custom exceptions, magic numbers, guard clauses, composition, immutability, raw type, try-with-resources, testable code, responsibility) to replace template placeholders with detailed descriptions, importance, gotchas, and small code examples.
- `anki/basic.tsv`: Replaced boilerplate with 17 high-quality concept-specific cards covering descriptive naming, constants compile-time inlining, guard clauses JIT flow, exception swallowing stack trace loss, checked vs unchecked exceptions, and other core best practices.
- `anki/basic-extra.tsv`: Replaced boilerplate with 12 deep conceptual basic-extra cards with detailed explanations (each Extra field ≥ 18 words), gotchas, and memory hooks.
- `anki/cloze.tsv`: Replaced boilerplate with 12 targeted cloze cards testing cognitive overload, constant inlining, flat control flow, diagnostic context, Checked vs Runtime exception types, and other core concepts.
- `anki/code-question.tsv`: Replaced boilerplate with 12 code challenges testing PascalCase/UPPER_SNAKE_CASE naming violations, constant compiler inlining, guard clauses, exception swallowing, checked custom exceptions, composition stack wrapping, equals/hashCode contract, StringBuilder loop concatenation, BigDecimal String constructor, try-with-resources cleanup, and raw type warnings.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, 45 card quality audit warnings.
- After: 5/5 PASS, 0 card quality warnings, all audit checks cleared.

---

## no45_interview_questions — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (JDK/JRE/JVM differences, HashSet backing HashMap uniqueness, Comparable vs Comparator sorting design, map vs flatMap transformations, and compile-time generics vs type erasure).
- `theory/01-how-are-jvm-jdk-and-jre-different-concepts.md`: Added "## Why JDK, JRE, and JVM Differ" explaining development vs runtime roles, JIT compiler execution, JRE wrappers, JVM virtual data areas, nested layers mental model, EnvironmentTest class code, and compilation-to-execution cause-effect chain.
- `theory/02-how-does-hashset-remove-duplicates-concepts.md`: Added "## Why HashSet Leverages HashMap to Remove Duplicates" explaining backing HashMap, PRESENT dummy object value, Map key uniqueness, facade layer delegation mental model, HashSetMechanismDemo code, and add-to-put delegation cause-effect chain.
- `theory/03-how-are-comparable-and-comparator-different-concepts.md`: Added "## Why Comparable and Comparator Differ in Sorting Design" explaining natural ordering internal vs custom external sorting rules, single responsibility separation, sorting views mental model, SortDemo Comparable/Comparator code, and collection sort check-run cause-effect chain.
- `theory/04-how-are-map-and-flatmap-different-concepts.md`: Added two sections: "## Why map and flatMap Stream Operations Differ" (explaining 1-to-1 vs 1-to-many transformations, nested list flattening, spliterator sub-stream traversal, mapping mental model, StreamMappingDemo code, and spliterator traversal cause-effect chain) and "## Why Generic Compile-Time Verification Differs from Runtime" (explaining Type Erasure, raw types compatibility, Heap Pollution, implicit casts, erasure mental model, GenericsErasureDemo code, and implicit checkcast validation cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote all 9 key terms (JDK, JRE, JVM, HashSet, Comparable, Comparator, Map, FlatMap, generics) to replace template placeholders with detailed descriptions, importance, gotchas, and small code examples.
- `anki/basic.tsv`: Replaced boilerplate with 16 high-quality concept-specific cards covering JDK/JRE/JVM, HashSet backing HashMap, Comparable/Comparator, map/flatMap, and generics erasure.
- `anki/basic-extra.tsv`: Replaced boilerplate with 12 deep conceptual basic-extra cards with detailed explanations, gotchas, and memory hooks.
- `anki/cloze.tsv`: Replaced boilerplate with 12 targeted cloze cards testing JDK compiler, JVM execution, pass-by-value, equals/==, HashSet/HashMap, PRESENT value, Comparable/Comparator, map/flatMap, type erasure, and Heap Pollution.
- `anki/code-question.tsv`: Replaced boilerplate with 12 code challenges testing String Pool memory identity, immutability, pass-by-value mutation/reassignment, HashSet addition, Comparable sorting, Comparator lambda, map/flatMap counting, generic raw types ClassCastException, and stream lazy execution.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, 123 card quality audit warnings.
- After: 5/5 PASS, 0 card quality warnings, all audit checks cleared.

## no43_design_patterns — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (Singleton volatile DCL, Bill Pugh JVM class loading, Observer decoupling, Factory Method instantiation dynamic dispatch, and Builder telescoping constructor replacement).
- `theory/01-singleton-concepts.md`: Added 4 Why sections: "Why Double-Checked Locking Ensures Thread-Safe Singleton" (explaining JMM volatile reordering, partially initialized objects, DCL check logic, reordering mental model, DCL code, and cause-effect chain), "Why Bill Pugh Singleton Achieves Thread-Safe Lazy Initialization" (explaining static helper class, lazy JVM class loading mechanics, class loader synchronization, helper loading mental model, Bill Pugh code, and cause-effect chain), "Why Factory Method Defers Object Instantiation" (explaining direct new coupling, runtime subclass creator polymorphism, dynamic vtable method dispatch, creator-product mental model, Factory Method code, and cause-effect chain), and "Why the Builder Pattern Replaces Telescoping Constructors" (explaining constructor overload bloat, parameter swaps, object immutability, build() verification gate, builder pipeline mental model, Builder code, and cause-effect chain).
- `theory/02-observer-concepts.md`: Added Why section: "Why the Observer Pattern Decouples Subjects from Observers" (explaining interface programming, runtime collection iteration, dynamic vtable method dispatch, decoupled subject-observer mental model, Observer code, and cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote all 10 key terms (Singleton, Double-Checked Locking, Bill Pugh Singleton, Factory Method, Builder, Adapter, Decorator, Strategy, Observer, Repository) to replace template placeholders with detailed descriptions, importance, gotchas, and small code examples.
- `anki/basic.tsv`: Replaced boilerplate with 16 high-quality concept-specific cards covering volatile DCL, class loader Bill Pugh, Observer decoupling, Factory Method dynamic dispatch, Builder telescoping, and other structural/behavioral patterns.
- `anki/basic-extra.tsv`: Replaced boilerplate with 12 deep conceptual basic-extra cards with detailed explanations, gotchas, and memory hooks.
- `anki/cloze.tsv`: Replaced boilerplate with 12 targeted cloze cards testing DCL volatile, Bill Pugh static helper, interface decoupling, Factory Method override, Builder telescoping, and NIO/Repository.
- `anki/code-question.tsv`: Replaced boilerplate with 12 code challenges on Singleton thread-safety, Bill Pugh initialization, Factory Method polymorphism, Builder fluent instantiation, Observer dynamic dispatch, and other GoF patterns.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, 83 card quality audit warnings.
- After: 5/5 PASS, 0 card quality warnings, all audit checks cleared.

## no29_synchronization_concurrency — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 6 conceptual why-questions linking to theory anchor sections (synchronized monitors/bytecode, deadlock resource acquisition sequence, lock-free atomic variables CAS, CyclicBarrier vs CountDownLatch await/reset, ExecutorService OS allocation/queue limit safety, and ForkJoinPool LIFO/FIFO work-stealing).
- `theory/01-synchronized-method-concepts.md`: Added Why section: "## Why synchronized Blocks Prevent Race Conditions" (explaining object monitors, `monitorenter`/`monitorexit` compiler-generated instructions, Entry Set blocked state, monitor lock queue mental model, lock demo code, and cause-effect chain).
- `theory/02-deadlock-concepts.md`: Added Why section: "## Why Deadlocks Occur and How to Avoid Them" (explaining Coffman conditions, nested lock resource acquisition cycles, lock ordering, tryLock timeouts, deadlock cycle mental model, lock avoidance demo code, and cause-effect chain).
- `theory/03-atomicreference-concepts.md`: Added Why section: "## Why Atomic Variables Avoid Lock-Based Synchronization" (explaining CPU-level instructions like `CMPXCHG`, Compare-And-Swap expected/new memory values, lock-free CAS loop spin, CAS spin loop mental model, CAS counter demo code, and cause-effect chain).
- `theory/04-cyclicbarrier-concepts.md`: Added Why section: "## Why CyclicBarrier and CountDownLatch Differ" (explaining Latch one-shot gate vs Barrier reusability, CyclicBarrier Condition/lock await and reset, Latch vs Barrier mental model, barrier demo code, and cause-effect chain).
- `theory/05-executor-concepts.md`: Added Why section: "## Why ExecutorService and Thread Pools Are Required" (explaining manual thread OS resource limits, 1MB stack memory footprint, bounded task queue JVM protection, thread pool bounded queue mental model, Executor service configuration demo code, and cause-effect chain).
- `theory/06-forkjoinpool-concepts.md`: Added Why section: "## Why ForkJoinPool Uses Work-Stealing" (explaining divide-and-conquer optimization, private deques, LIFO push/pop head, FIFO task steal tail, work-stealing deques mental model, sum demo code, and cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote the definitions for synchronized, deadlock, atomic variable, CAS, CyclicBarrier, CountDownLatch, Executor, and ForkJoinPool, replacing all template placeholders with detailed, accurate explanations, gotchas, and small code examples.
- `anki/basic.tsv`: Replaced template cards with 16 high-quality concept-specific cards covering monitor bytecode, Coffman deadlock conditions, CAS hardware instructions, CyclicBarrier await mechanics, thread footprint, and work-stealing.
- `anki/basic-extra.tsv`: Replaced template cards with 12 deep basic-extra cards with substantive explanations of monitor queues, ABA, optimistic reading, phasers, and pool rejections.
- `anki/cloze.tsv`: Replaced template cards with 12 precise cloze cards testing JVM lock internals, wait state exception, deadlock conditions, and work-stealing LIFO/FIFO deque traversal.
- `anki/code-question.tsv`: Replaced template cards with 12 code challenges on IllegalMonitorStateException, CAS output, lock ordering deadlock fix, StampedLock non-reentrancy, volatile count race, and pool capacity limits.

### Self-Check Coverage
- Before: 0/6 Self-Check questions, 0 Why sections, placeholder key terms, card quality audit warnings.
- After: 6/6 PASS, 0 card quality audit warnings, all audit checks cleared.

## no28_multithreading — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (OS process vs JVM thread resource allocation, Runnable/Callable task decoupling, Thread start() vs run() call stacks, thread join() wait/notify signaling, and race conditions/data visibility).
- `theory/01-process-vs-thread-concepts.md`: Added Why section: "Why Processes Differ from Threads" (explaining process memory isolation, JVM thread-private Stack and PC registers, shared Heap and Metaspace, process vs thread memory sharing mental model, shared memory demo code, and cause-effect chain).
- `theory/02-runnable-concepts.md`: Added Why sections: "Why Runnable/Callable Is Preferred Over Extending Thread" (explaining Java's single inheritance constraint, SRP-compliant task decoupling, Thread pools submission, composition mental model, ExecutorService demo code, and cause-effect chain) and "Why start() Is Required to Spawn a Thread" (explaining run() synchronous method invocation vs start() OS-level thread spawning sequence, native start0() call, asynchronous execution stack mental model, start-vs-run demo code, and cause-effect chain).
- `theory/03-join-concepts.md`: Added Why section: "Why join() Blocks the Calling Thread" (explaining wait/notifyAll primitives, isAlive() evaluation loop, target thread termination JVM native notification, wait-notify sequence diagram mental model, worker join coordination demo code, and cause-effect chain).
- `theory/04-thread-safety-concepts.md`: Added Why section: "Why Race Conditions and Data Visibility Issues Occur" (explaining interleaved non-atomic read-modify-write operations, CPU caching/instruction reordering invisibility, class invariants thread safety, CPU cache memory gap mental model, counter race demo code, and cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote the definitions for Process, Thread, Runnable, Callable, Start vs Run, Join, Thread-Safety, Race Condition, and Data Visibility, replacing template placeholder text with detailed, accurate descriptions, gotchas, and code examples.
- `anki/basic.tsv`: Replaced template cards with 18 high-quality concept-specific cards covering process vs thread resource allocation, interface-based task decoupling, start() vs run() call stacks, join() signaling, and race conditions/visibility.
- `anki/basic-extra.tsv`: Replaced template cards with 12 deep basic-extra cards with substantive explanations.
- `anki/cloze.tsv`: Replaced template cards with 12 precise cloze cards testing JVM thread internals, states, and scheduling primitives.
- `anki/code-question.tsv`: Replaced template cards with 12 code challenges on synchronous/asynchronous execution, IllegalThreadStateException, thread interruption, mutability leakage fix, and thread-safe CAS counters.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, card quality audit warnings.
- After: 5/5 PASS, 0 card quality audit warnings, all audit checks cleared.

## no37_jvm_advanced — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (ClassLoader subsystem phases, JIT and Interpreter tiered compilation, Survivor copy-and-evacuate mechanism, Shenandoah concurrent compaction, JVM flag classifications).
- `theory/01-jvm-architecture-concepts.md`: Added Why section: "Why Class Loading Has Three Distinct Phases" (explaining Loading, Linking, and Initialization phases, class file verification safety, static variables prep/init, classloader mental model, demo code, and cause-effect chain).
- `theory/02-execution-engine-concepts.md`: Added Why section: "Why JIT Compilation and Interpretation Are Combined" (explaining JIT/interpreter cooperation, C1 vs C2 compilers, compilation thresholds, invocation/back-edge counters, tiered compilation mental model, JIT profiling simulation, and cause-effect chain).
- `theory/03-survivor-concepts.md`: Added Why section: "Why Survivor Spaces Prevent Heap Fragmentation" (explaining generational spaces, Eden allocation, S0/S1 copy-and-evacuate garbage collection mechanism, heap aging, copy process mental model, garbage simulation code, and cause-effect chain).
- `theory/04-shenandoah-concepts.md`: Added Why section: "Why Shenandoah GC Achieves Ultra-Low Pause Times" (explaining concurrent compaction, Stop-the-world avoidance, Brooks Pointers/load barriers reference redirection mechanism, CAS update process, concurrent compaction mental model, concurrent GC demo code, and cause-effect chain).
- `theory/05-xx-concepts.md`: Added Why section: "Why JVM Flag Classifications Exist" (explaining Standard, Non-Standard, and Experimental/Developer flags, heap boundary tuning, options spectrum mental model, heap tuning inspection code, and cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote the definitions for JVM, ClassLoader, JIT compiler, Eden, Survivor spaces, Shenandoah GC, Brooks Pointer, and JVM flags, replacing template placeholder text with detailed, accurate descriptions, gotchas, and code examples.
- `anki/basic.tsv`: Replaced template cards with 16 high-quality concept-specific cards covering class loader phases, JIT C1/C2 tiered compilers, generational GC survivor copying, Shenandoah low-pause concurrent compaction, and JVM flag classifications.
- `anki/basic-extra.tsv`: Replaced template cards with 12 deep basic-extra cards with substantive explanations.
- `anki/cloze.tsv`: Replaced template cards with 12 precise cloze cards testing JVM internals and configuration parameters.
- `anki/code-question.tsv`: Replaced template cards with 12 code challenges on static initialization, heap querying, CLI flags configuration, and GC tuning parameters.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, placeholder key terms, 150 card quality audit warnings.
- After: 5/5 PASS, 0 card quality audit warnings, all audit checks cleared.

## no18_generics — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (Type Erasure backwards compatibility, Generic invariance & PECS, Raw types dangers, Primitive generic limitations, Generic arrays & instanceof ban).
- `theory/02-topic-concepts.md`: Added 2 Why sections: "Why Java Uses Type Erasure" (explaining backwards compatibility with legacy pre-generic code, compile-time checks vs runtime raw bytecode, compiler implicit casts, type erasure mental model, class check code, and cause-effect chain) and "Why Generics Are Invariant and How PECS Solves It" (explaining type pollution hazards of covariance, covariance/contravariance separation, read-only vs write-only bounds, PECS mental model, producer/consumer code, and cause-effect chain).
- `theory/03-raw-type-concepts.md`: Added 3 Why sections: "Why Raw Types Exist and Their Dangers" (explaining pre-generic interoperability, unchecked warnings, runtime ClassCastException, raw type mental model, ClassCastException code, and cause-effect chain), "Why Generics Do Not Support Primitives" (explaining erasure to Object reference vs primitive binaries, JVM reference slots, autoboxing/unboxing wrapper overhead, JVM memory layout mental model, boxing code, and cause-effect chain), and "Why Generic Array Creation and Runtime Type Checks Are Forbidden" (explaining reified arrays vs erased generics, ArrayStoreException enforcement failures, instanceof wildcard checks, reification vs erasure mental model, instanceof code, and cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote all 6 key terms definitions (`type parameter`, `bounded type parameter`, `wildcard`, `PECS`, `type erasure`, `raw type`) to eliminate boilerplate template text and replace with precise, detailed explanations, importance, common confusions, and runnable examples.
- `anki/basic.tsv`: Replaced boilerplate with 18 high-quality conceptual cards covering key generic definitions, type erasure mechanism, invariance, PECS wildcards, raw types dangers, generic limitations (arrays, primitives, static, overloading).
- `anki/basic-extra.tsv`: Replaced boilerplate with exactly 12 deep conceptual cards with detailed explanations, gotchas, and memory hooks.
- `anki/cloze.tsv`: Replaced boilerplate with exactly 12 precise cloze cards testing type erasure, invariance, PECS, raw types, primitive limits, generic arrays, instanceof checks, multiple bounds ordering, and method overloading.
- `anki/code-question.tsv`: Replaced boilerplate with exactly 12 code challenges on static parameter reference, raw type classcast, method erasure collision, extends/super wildcard validation, generic array creation compilation, subtyping invariance, instanceof checking, and PECS reversed copy failure.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections in theory, placeholder key terms, 3 warnings in card quality audit.
- After: 5/5 PASS, 0 card quality warnings, all audit checks cleared.

---

## no19_collections_framework — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 6 conceptual why-questions linking to theory anchor sections (ArrayList growth and resizing cost, HashMap equals/hashCode contract corruption, TreeSet Comparable/Comparator sorting/duplicate detection, ConcurrentHashMap CAS/bucket-head synchronized blocks, fail-fast expectedModCount mechanism, Collections.unmodifiableList views vs List.copyOf immutable copy structures).
- `theory/02-arraylist-concepts.md`: Added "Why ArrayList Resizes by 1.5x" explaining growth factor benefits, contiguous allocation, copying via `Arrays.copyOf`/`System.arraycopy()`, memory reuse, code examples, mental models, and cause-effect chains.
- `theory/03-treeset-concepts.md`: Added "Why TreeSet and TreeMap Rely on Comparable/Comparator" explaining Red-Black tree traversal, duplicate detection ignoring equals(), compareTo inconsistent with equals() bug, code examples, mental models, and cause-effect chains.
- `theory/04-linkedlist-as-queue-concepts.md`: Added "Why Equals and HashCode Must Be Overridden Together" explaining Object.hashCode address fallback, bucket distribution, key lookup bugs, duplicate entries, data leaks, code examples, mental models, and cause-effect chains.
- `theory/05-concurrenthashmap-concepts.md`: Added "Why ConcurrentHashMap Avoids Global Locking" explaining lock-striping, Compare-And-Swap (CAS) empty bucket optimization, head node synchronized locks, null key/value rejection reasons, code examples, mental models, and cause-effect chains.
- `theory/06-fail-fast-iterator-concepts.md`: Added "Why Fail-Fast Iterators Throw ConcurrentModificationException" explaining modCount/expectedModCount checking, structural modifications detection, CopyOnWriteArrayList snapshot iteration, code examples, mental models, and cause-effect chains.
- `theory/07-collections-unmodifiablelist-concepts.md`: Added "Why Unmodifiable Views and Immutable Collections Differ" explaining read-only delegation wrapper views, backing list live reference updates, List.of/List.copyOf self-contained immutable array allocation, JVM optimizations, code examples, mental models, and cause-effect chains.
- `terms/01-key-terms.md`: Completely rewrote all template placeholders for Iterable, Collection, List, Set, Queue, Deque, Map, Iterator, fail-fast, and ConcurrentModificationException with clear definitions, why it matters, common confusion, and small code examples.
- `anki/basic.tsv`: Replaced repetitive template cards with 16 high-quality concept-specific cards.
- `anki/basic-extra.tsv`: Replaced template cards with 12 high-quality basic-extra cards.
- `anki/cloze.tsv`: Replaced template cards with 12 high-quality cloze cards.
- `anki/code-question.tsv`: Replaced template cards with 12 high-quality code-question cards.

### Self-Check Coverage
- Before: 0/6 Self-Check questions, 0 Why sections, multiple template placeholder cards.
- After: 6/6 PASS, 0 `warn`-level card quality findings, 52 new concept-specific cards across all 4 note types.

---

## no23_stream_api — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 6 conceptual why-questions linking to theory anchor sections (lazy evaluation AbstractPipeline/Sink mechanism, peek state mutation hazards, flatMap vs map transformations, primitive streams memory optimization, parallel streams common ForkJoinPool/Spliterator hazards, groupingBy vs partitioningBy structure differences).
- `theory/01-what-is-stream-concepts.md`: Added Why section: "Why Primitive Streams Exist and Avoid Autoboxing" (heap memory layout, pointer chasing, IntStream/LongStream/DoubleStream contiguous memory allocation, specialized statistics, mental model, code example, cause-effect chain).
- `theory/02-longstream-concepts.md`: Added Why section: "Why flatMap() Differs from map()" (one-to-one vs one-to-many, flattening nested streams, closing transient streams, type signature contrast, mental model, code example, cause-effect chain).
- `theory/03-peek-concepts.md`: Added Why section: "Why peek() Should Not Be Used for State Mutation" (debugging intent, count optimization skipping traversal, parallel side-effect thread contention, pure function model, mental model, code example, cause-effect chain).
- `theory/05-lazy-evaluation-concepts.md`: Added 2 Why sections: "Why Streams Are Lazily Evaluated" (linked pipeline of AbstractPipeline/Sink stages, push-based single-pass Sink execution, short-circuit early termination, mental model, code example, cause-effect chain) and "Why Parallel Streams Are Not a Default Solution" (global ForkJoinPool.commonPool() thread starvation, Spliterator splitting efficiency of ArrayList vs LinkedList, coordination overhead, mental model, code example, cause-effect chain).
- `theory/06-partitioningby-concepts.md`: Added Why section: "Why groupingBy and partitioningBy Serve Different Purposes" (fixed binary Boolean keys vs dynamic arbitrary K keys, PartitioningCollector optimization, HashMap instantiation, mental model, code example, cause-effect chain).
- `terms/01-key-terms.md`: Completely rewrote all template placeholders for stream pipeline, intermediate operation, terminal operation, lazy evaluation, short-circuiting, collector, and parallel stream.
- `anki/basic.tsv`: Replaced repetitive template cards with 16 high-quality concept-specific cards.
- `anki/basic-extra.tsv`: Replaced template cards with 12 high-quality basic-extra cards.
- `anki/cloze.tsv`: Replaced template cards with 12 high-quality cloze cards.
- `anki/code-question.tsv`: Replaced template cards with 12 high-quality code-question cards.

### Self-Check Coverage
- Before: 0/6 Self-Check questions, 0 Why sections, 140 card quality warnings (template/placeholder content across all 4 files).
- After: 6/6 PASS, 0 `warn`-level card quality findings, all link audit checks cleared. 52 new concept-specific cards across all 4 note types.

---

## no13_memory_management — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (Stack vs Heap primitives, Java pass-by-value, reference types GC behavior, islands of isolation GC, OOM vs StackOverflowError catching danger).
- `theory/01-stack-concepts.md`: Added 2 Why sections: "Why Primitives Live on the Stack or Heap" (scope determination: local vs instance vs static primitives, LIFO stack frame lifecycle, heap allocation persistence, mental model, code example, cause-effect chain) and "Why Java Is Strictly Pass-by-Value" (bit-pattern copying, primitive values vs object pointer copying, shared heap state mutations, parameter reassignment limitations, mental model, code example, cause-effect chain). Added Reference Links.
- `theory/02-weak-reference-concepts.md`: Added 2 Why sections: "Why Different Reference Types Exist" (Strong vs Soft vs Weak vs Phantom references, garbage collection behavior, memory-sensitive caching, metadata maps, off-heap native resource post-mortem cleanup, mental model, code example, cause-effect chain) and "Why Islands of Isolation Can Be Garbage Collected" (reference counting failure on circular dependencies vs tracing GC reachability checks starting from GC Roots, mental model, code example, cause-effect chain). Added Reference Links.
- `theory/03-outofmemoryerror-concepts.md`: Added 1 Why section: "Why Heap and Stack Errors Differ" (OutOfMemoryError heap/metaspace dynamic allocation failure vs StackOverflowError thread-specific stack call frame exhaustion, dangerous catching of VirtualMachineError, secondary errors/nested OOM on logging, mental model, code example, cause-effect chain). Added Reference Links.
- `terms/01-key-terms.md`: Completely rewrote all placeholders for stack, heap, metaspace, strong reference, weak reference, garbage collection, and memory leak.
- `anki/basic.tsv`: Replaced template cards with 16 new concept-specific cards.
- `anki/basic-extra.tsv`: Replaced template cards with 12 new basic-extra cards.
- `anki/cloze.tsv`: Replaced template cards with 12 new cloze cards.
- `anki/code-question.tsv`: Replaced template cards with 12 new code-question cards.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, 51 card quality warnings (template/placeholder content across all 4 files).
- After: 5/5 PASS, 0 `warn`-level card quality findings, all link audit checks cleared. 52 new concept-specific cards across all 4 note types.

---

## no12_exception_handling — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (checked vs unchecked rationale, finally JVM guarantee, try-with-resources vs manual finally, exception chaining, broad catch danger).
- `theory/01-what-is-an-exception-concepts.md`: Added 2 Why sections: "Why Java Has Checked and Unchecked Exceptions" (external failure vs programming bug philosophy, compiler enforcement rationale, checked vs unchecked method signature code, cause-effect chain) and "Why Catching Broad Exception Types Is Dangerous" (bug masking mechanism, narrow vs broad catch scope mental model, broad catch NPE masking code, cause-effect chain).
- `theory/02-finally-concepts.md`: Added 3 Why sections: "Why Finally Executes Even When Try Returns Early" (JVM bytecode insertion at all exit paths, register-saved return value mechanism, PER_METHOD lifecycle mental model, finally with return code, cause-effect chain, System.exit exception), "Why Try-With-Resources Replaces Manual Finally for Resource Cleanup" (pre-Java 7 exception replacement bug, exception suppression via addSuppressed, reverse closing order mental model, TrackingResource code, cause-effect chain), "Why Exception Chaining Preserves Debugging Context" (abstraction layer translation, cause chain traversal, 4-layer chain mental model, DatabaseConnectionException code, cause-effect chain). Added Reference Links.
- `anki/basic.tsv`: Overhauled with 24 concept-specific cards covering Throwable hierarchy, Error vs Exception, checked vs unchecked rationale, finally guarantee, finally non-run cases, return-from-finally anti-pattern, throws/throw keywords, try-with-resources, closing order, exception chaining, e.getCause(), multi-catch ordering rule, multi-catch final, propagation, custom exception checked/unchecked, exception swallowing, NPE, broad catch danger, ArrayIndexOutOfBoundsException.
- `anki/basic-extra.tsv`: Created 12 interview-depth cards on checked vs unchecked philosophy, finally JVM bytecode mechanism, try-with-resources suppressed exception fix, exception chaining debugging, broad catch bug masking, catch ordering type system rule, exception swallowing worse than not catching, Exception vs RuntimeException design, propagation to thread root, suppressed exceptions retrieval, InterruptedException swallowing severe mistake, finally primitive vs reference mutation.
- `anki/cloze.tsv`: Created 12 cloze cards on Throwable hierarchy, checked vs unchecked rule, finally guarantee/non-run, finally-return anti-pattern, try-with-resources Java 7/AutoCloseable/closing order, suppressed exception fix, exception chaining cause, catch ordering rule, multi-catch final, propagation, custom exception extends, exception swallowing.
- `anki/code-question.tsv`: Created 12 code challenges on catch ordering compile error, finally-before-return output, finally-return anti-pattern, exception swallowing, try-with-resources close order, exception chaining code, uncatchable checked exception compile error, propagation output, multi-catch final variable compile error, custom exception with chaining constructor, finally primitive return value, broad catch bug masking.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, 61 card quality warnings (template/placeholder content across all 4 files).
- After: 5/5 PASS, 0 `warn`-level card quality findings, all link audit checks cleared. 60 new concept-specific cards across all 4 note types.

---

## no09_oop — 2026-06-25

### Files Modified
- `README.md`: Updated Self-Check section with 5 anchor-linked questions pointing to new Why sections.
- `theory/02-encapsulation.md`: Added Why section: Why Instance Variables Should Be Private.
- `theory/03-inheritance.md`: Added Why section: Why super() Must Be the First Statement in a Subclass Constructor.
- `theory/04-polymorphism.md`: Added Why section: Why Method Overriding Uses Runtime Dynamic Dispatch.
- `theory/05-abstraction.md`: Added 2 Why sections: Why Java Uses Interfaces Instead of Multiple Class Inheritance and Why Abstract Classes and Interfaces Serve Different Design Purposes.

### Self-Check Coverage
- Before: 6 plain Self-Check questions (no anchor links), 0 Why sections.
- After: 5/5 Why sections with anchor links. Cards already passed audit (0 warnings).

---

## no44_unit_testing — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (Mockito isolation, JUnit PER_METHOD instance lifecycle, private method indirect testing, assertThrows vs try-catch, coverage vs correctness).
- `theory/01-junit-concepts.md`: Added 2 Why sections: "Why Mocking Isolates the Unit Under Test" (3 failure modes of real dependencies, real vs mock dependency mental model, Mockito stubbing/verification code, cause-effect chain) and "Why JUnit Creates a New Instance Per Test Method" (order-dependent test failure mechanism, PER_METHOD vs PER_CLASS lifecycle mental model, PER_CLASS with non-static @BeforeAll code, cause-effect chain). Added Reference Links.
- `theory/02-test-exception-concepts.md`: Added 3 Why sections: "Why Private Methods Should Be Tested Indirectly Through the Public API" (implementation coupling mechanism, direct vs indirect testing mental model, public API test code, cause-effect chain), "Why assertThrows Is Safer Than Try-Catch for Exception Testing" (silent pass failure mode, try-catch trap vs assertThrows safety mental model, complete assertThrows code, cause-effect chain), "Why Code Coverage Is a Necessary but Insufficient Quality Metric" (execution vs correctness distinction, assertion-free testing anti-pattern, branch coverage limits code, cause-effect chain). Added Reference Links.
- `anki/basic.tsv`: Created 20 high-quality conceptual cards covering @Test, @BeforeEach, @BeforeAll, assertEquals order, assertThrows return, mock objects, stubbing, verification, PER_METHOD isolation, @TestInstance, private method reflection, indirect testing, coverage definition, 100% coverage gap, AAA pattern, what not to mock, verify(), design smell, branch vs line coverage.
- `anki/basic-extra.tsv`: Created 12 interview-depth cards on real dependency failure modes, assertThrows silent failure, PER_METHOD order-independence, PER_CLASS responsibility, private method reflection coupling, assertion-free testing anti-pattern, stubbing vs verification distinction, @BeforeAll static requirement, assertEquals parameter swap impact, private method complexity design signal, line vs branch coverage distinction, full multi-dependency mock isolation strategy.
- `anki/cloze.tsv`: Created 12 targeted cloze cards on lifecycle annotation order, PER_METHOD isolation, assertEquals parameter order, assertThrows behavior, try-catch silent pass trap, stubbing vs verification, private method indirect testing, coverage vs correctness, @BeforeAll static requirement, mock creation, line vs branch coverage, AAA pattern.
- `anki/code-question.tsv`: Created 12 interview-style code challenges on assertEquals swap misleading message, lifecycle annotation order, try-catch silent pass bug, @BeforeAll non-static JUnitException, List mock anti-pattern, Mockito AAA write-up, private reflection test fix, assertion-free coverage anti-pattern, PER_METHOD state isolation, assertThrows with message verification, missing verify call bug, exception test with verifyNoInteractions.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, all 4 Anki files contained 100% boilerplate placeholder content.
- After: 5/5 PASS, 0 card quality warnings, all link audit checks cleared. 56 new concept-specific cards across all 4 note types.

---

## no40_modern_java_concepts — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (var compile-time inference, Records immutability, Sealed Classes exhaustiveness, Pattern Matching dominance rule, Virtual Thread pinning).
- `theory/01-var-concepts.md`: Added 5 Why sections: "Why var Is Compile-Time Inference Not Dynamic Typing" (static vs dynamic typing mental model, 4 forbidden locations, var in for-each code, cause-effect chain), "Why Records Enforce Immutability Through Compiler-Generated Code" (manual class vs record mental model, compact constructor, validation code, cause-effect chain), "Why Sealed Classes Enable Safe Exhaustive Pattern Matching" (open vs sealed class switch mental model, sealed interface with switch expression code, cause-effect chain), "Why Pattern Matching for Switch Requires Ordering by Specificity" (dominance rule, ordering mental model, guarded pattern switch code, cause-effect chain), "Why Virtual Threads Pin Carrier Threads in Synchronized Blocks" (carrier thread model, synchronized vs ReentrantLock mental model, ReentrantLock fix code, cause-effect chain). Added Reference Links to JEPs 286, 395, 409, 441, 444.
- `anki/basic.tsv`: Overhauled and replaced all 24 placeholder cards with 20 high-quality conceptual cards covering var JDK version, compile-time inference, forbidden locations, Records version, auto-generated methods, Record finality, Sealed Classes version, subclass modifiers, switch exhaustiveness, dominance rule, when guard, Virtual Threads JEP, lightweight advantage, carrier pinning, ReentrantLock fix, executor, SequencedCollection API, reversed() view, Record accessor naming, permits keyword.
- `anki/basic-extra.tsv`: Created 12 interview-depth cards on var at API boundaries, compact constructor mechanics, sealed exhaustiveness with switch, dominance rule compile error, virtual thread pinning mechanism, Record accessor naming rationale, reversed() view vs copy, virtual thread lifecycle/anti-pooling, switch expression exhaustiveness, var anonymous type pitfall, SequencedCollection API gap, non-sealed vs final modifiers.
- `anki/cloze.tsv`: Created 12 targeted cloze cards on var JDK, var restrictions, Record generated methods, compact constructor, sealed permits, sealed switch exhaustiveness, pattern dominance, virtual thread JEP, synchronized pinning fix, SequencedCollection API, switch expression yield, text block indentation.
- `anki/code-question.tsv`: Created 13 interview-style challenges on var type inference, Record accessor/toString, compact constructor transformation, sealed switch exhaustiveness, switch dominance compile error, virtual thread submit output order, virtual thread pooling anti-pattern, SequencedCollection operations, switch yield, synchronized pinning bug, text block indent stripping, enhanced NPE message, Record field access compile error.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, all 4 Anki files contained 100% boilerplate placeholder content.
- After: 5/5 PASS, 0 card quality warnings, all link audit checks cleared. 57 new concept-specific cards across all 4 note types.

---

## no36_security_basic — 2026-06-25

### Files Modified
- `README.md`: Added Self-Check section with 5 conceptual why-questions linking to theory anchor sections (SHA-256 one-way, Base64 not encryption, SecureRandom, insecure deserialization RCE, password char[] storage).
- `theory/01-basic-secure-coding-concepts.md`: Added 4 Why sections: "Why Cryptographic Hashing Is One-Way and Collision-Resistant" (pre-image resistance, collision resistance, salted hashing code, cause-effect chain), "Why Base64 Is Encoding Not Encryption" (LCG vs OS entropy mental model, encoding vs encryption mental model, reversibility code, cause-effect chain), "Why SecureRandom Is Required for Cryptographic Values" (LCG predictability, OS entropy diagram, secure AES key gen code, cause-effect chain), "Why Passwords Must Not Be Stored in Strings" (JVM heap retention, char[] zeroing mental model, PBKDF2 with char[] code, cause-effect chain). Added Reference Links section.
- `theory/02-avoid-insecure-deserialization-concepts.md`: Added "Why Insecure Deserialization Enables Remote Code Execution" section (gadget chain mechanism, ObjectInputFilter whitelist, deserialization gadget chain diagram, safe deserialization code, cause-effect chain). Added Reference Links section.
- `terms/01-key-terms.md`: Fully rewrote all 7 key terms (secure coding, hashing, Base64, encryption, KeyStore, TLS, input validation) to remove all template placeholders and replace with accurate definitions, distinctions, common confusions, and practical examples.
- `anki/basic.tsv`: Created 20 high-quality conceptual cards covering SHA-256 API, one-way properties, MD5 vs SHA-256, Base64 identity, SecureRandom need, AES API, hashing vs encryption, password char[], SQL injection, insecure deserialization, ObjectInputFilter, KeyStore, TLS, input validation, cryptographic salt, AES algorithm string.
- `anki/basic-extra.tsv`: Created 12 interview-depth cards on SHA-256 collision importance, Base64 confusion risk, LCG predictability, String password heap exposure, gadget chain RCE, ObjectInputFilter mechanism, SQL injection structural prevention, AES-CBC IV uniqueness requirement, server-side validation requirement, salting mechanism, Base64 legitimate uses, KeyStore TLS role.
- `anki/cloze.tsv`: Created 12 targeted cloze cards on SHA-256 size, MessageDigest API, Base64 not encryption, Random vs SecureRandom, password char[] zeroing, SQL injection PreparedStatement, deserialization RCE mechanism, ObjectInputFilter filter, AES IV, cryptographic salt, broken algorithms, KeyStore.
- `anki/code-question.tsv`: Created 12 interview-style code challenges on SHA-256 digest length, Base64 reversibility, password String flaw, SQL injection vulnerability, Random in key gen, AES IV generation, deserialization without filter, SHA-256 hex conversion, transient field after deserialization, ObjectInputFilter configuration, salted hash completion, Base64 password storage flaw.

### Self-Check Coverage
- Before: 0/5 Self-Check questions, 0 Why sections, all 4 Anki files contained 100% boilerplate placeholder content.
- After: 5/5 PASS, 0 card quality warnings, all link audit checks cleared. 56 new concept-specific cards across all 4 note types.

---

## no35_networking — 2026-06-25

### Files Modified
- `theory/01-socket-programming-concepts.md`: Existing file already contained 4 depth-rich "Why" sections (Why TCP Handshakes Differ, Why Blocking Socket Operations Must Not Run on the Main Thread, Why Sockets and Streams Must Be Closed Properly, Why Java 20 Deprecated URL Constructors). Placeholder detail sections for Socket, ServerSocket, DatagramSocket, InetAddress, URL, URI, Basic HTTP request were present but the self-check sections answer those via the Why sections. No additional Why sections needed.
- `theory/02-httpurlconnection-concepts.md`: Fully rewrote all placeholder Detailed Notes sections for HttpURLConnection, Java 11 HttpClient, and Client-server model with real explanations. Added the "Why Java 11 HttpClient Supersedes HttpURLConnection" section with 4-limitation enumeration, mental model (Blocking vs Event Loop diagram), code example (async CompletableFuture), and cause-effect chain.
- `terms/01-key-terms.md`: Fully rewrote all key terms (socket, TCP, UDP, ServerSocket, DatagramSocket, URI, HttpClient) to remove all template placeholders and replace with accurate definitions, explanations of why each matters, common confusions, and illustrative examples.
- `anki/basic.tsv`: Overhauled and replaced all 26 boilerplate placeholder cards with 20 high-quality conceptual cards covering TCP vs UDP protocols, handshake mechanics, blocking main thread, socket resource leaks, URL deprecation, URI vs URL, HttpURLConnection limitations, Java 11 HttpClient features, HTTP/2, async requests, and client-server model.
- `anki/basic-extra.tsv`: Overhauled and replaced all 16 placeholder cards with 12 interview-depth cards explaining TCP handshake, UDP tradeoffs, multi-threaded server pattern, OS-level FD leaks, URI factory methods, URL DNS in HashMap, HttpURLConnection infinite timeouts, HttpClient async event loop, DatagramSocket connect semantics, client-server model mapping, HTTP/2 multiplexing, and try-with-resources closure order.
- `anki/cloze.tsv`: Overhauled and replaced all 13 placeholder cloze cards with 12 targeted cloze cards covering TCP/UDP classes, 3-way handshake steps, blocking accept pattern, FD leak exception types, URL deprecation Java version, URI vs URL DNS resolution, HttpURLConnection timeout defaults, HttpClient JEP number and protocols, sendAsync return type, HttpURLConnection HTTP version limits, try-with-resources closure order, and client-server model roles.
- `anki/code-question.tsv`: Overhauled and replaced all 19 placeholder cards with 13 interview-style code challenges covering ServerSocket accept behavior, TCP vs UDP send differences, blocking server bug identification, socket FD leak, deprecated URL construction, async HttpClient output order, HttpURLConnection timeout configuration, UDP receive mechanics, HttpClient builder configuration, HttpURLConnection common mistakes, DatagramPacket send, try-with-resources closure order, and URI-to-URL conversion pattern.

### Self-Check Coverage
- Before: 4/5 Why sections existed in theory; 1 section (Q4 HttpClient) was missing; all 4 Anki files contained 100% boilerplate placeholder content.
- After: 5/5 PASS, 0 card quality warnings, all link audit checks cleared. 57 new concept-specific cards across all 4 note types.

---

## no34_jdbc — 2026-06-25

### Files Modified
- `theory/02-rollback-concepts.md`: Added "Why Disabling Auto-Commit Establishes Transactional ACID Boundaries" (explaining default auto-commit behavior vs manual-transaction commit/rollback logic, money transfer ACID consistency diagram, bank transaction code example, and rollback cause-effect chain). Added "Why Savepoints Enable Partial Rollbacks and Their Isolation Mechanics" (explaining checkpoint rollback boundaries, savepoint database checkpointing sequence model, partial rollback error recovery code example, and savepoint rollback cause-effect chain). Added "Why Database Connection Pools Yield Massive Performance Gains" (explaining socket connection creation and authentication overhead, pool pre-allocation, close-intercept reuse, HikariCP reuse lifecycle sequence, HikariDataSource code example, and connection pool latency cause-effect chain). Added Reference Links section.
- `terms/01-key-terms.md`: Fully rewrote all key terms definitions (driver, Connection, PreparedStatement, ResultSet, transaction, SQL injection, DataSource) to eliminate generic placeholders and the wrong description of ResultSet as a duplicate-rejecting Set, replacing them with detailed, specific descriptions, gotchas, and code examples.
- `anki/basic.tsv`: Overhauled and replaced all boilerplate placeholder cards with 15 high-quality conceptual cards covering statements, SQL injection, auto-commit, savepoints, connection pools, reverse-order resource closure, and batching.
- `anki/basic-extra.tsv`: Overhauled and replaced all boilerplate placeholder cards with 10 high-quality detailed conceptual cards explaining statements vs prepared statements, SQL injection parameters, transaction connections, savepoint rollbacks, connection pools lifecycle, reverse resource closure sequence, batching transaction conditions, ResultSet next() cursor rules, DriverManager vs DataSource, and rollback exception handling.
- `anki/cloze.tsv`: Overhauled and replaced all boilerplate placeholder cards with 8 precise cloze deletion cards testing placeholders, auto-commit config, Savepoint checkpoints, connection pools, try-with-resources closure, CallableStatement, ResultSet cursor, and executeBatch.
- `anki/code-question.tsv`: Overhauled and replaced all boilerplate placeholder cards with 10 high-quality code-based prediction and mechanism analysis questions.

### Self-Check Coverage
- Before: 2/5 PASS, 3/5 FAIL, placeholder key terms definitions, incorrect ResultSet description, 78 Anki card quality warnings.
- After: 5/5 PASS, 0 quality warnings, all audit checks cleared.

---

## no32_classloader — 2026-06-25

### Files Modified
- `anki/basic.tsv`: Overhauled and replaced all boilerplate placeholder cards with 15 high-quality conceptual cards covering classloader phases, classloader types, parent delegation, namespaces, TCCL, and Metaspace layout.
- `anki/basic-extra.tsv`: Overhauled and replaced all boilerplate placeholder cards with 10 high-quality detailed conceptual cards explaining phases, delegation model, namespace type uniqueness, TCCL context loader SPI overrides, Metaspace memory leaks, verification bytecode safety, ClassNotFoundException/NoClassDefFoundError contrasts, and sizing configs.
- `anki/cloze.tsv`: Overhauled and replaced all boilerplate placeholder cards with 8 precise cloze deletion cards testing phases, linking steps, Bootstrap/Platform/Application classloaders, parent delegation model, namespace type identity, context classloader SPIs, and Metaspace native storage.
- `anki/code-question.tsv`: Overhauled and replaced all boilerplate placeholder cards with 10 high-quality code-based prediction and mechanism analysis questions.

### Self-Check Coverage
- Before: 5/5 PASS (existing theory met depth criteria), but all Anki cards were boilerplate placeholders with quality audit warnings.
- After: 5/5 PASS, 0 quality warnings, all audit checks cleared.

---

## no31_reflection — 2026-06-25

### Files Modified
- `theory/02-advantages-and-disadvantages-of-reflection-concepts.md`: Added "Why Reflection Introduces Performance Penalties and How to Optimize It" explaining JIT compilation bypass, runtime type checks, autoboxing overhead, and MethodHandles optimization with a comparison diagram, benchmarking code example, and dynamic invocation latency cause-effect chain. Added "Why Reflective Instantiation and Classloading Pose Security and Stability Risks" explaining unsafe deserialization vulnerabilities, gadget classes, Metaspace memory leaks, an instantiation attack sequence diagram, insecure ProcessBuilder code example, and Remote Code Execution cause-effect chain. Added "Why Reflection Enables Dependency Injection and ORM Frameworks" explaining framework boot classpath annotation scanning, setAccessible private field access, factory boilerplate elimination, dependency injection flowchart, reflective populating field example, and modular decoupling cause-effect chain. Added Reference Links section.
- `terms/01-key-terms.md`: Fully rewrote all key terms definitions (reflection, Class object, Field, Method, Constructor, private access) to remove placeholders and replace with deep explanations, Gotchas, and small code examples.
- `anki/basic.tsv`: Added 3 new basic cards covering performance JIT bypass, MethodHandles optimizations, and Dynamic classloading security risks.
- `anki/basic-extra.tsv`: Added 2 new basic extra cards covering performance overhead optimization techniques and classloading Metaspace/RCE vulnerabilities.

### Self-Check Coverage
- Before: 2/5 PASS, 3/5 FAIL, placeholder key terms definitions.
- After: 5/5 PASS, 0 quality warnings.

---

## no27_nio — 2026-06-25

### Files Modified
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 5/5 PASS, 0 quality warnings.

---

## no26_io — 2026-06-25

### Files Modified
- `theory/02-bufferedinputstream-concepts.md`: Added "Why Buffered Streams Significantly Outperform Raw Streams" explaining user/kernel space context switches, BufferedInputStream internal buffer mechanics, OS block size alignment, and page caching. Added a user/kernel space buffering sequence diagram, benchmarking code example, and I/O context switch cause-effect chain. Added Reference Links section.
- `theory/03-serialization-concepts.md`: Added "Why serialVersionUID is Critical for Class Version Compatibility" explaining hash generation algorithm, class structural modifications, stream vs local class UID comparison, class evolution, class evolution matching flowchart, version mismatch code example, and version mismatch cause-effect chain. Added "Why transient Fields are Excluded from Serialization and How Deserialization Restores Them" explaining sensitive/runtime resource exclusions, constructor bypass during direct heap instantiation, transient field default initialization, deserialization memory flow diagram, constructor/initializer bypass code example, and transient field reversion cause-effect chain. Added "Why Java Serialization is a Security Liability and How Modern Alternatives Mitigate It" explaining look-ahead deserialization vulnerabilities, gadget chains on classpath, ObjectInputFilter mitigations, data-only serialization alternatives (JSON/Protobuf), RCE exploit flowchart, safe JSON alternative DTO example, and deserialization RCE cause-effect chain. Added Reference Links section.
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions.

### Self-Check Coverage
- Before: Missing Self-Check section, no "why" sections in theory.
- After: 5/5 PASS, 0 quality warnings.

---

## no21_lambda_expression — 2026-06-25

### Files Modified
- `theory/01-what-is-a-lambda-concepts.md`: Added "Why Lambdas Use invokedynamic and Bootstrap Methods" (explaining class loading vs dynamic bootstrap method generation with metafactory, sequence diagram, dynamic class print example, and cause-effect chain), "Why Local Variables Captured by Lambdas Must Be Final or Effectively Final" (explaining stack vs heap lifetime desync, stack memory diagram, compile error example, and cause-effect chain), "How Method References Resolve Receivers and Signatures Under the Hood" (explaining parameter mapping and receiver assignment differences in static, bound, and unbound references, mapping diagram, code example, and cause-effect chain), "Why Lambdas Cannot Throw Checked Exceptions and How to Bypass It" (explaining functional interface throws signature checking, validation flow diagram, IOException handling code example, and cause-effect chain), and "Scope and Scoping Semantics: Lambdas vs Anonymous Inner Classes" (explaining lexical scoping boundaries, scoping layout diagram, this-reference printing example, and cause-effect chain). Added reference links to JLS, API docs, and Oracle tutorials.
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions (invokedynamic vs inner classes compile output, lambda closure local variables final restriction, method references syntax types under the hood, lambda checked exceptions declaration limits, lexical scoping semantics).
- `anki/basic.tsv`: Overhauled to replace placeholder cards with 18 high-quality conceptual cards covering compiler implementation, variable capture mechanisms, syntax constraints, scoping rules, method reference mapping, and exception limits.
- `anki/cloze.tsv`: Overhauled to replace placeholders with 13 specific, active recall cloze cards testing invokedynamic, final variables, unbound method references, checked exceptions, scoping, functional interface definition, and comparator bugs.
- `anki/code-question.tsv`: Overhauled to replace placeholder cards with 14 high-quality code compilation and behavior analysis questions.

### Self-Check Coverage
- Before: Missing Self-Check section, placeholder Anki cards with quality warnings.
- After: 5/5 PASS, 0 quality warnings, all audit checks cleared.

## no15_inner_nested_class — 2026-06-25

### Files Modified
- `theory/01-nested-class-concepts.md`: Added "Why Static Nested and Non-Static Inner Classes Differ in Initialization and Memory", "Why Non-Static Inner Classes Can Cause Memory Leaks", "Why Local and Anonymous Inner Classes Only Access Final or Effectively Final Variables", "Why JVM Generates Synthetic Accessors for Private Nested Access", and "Why Anonymous Classes Compile to Separate Class Files vs Lambdas". Each section contains detailed explanation, Mermaid diagrams/lifecycle models, code examples with expected outputs, cause-effect chains, and references.
- `README.md`: Restructured the topic's check and added a "Self-Check" section containing 5 deep conceptual "why" questions with hints referencing the theory sections.
- `anki/basic.tsv`: Added 2 new conceptual cards covering synthetic accessors and final capture mechanisms.
- `anki/basic-extra.tsv`: Added 2 new conceptual cards covering memory leaks and memory footprint/GC differences.
- `anki/cloze.tsv`: Added 1 new cloze card covering synthetic accessor methods.
- `anki/code-question.tsv`: Added 2 new code question cards covering physical `.class` file generation and effectively final compilation constraints.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 5/5 PASS, 0 quality warnings.

---

## no20_comparable_comparator — 2026-06-25

### Files Modified
- `theory/01-comparable-concepts.md`: Added "Why TreeSet and TreeMap Require Consistency with Equals" (explaining compareTo-based uniqueness vs equals/hashCode validation, with a flowchart, BigDecimal code example, and cause-effect chain), "Why Subtraction-Based Comparison Leads to Overflow Bugs" (explaining two's complement arithmetic overflow and sign bit flipping, with binary subtraction model, code example, and cause-effect chain), and "Why Java Separates Comparable and Comparator" (explaining single intrinsic natural order vs multiple extrinsic strategies, class diagram, code example, and cause-effect chain). Added "Why the Transitivity Contract is Critical for Sorting" (explaining linear ordering vs cyclic relationships, Rock-Paper-Scissors cycle diagram, sorting exception code example, and cause-effect chain). Added Reference Links section.
- `theory/02-reverse-order-concepts.md`: Added "Why Java Uses Dual-Pivot Quicksort for Primitives but TimSort for Objects" (explaining value types vs reference identity, sorting stability, Quicksort vs TimSort complexity comparison matrix, log severity stability code example, and cause-effect chain). Added Reference Links section.
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions (Comparable vs Comparator separation, transitivity violations, consistency-with-equals TreeSet duplicates, subtraction overflow, Quicksort vs TimSort stability).
- `anki/basic.tsv`: Appended 5 new high-quality conceptual basic cards covering consistency-with-equals, subtraction overflow, Comparable/Comparator separation, transitivity importance, and Quicksort/TimSort selection.
- `anki/basic-extra.tsv`: Appended 5 new high-quality conceptual basic-extra cards explaining the mechanics of consistency-with-equals, subtraction overflow, separation of concerns, transitivity contract violations, and sorting stability.
- `anki/cloze.tsv`: Appended 4 new high-quality cloze cards testing terms like consistency-with-equals, integer overflow/underflow, transitivity, and Dual-Pivot Quicksort vs TimSort.
- `anki/code-question.tsv`: Appended 2 new code prediction questions testing TreeSet BigDecimal size outcome and cyclic non-transitive comparator exceptions.

### Self-Check Coverage
- Before: Missing Self-Check section, no "why" sections in theory.
- After: 5/5 PASS, 0 quality warnings.

---

## no14_object_class — 2026-06-25

### Files Modified
- `theory/01-tostring-concepts.md`: Added "Why toString is Auto-Invoked and How Circular References Cause Stack Overflow" (explaining `String.valueOf()` conversion and infinite recursion, with a sequence diagram, code example, and cause-effect chain) and "Why Overloading equals Instead of Overriding It Fails Silently" (explaining static overload resolution vs runtime dynamic dispatch in generic collections, with a flowchart, code example, and cause-effect chain). Added reference links to Oracle docs and the Java Language Specification.
- `theory/02-contract-of-equals-concepts.md`: Added "Why Adding Value Fields to Subclasses Breaks Transitivity" (explaining Point/ColorPoint equivalence relations and symmetry vs transitivity limits, with a diagram, code example, and cause-effect chain), "Why identityHashCode Does Not Represent Physical Memory Addresses" (explaining compacting GC relocation and object header Mark Word caching, with a flowchart, code example, and cause-effect chain), and "Why Failing to Override hashCode Breaks Hash Collections" (explaining bucket lookup routing mismatches, with a lookup flowchart, code example, and cause-effect chain). Added reference links to Oracle docs.
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions (equals-hashCode violation, transitivity failure, identityHashCode representation, toString recursion, equals overload trap).
- `anki/basic.tsv`: Appended 2 new high-quality basic cards covering circular reference risks in toString and identityHashCode stability.
- `anki/basic-extra.tsv`: Appended 2 new high-quality basic-extra cards covering overloaded equals collection failure and subclass value field transitivity limits.
- `anki/cloze.tsv`: Appended 2 new high-quality cloze deletion cards covering String.valueOf/toString auto-invocation and Mark Word caching.
- `anki/code-question.tsv`: Appended 2 new high-quality code question cards testing circular reference stack overflows and Point/ColorPoint transitivity violation outcomes.

### Self-Check Coverage
- Before: Missing Self-Check section, no "why" sections in theory.
- After: 5/5 PASS, 0 quality warnings.

---

## no22_functional_interface — 2026-06-25

### Files Modified
- `theory/01-predicate-t-concepts.md`: Added "Why Use the @FunctionalInterface Annotation" (compile-time declaration checks, debugging localization) and "How the JLS Counts Abstract Methods and Treats java.lang.Object Overrides" (Object public methods exclusion from SAM count JLS §9.8). Added "Why Primitive Specializations Prevent Boxing Overhead" (autoboxing/unboxing heap allocations, generic type erasure constraints). Added Mermaid diagrams, code examples, and cause-effect chains.
- `theory/02-what-is-the-output-concepts.md`: Added "Why Functional Contract Styles Differ" (inputs/outputs mapping: Predicate, Function, Consumer, Supplier semantic structures) and "Why Functional Interfaces Leverage Default Methods for Chaining" (dynamic composition, default implementations without breaking SAM contract). Added Mermaid diagrams, code examples, and cause-effect chains.
- `README.md`: Added "Self-Check" section containing 5 deep conceptual "why" questions.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 5/5 PASS, 0 quality warnings.

---

## no08_string — 2026-06-25

### Files Modified
- `theory/01-string-basics.md`: Added "Deep-Dive: The Mechanics and Security of Immutability" explaining security checks, thread safety, and hashcode caching with a sequence diagram and code examples. Added "Deep-Dive: Memory Optimization and Heap Mechanics of the String Pool" explaining reference sharing, heap memory layout, and the `new` keyword bypassing pool optimizations with a pool reference model diagram and cause-effect chains. Added "Deep-Dive: Reference Comparison (==) vs. Content Equality (.equals())" explaining memory addresses comparison versus character-by-character validation with a reference comparison model, code example, and cause-effect chains. Added a "Reference Links" section.
- `theory/02-string-methods.md`: Added "Deep-Dive: Mechanical Differences Between trim() and strip()" explaining legacy ASCII space vs Unicode whitespace properties, a whitespace comparison matrix, a Unicode demonstration code example, and a cause-effect chain. Added a "Reference Links" section.
- `theory/03-stringbuilder-stringbuffer.md`: Added "Deep-Dive: Synchronization Overhead and Lock Contention Mechanics" explaining monitor locks, context-switch latency, and unsynchronized execution with a thread contention model diagram, unsafe concurrency code example, and a cause-effect chain of unsafe concurrency data corruption. Added a "Reference Links" section.
- `README.md`: Restructured the "Self-Check" section into 5 deep conceptual "why" questions.

### Self-Check Coverage
- Before: 0/5 PASS, simple questions without deep "why" criteria.
- After: 5/5 PASS, 0 quality warnings.

---

## no11_package_access_control — 2026-06-25

### Files Modified
- `theory/01-what-is-a-package-concepts.md`: Added "Why Java Uses Packages for Namespace Isolation and Reverse DNS", "Why Directory Structures Must Mirror Package Declarations", "Why Static Imports Balance Readability and Naming Collision Risks", "Why the Default Package Should Be Avoided in Production", "Why Default (Package-Private) Access Controls Internal Package Access", and "Why Classpath and Module Path Differ in Package Access Constraints". Each section includes prose, a Mermaid diagram, a code example, and a cause-effect chain. Added a comprehensive "Reference Links" section.
- `README.md`: Added "Self-Check" section containing 6 deep conceptual "why" questions.
- `anki/basic.tsv`: Replaced 18 boilerplate cards with 18 high-quality conceptual cards covering reverse DNS, directory mapping, default package constraints, wildcard import mechanics, static import collision risks, protected modifier rules, and classpath/modulepath constraints.
- `anki/basic-extra.tsv`: Replaced 9 boilerplate cards with 9 high-quality conceptual cards, expanding the "Extra" explanation fields to meet the minimum word count.
- `anki/cloze.tsv`: Replaced 9 boilerplate cards with 9 high-quality conceptual cloze deletion cards.
- `anki/code-question.tsv`: Replaced 15 boilerplate cards with 16 high-quality code prediction cards, adding a new card on modular encapsulation compile-time enforcement.

### Self-Check Coverage
- Before: Missing Self-Check section, boilerplate cards.
- After: 6/6 PASS, 0 quality warnings.

---

## no39_utility_apis — 2026-06-25

### Files Modified
- `theory/01-math-concepts.md`: Added "Why Random and Math.random() Have Flaws in Concurrency and Security" explaining AtomicLong CAS thread contention, LCG predictability vs. SecureRandom, and a vending machine analogy with a thread diagram. Added "Why BigDecimal is Precise: Unscaled Value and Scale Representation" explaining binary floating-point representation limits under IEEE 754 vs base-10 unscaledValue/scale, double initialization noise, and a sticky note/fraction analogy with a Mermaid diagram. Added "System vs. Runtime: Purpose and JVM Interaction" explaining the static wrapper class vs singleton JVM lifecycle interface, delegation wrappers, and a cruise ship captain/guest services analogy with a Mermaid flowchart.
- `theory/02-properties-concepts.md`: Added "Why Direct Map Manipulation of Properties is Dangerous" explaining LSP violations from Hashtable inheritance, type safety gaps, and ClassCastException during serialization with a mailbox analogy and a serialization workflow diagram. Added "Why the Scanner nextLine() Pitfall Occurs" explaining token-based methods leaving delimiter characters in the input buffer vs. line-based reading, step-by-step cursor movement, and a conveyor belt analogy with a buffer state transition diagram.
- `README.md`: Added "Self-Check" section containing 6 deep conceptual "why" questions.
- `anki/basic.tsv`: Replaced 32 low-quality generic boilerplate cards with 17 high-quality conceptual flashcards covering exact arithmetic, random contention, BigDecimal mechanics, System/Runtime separation, ProcessBuilder hangs, Properties Map dangers, and Scanner nextLine buffer mechanics.
- `anki/cloze.tsv`: Replaced 17 low-quality generic boilerplate cards with 16 high-quality conceptual cloze deletion cards.
- `anki/basic-extra.tsv`: Expanded all 24 cards' "Extra" fields to meet length requirements (minimum 18 words) with detailed explanations, gotchas, and memory hooks, eliminating all audit quality warnings.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 6/6 PASS

## no38_build_compile_run — 2026-06-25

### Files Modified
- `theory/01-javac-concepts.md`: Added "Why Executable JARs Need MANIFEST.MF" (JVM entry-point, Class-Path lookup, dependency jar configurations), "Why Classpath Resolution Fails: NoClassDefFoundError vs ClassNotFoundException" (checked exceptions vs linkage errors, librarian analogy, cause-effect chains), and "Why Build Tools (Maven/Gradle) Are Essential" (lifecycle orchestration, transitive dependency downloading, conflict resolution, pom.xml/build.gradle comparison). Added Mermaid diagrams, code examples, and cause-effect chains.
- `theory/02-standard-project-structure-concepts.md`: Added "Why Standard Project Structure Separates Source Code and Resources" (compiled code vs copied static resources, classloader NPE risk) and "Why JUnit Assertion Order and Annotations Matter" (JUnit test scanning, expected/actual failure reporting logs). Added Mermaid diagrams, code examples, and cause-effect chains.
- `terms/01-key-terms.md`: Fully rewrote all key terms to remove template placeholders and provide high-quality Java specifications.
- `README.md`: Added "Self-Check" section containing 6 deep conceptual questions.
- `anki/basic.tsv`: Replaced boilerplate template cards with 24 high-quality basic conceptual cards, matching updated theory depth and adding correct URL references.
- `anki/basic-extra.tsv`: Preserved existing cards and added 4 new detailed conceptual cards covering classpath exceptions, resources separation, JUnit expected/actual ordering, and build tools necessity. Expanded explanations to resolve quality warnings.
- `anki/cloze.tsv`: Replaced boilerplate template cards with 12 high-quality cloze cards covering syntax, OS separators, classpath errors, Maven, JUnit, and transitive dependencies.

### Self-Check Coverage
- Before: Missing Self-Check section, placeholders in key terms, template-generated basic/cloze cards.
- After: 6/6 PASS, 0 quality warnings.

---

## no33_module_system — 2026-06-25

### Files Modified
- `theory/01-what-is-a-module-concepts.md`: Rewrote entirely to explain strong encapsulation, Project Jigsaw, reliable configuration, `exports` vs `opens` (reflective access differences), modular vs flat classloading, and automatic/unnamed modules migration. Added analogies, Mermaid diagrams, and cause-effect chains.
- `terms/01-key-terms.md`: Rewrote terms definitions to eliminate generic template placeholders.
- `README.md`: Added "Self-Check" section with 5 deep conceptual questions.
- `anki/basic.tsv`: Replaced boilerplate cards with 14 high-quality basic conceptual cards.
- `anki/basic-extra.tsv`: Replaced boilerplate cards with 6 high-quality basic-extra cards.
- `anki/cloze.tsv`: Replaced boilerplate cards with 6 high-quality cloze cards.
- `anki/code-question.tsv`: Replaced boilerplate cards with 7 high-quality code prediction cards.

### Self-Check Coverage
- Before: Missing Self-Check section, skeletal theory with placeholders, 59 Anki card quality warnings.
- After: 5/5 PASS, 0 quality warnings.

---

## no24_optional — 2026-06-25

### Files Modified
- `theory/01-what-is-optional-t-concepts.md`: Added "Why orElse() and orElseGet() Differ in Evaluation Mechanics" explaining eager vs lazy evaluation with the vending machine analogy, Mermaid diagram, and code examples. Fixed Set boilerplate for `orElseThrow` and added links.
- `theory/02-map-concepts.md`: Added "Why map() and flatMap() Differ in Signature and Wrapping" (Nested Box analogy, NPE behavior, Mermaid diagram, code examples) and "Why Optional Should Not Be Used for Fields or Parameters" (Double-Wrapped Present analogy, serialization limits, memory footprint, Mermaid diagram, code examples). Fixed Map boilerplate for `map` and `flatMap` and added links.
- `README.md`: Added "Self-Check" section with 7 deep "why" questions.
- `anki/basic.tsv`: Added 4 new basic cards (optional-basic-031 to optional-basic-034) covering lazy execution, flatMap null checks, field suitability, and parameter anti-patterns.
- `anki/basic-extra.tsv`: Added 3 new basic-extra cards (optional-extra-018 to optional-extra-020) detailing field memory overhead, eager/lazy analogies, and map/flatMap null contrasts.
- `anki/cloze.tsv`: Added 3 new cloze cards (optional-cloze-016 to optional-cloze-018) covering serialization failures, 16-byte wrapper size, and eager evaluation.
- `anki/code-question.tsv`: Added 2 new code-question cards (optional-cq-022 to optional-cq-023) predicting serialization exceptions and null wrapping output.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 7/7 PASS

## no01_overview — 2026-06-25

### Files Modified
- `theory/01-what-is-java.md`: Added "Why Java Runs on a Virtual Machine: Platform Abstraction" explaining C/C++ compilation problems vs JVM virtualization.
- `theory/02-jvm-jre-jdk.md`: Added "Why Development Needs the JDK But Production Needs Only JRE" describing compiler toolchains vs runtime footprint and security.
- `theory/03-compile-runtime-flow.md`: Added "Source Code vs. Bytecode: Under the Hood" detailing the `.class` binary layout (`0xCAFEBABE`, constant pool, stack-based opcodes). Added "Why Java Combines Compilation and Virtual Execution" explaining compiler static checks and JIT dynamic optimizations. Added "What Garbage Collection Reclaims and How it Detects Garbage" detailing heap management and GC Roots reachability analysis.
- `theory/04-editions-and-versions.md`: Added "Why Backend Projects Standardize on LTS Versions Like Java 17 and 21" breaking down LTS release cycles and modern features (Records, Text Blocks, Virtual Threads).
- `README.md`: Added new Key Terms (`Stack`, `Heap`, `GC Roots`, `Records`, `Virtual Threads`).
- `anki/cloze.tsv`: Added 7 new cloze cards matching updated theory depth.
- `anki/basic-extra.tsv`: Added 6 new basic-extra cards matching updated theory depth.
- `anki/code-question.tsv`: Added 1 new code-question card matching updated theory depth.

### Self-Check Coverage
- Before: 0/6 PASS, 4/6 PARTIAL, 2/6 FAIL
- After: 6/6 PASS

## no00_setup — 2026-06-25

### Files Created
- `theory/01-setup-basics.md`: Created and explained JDK vs JRE, why filenames must match public class names, and terminal vs IDE compilation and execution. Included Mermaid diagrams, code snippets, analogies, and cause-effect chains.
- `anki/basic.tsv`: Added 3 Basic flashcards.
- `anki/basic-extra.tsv`: Added 3 Basic Extra flashcards.
- `anki/cloze.tsv`: Added 3 Cloze flashcards.
- `anki/code-question.tsv`: Added 3 Code Question flashcards.

### Files Modified
- `README.md`: Translated to English and updated to follow the standard topic layout with English Self-Check questions and Study Order/Anki links.

### Self-Check Coverage
- Before: 0/3 PASS, 0/3 PARTIAL, 3/3 FAIL (no theory existed)
- After: 3/3 PASS

## no03_data_types — 2026-06-25

### Files Modified
- `theory/01-primitive-types.md`: Added size table, cross-links
- `theory/02-reference-types.md`: Added "Why String Is Not a Primitive", "Stack vs Heap Memory Model", "Why Primitives Are Stored Directly", null explanation
- `theory/03-literals-casting-numeric-behavior.md`: Added "Why Narrowing Can Lose Data"
- `theory/04-wrappers-null-equality.md`: Added "int vs Integer: Full Comparison", "Why Use .equals() for String Content", "Why Unboxing null Throws NullPointerException", "Integer Cache"
- `README.md`: Added Stack, Heap, Integer Cache, Two's complement, NullPointerException to Key Terms; added Self-Check hints

### Self-Check Coverage
- Before: 0/6 PASS, 4/6 PARTIAL, 2/6 FAIL
- After: 6/6 PASS

---

## no05_operators — 2026-06-25

### Files Modified
- `theory/01-arithmetic-assignment-operators.md`: Added "Why Compound Assignment Performs Implicit Casting"
- `theory/02-comparison-logical-operators.md`: Added "Why Logical Operators Short-Circuit and How They Differ From Bitwise Operators"
- `theory/03-bitwise-increment-ternary-instanceof.md`: Added "How Prefix and Postfix Increments Work Under the Hood", "Why Pattern Matching for instanceof is Safer and Cleaner", "How Bitwise Shift Operators Manipulate Bits"
- `theory/04-precedence-short-circuit.md`: Added "Why Precedence and Associativity Dictate Expression Correctness"
- `README.md`: Added "Self-Check" section with 7 deep "why" questions.
- `anki/basic.tsv`: Added 5 new basic flashcards.
- `anki/basic-extra.tsv`: Added 2 new basic-extra flashcards.
- `anki/code-question.tsv`: Added 2 new code-question flashcards.

### Self-Check Coverage
- Before: Missing Self-Check section.
- After: 7/7 PASS

## no02_basic_syntax — 2026-06-25

### Files Modified
- `theory/01-program-anatomy.md`: Added "Why All Code Resides in Classes", "Why Java is Case-Sensitive", and updated Reference Links.
- `theory/02-main-method.md`: Added "Why the main Method Signature is Rigid" and updated Reference Links.
- `theory/03-comments-packages-imports.md`: Added "How Comments are Processed by the Compiler", "Why Reverse DNS and Package Structure Prevent Collisions", and updated Reference Links.
- `theory/04-naming-keywords-blocks-scope.md`: Added "Why Variable Scopes are Restricted to Blocks" and updated Reference Links.
- `README.md`: Updated Self-Check questions to deep "why" questions; added Stack Memory, Symbol Table, Case Sensitivity, Lexical Analysis, and Reverse DNS to Key Terms.
- `anki/basic-extra.tsv`: Added 6 new flashcards covering class residency, case-sensitivity mechanics, static main reasons, comment lexical stripping, reverse DNS domain collision prevention, and stack block scope memory management.
- `anki/code-question.tsv`: Fixed two shallow answer warnings (for class name conventions and main method entry point return type error options).

### Self-Check Coverage
- Before: 0/6 PASS, 6/6 PARTIAL
- After: 6/6 PASS

## no04_variables_constants — 2026-06-25

### Files Modified
- `theory/01-variable-categories.md`: Added "Local vs Instance vs Static: Memory Model and Lifetimes" detailing Stack frame vs Heap instance vs Metaspace memory allocations and lifetimes. Added JVM memory organization diagram, code example, and allocation/destruction cause-effect chains. Added Reference Links section.
- `theory/02-final-and-constants.md`: Added "Why Constants Are Declared static final" comparing memory footprints of instance-level final vs. static final variables. Added "How final Enables Compiler Optimizations" detailing constant folding and inlining mechanisms, code demo, and optimization cause-effect chain. Added Reference Links section.
- `theory/03-default-scope-lifetime.md`: Added "Why Local Variables Must Be Initialized But Fields Get Default Values" comparing heap/metaspace zero-initialization with stack definite assignment analysis and performance trade-offs. Added "Understanding Scope vs Lifetime" detailing compile-time scope vs runtime lifetime, diagram, code demo, and lifecycle cause-effect chain. Added Reference Links section.
- `theory/04-var-type-inference.md`: Added "Why var Type Inference Only Works Locally" explaining public class API contracts and separate compilation limits. Added contract vs implementation comparison, type inference boundary code demo, and API contract stability cause-effect chain. Added Reference Links section.
- `README.md`: Updated Self-Check section with 6 deep "why" questions.
- `anki/basic.tsv`: Added 5 new Basic cards (var-basic-036 to var-basic-040).
- `anki/basic-extra.tsv`: Added 4 new Basic Extra cards (var-extra-013 to var-extra-016).
- `anki/cloze.tsv`: Added 4 new Cloze cards (var-cloze-019 to var-cloze-022).
- `anki/code-question.tsv`: Added 3 new Code Question cards (var-code-018 to var-code-020).

### Self-Check Coverage
- Before: 0/6 PASS, 3/6 PARTIAL, 3/6 FAIL
- After: 6/6 PASS

## no16_enum — 2026-06-25

### Files Modified
- `theory/01-what-is-an-enum-concepts.md`: Added "Why Enums Are Compiled to Final Classes Extending java.lang.Enum" (JLS inheritance limits, type safety), "Why Enum Constructors Must Be Private" (instance control, reflection blocks), "Why values() Can Be a Performance Bottleneck" (array cloning overhead), and "Why Enums Are Safe to Compare Using the == Operator" (reference identity, null-safety, type compatibility checks). Included class diagrams, execution flows, and cause-effect chains.
- `theory/02-enum-implements-interface-concepts.md`: Added "Under the Hood: Constant-Specific Class Bodies and Anonymous Subclasses" (compilation to anonymous subclasses like `SystemAction$1.class` for constants with bodies) and "How Enum Singleton Works: Thread, Reflection, and Serialization Safety" (JVM classloading lock, Constructor.newInstance checks, and name-only serialization lookup). Included subclass diagrams, security test snippets, and cause-effect chains.
- `README.md`: Added Self-Check section with 6 deep "why" questions mapping to topic-level mechanisms.
- `anki/basic.tsv`: Added 2 new conceptual basic cards (`enum-basic-025` and `enum-basic-026`).
- `anki/basic-extra.tsv`: Added 2 new basic extra cards (`enum-extra-013` and `enum-extra-014`).
- `anki/cloze.tsv`: Added 2 new cloze cards (`enum-cloze-013` and `enum-cloze-014`).
- `anki/code-question.tsv`: Added 2 new code question cards (`enum-code-019` and `enum-code-020`).

### Self-Check Coverage
- Before: Missing Self-Check section (0/6 PASS, 4/6 PARTIAL, 2/6 FAIL estimated)
- After: 6/6 PASS

## no30_regex — 2026-06-25

### Files Modified
- `theory/01-what-is-regex-concepts.md`: Added "Why Pattern Compilation Is Expensive" (AST, NFA construction overhead), "Why Non-Capturing Groups Save Heap Allocations" (offsets/capture buffers bypass), and "Why Backtracking Occurs and How Quantifiers Prevent ReDoS" (quantifier backtracking dynamics, ReDoS prevention). Added diagrams, code examples, and cause-effect chains.
- `theory/02-basic-lookahead-lookbehind-concepts.md`: Added "Why Lookarounds are Zero-Width Assertions" (cursor anchors, non-consuming check mechanics), "Why Java Lookbehinds Have Width Limitations" (fixed-width/bounded rewinding logic), and "Why String.split Discards Trailing Empty Strings" (post-processing cleanup scan vs negative limit). Added diagrams, code examples, and cause-effect chains.
- `README.md`: Added the Self-Check section containing 6 deep "why" questions mapping to the underlying mechanisms.
- `anki/basic.tsv`: Added 4 new Basic cards (regex-basic-029 to regex-basic-032).
- `anki/basic-extra.tsv`: Added 4 new Basic Extra cards (regex-extra-017 to regex-extra-020).
- `anki/cloze.tsv`: Added 4 new Cloze cards (regex-cloze-015 to regex-cloze-018).
- `anki/code-question.tsv`: Added 4 new Code Question cards (regex-code-026 to regex-code-029).

### Self-Check Coverage
- Before: Missing Self-Check section (0/6 PASS, 6/6 FAIL estimated)
- After: 6/6 PASS

## no17_annotation — 2026-06-25

### Files Modified
- `theory/01-what-is-an-annotation-concepts.md`: Added "Why @Target Exists: Restricting Scope and Preventing Misuse" and "Why @Retention Exists and How Retention Policies Differ". Added diagrams, analogies, and cause-effect chains.
- `theory/02-documented-concepts.md`: Added "Why Runtime Annotation Processing Uses Dynamic Proxies Under the Hood". Added diagrams, analogies, and cause-effect chains.
- `README.md`: Added Self-Check section with 6 deep "why" questions mapping to topic-level mechanisms.
- `anki/basic.tsv`: Added 2 new basic cards (`annotation-basic-033`, `annotation-basic-034`).
- `anki/basic-extra.tsv`: Added 2 new basic-extra cards (`annotation-extra-017`, `annotation-extra-018`). Expanded 5 existing basic-extra cards to pass word count check.
- `anki/cloze.tsv`: Added 2 new cloze cards (`annotation-cloze-017`, `annotation-cloze-018`).
- `anki/code-question.tsv`: Added 2 new code question cards (`annotation-code-026`, `annotation-code-027`).

### Self-Check Coverage
- Before: Missing Self-Check section (0/6 PASS, 4/6 PARTIAL, 2/6 FAIL estimated)
- After: 6/6 PASS

## no25_date_time_api — 2026-06-25

### Files Modified
- `theory/01-date-concepts.md`: Added "Why the Legacy Date, Calendar, and SimpleDateFormat APIs Are Flawed", "Why Modern Java 8 Date-Time Objects Are Immutable and Thread-Safe", and "Why We Distinguish Instant, OffsetDateTime, and ZonedDateTime". Added diagrams, analogies, and cause-effect chains.
- `theory/02-period-concepts.md`: Added "Why Period and Duration Behave Differently (Daylight Saving Time Transitions)", "Why DateTimeFormatter Is Completely Thread-Safe", and "How ZonedDateTime Resolves Invalid and Overlapping Times (DST Shifts)". Added diagrams, analogies, and cause-effect chains.
- `README.md`: Added Self-Check section with 6 deep "why" questions mapping to topic-level mechanisms.
- `anki/basic.tsv`: Updated all cards to include URL references; added 4 new basic cards (`date_time_api-basic-037` to `date_time_api-basic-040`).
- `anki/basic-extra.tsv`: Updated all cards to include URL references; added 2 new basic-extra cards (`date_time_api-extra-026`, `date_time_api-extra-027`).
- `anki/cloze.tsv`: Updated all cards to include URL references; added 3 new cloze cards (`date_time_api-cloze-019` to `date_time_api-cloze-021`).
- `anki/code-question.tsv`: Updated all cards to include URL references; added 2 new code question cards (`date_time_api-code-028`, `date_time_api-code-029`).

### Self-Check Coverage
- Before: Missing Self-Check section (0/6 PASS, 6/6 FAIL estimated)
- After: 6/6 PASS

## no06_control_flow — 2026-06-25

### Files Modified
- `theory/01-if-else-switch.md`: Added "Why Dangling Else Ambiguity Occurs and How Java Resolves It" detailing Context-Free Grammar syntax tree ambiguity, resolution by JLS nearest-match rule, code demonstration, and cause-effect chain. Added Reference Links section.
- `theory/02-loops.md`: Added "Under the Hood: How while and do-while Differ in Bytecode" explaining execution entry points, compilation differences, code demos, conceptual bytecode offsets, and cause-effect chains. Added "Under the Hood: Array vs. Iterable Mechanics in Enhanced for Loops" detailing javac compilation bifurcation, array length bounds vs Iterator usage, ConcurrentModificationException mechanics, code examples, and cause-effect chains. Added Reference Links section.
- `theory/03-break-continue-return.md`: Added "Under the Hood: How the JVM Handles Labeled break and continue" detailing target updates/exits mapping to bytecode offset goto commands, code trace, and cause-effect chain. Added "Why Java Prohibits Unreachable Statements and How the Compiler Detects Them" explaining control flow graph static analysis, JLS 14.21 definite completion, code check, and cause-effect chain. Added Reference Links section.
- `theory/04-switch-expression-labeled-control.md`: Added "Why Switch Expressions Require Exhaustiveness and How It Is Enforced" explaining type safety value-resolution requirements, compiler checks for enum constants and default paths, code examples, and cause-effect chain. Added Reference Links section.
- `README.md`: Added Self-Check section with 6 deep conceptual questions.
- `anki/basic.tsv`: Added 2 new Basic cards (`control-basic-036`, `control-basic-037`).
- `anki/basic-extra.tsv`: Added 2 new Basic Extra cards (`control-extra-016`, `control-extra-017`).
- `anki/cloze.tsv`: Added 2 new Cloze cards (`control-cloze-021`, `control-cloze-022`).
- `anki/code-question.tsv`: Added 2 new Code Question cards (`control-code-024`, `control-code-025`).

### Self-Check Coverage
- Before: Missing Self-Check section (0/6 PASS, 6/6 FAIL estimated)
- After: 6/6 PASS

## no07_arrays — 2026-06-25

### Files Modified
- `theory/01-array-basics.md`: Added "Why Array Elements are Automatically Zero-Initialized" (detailing stack frame reuse vs. heap memory security, zero-filling, safety guarantees) and "Why Arrays Have Fixed Size and Contiguous Memory Layout" (detailing heap contiguous blocks, direct access offset math, O(1) performance limits, and ArrayList resizing mechanics). Added "Why Multidimensional Arrays are Arrays of Arrays" (detailing ragged/jagged array heap references representation, memory layout flexibility, and JVM representation simplification). Added Reference Links section.
- `theory/02-array-operations.md`: Added "Why System.arraycopy is Performant and Shallow" (native execution, OS/hardware memmove, loop overhead bypass, reference address copy vs underlying duplication). Added "Why Binary Search Requires Sorted Arrays and How Its Return Code Math Works" (halving assumptions, sorted contract requirement, negative insertionPoint offset math to avoid 0 index collision). Added "Why Arrays.equals Fails on Multidimensional Arrays" (single-level reference comparison vs nested value traversal with deepEquals). Added Reference Links section.
- `README.md`: Updated Self-Check section with 6 deep conceptual "why" questions.
- `anki/basic-extra.tsv`: Added 2 new basic extra cards (`array-extra-016`, `array-extra-017`).
- `anki/cloze.tsv`: Added 2 new cloze cards (`array-cloze-016`, `array-cloze-017`).
- `anki/code-question.tsv`: Added 2 new code question cards (`array-code-022`, `array-code-023`).

### Self-Check Coverage
- Before: 0/6 PASS, 3/6 PARTIAL, 3/6 FAIL
- After: 6/6 PASS

## no10_modifiers — 2026-06-25

### Files Modified
- `theory/01-access-modifier-concepts.md`: Added "Why Private Restricts Access and Supports Encapsulation" explaining how private visibility prevents direct access, forces control through public validation APIs, and protects class invariants with an encapsulation boundary diagram, a BankAccount code example, and a cause-effect chain.
- `theory/02-abstract-concepts.md`: Added "Why Static Members are Allocated in Metaspace and Shared" explaining class metadata Metaspace allocation vs Heap instance residency, a Metaspace vs Heap memory allocation model diagram, a shared counter code example, and a cause-effect chain. Added "Why Synchronized Methods Use Monitor Locks and Reentrancy" explaining intrinsic monitor locks, thread acquisition blocking, lock reentrancy counter semantics with a reentrancy execution model, a code example demonstrating reentrant calls, and a cause-effect chain. Added "Why Volatile Guarantees Visibility and Ordering, but Not Atomicity" explaining multi-level CPU caching, memory barriers, direct Main Memory syncing, instruction reordering prevention, and compound operation atomicity gaps with a cache visibility model diagram, a non-atomic volatile increment code example, and a cause-effect chain.
- `theory/03-static-block-concepts.md`: Added "Why Final Variables Prevent Re-Assignment and Enable Inlining" explaining constant variable immutability guarantees, JIT compiler constant folding and inlining optimizations with a compiler inlining optimization model, a code example, and a cause-effect chain.
- `terms/01-key-terms.md`: Fully rewrote all key terms (access modifier, non-access modifier, static, final, abstract, volatile, transient) to replace generic template placeholders with deep, specific explanations and code examples.
- `README.md`: Added a "Self-Check" section containing 5 deep conceptual "why" questions mapping to the added sections.
- `anki/basic.tsv`: Replaced all boilerplate cards with 25 high-quality conceptual cards covering access levels, subclass protected access limits, public default class constraints, private nested access, static Metaspace allocation, abstract structure, synchronized locking scopes, volatile memory semantics, transient serialization behavior, static blocks, static nested classes, and final variables.
- `anki/basic-extra.tsv`: Replaced all boilerplate cards with 10 high-quality conceptual cards, expanding the "Extra" explanation fields (e.g., stack reference vs heap object, Metaspace vs stack initialization lifecycle) to meet minimum length requirements and resolve all quality warnings.
- `anki/cloze.tsv`: Replaced all boilerplate cards with 10 high-quality conceptual cloze deletion cards.
- `anki/code-question.tsv`: Replaced all boilerplate cards with 8 high-quality code-based prediction cards testing compiler rules, static null references, static/instance blocks, and final reassignments.

### Self-Check Coverage
- Before: Missing Self-Check section, skeletal key terms, and 91 generic template-generated Anki card warnings.
- After: 5/5 PASS, 0 quality warnings.

