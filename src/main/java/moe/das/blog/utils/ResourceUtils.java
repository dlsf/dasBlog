package moe.das.blog.utils;

import moe.das.blog.App;

import java.io.IOException;

/**
 * Utility class that reads class resources.
 */
public class ResourceUtils {
    /**
     * Reads the resource at the required location as a String.
     *
     * @param resourceName the name of the resource
     * @return the content of the resource
     * @throws IOException if the resource is inaccessible
     */
    public static String getResourceAsString(String resourceName) throws IOException {
        try (var stream = App.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (stream == null) return "";
            return new String(stream.readAllBytes());
        }
    }
}
