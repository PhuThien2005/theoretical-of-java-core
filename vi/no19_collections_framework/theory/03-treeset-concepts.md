# Khung tập hợp (Collections Framework) - Phần 3

## Mục tiêu học tập (Learning Goal)

Tài liệu này đề cập đến một phần trọng tâm của **Khung tập hợp**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, chứ không phải là các từ vựng rời rạc.

## Khái quát nội dung (Outline Coverage)

- **`TreeSet`** — TreeSet: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`SortedSet`** — SortedSet: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`NavigableSet`** — NavigableSet: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Khi nào nên sử dụng tập hợp (Set)?`** — Khi nào nên sử dụng tập hợp (Set)?: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Cơ chế loại bỏ trùng lặp`** — Cơ chế loại bỏ trùng lặp: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Vai trò của equals() và hashCode()`** — Vai trò của equals() và hashCode(): Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`PriorityQueue`** — PriorityQueue: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`ArrayDeque`** — ArrayDeque: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi chú chi tiết (Detailed Notes)

### TreeSet

`TreeSet` là một lớp hiện thực (implementation) của `NavigableSet` được hỗ trợ bởi một thể hiện (instance) của `TreeMap`.
- **Thứ tự (Ordering)**: Được sắp xếp theo thứ tự tự nhiên (natural ordering) (hiện thực giao diện `Comparable`) hoặc theo một bộ so sánh (Comparator) tùy chỉnh được truyền vào khi khởi tạo.
- **Độ phức tạp (Complexity)**: O(log N) cho các thao tác cốt lõi (`add`, `remove`, `contains`).
- **Hạn chế (Restrictions)**: Không cho phép các phần tử `null` (ném ra ngoại lệ `NullPointerException` vì cần phải sắp xếp/so sánh các phần tử).

### SortedSet và NavigableSet (SortedSet & NavigableSet)

- **SortedSet**: Một giao diện (interface) đại diện cho một tập hợp được sắp xếp theo thứ tự tăng dần. Cung cấp các thao tác như `first()`, `last()`, và các dạng xem phạm vi (range views) `subSet(from, to)`.
- **NavigableSet**: Mở rộng từ `SortedSet` và bổ sung các phương thức điều hướng/ước lượng như `lower()`, `floor()`, `ceiling()`, và `higher()` để tìm kiếm các phần tử khớp gần nhất, cũng như `pollFirst()` và `pollLast()`.

### Khi nào nên sử dụng tập hợp (When to use Set?)

Sử dụng một tập hợp khi các phần tử trùng lặp không được chấp nhận.
- **HashSet**: Lựa chọn mặc định. Các thao tác O(1) nhanh chóng, không đảm bảo thứ tự.
- **LinkedHashSet**: Sử dụng khi bạn cần duy trì thứ tự chèn.
- **TreeSet**: Sử dụng khi bạn cần các phần tử được sắp xếp hoặc cần các phương thức điều hướng.

### So sánh HashSet với TreeSet và LinkedHashSet (Comparing HashSet vs TreeSet vs LinkedHashSet)

**HashSet** sử dụng cấu trúc bên trong là HashMap, độ phức tạp thời gian O(1), thứ tự duyệt chưa xác định, và được phép chứa một phần tử null.

**TreeSet** sử dụng cấu trúc bên trong là TreeMap (Cây Đỏ-Đen (Red-Black tree)), độ phức tạp thời gian O(log N), thứ tự duyệt được sắp xếp, và từ chối phần tử null (ném NullPointerException).

**LinkedHashSet** sử dụng cấu trúc bên trong là HashMap + Danh sách liên kết kép (Doubly-Linked List), độ phức tạp thời gian O(1), thứ tự duyệt theo thứ tự chèn, và được phép chứa một phần tử null.

### Cơ chế loại bỏ trùng lặp

Cách các tập hợp xác định phần tử trùng lặp:
- **HashSet / LinkedHashSet**: Kiểm tra xem `obj1.hashCode() == obj2.hashCode()`. Nếu các mã băm (hashes) khớp nhau, chúng sẽ gọi `obj1.equals(obj2)`. Nếu `equals` trả về true, phần tử đó sẽ bị từ chối do trùng lặp.
- **TreeSet**: Kiểm tra thứ tự sắp xếp. Gọi `comparator.compare(obj1, obj2)` hoặc `obj1.compareTo(obj2)`. Nếu kết quả trả về là `0`, phần tử đó sẽ bị từ chối do trùng lặp. **Lưu ý**: TreeSet hoàn toàn bỏ qua `equals()` và `hashCode()` khi phát hiện trùng lặp.

### Vai trò của equals() và hashCode() (Role of equals() and hashCode())

Để `HashSet` và `HashMap` hoạt động chính xác:
1. **Tính phản xạ (Reflexive)**: `x.equals(x)` phải là true.
2. **Tính đối xứng (Symmetric)**: Nếu `x.equals(y)` là true, thì `y.equals(x)` phải là true.
3. **Tính bắc cầu (Transitive)**: Nếu `x.equals(y)` và `y.equals(z)` là true, thì `x.equals(z)` phải là true.
4. **Tính nhất quán (Consistency)**: Nếu `x.equals(y)` là true, nó vẫn là true trừ khi các trường (fields) thay đổi.
5. **Ràng buộc hashCode (hashCode contract)**: Nếu `x.equals(y)` là true, thì `x.hashCode() == y.hashCode()` BẮT BUỘC phải là true. Nếu `x.equals(y)` là false, các mã băm của chúng không nhất thiết phải khác nhau (nhưng nên khác nhau để tối ưu hiệu năng).

### PriorityQueue

Một hàng đợi ưu tiên không giới hạn (unbounded priority queue) dựa trên cấu trúc đống nhị phân (binary heap).
- **Thứ tự**: Đầu (head) của hàng đợi là phần tử nhỏ nhất theo thứ tự sắp xếp.
- **Hiệu năng (Performance)**: O(log N) cho các thao tác chèn (`offer`) và xóa (`poll`); O(1) cho thao tác lấy ra mà không xóa (`peek`).
- **Các phần tử Null**: Bị từ chối `null`.

### ArrayDeque

Một lớp hiện thực mảng có thể thay đổi kích thước của giao diện `Deque`.
- **Hiệu năng**: Hiện thực dạng mảng vòng (circular array). Nhanh hơn `Stack` khi được sử dụng như một ngăn xếp, và nhanh hơn `LinkedList` khi được sử dụng như một hàng đợi.
- **Sức chứa (Capacity)**: Không giới hạn sức chứa; tự động mở rộng khi cần thiết. Từ chối phần tử `null`.

---

**Ví dụ mã nguồn có thể chạy được (TreeSet với bộ so sánh tùy chỉnh và PriorityQueue) (Runnable Code Example (TreeSet with Custom Comparator & PriorityQueue)):**
```java
import java.util.*;

