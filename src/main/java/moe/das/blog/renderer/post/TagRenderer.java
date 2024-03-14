package moe.das.blog.renderer.post;

import java.util.List;

/**
 * Converts a list of blog post tags to their HTML representation.
 */
public class TagRenderer {
    private final List<String> tags;

    /**
     * The default constructor.
     *
     * @param tags the list of blog post tags that should be rendered.
     */
    public TagRenderer(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Converts a list of blog post tags to their HTML representation.
     *
     * @return a HTML string representing the tag list
     */
    public String toHtml() {
        if (tags.isEmpty()) return "";

        return String.format("<div id=\"tags\">Tags:&nbsp;<div class=\"tag\">%s</div></div>", String.join("</div><div class=\"tag\">", tags));
    }
}
