package no25_date_time_api.practice.date_interval_calculator;

import java.time.LocalDate;

/**
 * Starter template for a workdays interval calculator.
 */
public class DateIntervalCalculator {

    /**
     * Calculates the number of working days (Monday through Friday, inclusive of both dates)
     * between startDate and endDate.
     * 
     * If startDate is after endDate, return 0.
     * If either date is null, return 0.
     *
     * @param startDate the starting boundary date
     * @param endDate the ending boundary date
     * @return the count of business/working days
     */
    public static long calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        // TODO: Implement workday iteration skipping Saturdays and Sundays
        return 0;
    }
}
