/**
 * 
 */
package org.devgateway.toolkit.persistence.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.devgateway.toolkit.persistence.spring.PersistenceApplication;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mpostelnicu
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { PersistenceApplication.class })
@Category(RepositoryTests.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCount() {
        assertThat(personRepository.count(), is(0L));
    }
}