public class SetQueueExample {
    static class Person implements Comparable<Person> {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public int compareTo(Person other) {
            return this.name.compareTo(other.name); // Sort by name
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    public static void main(String[] args) {
        // 1. TreeSet using natural ordering (Comparable -> name)
        Set<Person> peopleByName = new TreeSet<>();
        peopleByName.add(new Person("Charlie", 30));
        peopleByName.add(new Person("Alice", 25));
        peopleByName.add(new Person("Bob", 35));
        System.out.println("Sorted by Name (Natural): " + peopleByName);
        
        // 2. TreeSet using custom Comparator (by age)
        Set<Person> peopleByAge = new TreeSet<>(Comparator.comparingInt(p -> p.age));
        peopleByAge.addAll(peopleByName);
        System.out.println("Sorted by Age (Comparator): " + peopleByAge);
        
        // 3. PriorityQueue max-heap behavior
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(10);
        maxHeap.offer(30);
        maxHeap.offer(20);
        
        System.out.print("PriorityQueue polling: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " "); // 30 20 10
        }
        System.out.println();
    }
}
```

---

## Các sai lầm thường gặp (Common Mistakes)

### 1. Sự bất nhất giữa compareTo và equals
Nếu `compareTo` trả về `0` cho hai đối tượng, nhưng `equals` trả về `false`, việc chèn chúng vào một `TreeSet` sẽ khiến phần tử thứ hai bị loại bỏ. Luôn đảm bảo rằng `(x.compareTo(y) == 0) == x.equals(y)`.

### 2. Chèn các phần tử không thể so sánh được vào TreeSet hoặc PriorityQueue
Nếu bạn khởi tạo `new TreeSet<>()` và cố gắng thêm các đối tượng tùy chỉnh không hiện thực `Comparable` (mà không truyền một bộ so sánh tùy chỉnh vào hàm khởi tạo), một ngoại lệ `ClassCastException` sẽ bị ném ra tại thời điểm chạy (runtime) ngay lần thêm phần tử đầu tiên.

### 3. Duyệt qua một PriorityQueue với kỳ vọng nhận được thứ tự đã sắp xếp
Việc gọi `for (Integer i : priorityQueue)` hoặc sử dụng một bộ lặp (Iterator) KHÔNG duyệt qua hàng đợi theo thứ tự ưu tiên. Bộ lặp sẽ duyệt trực tiếp qua mảng đống nhị phân bên dưới, vốn không được sắp xếp. Để lấy các phần tử theo đúng thứ tự, bạn phải lấy và xóa chúng một cách tuần tự: `while(!pq.isEmpty()) { pq.poll(); }`.

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Những khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây dễ là các cạm bẫy khi phỏng vấn (interview traps)?

## Tại sao TreeSet và TreeMap phụ thuộc vào Comparable/Comparator

> Xem thêm: Cơ chế hoạt động của Comparable và Comparator để sắp xếp đối tượng, được trình bày chi tiết trong [Ch.20 - Comparable/Comparator](../../no20_comparable_comparator/theory/01-comparable-concepts.md).

Không giống như `HashSet` và `HashMap` sử dụng các ngăn chứa băm (hashing buckets), `TreeSet` và `TreeMap` được hỗ trợ bởi một Cây Đỏ-Đen, một dạng cây tìm kiếm nhị phân tự cân bằng (self-balancing binary search tree). Để chèn hoặc truy xuất bất kỳ nút (node) nào, cây phải điều hướng sang trái hoặc sang phải bắt đầu từ nút gốc (root node) dựa trên việc nút đích nhỏ hơn hay lớn hơn nút hiện tại. Việc điều hướng này đòi hỏi một cơ chế sắp xếp tất định (deterministic sorting mechanism), được cung cấp bởi thứ tự tự nhiên của phần tử (`Comparable.compareTo()`) hoặc bộ so sánh tùy chỉnh (`Comparator.compare()`). Nếu một phép so sánh trả về `0`, cây sẽ xác định rằng phần tử đã tồn tại, từ chối việc chèn để thực thi ràng buộc về tính duy nhất của một tập hợp (hoặc ghi đè giá trị trong một `Map`). Do đó, nếu `compareTo()` hoặc `compare()` không nhất quán với `equals()` (nghĩa là chúng trả về giá trị khác không cho các đối tượng vốn bằng nhau về mặt logic theo `equals()`), `TreeSet` sẽ cho phép các phần tử trùng lặp một cách sai sót, hoặc ngược lại, nếu chúng trả về `0` cho các đối tượng không bằng nhau, nó sẽ loại bỏ các phần tử duy nhất.

### Mô hình tư duy (Mental Model)

Một cây tìm kiếm nhị phân phụ thuộc hoàn toàn vào việc điều hướng so sánh (`<`, `>`, `==`) thay vì các ngăn chứa băm:
```text
                  [ Node B (Value: 20) ]
                       /         \
                      /           \
                     v             v
  [ Node A (Value: 10) ]         [ Node C (Value: 30) ]

Inserting new item (Value: 15):
1. Compare 15 to 20 (Root) -> 15 < 20 -> Go Left.
2. Compare 15 to 10 -> 15 > 10 -> Go Right (Insert here).

If compareTo returns 0, it means "Duplicate Found" -> Reject insertion.
```

### Ví dụ mã nguồn (Code Example)

```java
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetBehaviorDemo {
    static class Item implements Comparable<Item> {
        private final String name;
        private final int value;

        public Item(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return value == item.value && Objects.equals(name, item.name);
        }

        // compareTo is inconsistent with equals (only compares name length)
        @Override
        public int compareTo(Item other) {
            return Integer.compare(this.name.length(), other.name.length());
        }

        @Override
        public String toString() {
            return name + ":" + value;
        }
    }

    public static void main(String[] args) {
        Set<Item> set = new TreeSet<>();
        Item item1 = new Item("Apple", 10);
        Item item2 = new Item("Peach", 20); // Same name length (5), different value
        Item item3 = new Item("Pear", 10);  // Different name length (4), same value

        set.add(item1);
        set.add(item2); // Rejected because name lengths are both 5 (compareTo returns 0)
        set.add(item3); // Accepted because name length is 4 (compareTo returns non-zero)

        System.out.println("Set elements: " + set); 
        // Set elements: [Pear:10, Apple:10]
        
        System.out.println("Contains Peach? " + set.contains(item2)); // Contains Peach? true
        System.out.println("Equals Peach? " + item1.equals(item2));   // Equals Peach? false
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
TreeSet add() operation → Traverses Red-Black Tree using compareTo() or compare() → Node comparison returns 0 → Tree assumes element is a duplicate → Tree rejects insertion (even if equals() returns false) → Element is silently ignored, causing data loss and incorrect duplicates checks
```

## Đường liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/TreeSet.html (Tài liệu API lớp TreeSet)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html (Tài liệu giao diện Comparable)
