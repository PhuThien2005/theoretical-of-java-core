package no25_date_time_api.practice.global_meeting_scheduler;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Reference solution for GlobalMeetingSchedulerSolution.
 * 
 * ZonedDateTime and ZoneId:
 * - `LocalDateTime` contains date-time without timezone info.
 * - `ZonedDateTime` binds LocalDateTime with a `ZoneId`.
 * - `withZoneSameInstant(ZoneId)` converts the time to another zone keeping the same point in time (absolute instant).
 */
public class GlobalMeetingSchedulerSolution {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");

    public static String formatMeetingTime(String dateTimeStr, String sourceZoneId, String targetZoneId) {
        if (dateTimeStr == null || sourceZoneId == null || targetZoneId == null) {
            return null;
        }

        try {
            // Step 1: Parse input string to LocalDateTime
            LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);

            // Step 2: Establish ZonedDateTime in source zone
            ZoneId sourceZone = ZoneId.of(sourceZoneId);
            ZonedDateTime sourceZdt = ZonedDateTime.of(ldt, sourceZone);

            // Step 3: Convert to target zone keeping the same instant
            ZoneId targetZone = ZoneId.of(targetZoneId);
            ZonedDateTime targetZdt = sourceZdt.withZoneSameInstant(targetZone);

            // Step 4: Format to target output string
            return targetZdt.format(OUTPUT_FORMATTER);
        } catch (DateTimeException | IllegalArgumentException e) {
            // Catches parsing issues, zone ID formatting issues, etc.
            return null;
        }
    }
}
