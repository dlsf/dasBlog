package moe.das.blog;

import io.javalin.Javalin;
import moe.das.blog.home.Column;
import moe.das.blog.home.Table;
import moe.das.blog.model.BlogPost;
import moe.das.blog.renderer.HeadingAttributeProvider;
import moe.das.blog.utils.Constants;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The entry point of the app.
 */
public class App {
    /**
     * Sets up the entire website and starts the web server on port 7070.
     *
     * @throws IOException only thrown during startup when files are missing
     */
    public void run() throws IOException {
        var javalin = Javalin.create(config -> config.staticFiles.add(staticFiles -> {
            staticFiles.hostedPath = "/static";
            staticFiles.directory = "/static";
        }));

        var blogPosts = initializePostPages(javalin);

        // Generate and serve the home screen
        var homeScreenBody = generateHomeScreen(blogPosts);
        var homeTemplate = new String(getClass().getClassLoader().getResourceAsStream("home_template.html").readAllBytes());
        javalin = javalin.get("/", ctx -> ctx.html(homeTemplate.replace("%BODY%", homeScreenBody)));

        // Start server
        javalin.start(7070);
    }

    /**
     * Reads all blog posts from disk and configures their page endpoints.
     *
     * @param javalin the Javalin instance
     * @return a list of all successfully configured blog posts
     * @throws IOException only thrown during startup when files are missing
     */
    private List<BlogPost> initializePostPages(Javalin javalin) throws IOException {
        var postTemplate = new String(getClass().getClassLoader().getResourceAsStream("post_template.html").readAllBytes());
        var markdownParser = Parser.builder().extensions(Constants.EXTENSIONS).build();
        var htmlRenderer = HtmlRenderer.builder()
                .attributeProviderFactory(attributeProviderContext -> new HeadingAttributeProvider())
                .extensions(Constants.EXTENSIONS)
                .build();

        var paths = new ArrayList<BlogPost>();
        for (var file : Files.walk(Path.of("posts/")).toList()) {
            var postOptional = BlogPost.fromFile(file, markdownParser);
            if (postOptional.isEmpty()) continue;

            var post = postOptional.get();
            var html = post.getHtml(postTemplate, htmlRenderer);

            javalin = javalin.get("/" + post.getPath(), ctx -> ctx.html(html));
            paths.add(post);
        }

        return paths;
    }

    /**
     * Generates a home screen view based on the provided blog posts.
     * Hides posts with {{@link BlogPost#excludeFromHome()}} set to true.
     *
     * @param blogPosts the list of blog posts
     * @return the inner HTML body of the home screen
     */
    private String generateHomeScreen(List<BlogPost> blogPosts) {
        var categoryPosts = blogPosts.stream().filter(post -> !post.excludeFromHome()).collect(Collectors.groupingBy(BlogPost::getCategory));
        if (categoryPosts.isEmpty()) return Constants.NO_POSTS_PLACEHOLDER;

        var table = new Table();
        for (Map.Entry<String, List<BlogPost>> entry : categoryPosts.entrySet()) {
            var category = entry.getKey();
            var posts = entry.getValue();
            var column = new Column(category);
            posts.forEach(post -> column.add(post.getHtmlLink()));
            table.addColumn(column);
        }

        return table.toString();
    }
}
