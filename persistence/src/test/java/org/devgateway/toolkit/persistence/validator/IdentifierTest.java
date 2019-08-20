package org.devgateway.toolkit.persistence.validator;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Octavian Ciubotaru
 */
public class IdentifierTest {

    private static Validator validator;

    private class Dummy {

        @Identifier
        private String identifier;
    }

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testNullAllowed() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy(null));
        assertThat(violations, emptyIterable());
    }

    @Test
    public void testEmptyStringNotAllowed() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy(""));
        assertThat(violations, contains(identifierViolation()));
    }

    @Test
    public void testUppercaseNotAllowed() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy("A"));
        assertThat(violations, contains(identifierViolation()));
    }

    @Test
    public void testTooLong() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy(StringUtils.repeat('a', 64)));
        assertThat(violations, contains(identifierViolation()));
    }

    @Test
    public void testMaxValidLength() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy(StringUtils.repeat('a', 63)));
        assertThat(violations, emptyIterable());
    }

    @Test
    public void testShortestValid() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy("a"));
        assertThat(violations, emptyIterable());
    }

    @Test
    public void testValid() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy("a_1"));
        assertThat(violations, emptyIterable());
    }

    @Test
    public void testStartWithDigitNotAllowed() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy("1a"));
        assertThat(violations, contains(identifierViolation()));
    }

    @Test
    public void testStartWithUnderscoreNotAllowed() {
        Set<ConstraintViolation<Dummy>> violations = validator.validate(newDummy("_a"));
        assertThat(violations, contains(identifierViolation()));
    }

    private Dummy newDummy(String o) {
        Dummy dummy = new Dummy();
        dummy.identifier = o;
        return dummy;
    }

    private Matcher<ConstraintViolation<Dummy>> identifierViolation() {
        return hasProperty("messageTemplate",
                is("{org.devgateway.toolkit.persistence.dao.Identifier.message}"));
    }
}
