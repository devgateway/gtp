package org.devgateway.toolkit.persistence.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.devgateway.toolkit.persistence.dao.ipar.Column;
import org.devgateway.toolkit.persistence.dao.ipar.Table;
import org.devgateway.toolkit.persistence.service.ipar.DDLGenerator;
import org.junit.Test;

/**
 * @author Octavian Ciubotaru
 */
public class DDLGeneratorTest {

    @Test
    public void testDropDatabase() {
        Table statistics = new Table("statistics", null);
        DDLGenerator generator = new DDLGenerator();
        assertThat(generator.dropTable(statistics), is("DROP TABLE \"statistics\";"));
    }

    @Test
    public void testCreateDatabaseWithOneColumn() {
        Table statistics = new Table("statistics", null);
        statistics.addColumn(new Column("year", "BIGINT"));

        DDLGenerator generator = new DDLGenerator();
        assertThat(generator.createTable(statistics), is("CREATE TABLE \"statistics\" {\"year\" BIGINT};"));
    }

    @Test
    public void testCreateDatabaseWithTwoColumns() {
        Table statistics = new Table("statistics", null);
        statistics.addColumn(new Column("year", "BIGINT"));
        statistics.addColumn(new Column("amount", "DOUBLE PRECISION"));

        DDLGenerator generator = new DDLGenerator();
        assertThat(generator.createTable(statistics),
                is("CREATE TABLE \"statistics\" {\"year\" BIGINT, \"amount\" DOUBLE PRECISION};"));
    }
}
