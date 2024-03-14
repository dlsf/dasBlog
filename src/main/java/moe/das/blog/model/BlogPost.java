package moe.das.blog.model;

import moe.das.blog.App;
import moe.das.blog.utils.Constants;
import moe.das.blog.utils.Sanitizer;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a blog post on the site.
 */
public class BlogPost {
    private final String title;
    private final String path;
    private final String category;
    private final String description;
    private final String author;
    private final String date;
    private final Node document;
    private final boolean excludeFromHome;
    private final String url;

    private BlogPost(String title, String path, String category, String description, String author, String date, Node document, boolean excludeFromHome) {
        this.title = title;
        this.path = path;
        this.category = category;
        this.description = description;
        this.author = author;
        this.date = date;
        this.document = document;
        this.excludeFromHome = excludeFromHome;
        this.url = Constants.BASE_BLOG_URL + path;
    }

    /**
     * Constructs a new BlogPost based on the content of a file.
     *
     * @param file the file that should be parsed as a BlogPost
     * @param markdownParser parser used to parse the file's content
     * @return an Optional. Empty if something went wrong, otherwise the newly constructed blog post
     */
    public static Optional<BlogPost> fromFile(Path file, Parser markdownParser) {
        var fileName = file.toString().toLowerCase().replace("posts/", "");
        if (!fileName.endsWith(".md")) return Optional.empty();

        var path = Sanitizer.sanitizeString(fileName.replace(".md", ""));
        Node document;
        try {
            document = markdownParser.parse(Files.readString(file));
        } catch (IOException e) {
            return Optional.empty();
        }

        // Get and validate post metadata
        var metadataVisitor = new YamlFrontMatterVisitor();
        document.accept(metadataVisitor);
        var metadata = metadataVisitor.getData();
        var title = metadata.get("title");
        var author = metadata.get("author");
        var date = metadata.get("date");
        var description = metadata.get("description");

        if (title == null || title.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + path);
            LoggerFactory.getLogger(App.class).error("Title is invalid!");
            return Optional.empty();
        }

        if (author == null || author.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + path);
            LoggerFactory.getLogger(App.class).error("Author is invalid!");
            return Optional.empty();
        }

        if (date == null || date.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + path);
            LoggerFactory.getLogger(App.class).error("Date is invalid!");
            return Optional.empty();
        }

        if (description == null || description.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + path);
            LoggerFactory.getLogger(App.class).error("Short description is invalid!");
            return Optional.empty();
        }

        var excludeFromHome = false;
        var exclusionMetadata = metadata.get("exclude-from-home");
        if (exclusionMetadata != null && !exclusionMetadata.isEmpty() && exclusionMetadata.getFirst().startsWith("true")) {
            excludeFromHome = true;
        }

        String category;
        var categoryIndex = path.lastIndexOf("/");
        if (categoryIndex == -1) {
            category = "general";
        } else {
            category = path.substring(0, path.lastIndexOf("/"));
        }

        return Optional.of(new BlogPost(title.getFirst(), path, category, description.getFirst(), author.getFirst(), date.getFirst(), document, excludeFromHome));
    }

    /**
     * Returns the full HTML page of this blog post based on a template.
     *
     * @param postTemplate the template that should be used to generate this blog post
     * @param htmlRenderer the HTML renderer used to render the markdown of this blog post
     * @return the full HTML that represents this blog post
     */
    public String getHtml(String postTemplate, Renderer htmlRenderer) {
        var htmlBody = htmlRenderer.render(document);

        return postTemplate
                .replace("%TITLE%", title)
                .replace("%AUTHOR%", author)
                .replace("%DATE%", date)
                .replace("%DESCRIPTION%", description)
                .replace("%URL%", url)
                .replace("%BODY_TEXT%", htmlBody);
    }

    /**
     * The absolute path to this post.
     *
     * @return the absolute path
     */
    public String getPath() {
        return path;
    }

    /**
     * The category that this blog post belongs in. Might contain sub-categories
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Whether this blog post should be shown on the home screen.
     *
     * @return true if it should be excluded, otherwise false
     */
    public boolean excludeFromHome() {
        return excludeFromHome;
    }

    /**
     * An HTML link tag with the absolute location of this post as a target and the post's title as a name.
     *
     * @return the HTML link tag
     */
    public String getHtmlLink() {
        return "<a href=\"/" + path +"\">" + title + "</a>";
    }
}
