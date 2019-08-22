package org.devgateway.toolkit.persistence.service;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Octavian Ciubotaru
 */
public class SQLUtilsTest {

    @Test
    public void testQuotingSimple() {
        assertEquals("\"a\"", SQLUtils.quotedIdentifier("a"));
    }

    @Test
    public void testQuotingCaseIsNotChanged() {
        assertEquals("\"A\"", SQLUtils.quotedIdentifier("A"));
    }

    @Test
    public void testQuotingEscaping() {
        assertEquals("\"\"\"a\"", SQLUtils.quotedIdentifier("\"a"));
    }

    @Test
    public void testQuotingEscapingMultipleChars() {
        assertEquals("\"\"\"a\"\"\"", SQLUtils.quotedIdentifier("\"a\""));
    }
}
