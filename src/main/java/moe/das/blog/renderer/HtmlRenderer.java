package moe.das.blog.renderer;

/**
 * An object that can be rendered as HTML.
 */
public interface HtmlRenderer {
    /**
     * Returns the HTML representation of this object.
     *
     * @return the respective HTML source code
     */
    String toHtml();
}
