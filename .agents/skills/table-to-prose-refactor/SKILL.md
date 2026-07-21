---
name: table-to-prose-refactor
description: Use when refactoring theory Markdown files to replace table formatting with plain prose paragraphs, while preserving all content and meaning.
---

# Table-to-Prose Refactor

## Purpose

Convert all markdown tables in theory files into plain prose text (paragraphs, bullet lists, bold labels). The content must remain identical — only the visual presentation changes from tabular format to flowing text.

## Scope

All `.md` files under `vi/*/theory/` that contain markdown table syntax (`| --- |`).

## Table Categories and Conversion Rules

### Category A — Outline Coverage Table (Bảng Đề Cương)

**Pattern:** Appears near the top of every theory file, typically under `## Đề Cương Bao Phủ` or `## Nội Dung Tổng Quan`. Has 2 columns: `Khái niệm | Những điều cần biết` (or similar).

**Convert to:** A bullet list where each item uses bold for the concept name and a dash/colon for the description.

Before:
```markdown
| Khái niệm | Những điều cần biết |
| --- | --- |
| `Static block` | Khối mã chạy một lần duy nhất khi lớp được tải... |
| `Static nested class` | Lớp lồng tĩnh hoạt động độc lập... |
```

After:
```markdown
- **`Static block`** — Khối mã chạy một lần duy nhất khi lớp được tải...
- **`Static nested class`** — Lớp lồng tĩnh hoạt động độc lập...
```

### Category B — Data/Reference Table (Bảng dữ liệu tham chiếu)

**Pattern:** Tables listing factual data like primitive type sizes, default values, wrapper class mappings, keyword lists.

**Convert to:** A bullet list with bold labels for each item, grouping related data naturally.

Before:
```markdown
| Kiểu | Kích thước (bit) | Phạm vi |
| --- | --- | --- |
| `byte` | 8 | −128 đến 127 |
| `int` | 32 | −2,147,483,648 đến 2,147,483,647 |
```

After:
```markdown
- **`byte`** — 8 bit (1 byte), phạm vi từ −128 đến 127.
- **`int`** — 32 bit (4 byte), phạm vi từ −2,147,483,648 đến 2,147,483,647.
```

### Category C — Comparison Table (Bảng so sánh)

**Pattern:** Tables comparing 2–4 items across multiple properties (e.g., HashSet vs TreeSet, int vs Integer, access modifier visibility).

**Convert to:** Separate sub-paragraphs or bullet groups per item, each describing its properties. Use bold for the item name.

Before:
```markdown
| Thuộc tính | HashSet | TreeSet |
| --- | --- | --- |
| Cấu trúc bên trong | HashMap | TreeMap (Red-Black tree) |
| Độ phức tạp | O(1) | O(log N) |
```

After:
```markdown
**HashSet** sử dụng HashMap làm cấu trúc bên trong, các thao tác có độ phức tạp O(1), thứ tự duyệt không xác định và cho phép một phần tử null.

**TreeSet** sử dụng TreeMap (Cây Đỏ-Đen) làm cấu trúc bên trong, các thao tác có độ phức tạp O(log N), duyệt theo thứ tự đã sắp xếp và từ chối phần tử null (ném NullPointerException).
```

### Category D — Visibility/Permission Matrix (Ma trận quyền truy cập)

**Pattern:** Tables showing Yes/No grid for access modifiers across scopes.

**Convert to:** A bullet list per modifier, describing what it can/cannot access.

Before:
```markdown
| Bổ từ | Cùng Lớp | Cùng Gói | Lớp con | Bên ngoài |
| --- | --- | --- | --- | --- |
| `public` | Có | Có | Có | Có |
| `private` | Có | Không | Không | Không |
```

After:
```markdown
- **`public`** — Truy cập được từ mọi nơi: cùng lớp, cùng gói, lớp con ở gói khác, và bên ngoài.
- **`private`** — Chỉ truy cập được từ bên trong chính lớp khai báo. Không thể truy cập từ cùng gói, lớp con hay bên ngoài.
```

### Category E — Multi-row Summary Table (Bảng tóm tắt nhiều dòng)

**Pattern:** Tables summarizing rules across multiple dimensions (e.g., nested class comparison with 5+ columns).

**Convert to:** A series of labeled paragraphs, one per row, describing all properties inline.

## Conversion Principles

1. **Zero content loss** — Every piece of information in the table must appear in the prose version.
2. **Preserve section headings** — Keep the `###` or `####` heading that preceded the table.
3. **Natural Vietnamese prose** — Write in flowing Vietnamese, not mechanical "Column1: value, Column2: value" dumps.
4. **Use bold for key terms** — `**term**` for concept names, backticks for code.
5. **Use em-dash (—) as separator** — Between the bold label and the description.
6. **Maintain cross-references** — Keep all `> Xem thêm:` links and `[link text](path)` references intact.
7. **Do not alter code blocks** — Code examples (```` ``` ````) remain unchanged.
8. **Do not alter mermaid diagrams** — Mermaid blocks remain unchanged.

## Workflow

1. Open the target theory file.
2. Identify each table and classify it (A/B/C/D/E).
3. Convert each table according to its category rules.
4. Verify no content was lost.
5. Mark the file as done in the progress tracker.

## Quality Checklist (per file)

- [ ] All tables removed (no `| --- |` remains).
- [ ] All original content preserved.
- [ ] Headings and structure unchanged.
- [ ] Code blocks and mermaid diagrams untouched.
- [ ] Cross-reference links intact.
- [ ] Vietnamese prose reads naturally.
