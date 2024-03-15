package moe.das.blog.model;

import moe.das.blog.App;
import moe.das.blog.utils.Constants;
import moe.das.blog.utils.Sanitizer;
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
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
    private final List<String> tags;
    private final Node document;
    private final boolean excludeFromHome;

    private BlogPost(String title, String path, String category, String description, String author, String date, List<String> tags, Node document, boolean excludeFromHome) {
        this.title = title;
        this.path = path;
        this.category = category;
        this.description = description;
        this.author = author;
        this.date = date;
        this.tags = tags;
        this.document = document;
        this.excludeFromHome = excludeFromHome;
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

        var fullPath = Sanitizer.sanitizeString(fileName.replace(".md", ""));
        var pathParts = fullPath.split("/");
        var path = pathParts[pathParts.length - 1];

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
        var category = metadata.get("category");
        var author = metadata.get("author");
        var date = metadata.get("date");
        var description = metadata.get("description");
        var tags = metadata.get("tags");

        if (title == null || title.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + fullPath);
            LoggerFactory.getLogger(App.class).error("Title is invalid!");
            return Optional.empty();
        }

        if (category == null || category.size() != 1) {
            category = Collections.singletonList("general");
        }

        if (author == null || author.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + fullPath);
            LoggerFactory.getLogger(App.class).error("Author is invalid!");
            return Optional.empty();
        }

        if (date == null || date.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + fullPath);
            LoggerFactory.getLogger(App.class).error("Date is invalid!");
            return Optional.empty();
        }

        if (description == null || description.size() != 1) {
            LoggerFactory.getLogger(App.class).error("Failed to process post " + fullPath);
            LoggerFactory.getLogger(App.class).error("Short description is invalid!");
            return Optional.empty();
        }

        if (tags == null) {
            tags = Collections.emptyList();
        }

        var excludeFromHome = false;
        var exclusionMetadata = metadata.get("exclude-from-home");
        if (exclusionMetadata != null && !exclusionMetadata.isEmpty() && exclusionMetadata.getFirst().startsWith("true")) {
            excludeFromHome = true;
        }

        return Optional.of(new BlogPost(title.getFirst(), path, category.getFirst(), description.getFirst(), author.getFirst(), date.getFirst(), tags, document, excludeFromHome));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    /**
     * The parsed {@link Node} that represents the body of this blog post.
     *
     * @return the Node for this blog post
     */
    public Node getDocument() {
        return document;
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
     * The category that this blog post belongs in. Might contain sub-categories.
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

    public String getUrl() {
        return Constants.BASE_BLOG_URL + path;
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
