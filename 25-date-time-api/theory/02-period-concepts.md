# Date and Time API - Part 2

## Learning Goal

This file covers a focused slice of **Date and Time API**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Period` | Immutable, thread-safe representation of a date-based amount of time in years, months, and days. |
| `DateTimeFormatter` | Modern, immutable, thread-safe class for formatting and parsing date-time values. |
| `ZoneId` | Time zone identifier (e.g. `Asia/Tokyo`) used to resolve daylight saving time rules and regional transitions. |
| `Parse date/time` | Converting a string to a temporal object using a formatter; throws runtime `DateTimeParseException` on failure. |
| `Format date/time` | Converting a temporal object to a formatted string; throws `UnsupportedTemporalTypeException` if formatting fields not supported. |
| `Compare date/time` | Chronological comparison using `isBefore()`, `isAfter()`, `isEqual()`, and `compareTo()`. |
| `Add/subtract date/time` | Performing arithmetic using immutable methods (plus/minus) or adjustments (with/TemporalAdjusters). |
| `Timezone` | Standardized geographical time offset; resolved using `ZoneId` and `ZoneOffset`. |

## Detailed Notes

### Period
`java.time.Period` represents a date-based amount of time (in years, months, and days), such as "2 years, 3 months, and 6 days". It operates on date-based temporal types like `LocalDate`.

#### Formatting & String Representation:
* Format follows the ISO-8601 duration format for periods: `PnYnMnD` (where `P` stands for period, `Y` for years, `M` for months, and `D` for days). E.g., `P2Y3M6D`.

#### Warning about factory methods chaining:
* `Period` methods do not chain cumulatively. Calling `Period.ofYears(2).ofMonths(3)` returns a period of *3 months* only (the static methods overwrite rather than combine). Use `Period.of(2, 3, 0)` instead.

```java
// Correct Period instantiation
Period period = Period.of(1, 2, 3); // 1 year, 2 months, 3 days
System.out.println(period); // P1Y2M3D

// Static factory chaining trap:
Period badPeriod = Period.ofYears(1).ofDays(5); // Only represents 5 days!
System.out.println(badPeriod); // P5D
```

### DateTimeFormatter
`java.time.format.DateTimeFormatter` is the modern, immutable, and thread-safe class for formatting and parsing date-time objects. It replaces `SimpleDateFormat`.

#### Usage:
* It has pre-defined constants like `ISO_LOCAL_DATE` (`2026-06-12`) and allows custom patterns using pattern letters like `yyyy`, `MM`, `dd`, `HH`, `mm`, `ss`.

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate date = LocalDate.of(2026, 6, 12);
String formatted = date.format(formatter);
System.out.println(formatted); // 12/06/2026
```

### ZoneId
`java.time.ZoneId` represents a time zone identifier (e.g., `Europe/Paris`, `Asia/Tokyo`, `America/New_York`). It provides rules for converting instants to local date-times and dynamically handles daylight saving time transitions.

```java
ZoneId tokyo = ZoneId.of("Asia/Tokyo");
ZoneId ny = ZoneId.of("America/New_York");
System.out.println("Tokyo Zone Rules: " + tokyo.getRules());
```

### Parse date/time
Parsing converts a text string into a temporal object. If the string does not match the expected pattern, it throws a runtime `DateTimeParseException`.

#### Compile vs Runtime Formatter Errors:
* Using invalid pattern letters (like `Y` instead of `y` for year, or `m` instead of `M` for month, or calling `.parse()` on a formatter expecting time fields with only date string) will fail.
* E.g., `LocalDate.parse("2026-06-12", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))` throws `DateTimeParseException` because the time fields are missing.

```java
// Parsing standard ISO-8601 strings directly
LocalDate parsedDate = LocalDate.parse("2026-06-12"); 

// Custom formatting parse
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
LocalDate customParsed = LocalDate.parse("12-06-2026", formatter);
System.out.println(customParsed); // 2026-06-12
```

### Format date/time
Formatting converts a temporal object to a formatted string representation.

```java
LocalDateTime now = LocalDateTime.of(2026, 6, 12, 15, 30);
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm a");
System.out.println(now.format(formatter)); // Friday, June 12, 2026 at 03:30 PM
```

### Compare date/time
Java provides dedicated methods to compare temporal objects chronologically:
* `isBefore(ChronoLocalDate)` / `isAfter(ChronoLocalDate)`
* `isEqual(ChronoLocalDate)` checks if they represent the same date/time, ignoring calendar systems or timezone offsets (unlike `equals()`).
* `compareTo(ChronoLocalDate)` returns negative, zero, or positive integer.

```java
LocalDate date1 = LocalDate.of(2026, 6, 12);
LocalDate date2 = LocalDate.of(2026, 6, 15);

System.out.println(date1.isBefore(date2)); // true
System.out.println(date1.isAfter(date2));  // false
```

