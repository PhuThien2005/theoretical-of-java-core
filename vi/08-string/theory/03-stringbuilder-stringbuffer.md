# StringBuilder và StringBuffer (StringBuilder and StringBuffer)

Khi thực hiện các thao tác nối chuỗi thường xuyên (chẳng hạn như bên trong các vòng lặp), tính chất bất biến (immutable) của lớp `String` sẽ dẫn đến chi phí bộ nhớ rất lớn vì mỗi thay đổi đều tạo ra một đối tượng mới trên Heap. Để giải quyết vấn đề này, Java cung cấp các lớp quản lý chuỗi ký tự khả biến (mutable): `StringBuilder` và `StringBuffer`.

---

## Case Study: Tại Sao Phép Nối Chuỗi Trong Vòng Lặp Có Độ Phức Tạp $O(n^2)$ (Case Study: Why String Concatenation in a Loop is $O(n^2)$)

### Vấn Đề: Phép Nối Chuỗi Ngây Thơ (The Problem: Naive Concatenation)

Xét một vòng lặp xây dựng một chuỗi gồm $n$ con số:

```java
// KHÔNG NÊN LÀM THẾ NÀY trong code thực tế
String s = "";
for (int i = 0; i < n; i++) {
    s += i; // Hoặc s = s + i;
}
```

Ở bên dưới, trình biên dịch dịch chuyển lệnh `s += i` tương đương với:
```java
s = new StringBuilder().append(s).append(i).toString();
```

Trong mỗi lần lặp:
1. Một đối tượng `StringBuilder` mới được khởi tạo.
2. Toàn bộ nội dung của chuỗi hiện tại `s` được sao chép từng ký tự vào đối tượng builder mới tạo.
3. Ký tự/số nguyên mới được thêm vào cuối (append).
4. Phương thức `toString()` được gọi, thực hiện sao chép mảng ký tự của builder để cấu tạo nên một đối tượng `String` mới.

Nếu vòng lặp chạy $n$ lần và mỗi lần lặp thực hiện nối thêm một chuỗi nhỏ, độ dài của chuỗi `s` sẽ tăng tuyến tính. Ở lần lặp thứ $k$, JVM sao chép $k$ ký tự.
Tổng số lượng ký tự bị sao chép qua tất cả các lần lặp là:
$$\text{Tổng số ký tự sao chép} = 1 + 2 + 3 + \dots + n = \frac{n(n + 1)}{2} = O(n^2)$$

Điều này dẫn đến:
- **Độ phức tạp thời gian bậc hai ($O(n^2)$):** Thời gian chạy chương trình tăng theo hàm bậc hai của $n$.
- **Hao phí bộ nhớ / Áp lực GC (Memory Churn / GC Pressure):** Có $n$ đối tượng `StringBuilder` tạm thời và $n$ đối tượng `String` tạm thời được cấp phát rồi ngay lập tức bị vứt bỏ, kích hoạt các đợt tạm dừng thu gom rác (garbage collection) liên tục.

### Giải Pháp: Sử Dụng `StringBuilder`

