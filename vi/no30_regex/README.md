# 30 - Biểu thức chính quy (Regular Expression)

Chủ đề này tuân theo đề cương chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện nó trong mã nguồn và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập

- [Khái niệm Regex là gì](theory/01-what-is-regex-concepts.md)
- [Khái niệm cơ bản về Lookahead Lookbehind](theory/02-basic-lookahead-lookbehind-concepts.md)
- [Các thuật ngữ chính](terms/01-key-terms.md)

## Danh sách kiểm tra Đề cương

- Biểu thức chính quy (Regex) là gì?
- Pattern
- Matcher
- matches
- find
- group
- Các lớp ký tự (character classes)
- Các bộ định lượng (quantifiers)
- Nhóm bắt giữ (capturing group)
- Nhóm không bắt giữ (non-capturing group)
- Khái niệm lookahead / lookbehind cơ bản
- Xác thực email, số điện thoại, mật khẩu
- Thay thế bằng regex
- Tách chuỗi bằng regex

## Tự kiểm tra (Self-Check)

- Tại sao việc biên dịch một `Pattern` tốn kém tài nguyên tính toán, và tại sao `Pattern.compile()` nên được lưu đệm (cache) thay vì gọi `String.matches()` lặp đi lặp lại trong một vòng lặp tần suất cao?
  → Xem [Tại sao biên dịch Pattern tốn kém](theory/01-what-is-regex-concepts.md#why-pattern-compilation-is-expensive)
- Sự khác biệt chính giữa nhóm bắt giữ `(group)` và nhóm không bắt giữ `(?:group)` về mặt cấp phát bộ nhớ heap và hiệu năng là gì?
  → Xem [Tại sao nhóm không bắt giữ tiết kiệm cấp phát Heap](theory/01-what-is-regex-concepts.md#why-non-capturing-groups-save-heap-allocations)
- Các khẳng định lookahead và lookbehind hoạt động về mặt khái niệm như thế nào mà không tiêu thụ bất kỳ ký tự đầu vào nào, và bản chất "độ rộng bằng không (zero-width)" của chúng là gì?
  → Xem [Tại sao Lookaround là các khẳng định độ rộng bằng không](theory/02-basic-lookahead-lookbehind-concepts.md#why-lookarounds-are-zero-width-assertions)
- Hạn chế kỹ thuật về độ rộng của các khẳng định lookbehind trong công cụ regex của Java so với lookahead là gì, và tại sao nó lại tồn tại?
  → Xem [Tại sao Lookbehind trong Java có các hạn chế về độ rộng](theory/02-basic-lookahead-lookbehind-concepts.md#why-java-lookbehinds-have-width-limitations)
- Tại sao hiện tượng quay lui (backtracking) xảy ra trong quá trình thực thi regex, và làm thế nào các bộ định lượng tham lam (greedy) so với miễn cưỡng (reluctant) so với sở hữu (possessive) có thể ngăn chặn hiện tượng quay lui thảm họa (ReDoS)?
  → Xem [Tại sao quay lui xảy ra và cách các bộ định lượng ngăn chặn ReDoS](theory/01-what-is-regex-concepts.md#why-backtracking-occurs-and-how-quantifiers-prevent-redos)
- Tại sao `String.split(regex)` loại bỏ các chuỗi trống ở cuối theo mặc định, và làm thế nào việc truyền một tham số giới hạn (limit) âm có thể ngăn chặn hành vi này?
  → Xem [Tại sao String.split loại bỏ các chuỗi trống ở cuối](theory/02-basic-lookahead-lookbehind-concepts.md#why-stringsplit-discards-trailing-empty-strings)

## Thẻ Anki

- [Cơ bản](anki/basic.tsv)
- [Cơ bản bổ sung](anki/basic-extra.tsv)
- [Điền vào chỗ trống](anki/cloze.tsv)
- [Câu hỏi mã nguồn](anki/code-question.tsv)

## Tổng quan Mermaid

```mermaid
flowchart TD
    A[Biểu thức chính quy (Regular Expression)] --> B[Định nghĩa]
    A --> C[Quy tắc và cú pháp]
    A --> D[Các lỗi thường gặp]
    A --> E[Ghi nhớ phỏng vấn]
```

## Liên kết tham khảo

- https://docs.oracle.com/javase/tutorial/essential/regex/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html