### Add/subtract date/time
Temporal arithmetic is performed using fluent, immutable methods:
* `plus(long, TemporalUnit)` / `minus(long, TemporalUnit)`
* Specific convenience methods like `plusDays()`, `minusMonths()`.
* `with(TemporalField, long)` / `with(TemporalAdjuster)` to adjust specific fields or find dynamic dates (e.g. next Monday).

```java
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

LocalDate date = LocalDate.of(2026, 6, 12); // A Friday
LocalDate nextMonday = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

System.out.println("Next Monday: " + nextMonday);        // 2026-06-15
System.out.println("Last Day of Month: " + lastDayOfMonth); // 2026-06-30
```

### Timezone
A timezone offset is the difference between local time and Coordinated Universal Time (UTC). Modern Java represents this offset using `ZoneOffset` (e.g., `+05:30`), while regional timezones (with historical DST rules) are managed using `ZoneId`.

---

## Case Study: Scheduling a Meeting Across Timezones (New York and Tokyo)

### Problem Description
A company wants to schedule a synchronous virtual meeting between its offices in New York (`America/New_York`) and Tokyo (`Asia/Tokyo`). The meeting is proposed for **October 15, 2026, at 9:00 AM New York local time**. 
We need to calculate:
1. What local time and date the Tokyo team must join the meeting.
2. How the offset and time change if the meeting is rescheduled to **December 15, 2026, at 9:00 AM New York local time** (when New York transitions back to Eastern Standard Time (EST), while Tokyo does not observe Daylight Saving Time).

### Implementation

```java
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneCaseStudy {
    public static void main(String[] args) {
        ZoneId nyZone = ZoneId.of("America/New_York");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");

        // Scenario 1: Meeting in October (Daylight Saving Time in New York)
        LocalDateTime octoberTime = LocalDateTime.of(2026, 10, 15, 9, 0); // 9:00 AM
        ZonedDateTime nyMeetingOct = ZonedDateTime.of(octoberTime, nyZone);
        ZonedDateTime tokyoMeetingOct = nyMeetingOct.withZoneSameInstant(tokyoZone);

        System.out.println("--- Scenario 1 (October Meeting) ---");
        System.out.println("New York Time: " + nyMeetingOct); 
        // Output: 2026-10-15T09:00-04:00[America/New_York]
        System.out.println("Tokyo Time:    " + tokyoMeetingOct); 
        // Output: 2026-10-15T22:00+09:00[Asia/Tokyo] (13 hours difference)

        // Scenario 2: Meeting in December (Standard Time in New York)
        LocalDateTime decemberTime = LocalDateTime.of(2026, 12, 15, 9, 0); // 9:00 AM
        ZonedDateTime nyMeetingDec = ZonedDateTime.of(decemberTime, nyZone);
        ZonedDateTime tokyoMeetingDec = nyMeetingDec.withZoneSameInstant(tokyoZone);

        System.out.println("\n--- Scenario 2 (December Meeting) ---");
        System.out.println("New York Time: " + nyMeetingDec); 
        // Output: 2026-12-15T09:00-05:00[America/New_York] (Offset shifted to -05:00)
        System.out.println("Tokyo Time:    " + tokyoMeetingDec); 
        // Output: 2026-12-16T23:00+09:00[Asia/Tokyo] (14 hours difference, shifts to next day)
    }
}
```

### Key Takeaways
1. **Dynamic Offset Resolution**: Java resolves daylight saving offsets dynamically based on the target date. For New York, it applies `-04:00` in October (EDT) and `-05:00` in December (EST).
2. **`withZoneSameInstant` vs `withZoneSameLocal`**: 
   * `withZoneSameInstant()` preserves the absolute moment on the timeline, returning the equivalent time in the target zone (used here).
   * `withZoneSameLocal()` shifts the timezone but keeps the same local date and time digits (e.g., 9:00 AM NY time becomes 9:00 AM Tokyo time).

---

## Common Mistakes

### 1. Static Factory Chaining Trap on Periods
Static factory methods on `Period` do not accumulate. The last called factory method overwrites previous values.
```java
// BAD: Expecting 1 year, 2 months, and 3 days
Period p = Period.ofYears(1).ofMonths(2).ofDays(3); // Result: 3 days (P3D)

// GOOD: Use the multi-argument factory method
Period pCorrect = Period.of(1, 2, 3); // Result: P1Y2M3D
```

### 2. Time Pattern Case Sensitivity in `DateTimeFormatter`
Case matters for pattern characters. Using lower-case `m` instead of upper-case `M` results in formatting minutes instead of months.
* `M` = Month-of-year (e.g., `06`)
* `m` = Minute-of-hour (e.g., `30`)
* `y` = Year-of-era (e.g., `2026`)
* `Y` = Week-based-year (often behaves differently from calendar year around new-year transitions)

```java
// BAD: Formatting month using minutes
DateTimeFormatter badFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd"); 
// Output might look like "2026-30-12" where 30 is the minute!
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
