package moe.das.blog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;

import java.util.Set;

/**
 * Constants used by the application.
 */
public class Constants {
    /**
     * Extensions that should be enabled when parsing and rendering Markdown.
     */
    public static final Set<Extension> EXTENSIONS = Set.of(YamlFrontMatterExtension.create());

    /**
     * HTML displayed on the home screen when there are no other posts available.
     */
    public static final String NO_POSTS_PLACEHOLDER = "<br><center><h3>No Available Posts!</h3></center>";

    /**
     * The URL to this blog.
     */
    public static final String BASE_BLOG_URL = "https://blog.das.moe/";
}
