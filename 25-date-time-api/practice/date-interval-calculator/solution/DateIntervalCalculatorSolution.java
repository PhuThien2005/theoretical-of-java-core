import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Reference solution for DateIntervalCalculatorSolution.
 * 
 * LocalDate and DayOfWeek:
 * - `LocalDate` represents ISO-8601 calendar dates without time.
 * - `.getDayOfWeek()` returns `DayOfWeek` enum constant (MONDAY through SUNDAY).
 */
public class DateIntervalCalculatorSolution {

    public static long calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return 0;
        }

        long workingDays = 0;
        LocalDate current = startDate;

        // Loop through all dates inclusive of both boundaries
        while (!current.isAfter(endDate)) {
            DayOfWeek day = current.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            current = current.plusDays(1);
        }

        return workingDays;
    }
}
