package org.devgateway.toolkit.persistence.repository;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;

import org.devgateway.toolkit.persistence.dao.ipar.DataSource;
import org.devgateway.toolkit.persistence.spring.PersistenceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Octavian Ciubotaru
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { PersistenceApplication.class })
public class DataSourceRepositoryTest {

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSave() {
        DataSource dataSource = new DataSource("Test");
        dataSourceRepository.saveAndFlush(dataSource);

        entityManager.clear();

        DataSource dataSourceFromDb = dataSourceRepository.findAll().get(0);

        assertThat(dataSourceFromDb, allOf(
                not(sameInstance(dataSource)),
                hasProperty("name", is("Test"))));
    }
}
