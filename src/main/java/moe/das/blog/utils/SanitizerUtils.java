package moe.das.blog.utils;

/**
 * Utility class that sanitizes strings.
 */
public class SanitizerUtils {
    /**
     * Replaces certain characters in a string with others to prevent HTML and Markdown encoding issues.
     */
    public static String sanitizeString(String string) {
        return string.toLowerCase().replaceAll("[^A-Za-z0-9/]", "-");
    }
}