Bằng cách khởi tạo một đối tượng `StringBuilder` duy nhất bên ngoài vòng lặp, chúng ta tránh việc tạo ra các đối tượng tạm thời và các thao tác sao chép mảng dư thừa:

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append(i);
}
String s = sb.toString();
```

Tại đây:
1. Chỉ có duy nhất **một** đối tượng `StringBuilder` được cấp phát.
2. Phương thức `append()` thực hiện sửa đổi trực tiếp (in-place) trên mảng buffer `byte[]`/`char[]` nội bộ của nó.
3. Thao tác sao chép mảng chỉ xảy ra khi mảng buffer hiện tại bị hết dung lượng. Nhờ chiến lược nhân đôi dung lượng (`(dung_lượng_cũ * 2) + 2`), việc thay đổi kích thước mảng xảy ra theo thang logarit ($O(\log n)$ lần).
4. Độ phức tạp khấu hao (amortized complexity) của mỗi lệnh `append()` là $O(1)$.
5. Tổng độ phức tạp thời gian của toàn bộ vòng lặp giảm xuống còn **$O(n)$**.
6. Chỉ có duy nhất **một** đối tượng `String` cuối cùng được tạo ra khi gọi phương thức `.toString()` ở cuối chương trình.

---

## Dung Lượng Và Bộ Đệm Nội Bộ (Internal Buffer and Capacity)

Cả `StringBuilder` và `StringBuffer` đều kế thừa từ một lớp cha trừu tượng ở phạm vi package-private là `AbstractStringBuilder`.
- Chúng quản lý một mảng byte/char buffer khả biến để lưu trữ các ký tự.
- **Length (Độ dài):** Số lượng ký tự thực tế đang được lưu trữ trong buffer.
- **Capacity (Dung lượng):** Tổng kích thước của mảng buffer được cấp phát. Theo mặc định, một đối tượng builder mới tạo có dung lượng ban đầu là **16 ký tự** cộng với độ dài của chuỗi được dùng để khởi tạo nó.

### Thuật Toán Thay Đổi Kích Thước (Resizing Algorithm)
Khi bạn thực hiện append một nội dung vượt quá dung lượng hiện tại, JVM sẽ cấp phát một mảng mới lớn hơn và sao chép nội dung cũ sang. Công thức tăng dung lượng là:
$$\text{Dung lượng mới} = (\text{Dung lượng cũ} \times 2) + 2$$
Nếu dung lượng mới tính theo công thức này vẫn không đủ chứa, JVM sẽ thiết lập dung lượng mới bằng chính xác độ dài của nội dung mới cần lưu.

---

## So Sánh Các Đặc Tính Chi Tiết (Detailed Feature Comparison)

| Đặc tính (Feature) | `String` | `StringBuilder` (Java 5+) | `StringBuffer` (Java 1.0) |
| :--- | :--- | :--- | :--- |
| **Tính khả biến** | Bất biến (Immutable) | Khả biến (Mutable) | Khả biến (Mutable) |
| **An toàn đa luồng** | **An toàn** (nhờ tính bất biến) | **Không an toàn** | **An toàn** (Được đồng bộ) |
| **Hiệu năng** | Chậm nhất (khi thao tác) | **Nhanh nhất** | Chậm (do chi phí khóa) |
| **Vùng lưu trữ** | Heap & String Pool | Heap | Heap |

---

## An Toàn Đa Luồng Và Tranh Chấp Khóa (Thread Safety and Lock Contention)

- **`StringBuffer`:** Tất cả các phương thức ghi/sửa đổi dữ liệu (như `append()`, `insert()`, `delete()`) đều được đánh dấu bằng từ khóa `synchronized`. Điều này đảm bảo rằng chỉ có duy nhất một luồng có thể sửa đổi buffer tại một thời điểm. Tuy nhiên, cơ chế đồng bộ hóa này tiêu tốn chi phí hiệu năng đáng kể:
  - Ngay cả trong một chương trình đơn luồng, việc yêu cầu và giải phóng khóa monitor (monitor locks) vẫn gây ra chi phí đồng bộ luồng.
  - Trong môi trường đa luồng, nếu nhiều luồng cùng cố gắng ghi dữ liệu vào một đối tượng `StringBuffer` đồng thời, nó sẽ gây ra **tranh chấp khóa (lock contention)**, làm chặn (blocking) các luồng và làm suy giảm hiệu năng hệ thống.
- **`StringBuilder`:** Loại bỏ hoàn toàn từ khóa `synchronized`. Nó không an toàn luồng. Nếu nhiều luồng cùng ghi dữ liệu vào một thực thể `StringBuilder` đồng thời, nó sẽ dẫn đến sai lệch dữ liệu hoặc các lỗi chỉ số mảng vượt quá giới hạn. Tuy nhiên, đối với các biến cục bộ khai báo bên trong một phương thức, `StringBuilder` luôn luôn là sự lựa chọn được ưu tiên tối đa vì các biến cục bộ vốn dĩ được giới hạn hoạt động trong một luồng duy nhất (thread-confined).

### Đi Sâu: Chi Phí Đồng Bộ Hóa Và Cơ Chế Tranh Chấp Khóa (Deep-Dive: Synchronization Overhead and Lock Contention Mechanics)

Sự chênh lệch hiệu năng giữa `StringBuilder` và `StringBuffer` bắt nguồn hoàn toàn từ chi phí runtime của cơ chế đồng bộ hóa luồng. Trong `StringBuffer`, mọi phương thức thay đổi trạng thái đều được khai báo với từ khóa `synchronized`, yêu cầu luồng đang thực thi phải chiếm giữ khóa monitor của đối tượng trước khi chạy phương thức và giải phóng khóa đó sau khi hoàn thành. Quá trình này liên quan đến các bước kiểm tra của JVM và hệ điều hành, tạo ra độ trễ ngay cả trong môi trường đơn luồng. Khi nhiều luồng cùng truy cập đồng thời vào một thực thể `StringBuffer` duy nhất, chúng sẽ gặp hiện tượng tranh chấp khóa, khiến các luồng bị chặn và phải thực hiện chuyển cảnh (context-switch), làm giảm nghiêm trọng thông lượng của ứng dụng. Vì `StringBuilder` hoàn toàn không đồng bộ hóa, nó tránh được tất cả các chi phí yêu cầu khóa và thực hiện các thao tác trực tiếp trên mảng buffer nội bộ của nó, giúp nó trở thành lựa chọn vượt trội cho các tác vụ đơn luồng và các biến cục bộ giới hạn trong luồng.

#### Mô Hình So Sánh Tranh Chấp Luồng (Thread Contention Comparison Model)

```mermaid
graph TD
    subgraph StringBuffer [StringBuffer (Đồng bộ - Synchronized)]
        sb[Khóa Monitor của StringBuffer]
        t1[Luồng 1] -->|Chiếm Khóa| sb
        t2[Luồng 2] -->|Bị chặn / Chờ| sb
    end
    subgraph StringBuilder [StringBuilder (Không đồng bộ)]
        sbuilder[Bộ đệm StringBuilder]
        t3[Luồng 3] -->|Ghi Trực tiếp| sbuilder
        t4[Luồng 4] -->|Ghi Trực tiếp / Nguy cơ Race Condition| sbuilder
    end
