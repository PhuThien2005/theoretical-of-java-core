# Practice Exercises: Date-Time API

This folder contains hands-on practice exercises to reinforce your understanding of Java 8's modern Date-Time API, including `LocalDate`, `LocalDateTime`, timezone conversions with `ZonedDateTime`, and parsing/formatting with `DateTimeFormatter`.

## Exercises

### 1. Global Meeting Scheduler (`global-meeting-scheduler`)
Scheduling meetings across international offices requires converting times accurately across different time zones.
- **Goal**: Implement a timezone converter in `GlobalMeetingScheduler` that parses a local meeting date/time in a source timezone and converts it to its matching date/time representation in a target timezone, formatted cleanly.

#### Directory Structure
- [GlobalMeetingScheduler.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/global-meeting-scheduler/src/GlobalMeetingScheduler.java)
- [GlobalMeetingSchedulerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/global-meeting-scheduler/test/GlobalMeetingSchedulerTest.java)
- [GlobalMeetingScheduler.java (Solution)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/global-meeting-scheduler/solution/GlobalMeetingScheduler.java)

---

### 2. Date Interval Calculator (`date-interval-calculator`)
Calculating intervals (like business working days) is a common requirement in workflow and financial software.
- **Goal**: Implement a method that calculates the exact number of working days (Monday through Friday, inclusive) between two `LocalDate` boundaries.

#### Directory Structure
- [DateIntervalCalculator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/date-interval-calculator/src/DateIntervalCalculator.java)
- [DateIntervalCalculatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/date-interval-calculator/test/DateIntervalCalculatorTest.java)
- [DateIntervalCalculator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/25-date-time-api/practice/date-interval-calculator/solution/DateIntervalCalculator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 25-date-time-api
```
