import csv
import os

basic_rows = [
    [
        "date_time_api-basic-001",
        "Why is legacy `java.util.Date` considered a poorly designed API?",
        "It is mutable (allowing state corruption), month indexing is 0-indexed (0 is January), and years are offset by 1900 (e.g., year 126 is 2026).",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-002",
        "How does the mutability of `java.util.Date` cause issues in multi-threaded environments?",
        "Multiple threads sharing a `Date` reference can modify its state concurrently (e.g. via `setTime`), leading to data races and variable corruption.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-003",
        "Why is the legacy `java.util.Calendar` class not type-safe?",
        "It relies on arbitrary integer constants (like `Calendar.MONTH` or `Calendar.YEAR`) for accessing and modifying fields, rather than type-safe enums.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-004",
        "What is the month indexing scheme in the legacy `java.util.Calendar` class?",
        "Months are 0-indexed; `Calendar.JANUARY` is 0, and `Calendar.DECEMBER` is 11.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-005",
        "Why is `java.text.SimpleDateFormat` unsafe to share across threads or use as a static variable?",
        "It is not thread-safe. It internally maintains mutable state in a shared `Calendar` instance during format and parse operations, causing date corruption or exceptions under concurrency.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-006",
        "What is the modern, thread-safe replacement for `SimpleDateFormat`?",
        "java.time.format.DateTimeFormatter",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-007",
        "What does `LocalDate` represent in Java?",
        "A date without a time or a timezone context (e.g., `2026-06-12`) in the ISO-8601 calendar system.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-008",
        "How does month indexing in modern `java.time.LocalDate` differ from legacy `java.util.Date`?",
        "LocalDate uses standard 1-indexed months (1 = January, 12 = December), unlike legacy Date which is 0-indexed (0 = January).",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-009",
        "What does `LocalTime` represent in Java?",
        "A time of day without a date or a timezone context (e.g., `13:45:00`) with nanosecond precision.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-010",
        "What does `LocalDateTime` represent in Java?",
        "A combination of date and time without a timezone context (e.g., `2026-06-12T13:45:00`).",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-011",
        "Why is `LocalDateTime` insufficient for representing a specific moment on the global timeline?",
        "It has no timezone offset information, meaning it represents only a local wall-clock time which corresponds to different actual moments depending on region.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-012",
        "What does `ZonedDateTime` represent in Java?",
        "A date and time with a full geographical timezone context (e.g., `2026-06-12T13:45:00+09:00[Asia/Tokyo]`) containing DST and offset transition rules.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-013",
        "How does `ZonedDateTime` resolve transitions during Daylight Saving Time (DST) changes?",
        "It uses the rules of the target `ZoneId` to automatically adjust the offset (e.g. from -04:00 to -05:00) depending on the calendar date.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-014",
        "What does `OffsetDateTime` represent in Java?",
        "A date and time with a fixed UTC offset (e.g., `2026-06-12T13:45:00+09:00`), but without daylight saving time or regional rules.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-015",
        "When is `OffsetDateTime` typically preferred over `ZonedDateTime`?",
        "For database serialization, XML/JSON APIs, and network protocols where exact offsets are required without regional timezone logic.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-016",
        "What does `Instant` represent in the Java Date-Time API?",
        "An instantaneous point on the UTC timeline, measured in seconds and nanoseconds from the Unix Epoch (1970-01-01T00:00:00Z).",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-017",
        "What timezone is used when printing an `Instant` object directly using `toString()`?",
        "The UTC timezone, indicated by a trailing 'Z' (e.g., `2026-06-12T13:45:00Z`).",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-018",
        "Why does `Instant` matter in Java?",
        "It provides a completely standardized, UTC-based epoch offset for logging, serialization, and high-precision execution duration measurements.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-019",
        "What does `Duration` represent in Java?",
        "A time-based amount of time measured in seconds and nanoseconds (e.g., '3 hours' or '45 seconds').",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-020",
        "What types of temporal classes can be passed to `Duration.between()`?",
        "Time-based temporal classes like `Instant`, `LocalTime`, and `LocalDateTime`.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-021",
        "Why does passing a `LocalDate` to `Duration.between()` fail?",
        "Because `LocalDate` has no time component (no seconds or nanoseconds), which are required by the time-based `Duration` class.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-022",
        "What does `Period` represent in Java?",
        "A date-based amount of time measured in years, months, and days (e.g., '1 year, 2 months, and 3 days').",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-023",
        "What string format is used when printing a `Period` object?",
        "The ISO-8601 period format: `PnYnMnD` (e.g., `P1Y2M3D` represents 1 year, 2 months, and 3 days).",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-024",
        "Why is chaining static factory methods on `Period` (e.g., `Period.ofYears(1).ofDays(5)`) a trap?",
        "Because static factory methods do not accumulate; the last call overrides the previous ones, so `Period.ofYears(1).ofDays(5)` represents only 5 days.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-025",
        "How do you correctly construct a `Period` containing years, months, and days together?",
        "Use the multi-argument factory method: `Period.of(years, months, days)`.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-026",
        "How do you parse a custom date string into a `LocalDate` object?",
        "Use the static method `LocalDate.parse(text, formatter)`, passing the date string and a matching `DateTimeFormatter`.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-027",
        "What exception is thrown when parsing a string that does not match the `DateTimeFormatter` pattern?",
        "java.time.format.DateTimeParseException (which is a subclass of DateTimeException).",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-028",
        "Why are all classes in the `java.time` package inherently thread-safe?",
        "They are immutable. Modifying operations do not change the state of the existing object; they return a new instance representing the modified value.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-029",
        "What is the effect of calling `date.plusDays(5)` on an existing `LocalDate` variable without assignment?",
        "It has no effect on the original variable because `LocalDate` is immutable. The returned new instance is discarded.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-030",
        "What is the difference between `isEqual()` and `equals()` when comparing two `ZonedDateTime` objects?",
        "isEqual() compares only the chronological timeline position (instant), whereas equals() also compares class type, timezone, and calendar system.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-031",
        "What is a `TemporalAdjuster` used for in Java?",
        "For executing complex, custom date adjustments, such as finding the next Monday, the last day of the month, or the first Tuesday of next year.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-032",
        "How do you find the last day of the current month using `LocalDate`?",
        "By calling `date.with(TemporalAdjusters.lastDayOfMonth())`.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-033",
        "What does `ZoneOffset` represent in the Java Date-Time API?",
        "A fixed offset difference from UTC/Greenwich time, represented by hours, minutes, and seconds (e.g., `+05:30`).",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-034",
        "How does `ZoneId` differ from `ZoneOffset`?",
        "ZoneId represents a full geographical region (like `Asia/Tokyo`) and includes rules for Daylight Saving Time; ZoneOffset is a simple, fixed difference from UTC.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-035",
        "How do you convert a `ZonedDateTime` object to a different timezone while preserving the exact same instant on the timeline?",
        "By calling `zonedDateTime.withZoneSameInstant(targetZoneId)`.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-basic-036",
        "What is the effect of calling `withZoneSameLocal(ZoneId)` on a `ZonedDateTime` object?",
        "It shifts the timezone of the object but retains the local date-time digits, which changes the represented instant on the global timeline.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ]
]

cloze_rows = [
    [
        "date_time_api-cloze-001",
        "Legacy `java.util.Date` months are {{c1::0-indexed}} (0 = January), whereas modern `java.time.LocalDate` months are {{c2::1-indexed}} (1 = January).",
        "In java.util.Date and Calendar, months range from 0 to 11. In the java.time package, they range from 1 to 12.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-002",
        "Legacy `java.util.Date` constructor expects the year argument to be {{c1::year minus 1900}} (e.g., 126 represents the year 2026).",
        "This subtraction makes legacy Date creation extremely error-prone and hard to read.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-003",
        "SimpleDateFormat is not thread-safe because it internally maintains mutable state in a shared {{c1::Calendar}} instance.",
        "If shared across multiple threads, format() and parse() operations will overwrite each other's data, causing garbage output or exceptions.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-004",
        "Modern Java date-time classes (like `LocalDate`) are {{c1::immutable}} and thread-safe, meaning modification methods return new instances.",
        "Because they are immutable, they can be shared freely across threads without synchronization, preventing shared state bugs.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-005",
        "LocalDate represents a date without time or timezone, while LocalTime represents time without date or timezone, both formatted using the {{c1::ISO-8601}} calendar system.",
        "The default format is yyyy-MM-dd for LocalDate and HH:mm:ss for LocalTime.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-006",
        "LocalDateTime combines date and time but lacks a {{c1::timezone offset}}, so it does not represent a unique instant on the global timeline.",
        "Without an offset or zone, '10:00 AM' represents a different absolute point in time in New York compared to Tokyo.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-007",
        "ZonedDateTime contains a date, time, offset, and a regional {{c1::ZoneId}} (e.g. `America/New_York`) to automatically handle DST rules.",
        "This class is optimal for user-facing applications that need to dynamically resolve transitions like Daylight Saving Time.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-008",
        "OffsetDateTime contains date, time, and a fixed offset (represented by {{c1::ZoneOffset}}), but does not have regional timezone rules.",
        "Because it has a fixed offset without DST rules, it is widely used for system integration, databases, and network serializations.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-009",
        "Instant represents a point on the timeline measured in seconds and nanoseconds relative to the {{c1::Unix Epoch}} (January 1, 1970 UTC).",
        "It acts as a timezone-neutral UTC timestamp. Its toString() representation always appends 'Z'.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-010",
        "Passing LocalDate to `Duration.between()` throws {{c1::UnsupportedTemporalTypeException}} because LocalDate lacks time-based units like seconds.",
        "Duration only operates on time-based objects (Instant, LocalTime, LocalDateTime). Use Period.between() for date-based objects.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-011",
        "Period represents a date-based amount of time, whereas Duration represents a {{c1::time-based}} amount of time.",
        "Period is measured in years, months, and days. Duration is measured in seconds and nanoseconds.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-012",
        "Printing a Period displays a string in ISO-8601 format beginning with the prefix {{c1::P}} (e.g. `P1Y2M`).",
        "For example, P1Y2M3D represents a period of 1 year, 2 months, and 3 days.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-013",
        "Chaining static factories like `Period.ofYears(1).ofDays(5)` produces a period containing only {{c1::5 days}} because the last static call overrides previous values.",
        "To get a period representing both, use Period.of(1, 0, 5) or use plus methods.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-014",
        "In DateTimeFormatter patterns, uppercase `M` represents {{c1::month}}, whereas lowercase `m` represents {{c1::minute}}.",
        "Confusing these two is a very common bug, resulting in values like '2026-30-12' where minutes are printed instead of month.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-015",
        "Parsing an invalid date format string throws the runtime exception {{c1::DateTimeParseException}}.",
        "This occurs when the characters do not match the expected pattern or when required components (like time) are missing.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-016",
        "Comparing two date-times using the `equals()` method checks for exact object equality, whereas {{c1::isEqual()}} compares chronological positions on the timeline.",
        "For instance, ZonedDateTime at +01:00 vs +02:00 representing the same instant will return true for isEqual() but false for equals().",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-017",
        "Complex date adjustments, such as finding the next Tuesday, are performed using the {{c1::TemporalAdjusters}} utility class.",
        "TemporalAdjusters provides standard adjusters like next(), lastDayOfMonth(), and firstDayOfNextYear().",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ],
    [
        "date_time_api-cloze-018",
        "To adjust a ZonedDateTime to a target timezone while maintaining the exact same global instant, use the {{c1::withZoneSameInstant()}} method.",
        "In contrast, withZoneSameLocal() retains the local digits, changing the actual moment on the timeline.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api"
    ]
]

code_rows = [
    [
        "date_time_api-code-001",
        "What does this represent?",
        "LocalDate date = LocalDate.of(2026, 6, 12);",
        "A date without time or time zone",
        "LocalDate stores year-month-day only.",
        "25-date-time-api/README.md | https://docs.oracle.com/javase/tutorial/datetime/",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-002",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "java.util.Date date = new java.util.Date(126, 5, 12);\\nSystem.out.println(date.getMonth());",
        "5",
        "In legacy java.util.Date, the month index is 0-based. The constructor takes 5 (June), and getMonth() returns 5. The constructor expects year as year - 1900, so 126 represents the year 2026.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-003",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "Calendar cal = Calendar.getInstance();\\ncal.set(2026, 0, 15);\\nSystem.out.println(cal.get(Calendar.MONTH));",
        "0",
        "Legacy Calendar uses 0-indexed months. 0 represents January, so calling get(Calendar.MONTH) returns 0.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-004",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\nSystem.out.println(date.getMonthValue() + \" \" + date.getMonth());",
        "6 JUNE",
        "Modern LocalDate month values are 1-based, so getMonthValue() returns 6. getMonth() returns the Month enum value, which is JUNE.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-005",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "ZonedDateTime zdt1 = ZonedDateTime.of(2026, 6, 12, 10, 0, 0, 0, ZoneId.of(\"America/New_York\"));\\nZonedDateTime zdt2 = ZonedDateTime.of(2026, 6, 12, 7, 0, 0, 0, ZoneId.of(\"America/Los_Angeles\"));\\nSystem.out.println(zdt1.equals(zdt2) + \" \" + zdt1.isEqual(zdt2));",
        "false true",
        "equals() checks if class, date, time, and timezone/offset match exactly; since their timezones are different, it returns false. isEqual() compares only the underlying instant on the timeline; since 10:00 AM NY time is the exact same instant as 7:00 AM LA time (both are 14:00 UTC), it returns true.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-006",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\nSystem.out.println(date.plus(Period.ofMonths(2)));",
        "2026-08-12",
        "LocalDate.plus() accepts a TemporalAmount. Since Period implements TemporalAmount, this adds 2 months, producing 2026-08-12.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-007",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalTime time = LocalTime.of(12, 30);\\nSystem.out.println(time.plus(Period.ofDays(1)));",
        "throws UnsupportedTemporalTypeException",
        "LocalTime represents time-of-day without a date. Attempting to add a date-based Period of days to a LocalTime throws UnsupportedTemporalTypeException at runtime because days/dates are not supported.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-008",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "Instant instant = Instant.ofEpochSecond(0);\\nSystem.out.println(instant);",
        "1970-01-01T00:00:00Z",
        "An Instant representing 0 epoch seconds points exactly to the Unix Epoch, which is formatted as 1970-01-01T00:00:00Z in UTC.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-009",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate d1 = LocalDate.of(2026, 6, 12);\\nLocalDate d2 = LocalDate.of(2027, 8, 15);\\nPeriod p = Period.between(d1, d2);\\nSystem.out.println(p.getYears() + \"-\" + p.getMonths() + \"-\" + p.getDays());",
        "1-2-3",
        "Period.between() calculates the difference between dates as years, months, and days. The difference between 2026-06-12 and 2027-08-15 is exactly 1 year, 2 months, and 3 days.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-010",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate d1 = LocalDate.of(2026, 6, 12);\\nLocalDate d2 = LocalDate.of(2027, 8, 15);\\nPeriod p = Period.between(d1, d2);\\nSystem.out.println(p.toTotalMonths());",
        "14",
        "toTotalMonths() returns the total number of months in the period by multiplying years by 12 and adding the months. Here, 1 year and 2 months equals 14 months.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-011",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDateTime ldt = LocalDateTime.of(2026, 6, 12, 15, 0);\\nDateTimeFormatter f = DateTimeFormatter.ofPattern(\"hh a\");\\nSystem.out.println(ldt.format(f));",
        "03 PM",
        "The pattern hh formats hour-of-am-pm (1-12) and a formats am-pm marker. 15:00 is 3:00 PM, so it outputs 03 PM.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-012",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDateTime ldt = LocalDateTime.of(2026, 6, 12, 15, 0);\\nDateTimeFormatter f = DateTimeFormatter.ofPattern(\"HH mm\");\\nSystem.out.println(f.format(ldt));",
        "15 00",
        "The pattern HH formats the 24-hour hour and mm formats minutes. f.format(ldt) yields 15 00.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-013",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 30);\\nSystem.out.println(date.plusMonths(1));",
        "2026-07-30",
        "Adding 1 month to June 30 returns July 30.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-014",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 5, 31);\\nSystem.out.println(date.plusMonths(1));",
        "2026-06-30",
        "June has only 30 days. When adding a month to a date (May 31) that doesn't exist in the target month (June 31), Java resolves it to the last valid day of that month, which is June 30.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-015",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDateTime ldt = LocalDateTime.of(2026, 6, 12, 12, 0);\\nSystem.out.println(ldt.withHour(5).getHour());",
        "5",
        "All java.time classes are immutable. Calling withHour(5) returns a new LocalDateTime with hour set to 5. Since we chained .getHour() on the returned instance, it returns 5. The original ldt remains unchanged.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-016",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\ndate.plusDays(5);\\nSystem.out.println(date);",
        "2026-06-12",
        "LocalDate is immutable. Calling plusDays() does not modify the original object; it returns a new instance, which was discarded in this code. To update the date, it must be reassigned: date = date.plusDays(5);.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-017",
        "What happens when this code is executed?",
        "LocalDate date = LocalDate.parse(\"2026-06-12\", DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm\"));",
        "throws DateTimeParseException",
        "The formatter pattern yyyy-MM-dd HH:mm requires time fields (hour and minute), but the input string \"2026-06-12\" only contains date fields. Since the required components are missing, a DateTimeParseException is thrown at runtime.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-018",
        "What happens when this code is executed?",
        "LocalDate d1 = LocalDate.of(2026, 6, 12);\\nLocalDate d2 = LocalDate.of(2026, 6, 15);\\nDuration.between(d1, d2);",
        "throws UnsupportedTemporalTypeException",
        "Duration represents a time-based amount of time (seconds, nanoseconds). LocalDate represents date only and has no time component, meaning it doesn't support the seconds unit. Passing it to Duration.between() throws UnsupportedTemporalTypeException. Use Period.between(d1, d2) for date-only types.",
        "25-date-time-api/theory/01-date-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-019",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "Period period = Period.ofYears(1).ofMonths(2).ofDays(3);\\nSystem.out.println(period);",
        "P3D",
        "Static factory methods on Period (like ofYears, ofMonths, ofDays) do not accumulate. Each call is a static reference that discards the previous result. The last method called was ofDays(3), so only that value is returned. To create a period of 1 year, 2 months, and 3 days, use Period.of(1, 2, 3).",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-020",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12); // Friday\\nLocalDate result = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));\\nSystem.out.println(result);",
        "2026-06-15",
        "TemporalAdjusters.next(DayOfWeek.MONDAY) finds the first date after June 12, 2026, that is a Monday, which is June 15, 2026.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-021",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\nDateTimeFormatter formatter = DateTimeFormatter.ofPattern(\"yyyy-mm-dd\");\\nSystem.out.println(date.format(formatter));",
        "throws UnsupportedTemporalTypeException",
        "The pattern letter 'm' stands for minute-of-hour, not month. Since LocalDate does not contain time fields (such as minutes), calling date.format() with a formatter that requests minutes ('m') throws an UnsupportedTemporalTypeException. Month-of-year must be formatted using uppercase 'M' (e.g., yyyy-MM-dd).",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-022",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 2, 28);\\nPeriod period = Period.ofDays(1);\\nSystem.out.println(date.plus(period));",
        "2026-03-01",
        "2026 is not a leap year, so February has 28 days. Adding 1 day to February 28, 2026 transitions to March 1, 2026.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-023",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2028, 2, 28);\\nPeriod period = Period.ofDays(1);\\nSystem.out.println(date.plus(period));",
        "2028-02-29",
        "2028 is a leap year (divisible by 4), so February has 29 days. Adding 1 day to February 28, 2028 yields February 29, 2028.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-024",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\nLocalDate result = date.minusDays(5).plusMonths(1);\\nSystem.out.println(result);",
        "2026-07-07",
        "date.minusDays(5) returns June 7. plusMonths(1) on that returns July 7. Both operations are chained, returning a new instance at each step.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-025",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalTime time = LocalTime.of(23, 30);\\nSystem.out.println(time.plusHours(2));",
        "01:30",
        "LocalTime wraps around at midnight (24-hour cycle). Adding 2 hours to 23:30 rolls over to 01:30.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-026",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "Instant instant = Instant.ofEpochMilli(1000);\\nDuration duration = Duration.ofMillis(500);\\nSystem.out.println(instant.plus(duration).toEpochMilli());",
        "1500",
        "instant is 1000 ms. duration is 500 ms. Adding them returns a new Instant representing 1500 ms from Epoch.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ],
    [
        "date_time_api-code-027",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "LocalDate date = LocalDate.of(2026, 6, 12);\\nSystem.out.println(date.with(java.time.temporal.ChronoField.DAY_OF_MONTH, 20));",
        "2026-06-20",
        "with(TemporalField, long) replaces the specified field value and returns a new LocalDate instance with the value updated. Here, day of month is updated to 20.",
        "25-date-time-api/theory/02-period-concepts.md",
        "java::core::date-time-api java::code"
    ]
]

def write_tsv(filepath, headers, rows):
    with open(filepath, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t", quoting=csv.QUOTE_MINIMAL)
        writer.writerow(headers)
        for r in rows:
            writer.writerow(r)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/25-date-time-api/anki/basic.tsv",
    ["ID", "Front", "Back", "Source", "Tags"],
    basic_rows
)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/25-date-time-api/anki/cloze.tsv",
    ["ID", "Text", "Extra", "Source", "Tags"],
    cloze_rows
)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/25-date-time-api/anki/code-question.tsv",
    ["ID", "Question", "Code", "Answer", "Explanation", "Source", "Tags"],
    code_rows
)

print("Successfully rewrote date-time cards!")
