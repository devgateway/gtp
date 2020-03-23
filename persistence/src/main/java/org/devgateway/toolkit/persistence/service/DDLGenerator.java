package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.joining;
import static org.devgateway.toolkit.persistence.service.SQLUtils.quotedIdentifier;

import org.devgateway.toolkit.persistence.dao.ipar.Column;
import org.devgateway.toolkit.persistence.dao.ipar.Table;

/**
 * DDL SQL Generator for tables.
 *
 * @author Octavian Ciubotaru
 */
public class DDLGenerator {

    public String createTable(Table table) {
        return String.format("CREATE TABLE %s {%s};",
                quotedIdentifier(table.getName()),
                table.getColumns().stream().map(this::toColumnDefinition).collect(joining(", ")));
    }

    private String toColumnDefinition(Column column) {
        return String.format("%s %s", quotedIdentifier(column.getName()), column.getType());
    }

    public String dropTable(Table table) {
        return "DROP TABLE " + quotedIdentifier(table.getName()) + ";";
    }

    // TODO insert batch?
    // https://vladmihalcea.com/wp-content/uploads/2018/04/VladimirSitnikovreWriteBatchedInserts.png
}
