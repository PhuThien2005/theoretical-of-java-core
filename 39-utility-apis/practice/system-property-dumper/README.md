# Exercise: System Property Dumper

## Objective
Implement a system utility that formats and prints system environment information, dates, and numbers conforming to specific localized user formats.

## Requirements
Implement the following methods in `SystemPropertyDumper`:
1. `String getLocalizedSystemInfo(Locale locale, long timestamp, double usagePercent)`:
   - Construct a localized environment report using `java.text.MessageFormat`, `java.time` or `java.text.SimpleDateFormat`, and `java.text.NumberFormat`.
   - The formatted string must follow the exact structure:
     `"On [FormattedDate], system usage was [FormattedPercent]."`
     - `[FormattedDate]` must be formatted as a medium date format for the specified `Locale` (e.g. `DateFormat.getDateInstance(DateFormat.MEDIUM, locale).format(new Date(timestamp))`).
     - `[FormattedPercent]` must be formatted as a percentage for the specified `Locale` (e.g. `NumberFormat.getPercentInstance(locale).format(usagePercent)`).
   - Return this formatted string.
   
2. `String getSystemPropertyOrFallback(String propertyName, String fallback)`:
   - Read Java system property `System.getProperty(propertyName)`.
   - Return the system property value if present and not empty, otherwise return `fallback`.
