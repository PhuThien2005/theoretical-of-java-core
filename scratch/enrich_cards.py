import csv

concepts = [
    # Topic 1
    ("What is the Collection Framework?", 
     "A unified architecture in Java for representing and manipulating collections, containing interfaces, implementations, and algorithms.",
     "It reduces programming effort, increases performance, and fosters software reuse by providing standard data structures.",
     "Assuming all collections extend the Collection interface, whereas Map is a completely separate hierarchy.",
     "The Collections Framework consists of three parts: interfaces, implementations, and algorithms. Note that Map does NOT extend Collection.",
     "The {{c1::Collections Framework}} is a unified architecture representing and manipulating collections, including interfaces, implementations, and algorithms.",
     "The Collections Framework consists of three main parts: {{c1::interfaces}}, {{c2::implementations}}, and {{c3::algorithms}}."),
    
    ("Iterable",
     "The root traversal interface of the Collection hierarchy; classes implementing it can be traversed using enhanced for loops.",
     "It provides a standard way to obtain an Iterator for sequential traversal.",
     "Modifying the collection directly inside an enhanced for loop, causing a ConcurrentModificationException.",
     "Requires implementing iterator(). Example:\nfor(String s : list) { ... }\nGotcha: Modifying the collection during traversal throws ConcurrentModificationException.",
     "The root traversal contract in Java that returns an Iterator and enables for-each loop traversal is {{c1::Iterable}}.",
     "Any class that implements the {{c1::Iterable}} interface must provide an implementation for the {{c2::iterator()}} method."),

    ("Collection",
     "The root interface of the collection hierarchy (List, Set, Queue) representing a group of objects.",
     "Defines common operations like size(), isEmpty(), contains(), add(), and remove() shared by all collections.",
     "Assuming Map extends Collection (it does not) or that Collection guarantees a specific order.",
     "Root of List, Set, Queue hierarchies. Common methods: size(), add(), remove(). Note that Map does not extend Collection.",
     "The root interface of the main collection hierarchy is {{c1::Collection}}.",
     "The {{c1::Collection}} interface is extended by {{c2::List}}, {{c3::Set}}, and {{c4::Queue}}."),

    ("List",
     "An ordered collection (sequence) that allows duplicates and positional access by index.",
     "Essential when element order must be preserved and duplicate values are permitted.",
     "Using index-based deletion in a forward loop, which shifts elements and causes items to be skipped.",
     "Ordered collection, duplicate-friendly. Backed by array (ArrayList) or nodes (LinkedList). Positional access via get(index).",
     "An ordered collection that can contain duplicate elements and supports index-based access is {{c1::List}}.",
     "Common implementations of the List interface include {{c1::ArrayList}} and {{c2::LinkedList}}."),

    ("Set",
     "A collection that contains no duplicate elements, modeling the mathematical set abstraction.",
     "Ensures uniqueness of entries automatically using equality checks.",
     "Modifying key fields of an object after adding it to a HashSet, making the object unretrievable.",
     "No duplicates. HashSet has O(1) ops (unordered); TreeSet has O(log N) ops (sorted). Rejection based on equals/hashCode or compareTo.",
     "A collection that cannot contain duplicate elements is {{c1::Set}}.",
     "The three main implementations of the Set interface are {{c1::HashSet}}, {{c2::LinkedHashSet}}, and {{c3::TreeSet}}."),

    ("Queue",
     "A collection designed for holding elements prior to processing, typically in FIFO order.",
     "Decouples producers from consumers in processing pipelines using standard queue APIs.",
     "Confusing exception-throwing methods (add, remove) with special-value return methods (offer, poll).",
     "Typically FIFO. Inserters: offer() vs add(); Removers: poll() vs remove(); Peepers: peek() vs element().",
     "A collection designed for holding elements prior to processing, typically in FIFO order, is {{c1::Queue}}.",
     "Queue methods that return special values on failure instead of throwing exceptions are {{c1::offer()}}, {{c2::poll()}}, and {{c3::peek()}}."),

    ("Deque",
     "A double-ended queue supporting element insertion, removal, and inspection at both ends.",
     "Can be used as both a FIFO queue and a LIFO stack. ArrayDeque is faster than Stack and LinkedList.",
     "Using legacy Stack instead of Deque for stack operations, incurring synchronization overhead.",
     "Double-ended queue. Useful as stack (push, pop) or queue (addLast, removeFirst). ArrayDeque is the default choice.",
     "A double-ended queue that supports element insertion and removal at both ends is {{c1::Deque}}.",
     "To implement a stack, Deque provides the LIFO methods {{c1::push()}} and {{c2::pop()}}."),

    ("Map",
     "An object that maps keys to values, rejecting duplicate keys.",
     "Enables key-value associations and fast key-based retrieval.",
     "Expecting Map to implement Collection, or using mutable objects as keys and changing their hashCode.",
     "Key-value pairs. Keys must be unique. HashMap (unordered), LinkedHashMap (insertion/access order), TreeMap (sorted).",
     "An object that maps keys to values and cannot contain duplicate keys is {{c1::Map}}.",
     "Although part of the Collections Framework, the {{c1::Map}} interface does NOT extend the {{c2::Collection}} interface."),

    # Topic 2
    ("ArrayList",
     "A resizable-array implementation of the List interface.",
     "Provides O(1) random access and low memory overhead, making it the default choice for lists.",
     "Adding/removing items in the middle of a large ArrayList frequently, incurring O(N) element shifting cost.",
     "Backed by array. Growth is usually 1.5x of old capacity using System.arraycopy(). O(1) random access, O(N) middle insert/delete.",
     "A resizable-array implementation of the List interface is {{c1::ArrayList}}.",
     "ArrayList provides {{c1::O(1)}} random access time complexity, but middle insertions take {{c2::O(N)}} time due to element shifting."),

    ("LinkedList",
     "A doubly-linked list implementation of List and Deque interfaces.",
     "Permits O(1) insertion/removal at the head and tail without resizing overhead.",
     "Iterating LinkedList using index-based get(i) loops, resulting in O(N^2) complexity.",
     "Backed by doubly-linked nodes (3 references per node: value, next, prev). No array resizing. Poor cache locality.",
     "A doubly-linked list implementation of the List and Deque interfaces is {{c1::LinkedList}}.",
     "LinkedList requires {{c1::O(N)}} time for index lookup, but insertion/removal at a known node is {{c2::O(1)}}."),

    ("Vector",
     "A legacy synchronized resizable-array implementation of List.",
     "Synchronizes all methods, guaranteeing thread safety but causing severe contention.",
     "Using Vector in single-threaded applications instead of ArrayList, hurting performance.",
     "Legacy synchronized resizable array. Obsolete. Doubles capacity when resized (ArrayList grows 1.5x).",
     "A legacy synchronized version of ArrayList is {{c1::Vector}}.",
     "Vector is obsolete because all its public methods are {{c1::synchronized}}, causing high performance overhead in single-threaded scenarios."),

    ("Stack",
     "A legacy synchronized subclass of Vector implementing a LIFO stack.",
     "Violates encapsulation by exposing all Vector methods (like insert at arbitrary index).",
     "Using Stack instead of ArrayDeque, which is unsynchronized and enforces clean stack boundaries.",
     "Legacy LIFO stack extending Vector. Exposes illegal list methods like insert/remove by index. Use ArrayDeque instead.",
     "The legacy LIFO stack class in java.util that extends Vector is {{c1::Stack}}.",
     "Stack is considered poorly designed because it inherits list operations from {{c1::Vector}}, violating {{c2::LIFO}} encapsulation."),

    ("Comparing ArrayList and LinkedList",
     "ArrayList is array-backed (fast random access); LinkedList is node-backed (fast head/tail insertions).",
     "ArrayList has better cache locality and lower memory overhead; LinkedList has node allocation cost.",
     "Assuming LinkedList is always faster for inserts; it is slower if index lookup is required.",
     "ArrayList: O(1) get, O(N) shift, high cache hit. LinkedList: O(N) get, O(1) pointer updates, high GC/memory overhead.",
     "Compared to LinkedList, ArrayList has better CPU cache performance due to {{c1::spatial locality}} of contiguous memory.",
     "LinkedList has higher memory overhead than ArrayList because it creates a {{c1::node object}} with next/prev references for every element."),

    ("When to use List?",
     "Use when sequence order matters, duplicate elements are acceptable, or index-based access is needed.",
     "Provides predictable, ordered storage for elements.",
     "Choosing LinkedList by default instead of ArrayList, which is generally faster due to cache hits.",
     "Use List for ordered duplicates. ArrayList is the default. LinkedList only for extensive front insertions/removals.",
     "You should use a List when you need to maintain element {{c1::order}} and allow {{c2::duplicate}} values.",
     "The default List implementation choice in Java is {{c1::ArrayList}} due to performance and memory efficiency."),

    ("HashSet",
     "An unordered Set implementation backed by a HashMap.",
     "Provides constant-time O(1) operations for basic additions and lookups.",
     "Expecting HashSet to maintain insertion order, which is not guaranteed.",
     "Backed by HashMap. O(1) performance. Unordered. Null key is allowed. Elements must override equals and hashCode.",
     "A Set implementation that makes no guarantees about element iteration order is {{c1::HashSet}}.",
     "HashSet uses a {{c1::HashMap}} internally, storing set elements as map {{c2::keys}} with a dummy value object."),

    ("LinkedHashSet",
     "A HashSet implementation that maintains a doubly-linked list of entries.",
     "Preserves the insertion order of elements during iteration with O(1) performance.",
     "Assuming it sorts elements; it only preserves insertion order.",
     "HashMap + doubly-linked list. Maintains insertion order. O(1) operations, slightly slower than HashSet.",
     "A Set implementation that maintains the insertion order of its elements is {{c1::LinkedHashSet}}.",
     "LinkedHashSet maintains insertion order by using a {{c1::doubly-linked list}} running through its entries."),

    # Topic 3
    ("TreeSet",
     "A NavigableSet implementation backed by a TreeMap, keeping elements sorted.",
     "Maintains elements in sorted order and provides range-search navigation methods.",
     "Adding objects that do not implement Comparable or have no Comparator, causing ClassCastException.",
     "Red-Black tree. Sorted order. O(log N) operations. Rejects null. Ignores equals/hashCode, relies on compare/compareTo.",
     "A Set implementation backed by a Red-Black tree that keeps elements sorted is {{c1::TreeSet}}.",
     "TreeSet rejects {{c1::null}} elements and requires elements to implement {{c2::Comparable}} or use a custom {{c3::Comparator}}."),

    ("SortedSet",
     "An interface representing a Set sorted in ascending order.",
     "Provides range views and extreme element retrieval (first, last).",
     "Adding null elements, which TreeSet (the main implementation) rejects.",
     "Interface for sorted sets. Defines first(), last(), subSet(), headSet(), tailSet().",
     "An interface representing a Set sorted in ascending order of its elements is {{c1::SortedSet}}.",
     "SortedSet provides range view operations such as {{c1::subSet()}}, {{c2::headSet()}}, and {{c3::tailSet()}}."),

    ("NavigableSet",
     "An interface extending SortedSet with navigation methods like lower() and higher().",
     "Allows finding closest matching elements in a sorted collection.",
     "Confusing ceiling() (least element >= e) with higher() (least element > e).",
     "Extends SortedSet. Adds floor(), ceiling(), lower(), higher(), pollFirst(), pollLast(), descendingSet().",
     "An interface extending SortedSet that adds closest-match search methods is {{c1::NavigableSet}}.",
     "In NavigableSet, {{c1::floor()}} returns the greatest element <= e, while {{c2::ceiling()}} returns the least element >= e."),

    ("When to use Set?",
     "Use when duplicate elements are not allowed and uniqueness must be enforced.",
     "Models mathematical set operations like union, intersection, and difference.",
     "Using a Set when element insertion order or positional access is required.",
     "Use Set to prevent duplicates. HashSet (default/fast), LinkedHashSet (insertion order), TreeSet (sorted/navigable).",
     "You should choose a Set implementation when your collection must reject {{c1::duplicate}} elements.",
     "For sorted unique elements, choose {{c1::TreeSet}}; for insertion-ordered uniqueness, choose {{c2::LinkedHashSet}}."),

    ("Duplicate removal mechanism",
     "HashSet uses hashCode() and equals(); TreeSet uses compareTo() or compare() returning 0.",
     "Ensures correct duplicate rejection depending on the Set implementation.",
     "Assuming TreeSet uses equals() to check duplicates; it only uses compareTo().",
     "HashSet: hashCode() matches then equals() returns true. TreeSet: compareTo() or compare() returns 0.",
     "HashSet checks duplicates using {{c1::hashCode()}} and {{c2::equals()}}, whereas TreeSet uses {{c3::compareTo()}} or {{c4::compare()}}.",
     "HashSet checks duplicates using hashCode() and equals(), while TreeSet uses {{c1::compareTo()}} or {{c2::compare()}}."),

    ("Role of equals() and hashCode()",
     "equals() defines logical equality; hashCode() returns an integer bucket address.",
     "The hashCode contract requires that if x.equals(y), then x.hashCode() == y.hashCode().",
     "Overriding equals() but not hashCode(), causing duplicate keys/values in HashMaps/HashSets.",
     "Must be overridden together. If equals() is true, hashCode() must match. Mismatched hashes cause collision lookup bugs.",
     "If two objects are equal according to equals(), they must return the {{c1::same}} value from {{c2::hashCode()}}.",
     "If equals() is overridden without hashCode(), hashing collections will store duplicates because they search in different {{c1::buckets}}."),

    ("PriorityQueue",
     "An unbounded priority queue based on a binary heap, keeping the smallest element at the head.",
     "Allows retrieving elements according to priority rather than insertion order.",
     "Iterating over a PriorityQueue and expecting elements to be sorted; the iterator is unsorted.",
     "Min-heap based unbounded queue. O(log N) for offer/poll, O(1) for peek. Iterator is unsorted.",
     "An unbounded queue based on a binary heap that orders elements by priority is {{c1::PriorityQueue}}.",
     "Iterating over a PriorityQueue using an iterator does NOT yield sorted elements; you must call {{c1::poll()}} sequentially."),

    ("ArrayDeque",
     "A resizable circular-array Deque implementation.",
     "Faster and more memory-efficient than Stack (as stack) and LinkedList (as queue).",
     "Attempting to insert null elements, which ArrayDeque rejects.",
     "Circular array double-ended queue. Faster than Stack/LinkedList. Rejects nulls. Growable, unsynchronized.",
     "A resizable-array Deque implementation that is faster than Stack and LinkedList is {{c1::ArrayDeque}}.",
     "ArrayDeque rejects {{c1::null}} elements and is not thread-safe; for concurrent scenarios, use {{c2::LinkedBlockingDeque}}."),

    # Topic 4
    ("LinkedList as Queue",
     "LinkedList implementing Queue to act as a FIFO queue.",
     "Provides fast O(1) enqueues and dequeues backed by list nodes.",
     "Using LinkedList when ArrayDeque would be faster and use less memory.",
     "LinkedList implements Queue. Unbounded. Uses node pointer updates for O(1) queue operations. ArrayDeque is generally preferred.",
     "When LinkedList is used as a Queue, elements are enqueued using {{c1::offer()}} and dequeued using {{c2::poll()}}.",
     "LinkedList acts as an {{c1::unbounded}} queue, which means it will never throw an exception from {{c2::offer()}} due to capacity limits."),

    ("FIFO",
     "First-In-First-Out processing order.",
     "Standard queue behavior where elements are added to tail and removed from head.",
     "Expecting Deque.push() to behave in FIFO order; push() is LIFO.",
     "First-In-First-Out. Queue inserts at tail, removes from head. offer() and poll() are standard FIFO methods.",
     "The queue ordering policy where the oldest element is processed first is {{c1::FIFO}}.",
     "In a standard FIFO queue, elements are added to the {{c1::tail}} and removed from the {{c2::head}}."),

    ("LIFO",
     "Last-In-First-Out processing order.",
     "Stack behavior where element added last is removed first.",
     "Using legacy java.util.Stack instead of Deque implementations.",
     "Last-In-First-Out. Stack behavior. Deque uses push() and pop() for LIFO operations.",
     "The stack ordering policy where the most recently added element is processed first is {{c1::LIFO}}.",
     "For LIFO operations, Java developers should use the {{c1::Deque}} interface instead of the legacy {{c2::Stack}} class."),

    ("Priority queue",
     "A queue where elements are ordered by natural comparison or a custom comparator.",
     "Enables priority-based task execution.",
     "Adding non-comparable elements, which triggers ClassCastException.",
     "Orders elements by priority using binary heap. Min-priority by default. Rejects nulls.",
     "A queue that orders its elements according to natural order or a custom Comparator is a {{c1::Priority queue}}.",
     "The head of a PriorityQueue is the {{c1::least}} element according to the specified ordering."),

    ("HashMap",
     "Unordered Map implementation mapping keys to values using a bucket array.",
     "Provides O(1) performance for lookups and insertions.",
     "Using mutable keys whose hashcodes change, causing memory leaks and unreachable values.",
     "Hash table bucket array. Java 8+ treeification (list size > 8, capacity >= 64). O(1) avg ops, O(log N) worst.",
     "An unordered Map implementation based on a hash table is {{c1::HashMap}}.",
     "In HashMap, buckets containing more than 8 elements are treeified into {{c1::Red-Black trees}} if total capacity is at least 64."),

    ("LinkedHashMap",
     "HashMap with a doubly-linked list maintaining insertion or access order.",
     "Enables building LRU caches using access-order mode.",
     "Thinking LinkedHashMap sorts keys; it only preserves access/insertion order.",
     "Extends HashMap. Doubly-linked list preserves order. removeEldestEntry() allows automatic eviction.",
     "A Map implementation that maintains insertion or access order using a doubly-linked list is {{c1::LinkedHashMap}}.",
     "To build an LRU cache, LinkedHashMap is constructed with access-order set to {{c1::true}}, overriding {{c2::removeEldestEntry()}}."),

    ("TreeMap",
     "Red-Black tree Map implementation keeping keys sorted.",
     "Maintains keys in sorted order and provides navigable lookup methods.",
     "Inserting null keys, which causes NullPointerException.",
     "Red-Black tree NavigableMap. Sorted keys. O(log N) operations. Rejects null keys. Compares keys using compare/compareTo.",
     "A Map implementation based on a Red-Black tree that keeps keys sorted is {{c1::TreeMap}}.",
     "TreeMap compares keys using {{c1::compareTo()}} or {{c2::compare()}} instead of hashCode() and equals()."),

    ("Hashtable",
     "Legacy synchronized Map implementation.",
     "Guarantees thread safety via coarse locking, which degrades performance.",
     "Using Hashtable in modern code instead of ConcurrentHashMap.",
     "Legacy synchronized map. Rejects null keys and values. Synchronization on every method. Obsolete.",
     "The legacy synchronized map class that rejects null keys and null values is {{c1::Hashtable}}.",
     "Hashtable is obsolete because it synchronizes all methods globally; you should use {{c1::ConcurrentHashMap}} for thread safety."),

    # Topic 5
    ("ConcurrentHashMap",
     "Thread-safe Map utilizing node-level locking and CAS operations.",
     "Allows high-concurrency read and write operations without blocking the entire map.",
     "Assuming sequence of operations (containsKey + put) on ConcurrentHashMap is atomic.",
     "Thread-safe. Node-level locking since Java 8. CAS for empty bins. No global lock. Read operations are lock-free.",
     "A highly concurrent, thread-safe Map implementation that locks at node/bucket level is {{c1::ConcurrentHashMap}}.",
     "ConcurrentHashMap does not throw {{c1::ConcurrentModificationException}} during iteration because it uses weakly consistent iterators."),

    ("WeakHashMap",
     "Map with keys wrapped in WeakReferences.",
     "Allows keys to be garbage collected when no longer strongly referenced elsewhere.",
     "Storing values that contain strong references back to their keys, preventing GC.",
     "Keys wrapped in WeakReference. Entry is removed when GC collects the key. Useful for metadata caching.",
     "A Map implementation where keys are wrapped in weak references is {{c1::WeakHashMap}}.",
     "In WeakHashMap, entries are automatically removed when their {{c1::keys}} are garbage collected."),

    ("IdentityHashMap",
     "Map comparing keys using reference equality (==) instead of equals().",
     "Useful for graph algorithms or topology tracking where instance identity matters.",
     "Using it as a general-purpose map, expecting logical equals() comparisons.",
     "Reference equality (==) comparisons. Uses System.identityHashCode() for hashing. Not a general-purpose Map.",
     "A Map implementation that uses reference equality (==) instead of equals() is {{c1::IdentityHashMap}}.",
     "IdentityHashMap compares keys using {{c1::==}} and handles hash codes using {{c2::System.identityHashCode()}}."),

    ("SortedMap",
     "Interface representing a Map sorted in ascending key order.",
     "Provides firstKey(), lastKey(), and range views.",
     "Inserting non-comparable keys, causing ClassCastException.",
     "Interface for sorted maps. Provides subMap(), headMap(), tailMap(), firstKey(), lastKey().",
     "An interface representing a Map sorted in ascending order of its keys is {{c1::SortedMap}}.",
     "SortedMap provides key range operations including {{c1::subMap()}}, {{c2::headMap()}}, and {{c3::tailMap()}}."),

    ("NavigableMap",
     "Interface extending SortedMap with closest-match search methods.",
     "Allows ceilingEntry(), floorEntry(), lowerEntry(), and higherEntry() operations.",
     "Mixing up floorKey() (greatest key <= k) with lowerKey() (greatest key < k).",
     "Extends SortedMap. Navigation methods: floorEntry, ceilingEntry, lowerKey, higherKey, descendingMap.",
     "An interface extending SortedMap with navigation search methods is {{c1::NavigableMap}}.",
     "In NavigableMap, {{c1::floorEntry()}} returns the entry for the greatest key <= target, while {{c2::lowerEntry()}} is for key < target."),

    ("When to use Map?",
     "Use when data is represented as key-value pairs for fast lookup.",
     "Allows mapping identifiers to objects.",
     "Using key-value lookup when simple Set uniqueness or List ordering is sufficient.",
     "Use Map for key-value association. HashMap (default/fast), LinkedHashMap (ordering), TreeMap (sorted/navigable).",
     "You should use a Map when you need to store data in {{c1::key-value}} pairs.",
     "For O(1) lookups, choose {{c1::HashMap}}; if keys must be sorted, choose {{c2::TreeMap}}."),

    ("Iterator",
     "Interface for forward traversal and element removal on collections.",
     "Provides safe element deletion during traversal via iterator.remove().",
     "Calling list.remove() inside an active iterator loop instead of iterator.remove().",
     "Forward traversal of Collection. Methods: hasNext(), next(), remove(). remove() is the only safe way to delete items during loop.",
     "An object that enables forward traversal and safe element removal in a collection is an {{c1::Iterator}}.",
     "To safely remove an element while iterating a collection, you must call the iterator's own {{c1::remove()}} method."),

    ("ListIterator",
     "Bidirectional Iterator designed specifically for Lists.",
     "Enables traversing back and forth, modifying, and adding elements during iteration.",
     "Attempting to use ListIterator on Sets or Maps (only works on Lists).",
     "Bidirectional list iterator. Adds hasPrevious(), previous(), set(), add(), nextIndex(), previousIndex().",
     "An iterator interface that supports bidirectional traversal and modification of Lists is {{c1::ListIterator}}.",
     "Unlike standard Iterator, ListIterator supports moving backward using {{c1::hasPrevious()}} and {{c2::previous()}}."),

    # Topic 6
    ("Fail-fast iterator",
     "Iterator that throws ConcurrentModificationException if the collection is structurally modified.",
     "Prevents non-deterministic behavior under concurrent modification.",
     "Structural modification of a collection using collection methods during a for-each loop.",
     "Throws ConcurrentModificationException. Tracks modCount vs expectedModCount. ArrayList/HashSet iterators are fail-fast.",
     "An iterator that throws ConcurrentModificationException upon detecting modification outside the iterator is {{c1::Fail-fast iterator}}.",
     "Fail-fast iterators detect structural changes by comparing their expectedModCount with the collection's {{c1::modCount}}."),

    ("Fail-safe iterator",
     "Iterator operating on a copy/snapshot, allowing modification without exceptions.",
     "Used in concurrent collections to prevent ConcurrentModificationException.",
     "Using snapshot iterators in write-heavy loops, causing excessive garbage collection.",
     "Operates on snapshot/copy (CopyOnWriteArrayList) or weakly consistent view (ConcurrentHashMap). No exception thrown.",
     "An iterator that operates on a copy or snapshot, preventing ConcurrentModificationException, is {{c1::Fail-safe iterator}}.",
     "Fail-safe iterators used in CopyOnWriteArrayList iterate over a {{c1::snapshot array}}, ignoring subsequent writes."),

    ("ConcurrentModificationException",
     "Exception thrown when structural modification occurs on a collection during iteration.",
     "Guards against modification conflicts during traversal.",
     "Failing to use Iterator.remove() when removing items from standard lists during loops.",
     "Runtime exception. Triggered by modCount mismatch. Thrown by next() or remove() in fail-fast iterators.",
     "The runtime exception thrown when a collection is modified structurally during active iteration is {{c1::ConcurrentModificationException}}.",
     "ConcurrentModificationException is thrown when a collection is modified via its own methods instead of the {{c1::iterator's}} remove()."),

    ("Collections.sort",
     "Static utility method to sort a List in place.",
     "Uses Timsort to sort lists in O(N log N) time.",
     "Attempting to sort an unmodifiable list, which throws UnsupportedOperationException.",
     "Sorts List in place. Uses modified Timsort. stable sort.",
     "The static utility method used to sort a List in place is {{c1::Collections.sort}}.",
     "Collections.sort internally uses the {{c1::Timsort}} algorithm, which guarantees a time complexity of {{c2::O(N log N)}}."),

    ("Collections.reverse",
     "Static utility method that reverses the elements in a List.",
     "Reverses elements in place in O(N) time.",
     "Attempting to reverse arrays directly (only works on lists; use Arrays for arrays).",
     "Reverses List in place. Swaps elements from ends towards center.",
     "The static utility method used to reverse the order of elements in a List in place is {{c1::Collections.reverse}}.",
     "Collections.reverse operates on a List in place, running in {{c1::O(N)}} linear time complexity."),

    ("Collections.shuffle",
     "Static utility method that randomly permutes a List.",
     "Useful for generating random permutations.",
     "Calling shuffle on a large LinkedList, which is slow due to O(N) random access.",
     "Randomly permutes List. Uses default Random source. Slow on LinkedList due to positional accesses.",
     "The static utility method used to randomly permute elements in a List in place is {{c1::Collections.shuffle}}.",
     "Shuffling a LinkedList using Collections.shuffle is inefficient because it requires {{c1::O(N)}} random access lookups."),

    ("Collections.max",
     "Static utility method returning the maximum element of a collection.",
     "Iterates through the collection to find the maximum in O(N) time.",
     "Calling max on a collection containing nulls if the comparator doesn't support nulls.",
     "Iterates Collection to find max. O(N) complexity. Requires Comparable elements or Comparator.",
     "The static utility method that returns the maximum element of a collection is {{c1::Collections.max}}.",
     "Collections.max runs in {{c1::O(N)}} linear time because it must inspect every element in the collection."),

    ("Collections.min",
     "Static utility method returning the minimum element of a collection.",
     "Iterates through the collection to find the minimum in O(N) time.",
     "Calling min on an empty collection, which throws NoSuchElementException.",
     "Iterates Collection to find min. O(N) complexity. Throws NoSuchElementException if empty.",
     "The static utility method that returns the minimum element of a collection is {{c1::Collections.min}}.",
     "Calling Collections.min on an empty collection throws a {{c1::NoSuchElementException}}."),

    # Topic 7
    ("Collections.unmodifiableList",
     "Returns an unmodifiable read-only wrapper view of a List.",
     "Protects a list from direct user modification.",
     "Assuming the returned list is immutable; modifications to the backing list still propagate.",
     "Returns wrapper view. Throws UnsupportedOperationException on modifications. Backing list modifications propagate.",
     "The static method that returns a read-only unmodifiable view of a backing List is {{c1::Collections.unmodifiableList}}.",
     "An unmodifiableList view is not fully immutable; modifications to the original {{c1::backing list}} will be reflected in the view."),

    ("Collections.synchronizedList",
     "Returns a thread-safe synchronized wrapper view of a List.",
     "Synchronizes individual operations to allow thread-safe access.",
     "Iterating over the synchronized list without manual synchronization on the list object.",
     "Returns thread-safe wrapper. All methods synchronized on wrapper object. Iterators must be manually synchronized.",
     "The static method that returns a synchronized thread-safe wrapper for a List is {{c1::Collections.synchronizedList}}.",
     "To iterate safely over a synchronizedList under concurrency, you must manually synchronize on the {{c1::list object}}."),

    ("Arrays.sort",
     "Static utility method to sort an array in place.",
     "Uses Dual-Pivot Quicksort for primitives and Timsort for objects.",
     "Sorting arrays of custom objects that do not implement Comparable.",
     "Sorts array in place. Primitives: Dual-Pivot Quicksort (unstable, O(N log N)). Objects: Timsort (stable, O(N log N)).",
     "The static utility method used to sort arrays in place is {{c1::Arrays.sort}}.",
     "For primitive arrays, Arrays.sort uses {{c1::Dual-Pivot Quicksort}}; for object arrays, it uses {{c2::Timsort}}."),

    ("Arrays.binarySearch",
     "Static utility method to search a sorted array.",
     "Returns index of target, or a negative value representing insertion point if not found.",
     "Calling binarySearch on an unsorted array, leading to undefined results.",
     "Searches sorted array. If found, returns index. If not, returns -(insertion point) - 1. Requires sorted array.",
     "The static method to perform binary search on a sorted array is {{c1::Arrays.binarySearch}}.",
     "If an element is not found, Arrays.binarySearch returns {{c1::-(insertion point) - 1}}."),

    ("Arrays.asList",
     "Static utility method returning a fixed-size list backed by an array.",
     "Changes in the list write through to the array and vice versa.",
     "Calling add() or remove() on the returned fixed-size list.",
     "Fixed-size list wrapper. Element updates write through to array. add/remove throws UnsupportedOperationException.",
     "The static utility method that returns a fixed-size List backed by the specified array is {{c1::Arrays.asList}}.",
     "Modifications using set() on an Arrays.asList list will write through to the {{c1::backing array}}."),

    ("Arrays.copyOf",
     "Static utility method to copy an array, padding or truncating if necessary.",
     "Allocates a new array and copies elements.",
     "Assuming copyOf performs a deep copy of object arrays (it performs a shallow copy).",
     "Copies array. Allocates new array. Pads with default values if new length is larger. Shallow copy.",
     "The static utility method used to copy an array and return a new array of a specific length is {{c1::Arrays.copyOf}}.",
     "For object arrays, Arrays.copyOf perform a {{c1::shallow copy}}, copying object references rather than cloning them."),

    ("Arrays.equals",
     "Static utility method to compare two 1D arrays for equality.",
     "Returns true if both arrays contain equal elements in the same order.",
     "Using equals() on multi-dimensional arrays, which only checks 1D reference identity.",
     "Compares 1D arrays. Checks element equality (equals() for objects). Returns false for nested multidimensional arrays.",
     "The static utility method to compare two 1D arrays for element equality is {{c1::Arrays.equals}}.",
     "Arrays.equals does not work for multi-dimensional arrays; it only compares references of the {{c1::nested array}} elements."),

    ("Arrays.deepEquals",
     "Static utility method to recursively compare multi-dimensional arrays.",
     "Enables structural comparison of nested arrays.",
     "Calling deepEquals on non-array object references.",
     "Recursively compares multidimensional arrays for element-by-element equality.",
     "The static utility method to recursively compare multi-dimensional arrays for deep equality is {{c1::Arrays.deepEquals}}.",
     "To structurally compare nested arrays like int[][] or String[][], you should use {{c1::Arrays.deepEquals}}.")
]

