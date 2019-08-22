package org.devgateway.toolkit.persistence.service;

/**
 * @author Octavian Ciubotaru
 */
public final class SQLUtils {

    private SQLUtils() {
    }

    /**
     * Take an identifier and surround it with quotes and escape any quotes inside.
     */
    public static String quotedIdentifier(String unquotedIdentifier) {
        return "\"" + unquotedIdentifier.replace("\"", "\"\"") + "\"";
    }
}
