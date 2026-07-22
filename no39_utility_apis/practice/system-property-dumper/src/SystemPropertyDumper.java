package systempropertydumper;

import java.util.Locale;

public class SystemPropertyDumper {

    /**
     * Constructs a localized system report containing the formatted date and system usage.
     * Output format must match: "On [FormattedDate], system usage was [FormattedPercent]."
     * 
     * @param locale the target locale for formatting
     * @param timestamp the epoch millisecond timestamp of the check
     * @param usagePercent system usage as a fraction (e.g. 0.725 for 72.5%)
     * @return the localized report string
     */
    public static String getLocalizedSystemInfo(Locale locale, long timestamp, double usagePercent) {
        // TODO: Format timestamp as a medium Date format for the locale.
        // TODO: Format usagePercent as a percentage for the locale.
        // TODO: Combine them into the exact pattern: "On [FormattedDate], system usage was [FormattedPercent]."
        return null;
    }

    /**
     * Reads a system property and returns it, or returns the fallback if it's missing or empty.
     */
    public static String getSystemPropertyOrFallback(String propertyName, String fallback) {
        // TODO: Read System.getProperty.
        return null;
    }
}