def enrich_basic_tsv():
    path = "/home/fhu_thjen/projects/learning-java/19-collections-framework/anki/basic.tsv"
    with open(path, "r", encoding="utf-8") as f:
        reader = csv.reader(f, delimiter="\t")
        header = next(reader)
        rows = list(reader)
    
    # 56 concepts
    # 2*i + 1 -> What should you know about C?
    # 2*i + 2 -> Why does C matter?
    # 113 + i -> What is a common mistake?
    for i, (name, defn, importance, mistake, extra, c1, c2) in enumerate(concepts):
        # 1. basic card (what should you know)
        idx1 = 2 * i
        rows[idx1][1] = f"What should you know about `{name}` in Collections Framework?"
        rows[idx1][2] = defn
        
        # 2. basic card (why does it matter)
        idx2 = 2 * i + 1
        rows[idx2][1] = f"Why does `{name}` matter in Java?"
        rows[idx2][2] = importance
        
        # 3. common mistake card
        idx3 = 112 + i
        rows[idx3][1] = f"What is a common mistake with `{name}`?"
        rows[idx3][2] = mistake

    with open(path, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t")
        writer.writerow(header)
        writer.writerows(rows)
    print("Enriched basic.tsv")

def enrich_basic_extra_tsv():
    path = "/home/fhu_thjen/projects/learning-java/19-collections-framework/anki/basic-extra.tsv"
    with open(path, "r", encoding="utf-8") as f:
        reader = csv.reader(f, delimiter="\t")
        header = next(reader)
        rows = list(reader)
        
    for i, (name, defn, importance, mistake, extra, c1, c2) in enumerate(concepts):
        # collections_framework-extra-(2*i + 1) -> Explain deeply
        idx1 = 2 * i
        rows[idx1][1] = f"Explain `{name}` deeply enough for review."
        rows[idx1][2] = defn
        
        # Build a long, detailed combined string for the Extra field to satisfy the deep card length constraint
        combined_extra1 = (
            f"Meaning & Detailed Concept:\n{defn}\n\n"
            f"Why this is important in development:\n{importance}\n\n"
            f"Implementation details and rules:\n{extra}\n\n"
            f"Common mistake/gotcha to watch out for:\n{mistake}"
        )
        rows[idx1][3] = combined_extra1
        
        # collections_framework-extra-(2*i + 2) -> What confusion
        idx2 = 2 * i + 1
        rows[idx2][1] = f"What confusion should you avoid with `{name}`?"
        rows[idx2][2] = f"Do not confuse it with other implementations; make sure you understand its exact contract."
        
        combined_extra2 = (
            f"Avoid this common trap / bug:\n{mistake}\n\n"
            f"Why this concept matters for correctness and performance:\n{importance}\n\n"
            f"Detailed context and examples:\n{extra}"
        )
        rows[idx2][3] = combined_extra2

    with open(path, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t")
        writer.writerow(header)
        writer.writerows(rows)
    print("Enriched basic-extra.tsv")

def enrich_cloze_tsv():
    path = "/home/fhu_thjen/projects/learning-java/19-collections-framework/anki/cloze.tsv"
    with open(path, "r", encoding="utf-8") as f:
        reader = csv.reader(f, delimiter="\t")
        header = next(reader)
        rows = list(reader)
        
    for i, (name, defn, importance, mistake, extra, c1, c2) in enumerate(concepts):
        idx1 = 2 * i
        rows[idx1][1] = c1
        rows[idx1][2] = f"Why it matters: {importance}\n\nDetails: {extra}"
        
        idx2 = 2 * i + 1
        rows[idx2][1] = c2
        rows[idx2][2] = f"Gotcha: {mistake}\n\nDetails: {extra}"

    with open(path, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t")
        writer.writerow(header)
        writer.writerows(rows)
    print("Enriched cloze.tsv")

# We will overwrite code-question.tsv with 32 high-quality programming questions
code_questions = [
    ("collections_framework-code-001",
     "What is printed by this code?",
     "Set<String> names = new HashSet<>();\nnames.add(\"A\");\nnames.add(\"A\");\nSystem.out.println(names.size());",
     "1",
     "HashSet rejects duplicates based on equals() and hashCode() checks. Adding 'A' twice results in a set containing a single element.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),
     
    ("collections_framework-code-002",
     "What is printed when this code is executed?",
     "ArrayList<Integer> list = new ArrayList<>(10);\nSystem.out.println(list.size());",
     "0",
     "The constructor parameter 10 sets the initial capacity of the underlying array, but the list's logical size remains 0 until elements are added.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-003",
     "What is the result of running this code?",
     "List<String> list = new ArrayList<>(List.of(\"A\", \"B\"));\nfor (String s : list) {\n    if (s.equals(\"A\")) list.remove(s);\n}",
     "ConcurrentModificationException",
     "Modifying a collection structurally during enhanced for-loop iteration using collection methods throws ConcurrentModificationException. Use Iterator.remove() instead.",
     "19-collections-framework/theory/06-fail-fast-iterator-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-004",
     "What is printed after executing this code?",
     "Collection<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4));\nnums.removeIf(n -> n % 2 == 0);\nSystem.out.println(nums);",
     "[1, 3]",
     "removeIf takes a Predicate and removes all elements that satisfy the condition (even numbers in this case).",
     "19-collections-framework/theory/01-what-is-the-collection-framework-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-005",
     "What is printed by this code?",
     "List<String> list = new ArrayList<>(List.of(\"A\", \"B\", \"C\"));\nList<String> sub = list.subList(0, 2);\nsub.set(0, \"Z\");\nSystem.out.println(list.get(0));",
     "Z",
     "The list returned by subList is backed by the original list, so changes to the sublist propagate back to the original list.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-006",
     "What is the result of running this code?",
     "Set<String> set = new TreeSet<>();\nset.add(null);",
     "NullPointerException",
     "TreeSet sorts elements using Comparable natural order or a custom Comparator. Comparing any object to null throws a NullPointerException.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-007",
     "What is the difference between offer() and add() when a Queue's capacity is full?",
     "// Queue capacity check",
     "offer() returns false; add() throws IllegalStateException",
     "Queue defines add() to throw IllegalStateException when capacity is exceeded, while offer() returns false on failure.",
     "19-collections-framework/theory/01-what-is-the-collection-framework-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-008",
     "What is printed by this Deque LIFO stack operation?",
     "Deque<String> stack = new ArrayDeque<>();\nstack.push(\"A\");\nstack.push(\"B\");\nSystem.out.println(stack.peek());",
     "B",
     "push() inserts elements at the front of the Deque (the head of the stack), so peek() returns the most recently pushed element.",
     "19-collections-framework/theory/01-what-is-the-collection-framework-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-009",
     "What is printed when this code is executed?",
     "Map<String, Integer> map = new HashMap<>();\nSystem.out.println(map.put(\"A\", 1));\nSystem.out.println(map.put(\"A\", 2));",
     "null then 1",
     "Map.put() returns the previous value associated with the key, or null if there was no prior mapping for the key.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-010",
     "By how much does ArrayList typically grow when it runs out of capacity?",
     "// Growth factor of ArrayList",
     "1.5x of current capacity",
     "ArrayList increases its capacity by approximately 50% (newCapacity = oldCapacity + (oldCapacity >> 1)) using System.arraycopy().",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-011",
     "Why does LinkedList use significantly more memory than ArrayList for the same elements?",
     "// Memory comparison",
     "Every element is wrapped in a Node object containing two references (next, prev) and the value reference",
     "LinkedList allocates a Node object for every entry, incurring object header and three pointer references overhead.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-012",
     "Why is the Vector class obsolete for single-threaded code?",
     "// Thread safety overhead",
     "All public methods are synchronized, incurring unnecessary locking overhead",
     "Using Vector in single-threaded scenarios hurts performance due to useless lock checks on every operation.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-013",
     "What is the compile/run result of this code?",
     "Stack<String> stack = new Stack<>();\nstack.push(\"A\");\nstack.add(0, \"B\");\nSystem.out.println(stack.pop());",
     "B",
     "Since Stack extends Vector, it inherits all Vector methods like add(index, element), allowing elements to be inserted at the bottom, violating LIFO rules.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-014",
     "What is the time complexity of get(index) for ArrayList and LinkedList?",
     "// Time complexity comparison",
     "ArrayList is O(1); LinkedList is O(N)",
     "ArrayList uses direct array offset calculation; LinkedList must traverse nodes from head or tail.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-015",
     "Identify the interface that maintains insertion order and allows duplicate elements.",
     "// Ordering and duplicates",
     "The List interface",
     "The List interface specifies an ordered collection that permits duplicate elements.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-016",
     "Does HashSet guarantee that the iteration order remains identical after adding new elements?",
     "// Order stability",
     "No",
     "HashSet makes no guarantees about iteration order. Adding elements can trigger table resizing/rehashing, changing bucket positions.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-017",
     "What is printed by this code?",
     "Set<String> s2 = new LinkedHashSet<>(List.of(\"B\", \"A\"));\nSystem.out.println(s2);",
     "[B, A]",
     "LinkedHashSet preserves insertion order, so iterating or printing it always matches the order items were added.",
     "19-collections-framework/theory/02-arraylist-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-018",
     "What happens when running this code?",
     "class Person {}\nSet<Person> set = new TreeSet<>();\nset.add(new Person());",
     "ClassCastException",
     "TreeSet sorts elements using Comparable. Since Person does not implement Comparable, it throws ClassCastException.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-019",
     "What is printed by this code?",
     "SortedSet<Integer> set = new TreeSet<>(List.of(40, 10, 30));\nSystem.out.println(set.first() + \" \" + set.last());",
     "10 40",
     "SortedSet maintains elements in ascending order, so first() returns the lowest element (10) and last() returns the highest (40).",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-020",
     "What is printed by this code?",
     "NavigableSet<Integer> set = new TreeSet<>(List.of(10, 20, 30));\nSystem.out.println(set.floor(25) + \" \" + set.higher(20));",
     "20 30",
     "floor(e) returns the greatest element <= e (20); higher(e) returns the least element > e (30).",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-021",
     "How does TreeSet detect and reject duplicate elements?",
     "// Duplicate check mechanism",
     "It calls compareTo() or compare() and checks if the result is 0",
     "TreeSet uses the comparator/comparable return value. It completely ignores equals() and hashCode() for duplicate checks.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-022",
     "What is the consequence of overriding equals() but not hashCode() when using HashMap?",
     "// Hashing contract",
     "Equal keys generate different hashCodes, placing them in different buckets and allowing duplicate keys",
     "If x.equals(y) is true, their hashCodes must match. Otherwise, map.get(x) won't find the entry put with key y.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-023",
     "What is printed by this code?",
     "PriorityQueue<Integer> pq = new PriorityQueue<>();\npq.offer(30);\npq.offer(10);\npq.offer(20);\nSystem.out.println(pq.peek());",
     "10",
     "PriorityQueue is a min-heap by default, so peek() always retrieves the smallest element.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-024",
     "What happens when running this code?",
     "Deque<Integer> deque = new ArrayDeque<>();\ndeque.add(null);",
     "NullPointerException",
     "ArrayDeque does not allow null elements and throws NullPointerException on any attempt to insert null.",
     "19-collections-framework/theory/03-treeset-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-025",
     "What is printed when this code is executed?",
     "Queue<Integer> q = new LinkedList<>();\nSystem.out.println(q.poll());\nq.remove();",
     "null then throws NoSuchElementException",
     "poll() returns null on an empty queue, while remove() throws NoSuchElementException.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-026",
     "How do you enqueue and dequeue elements in a FIFO queue using Deque?",
     "// Deque FIFO methods",
     "Use offerLast() to enqueue, and pollFirst() to dequeue",
     "To maintain FIFO queue behavior using Deque, you insert at the tail (offerLast) and remove from the head (pollFirst).",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-027",
     "How do you push and pop elements in a LIFO stack using Deque?",
     "// Deque LIFO methods",
     "Use push() to insert elements, and pop() to remove elements",
     "Deque provides push() (front insert) and pop() (front remove) which operate on the head of the deque.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-028",
     "At what bucket size threshold does a HashMap convert a linked list to a tree?",
     "// Treeification threshold",
     "When list size exceeds 8 and total capacity is at least 64",
     "HashMap converts a bucket to a red-black tree (TREEIFY_THRESHOLD = 8) if the capacity is also >= 64.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-029",
     "What must you override in LinkedHashMap to build a fixed-size cache?",
     "// Cache eviction override",
     "removeEldestEntry(Map.Entry eldest) returning true when size exceeds capacity",
     "Overriding removeEldestEntry to return true when size() > capacity automatically evicts the oldest entry on put.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-030",
     "What is printed by this code?",
     "TreeMap<String, Integer> map = new TreeMap<>(Collections.reverseOrder());\nmap.put(\"A\", 1);\nmap.put(\"B\", 2);\nSystem.out.println(map.firstKey());",
     "B",
     "Since reverseOrder is specified, B is sorted before A, so firstKey() returns B.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-031",
     "What happens when running this code?",
     "Map<String, Integer> table = new Hashtable<>();\ntable.put(null, 1);",
     "NullPointerException",
     "Hashtable is synchronized and does not allow null keys or null values, throwing NullPointerException.",
     "19-collections-framework/theory/04-linkedlist-as-queue-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code"),

    ("collections_framework-code-032",
     "Is map.putIfAbsent(k, v) thread-safe and atomic on ConcurrentHashMap?",
     "// Atomic Map write",
     "Yes",
     "putIfAbsent is guaranteed to be thread-safe and atomic on ConcurrentHashMap, preventing race conditions.",
     "19-collections-framework/theory/05-concurrenthashmap-concepts.md | https://docs.oracle.com/javase/tutorial/collections/",
     "java::core::collections-framework java::code")
]

def enrich_code_questions_tsv():
    path = "/home/fhu_thjen/projects/learning-java/19-collections-framework/anki/code-question.tsv"
    header = ["ID", "Question", "Code", "Answer", "Explanation", "Source", "Tags"]
    
    with open(path, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t")
        writer.writerow(header)
        writer.writerows(code_questions)
    print("Enriched code-question.tsv")

if __name__ == "__main__":
    enrich_basic_tsv()
    enrich_basic_extra_tsv()
    enrich_cloze_tsv()
    enrich_code_questions_tsv()
