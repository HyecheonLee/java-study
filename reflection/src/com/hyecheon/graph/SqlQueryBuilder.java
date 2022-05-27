package com.hyecheon.graph;

import com.hyecheon.graph.annotations.Annotations;

import java.util.List;

import static com.hyecheon.graph.annotations.Annotations.*;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/27
 */
public class SqlQueryBuilder {
    @Input("ids")
    private List<String> ids;

    @Input("limit")
    private Integer limit;

    @Input("table")
    private String tableName;

    @Input("columns")
    private List<String> columnNames;

    public SqlQueryBuilder(List<String> ids, Integer limit, String tableName, List<String> columnNames) {
        this.ids = ids;
        this.limit = limit;
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    @Operation("SelectBuilder")
    public String selectStatementBuilder(@Input("table") String tableName,
                                         @Input("columns") List<String> columnNames) {

        final var columnString = columnNames.isEmpty() ? "*" : String.join(",", columnNames);

        return "select %s from %s".formatted(columnString, tableName);
    }

    @FinalResult
    public String addWhereClause(
            @DependsOn("SelectBuilder") String query,
            @Input("ids") List<String> ids) {
        if (ids.isEmpty()) {
            return query;
        }
        return "%s where id in (%s)".formatted(query, String.join(",", ids));
    }

    public String addLimit(@DependsOn("SelectBuilder") String query,
                           @Input("limit") Integer limit) {
        if (limit == null || limit == 0) {
            return query;
        }
        if (limit < 0) {
            throw new RuntimeException("limit cannot be negative");
        }
        return "%s limit %d".formatted(query, limit);
    }

}
