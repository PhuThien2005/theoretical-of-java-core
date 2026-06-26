# Collections Framework - Phần 3 (Collections Framework - Part 3)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, chứ không chỉ là từ vựng rời rạc.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `TreeSet` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |
| `SortedSet` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |
| `NavigableSet` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |
| `When to use Set?` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |
| `Duplicate removal mechanism` | Cơ chế loại bỏ trùng lặp là một khái niệm cụ thể trong Collections Framework; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `Role of equals() and hashCode()` | equals() định nghĩa sự bằng nhau về mặt logic giữa các đối tượng. |
| `PriorityQueue` | PriorityQueue loại bỏ các phần tử theo độ ưu tiên thay vì thứ tự chèn. |
| `ArrayDeque` | ArrayDeque là một Deque mảng có thể thay đổi kích thước thường được ưu tiên cho hành vi ngăn xếp hoặc hàng đợi. |

---

## Ghi chú chi tiết (Detailed Notes)

### TreeSet

`TreeSet` là một triển khai `NavigableSet` được hỗ trợ bởi một thực thể `TreeMap`.
- **Thứ tự**: Được sắp xếp theo thứ tự tự nhiên (triển khai `Comparable`) hoặc một `Comparator` tùy chỉnh được truyền vào khi khởi tạo.
- **Độ phức tạp**: O(log N) cho các thao tác cốt lõi (`add`, `remove`, `contains`).
- **Hạn chế**: Không cho phép chứa phần tử `null` (ném ra `NullPointerException` vì nó cần sắp xếp/so sánh các phần tử).

### SortedSet & NavigableSet

- **SortedSet**: Một giao diện đại diện cho một Set được sắp xếp theo thứ tự tăng dần. Cung cấp các thao tác như lấy phần tử đầu tiên `first()`, phần tử cuối cùng `last()`, và các dạng hiển thị khoảng dữ liệu `subSet(from, to)`.
- **NavigableSet**: Kế thừa `SortedSet` và bổ sung thêm các phương thức định vị/ước lượng như `lower()`, `floor()`, `ceiling()`, và `higher()` để tìm các kết quả khớp gần nhất, cũng như `pollFirst()` và `pollLast()`.

### Khi nào nên dùng Set? (When to use Set?)

Sử dụng một Set khi không cho phép chứa các phần tử trùng lặp.
- **HashSet**: Lựa chọn mặc định. Các thao tác O(1) nhanh chóng, không đảm bảo thứ tự.
- **LinkedHashSet**: Sử dụng khi bạn cần duy trì thứ tự chèn phần tử.
- **TreeSet**: Sử dụng khi bạn cần các phần tử được sắp xếp hoặc cần các phương thức điều hướng (navigation).

### So sánh HashSet với TreeSet và LinkedHashSet (Comparing HashSet vs TreeSet vs LinkedHashSet)

| Đặc tính | HashSet | TreeSet | LinkedHashSet |
| --- | --- | --- | --- |
| **Cấu trúc bên dưới** | HashMap | TreeMap (Cây đỏ đen) | HashMap + Danh sách liên kết kép |
| **Độ phức tạp thời gian** | O(1) | O(log N) | O(1) |
| **Thứ tự lặp** | Không xác định | Đã sắp xếp | Thứ tự chèn |
| **Phần tử Null** | Được phép (một) | Bị từ chối (NullPointerException)| Được phép (một) |

### Cơ chế loại bỏ phần tử trùng lặp (Duplicate Removal Mechanism)

Cách các Set xác định phần tử trùng lặp:
- **HashSet / LinkedHashSet**: Kiểm tra xem `obj1.hashCode() == obj2.hashCode()`. Nếu các mã băm khớp nhau, chúng sẽ gọi `obj1.equals(obj2)`. Nếu `equals` trả về true, phần tử đó bị từ chối do trùng lặp.
- **TreeSet**: Kiểm tra thứ tự sắp xếp. Gọi `comparator.compare(obj1, obj2)` hoặc `obj1.compareTo(obj2)`. Nếu kết quả trả về `0`, phần tử đó bị từ chối do trùng lặp. **Lưu ý**: TreeSet hoàn toàn bỏ qua `equals()` và `hashCode()` cho việc phát hiện trùng lặp.

### Vai trò của equals() và hashCode() (Role of equals() and hashCode())

Để `HashSet` và `HashMap` hoạt động chính xác:
1. **Tính phản xạ (Reflexive)**: `x.equals(x)` phải trả về true.
2. **Tính đối xứng (Symmetric)**: Nếu `x.equals(y)` trả về true, thì `y.equals(x)` phải trả về true.
3. **Tính bắc cầu (Transitive)**: Nếu `x.equals(y)` và `y.equals(z)` đều trả về true, thì `x.equals(z)` phải trả về true.
4. **Tính nhất quán (Consistency)**: Nếu `x.equals(y)` trả về true, nó vẫn giữ nguyên kết quả trừ khi các trường dữ liệu bị thay đổi.
5. **Hợp đồng hashCode (hashCode contract)**: Nếu `x.equals(y)` trả về true, thì `x.hashCode() == y.hashCode()` BẮT BUỘC phải trả về true. Nếu `x.equals(y)` trả về false, mã băm của chúng không nhất thiết phải khác nhau (nhưng nên khác nhau để tăng hiệu năng).

### PriorityQueue

Một hàng đợi ưu tiên không giới hạn dựa trên cấu trúc nhị phân (binary heap).
- **Thứ tự**: Đầu hàng đợi (head) là phần tử nhỏ nhất theo cách sắp xếp.
- **Hiệu năng**: O(log N) cho việc thêm (`offer`) và xóa (`poll`); O(1) cho việc truy xuất (`peek`).
- **Giá trị null**: Từ chối `null`.

