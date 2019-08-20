package org.devgateway.toolkit.persistence.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Octavian Ciubotaru
 */
@Suite.SuiteClasses({
        PersonRepositoryTest.class,
        DataSourceRepositoryTest.class,
        TableRepositoryTest.class})
@RunWith(Suite.class)
public class RepositoryTestSuite {
}
