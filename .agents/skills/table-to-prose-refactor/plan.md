# Kế Hoạch Refactor: Bỏ Dạng Bảng → Viết Văn Xuôi

## Tổng Quan

Chuyển đổi **tất cả bảng markdown** trong 100 file theory sang dạng **văn xuôi** (đoạn văn, bullet list, bold label). Nội dung giữ nguyên 100%, chỉ thay đổi cách trình bày.

## Skill Sử Dụng

[table-to-prose-refactor](file:///home/fhu_thjen/projects/learning-java/.agents/skills/table-to-prose-refactor/SKILL.md) — Định nghĩa 5 loại bảng (A–E) và quy tắc chuyển đổi tương ứng cho từng loại.

## Phân Loại Bảng (5 Loại)

### Loại A — Bảng Đề Cương (Outline Coverage)
- **Vị trí:** Đầu mỗi file theory, dưới heading `## Đề Cương Bao Phủ` hoặc `## Nội Dung Tổng Quan`
- **Cấu trúc:** 2 cột: `Khái niệm | Những điều cần biết`
- **Số file:** ~90 file (Ch.10–45)
- **Chuyển thành:** Bullet list với bold concept + em-dash + mô tả

### Loại B — Bảng Dữ Liệu Tham Chiếu (Reference/Data)
- **Vị trí:** Xen kẽ trong nội dung, thường ở chương đầu (02–09, 18)
- **Cấu trúc:** 2–5 cột liệt kê dữ liệu kỹ thuật (kích thước, phạm vi, toán tử, giá trị mặc định, ký hiệu generics)
- **Số file:** ~15 file
- **Chuyển thành:** Bullet list với bold label, gom dữ liệu cùng dòng

### Loại C — Bảng So Sánh (Comparison)
- **Vị trí:** Trong phần nội dung chi tiết
- **Cấu trúc:** 3–6 cột so sánh 2+ đối tượng theo nhiều thuộc tính
- **Số file:** ~7 file
- **Chuyển thành:** Đoạn văn riêng cho mỗi đối tượng, mô tả tất cả thuộc tính inline

### Loại D — Ma Trận Quyền Truy Cập (Visibility Matrix)
- **Vị trí:** Chương access modifiers (Ch.10)
- **Cấu trúc:** Grid Có/Không theo scope
- **Số file:** 1–2 file
- **Chuyển thành:** Bullet list mô tả phạm vi truy cập cho từng modifier

### Loại E — Bảng Tóm Tắt Nhiều Chiều (Multi-row Summary)
- **Vị trí:** Chương nested class (Ch.15), module system (Ch.33)
- **Cấu trúc:** 5–6 cột tổng hợp quy tắc
- **Số file:** 2–3 file
- **Chuyển thành:** Đoạn văn có label cho từng row

## Workflow Thực Thi

```
Batch → Subagent đọc SKILL.md → Mở file → Phân loại bảng → Chuyển đổi → Kiểm tra → Đánh dấu DONE
```

### Phase 1 — Chương đầu (Ch.00–09): 7 file
Chủ yếu Loại B (bảng dữ liệu). Ít file, dễ xử lý, dùng để test workflow.

### Phase 2 — Modifiers + OOP (Ch.10–15): 9 file  
Hỗn hợp Loại A + C + D + E. Phức tạp nhất, cần cẩn thận.

### Phase 3 — Exception + Memory + Object (Ch.12–14): 8 file
Chủ yếu Loại A. Cơ học, batch nhanh.

### Phase 4 — Enum + Annotation + Generics (Ch.16–18): 5+ file
Hỗn hợp Loại A + B.

### Phase 5 — Collections + Comparable (Ch.19–20): 9 file
Hỗn hợp Loại A + C. TreeSet có bảng so sánh quan trọng.

### Phase 6 — Lambda + Functional + Stream (Ch.21–23): 10 file
Chủ yếu Loại A. Batch nhanh.

### Phase 7 — Optional + DateTime + IO + NIO (Ch.24–27): 8 file
Chủ yếu Loại A. Batch nhanh.

### Phase 8 — Multithreading + Concurrency (Ch.28–29): 10 file
Chủ yếu Loại A. Batch nhanh.

### Phase 9 — Regex → Modern Java (Ch.30–40): 18 file
Hỗn hợp Loại A + B + C. Module system (Ch.33) có bảng so sánh.

### Phase 10 — Best Practices → Interview (Ch.41–45): 10 file
Hỗn hợp Loại A + C. Interview questions có bảng so sánh.

## File Tiến Độ

Theo dõi tại: [progress.md](file:///home/fhu_thjen/projects/learning-java/.agents/skills/table-to-prose-refactor/progress.md)

## Quy Tắc Chung

1. **Không mất nội dung** — Mọi thông tin trong bảng phải xuất hiện trong bản văn xuôi
2. **Giữ nguyên heading** — Không thay đổi cấu trúc `###` / `####`
3. **Giữ nguyên code blocks** — Không đụng vào ``` và mermaid
4. **Giữ nguyên cross-references** — Tất cả link `> Xem thêm:` giữ nguyên
5. **Viết tiếng Việt tự nhiên** — Không dịch máy móc "Cột1: giá trị, Cột2: giá trị"
6. **Bold cho term chính** — Dùng `**term**` cho tên khái niệm, backtick cho code
7. **Em-dash (—) làm dấu phân cách** — Giữa bold label và mô tả
