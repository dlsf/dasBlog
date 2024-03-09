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
}