```

#### Ví Dụ Code Minh Họa Bất Đồng Bộ Không An Toàn (Unsafe Concurrency Demonstration Code Example)

```java
// Thao tác ghi đồng thời không an toàn vào StringBuilder
StringBuilder sb = new StringBuilder();
Runnable task = () -> {
    for (int i = 0; i < 1000; i++) {
        sb.append("A");
    }
};

Thread thread1 = new Thread(task);
Thread thread2 = new Thread(task);
thread1.start();
thread2.start();
try {
    thread1.join();
    thread2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Có thể ném ra lỗi ArrayIndexOutOfBoundsException hoặc in ra độ dài nhỏ hơn 2000!
System.out.println("Expected: 2000, Actual Length: " + sb.length()); 
```

#### Chuỗi Nguyên Nhân - Kết Quả Bất Đồng Bộ StringBuilder Không An Toàn

```text
Ghi dữ liệu đồng thời vào `StringBuilder`
  → Nhiều luồng cùng đọc chung chỉ số ghi nội bộ tại một thời điểm
  → Các luồng cùng ghi đè ký tự vào chung một ô chỉ số mảng
  → Lệnh ghi của luồng này đè lên luồng kia
  → Bộ theo dõi kích thước nội bộ tăng lên không đồng bộ
  → Tạo ra giới hạn mảng bị sai lệch hoặc ném ra ngoại lệ `ArrayIndexOutOfBoundsException`.
```


---

## Các Phương Thức API Quan Trọng (Key API Methods)

### 1. `append()`
Thực hiện nối thêm biểu diễn chuỗi của bất kỳ kiểu dữ liệu nào vào cuối chuỗi hiện tại. Hỗ trợ cơ chế gọi chuỗi phương thức (method chaining).
```java
StringBuilder sb = new StringBuilder("Base");
sb.append("-").append(12.34).append(true); // Kết quả: "Base-12.34true"
```

### 2. `insert(int offset, Object obj)`
Chèn chuỗi ký tự tại vị trí chỉ số (index) được chỉ định.
```java
StringBuilder sb = new StringBuilder("Jva");
sb.insert(1, "a"); // sb bây giờ là "Java"
```
- Ném ra ngoại lệ `StringIndexOutOfBoundsException` nếu `offset < 0` hoặc `offset > length()`.

### 3. `delete(int start, int end)` và `deleteCharAt(int index)`
- `delete()`: Loại bỏ các ký tự từ chỉ số `start` (bao gồm) đến `end` (loại trừ).
- `deleteCharAt()`: Loại bỏ một ký tự tại chỉ số cụ thể.
```java
StringBuilder sb = new StringBuilder("012345");
sb.delete(2, 4); // Loại bỏ các chỉ số 2 và 3 -> sb bây giờ là "0145"
```

### 4. `replace(int start, int end, String str)`
Thay thế các ký tự từ chỉ số `start` đến `end` bằng chuỗi `str`.
```java
StringBuilder sb = new StringBuilder("Hello World");
sb.replace(6, 11, "Java"); // sb bây giờ là "Hello Java"
```

### 5. `reverse()`
Đảo ngược thứ tự chuỗi ký tự.
```java
StringBuilder sb = new StringBuilder("live");
sb.reverse(); // sb bây giờ là "evil"
```

### 6. `setLength(int newLength)`
Thiết lập độ dài mới cho chuỗi ký tự:
- Nếu `newLength` nhỏ hơn độ dài hiện tại, chuỗi ký tự sẽ bị cắt ngắn.
- Nếu `newLength` lớn hơn, mảng buffer sẽ được đệm thêm các ký tự null (`\u0000`).
```java
StringBuilder sb = new StringBuilder("Java");
sb.setLength(2); // sb bây giờ là "Ja"
```

### 7. `ensureCapacity(int minimumCapacity)`
Bắt buộc mảng buffer cấp phát bộ nhớ đủ để chứa tối thiểu `minimumCapacity` ký tự, giúp ngăn chặn việc thay đổi kích thước mảng liên tục nếu bạn đã biết trước kích thước chuỗi kết quả cuối cùng.

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Khởi tạo lại `StringBuilder` ngay bên trong vòng lặp
Việc tạo mới một đối tượng `StringBuilder` bên trong vòng lặp sẽ phá vỡ mục đích sử dụng của nó. Đoạn code vẫn phải chịu độ phức tạp sao chép mảng $O(n^2)$ và chi phí thu gom rác.
```java
// TỆ: StringBuilder vẫn bị tạo mới trong mọi lần lặp!
String s = "";
for (int i = 0; i < 1000; i++) {
    StringBuilder sb = new StringBuilder();
    sb.append(s).append(i);
    s = sb.toString();
}

// TỐT: Khởi tạo một StringBuilder duy nhất ở ngoài vòng lặp
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String s = sb.toString();
```

### 2. Sử dụng `append()` kết hợp với phép nối chuỗi
Viết `sb.append(a + b)` thay vì `sb.append(a).append(b)`. Cách viết trước thực hiện phép nối chuỗi tạo ra chuỗi mới *trước* khi truyền kết quả cho phương thức `append()`, tạo ra một đối tượng `String` tạm thời gây lãng phí bộ nhớ.
```java
String first = "John";
String last = "Doe";
StringBuilder sb = new StringBuilder();

// TỆ: Tạo ra một chuỗi tạm thời "John Doe"
sb.append(first + " " + last);

// TỐT: Sử dụng chuỗi phương thức giúp tránh cấp phát tạm thời
sb.append(first).append(" ").append(last);
```

### 3. Chia sẻ `StringBuilder` trong đa luồng
Lớp `StringBuilder` KHÔNG an toàn luồng. Nếu nhiều luồng cùng gọi append vào một thực thể `StringBuilder` dùng chung đồng thời, các ký tự có thể ghi đè lên nhau hoặc ném ra ngoại lệ `ArrayIndexOutOfBoundsException`.
Nếu yêu cầu an toàn luồng, hãy sử dụng `StringBuffer` (hoặc tự quản lý cơ chế khóa đồng bộ hóa ngoài).
```java
// KHÔNG AN TOÀN: Nhiều luồng sửa đổi cùng một đối tượng builder
StringBuilder sharedBuilder = new StringBuilder();
Runnable r = () -> {
    for (int i = 0; i < 100; i++) {
        sharedBuilder.append("A"); // Xảy ra hiện tượng Race condition!
    }
};
```

### 4. Bỏ qua giá trị Dung lượng ban đầu (Initial Capacity)
Nếu bạn đã biết trước chuỗi cuối cùng sẽ rất lớn (ví dụ: 100,000 ký tự), việc khởi tạo một `StringBuilder` với dung lượng mặc định là 16 sẽ bắt buộc JVM phải thực hiện thay đổi kích thước mảng buffer rất nhiều lần.
Hãy luôn chỉ định một dung lượng khởi tạo ước tính nếu biết trước:
```// Thay đổi kích thước nhiều lần: 16 -> 34 -> 70 -> 142 -> ...
StringBuilder sb1 = new StringBuilder(); 

// Không cần thay đổi kích thước lần nào:
StringBuilder sb2 = new StringBuilder(100_000); 
```

---

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StringBuilder.html (Tài liệu Javadoc của lớp StringBuilder trong Oracle Java API)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StringBuffer.html (Tài liệu Javadoc của lớp StringBuffer trong Oracle Java API)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html (Đặc tả Ngôn ngữ Java: Luồng và Khóa)
