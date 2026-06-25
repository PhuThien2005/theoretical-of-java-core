# 25 - Date and Time API

This topic follows the master outline in [outline.md](../outline.md). The goal is to understand each concept deeply enough to explain it, recognize it in code, and answer interview-style questions.

## Study Order

- [Date Concepts](theory/01-date-concepts.md)
- [Period Concepts](theory/02-period-concepts.md)
- [Key Terms](terms/01-key-terms.md)

## Outline Checklist

- Date
- Calendar
- SimpleDateFormat
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

## Self-Check

- Why is the legacy date and calendar API (`java.util.Date`, `java.util.Calendar`, `java.text.SimpleDateFormat`) flawed (e.g. mutability, index bias, thread-safety issues)?
- Why are modern Java 8 date-time objects (like `LocalDate`, `LocalTime`, `ZonedDateTime`) designed to be immutable and thread-safe, and what pattern is used to obtain modified instances?
- What is the difference in timezone representation and rules between `OffsetDateTime`, `ZonedDateTime`, and `Instant`?
- Why does `java.time.format.DateTimeFormatter` avoid the concurrency bugs of `SimpleDateFormat`?
- How do `Period` and `Duration` differ in representation and behavior, particularly when added to a timezone-aware temporal object like `ZonedDateTime` across Daylight Saving Time (DST) changes?
- How does `ZonedDateTime` handle invalid or overlapping local date-times resulting from DST transitions?

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)

## Mermaid Overview

```mermaid
flowchart TD
    A[Date and Time API] --> B[Definitions]
    A --> C[Rules and syntax]
    A --> D[Common mistakes]
    A --> E[Interview recall]
```

## Reference Links

- https://docs.oracle.com/javase/tutorial/datetime/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/package-summary.html