### ArrayDeque

Một triển khai mảng có thể thay đổi kích thước của giao diện `Deque`.
- **Hiệu năng**: Triển khai mảng vòng (circular array). Nhanh hơn `Stack` khi sử dụng làm ngăn xếp, và nhanh hơn `LinkedList` khi sử dụng làm hàng đợi.
- **Dung lượng**: Không giới hạn dung lượng; tự động tăng khi cần. Từ chối `null`.

---

**Ví dụ Code có thể chạy (TreeSet với Comparator tùy chỉnh & PriorityQueue) (Runnable Code Example (TreeSet with Custom Comparator & PriorityQueue)):**
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

## Các lỗi thường gặp (Common Mistakes)

### 1. compareTo không nhất quán với equals (Inconsistent compareTo and equals)
Nếu `compareTo` trả về `0` cho hai đối tượng, nhưng `equals` trả về `false`, việc chèn chúng vào một `TreeSet` sẽ khiến phần tử thứ hai bị loại bỏ. Hãy luôn đảm bảo rằng `(x.compareTo(y) == 0) == x.equals(y)`.

### 2. Chèn các phần tử không thể so sánh (non-Comparable) vào TreeSet hoặc PriorityQueue (Inserting non-Comparable elements into TreeSet or PriorityQueue)
Nếu bạn khởi tạo `new TreeSet<>()` và cố gắng thêm các đối tượng tùy chỉnh không triển khai giao diện `Comparable` (mà không truyền một `Comparator` tùy chỉnh vào hàm khởi dựng), một ngoại lệ `ClassCastException` sẽ bị ném ra lúc chạy ngay ở lần thêm phần tử đầu tiên.

### 3. Duyệt qua một PriorityQueue với mong muốn các phần tử có thứ tự (Iterating a PriorityQueue expecting order)
Việc gọi vòng lặp `for (Integer i : priorityQueue)` hoặc sử dụng một `Iterator` sẽ **KHÔNG** duyệt qua hàng đợi theo thứ tự ưu tiên. Iterator duyệt trực tiếp qua mảng heap nhị phân bên dưới vốn không được sắp xếp. Để lấy các phần tử theo đúng thứ tự ưu tiên, bạn phải rút chúng ra lần lượt bằng phương thức poll: `while(!pq.isEmpty()) { pq.poll(); }`.

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Khái niệm nào ở đây dễ là những bẫy câu hỏi phỏng vấn?

---

## Tại sao TreeSet và TreeMap dựa vào Comparable/Comparator (Why TreeSet and TreeMap Rely on Comparable/Comparator)

Không giống như `HashSet` và `HashMap` sử dụng các xô băm (hashing buckets), `TreeSet` và `TreeMap` được hỗ trợ bởi cấu trúc Cây Đỏ Đen (Red-Black Tree), là một cây tìm kiếm nhị phân tự cân bằng. Để chèn hoặc truy xuất bất kỳ nút nào, cây phải điều hướng sang trái hoặc sang phải bắt đầu từ gốc dựa trên việc nút đích nhỏ hơn hay lớn hơn nút hiện tại. Việc điều hướng này yêu cầu một cơ chế sắp xếp tất định, được cung cấp bởi thứ tự tự nhiên của phần tử (`Comparable.compareTo()`) hoặc một `Comparator.compare()` tùy chỉnh. Nếu phép so sánh trả về `0`, cây xác định rằng phần tử đó đã tồn tại, từ chối việc chèn để thực thi ràng buộc tính duy nhất của một `Set` (hoặc ghi đè giá trị trong một `Map`). Do đó, nếu `compareTo()` hoặc `compare()` không nhất quán với `equals()` (nghĩa là chúng trả về kết quả khác không cho các đối tượng bằng nhau về mặt logic theo `equals()`), `TreeSet` sẽ cho phép chứa các phần tử trùng lặp một cách không chính xác, hoặc ngược lại, nếu chúng trả về `0` cho các đối tượng không bằng nhau, nó sẽ loại bỏ các phần tử duy nhất đó.

### Mô hình tư duy (Mental Model)

Một Cây tìm kiếm nhị phân (Binary Search Tree) hoàn toàn dựa vào việc điều hướng so sánh (`<`, `>`, `==`) chứ không dựa vào các xô băm (hash buckets):
```text
                  [ Nút B (Giá trị: 20) ]
                       /         \
                      /           \
                     v             v
  [ Nút A (Giá trị: 10) ]         [ Nút C (Giá trị: 30) ]

Chèn phần tử mới (Giá trị: 15):
1. So sánh 15 với 20 (Gốc) -> 15 < 20 -> Đi sang trái.
2. So sánh 15 với 10 -> 15 > 10 -> Đi sang phải (Chèn vào đây).

Nếu compareTo trả về 0, nó có nghĩa là "Tìm thấy trùng lặp" -> Từ chối chèn.
```

### Ví dụ Code (Code Example)

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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Thao tác `add()` trên `TreeSet`
  → Duyệt Cây Đỏ Đen bằng phương thức `compareTo()` hoặc `compare()`
  → Phép so sánh nút trả về `0`
  → Cây giả định phần tử là trùng lặp
  → Cây từ chối chèn (ngay cả khi `equals()` trả về false)
  → Phần tử bị bỏ qua một cách âm thầm, gây mất mát dữ liệu và sai lệch kiểm tra trùng lặp.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/TreeSet.html (API lớp TreeSet)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html (Tài liệu giao diện Comparable)
