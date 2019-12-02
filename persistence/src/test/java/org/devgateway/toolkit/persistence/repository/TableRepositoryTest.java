package org.devgateway.toolkit.persistence.repository;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;

import org.devgateway.toolkit.persistence.dao.Column;
import org.devgateway.toolkit.persistence.dao.DataSource;
import org.devgateway.toolkit.persistence.dao.Table;
import org.devgateway.toolkit.persistence.spring.PersistenceApplication;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { PersistenceApplication.class })
public class TableRepositoryTest {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSave() {
        DataSource ds = new DataSource("Data Source 1");
        dataSourceRepository.save(ds);

        Table table = new Table("statistics", ds);
        table.addColumn(new Column("crop", "EMPTY_STRING"));
        table.addColumn(new Column("location", "EMPTY_STRING"));
        table.addColumn(new Column("production", "DOUBLE PRECISION"));
        table.addColumn(new Column("yield", "DOUBLE PRECISION"));
        table.addColumn(new Column("area_seeded", "DOUBLE PRECISION"));
        tableRepository.saveAndFlush(table);

        entityManager.clear();

        Table tableFromDb = tableRepository.findAll().get(0);

        assertThat(tableFromDb, allOf(
                not(sameInstance(table)),
                hasProperty("name", is(table.getName())),
                hasProperty("columns", contains(
                        column("crop"),
                        column("location"),
                        column("production"),
                        column("yield"),
                        column("area_seeded")))));
    }

    private Matcher<Column> column(String name) {
        return hasProperty("name", is(name));
    }
}
