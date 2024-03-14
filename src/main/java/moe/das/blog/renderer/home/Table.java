package moe.das.blog.renderer.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an HTML table with its columns.
 */
public class Table {
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

    /**
     * The full HTML table which this object represents.
     *
     * @return the HTML table
     */
    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("<table id=\"index\">");

        for (Column column : columns) {
            stringBuilder.append(column);
        }

        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }
}
