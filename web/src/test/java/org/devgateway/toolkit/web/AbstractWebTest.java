package org.devgateway.toolkit.web;

import org.devgateway.toolkit.persistence.spring.PersistenceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration")
@SpringBootTest(classes = { WebApplication.class, PersistenceApplication.class },
        webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public abstract class AbstractWebTest {

}
