package systempropertydumper;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class SystemPropertyDumperSolution {

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
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }

        // Format the date based on target locale using MEDIUM style
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        String formattedDate = dateFormat.format(new Date(timestamp));

        // Format the percentage based on target locale
        NumberFormat percentFormat = NumberFormat.getPercentInstance(locale);
        String formattedPercent = percentFormat.format(usagePercent);

        return "On " + formattedDate + ", system usage was " + formattedPercent + ".";
    }

    /**
     * Reads a system property and returns it, or returns the fallback if it's missing or empty.
     */
    public static String getSystemPropertyOrFallback(String propertyName, String fallback) {
        if (propertyName == null || propertyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }
        String value = System.getProperty(propertyName);
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }
        return value;
    }
}
