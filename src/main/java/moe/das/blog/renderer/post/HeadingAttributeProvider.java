package moe.das.blog.renderer.post;

import moe.das.blog.utils.Sanitizer;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * Attribute provider that adds an HTML attribute to all headings, so they can be referenced in links.
 */
public class HeadingAttributeProvider implements AttributeProvider {
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
        if (node instanceof Heading) {
            var child = node.getFirstChild();
            while (child.getFirstChild() != null) {
                child = child.getFirstChild();
            }

            if (child instanceof Text text) {
                attributes.put("id", Sanitizer.sanitizeString(text.getLiteral()));
            }
        }
    }
}
