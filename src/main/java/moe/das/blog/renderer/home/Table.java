package moe.das.blog.renderer.home;

import moe.das.blog.renderer.HtmlRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an HTML table with its columns.
 */
public class Table implements HtmlRenderer {
    private final List<Column> columns;

    /**
     * The default constructor. Initializes a new, empty table.
     */
    public Table() {
        this.columns = new ArrayList<>();
    }

    /**
     * Appends a new column to this table.
     *
     * @param column the column that should be appended
     */
    public void addColumn(Column column) {
        columns.add(column);
    }

    @Override
    public String toHtml() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("<table id=\"index\">");

        for (Column column : columns) {
            stringBuilder.append(column.toHtml());
        }

        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }
}
