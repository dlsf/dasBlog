package moe.das.blog.renderer.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an HTML column with its individual fields.
 */
public class Column {
    private final List<String> items;

    /**
     * The default constructor. Initializes a new column with the provided header.
     *
     * @param header the header of this column
     */
    public Column(String header) {
        this.items = new ArrayList<>(Collections.singletonList(header));
    }

    /**
     * Appends a new item to this table.
     *
     * @param item the item that should be appended
     */
    public void add(String item) {
        items.add(item);
    }

    /**
     * The full HTML column which this object represents.
     *
     * @return the HTML column
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<tr>");

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (i == 0) {
                stringBuilder.append("<th class=\"table-header\">").append(item).append("</th>");
            } else {
                stringBuilder.append("<th>").append(item).append("</th>");
            }
        }

        stringBuilder.append("</tr>");
        return stringBuilder.toString();
    }
}
