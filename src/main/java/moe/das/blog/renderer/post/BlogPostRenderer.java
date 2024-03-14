package moe.das.blog.renderer.post;

import moe.das.blog.model.BlogPost;
import moe.das.blog.renderer.HtmlRenderer;
import org.commonmark.renderer.Renderer;

/**
 * Renders a given {@link BlogPost} according to a post template as HTML.
 */
public class BlogPostRenderer implements HtmlRenderer {
    private final BlogPost blogPost;
    private final String postTemplate;
    private final Renderer htmlRenderer;

    /**
     * Renders a given {@link BlogPost} according to a post template as HTML.
     *
     * @param blogPost the blog post to be rendered
     * @param postTemplate the template to be used to generate this blog post
     * @param htmlRenderer the HTML renderer to be used to render the markdown of this blog post
     */
    public BlogPostRenderer(BlogPost blogPost, String postTemplate, Renderer htmlRenderer) {
        this.blogPost = blogPost;
        this.postTemplate = postTemplate;
        this.htmlRenderer = htmlRenderer;
    }

    @Override
    public String toHtml() {
        var bodyHtml = htmlRenderer.render(blogPost.getDocument());

        var tagRenderer = new TagRenderer(blogPost.getTags());
        var tagHtml = tagRenderer.toHtml();

        return postTemplate
                .replace("%TITLE%", blogPost.getTitle())
                .replace("%AUTHOR%", blogPost.getAuthor())
                .replace("%DATE%", blogPost.getDate())
                .replace("%DESCRIPTION%", blogPost.getDescription())
                .replace("%URL%", blogPost.getUrl())
                .replace("%BODY_TEXT%", bodyHtml)
                .replace("%TAGS%", tagHtml);
    }
}
