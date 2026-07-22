package no25_date_time_api.practice.global_meeting_scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Starter template for a timezone meeting scheduling utility.
 */
public class GlobalMeetingScheduler {

    /**
     * Converts a local date-time string from a source timezone to a target timezone,
     * formatting the output string.
     * 
     * Requirements:
     * - Input format: "yyyy-MM-dd HH:mm" (e.g. "2026-06-30 14:00")
     * - Output format: "yyyy-MM-dd HH:mm z" (e.g. "2026-06-30 19:00 BST" or "2026-07-01 03:00 JST")
     * - Handle invalid source/target Zone IDs safely.
     *
     * @param dateTimeStr local source date and time
     * @param sourceZoneId timezone of the source (e.g. "America/New_York")
     * @param targetZoneId target timezone (e.g. "Europe/London")
     * @return formatted target date-time string, or null if inputs are invalid/null
     */
    public static String formatMeetingTime(String dateTimeStr, String sourceZoneId, String targetZoneId) {
        // TODO: Parse, convert timezone, and format output
        return null;
    }
}
