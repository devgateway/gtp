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

    /**
     * Generates pattern to be used in LIKE expression with the "contains" semantics.
     * Any % or _ characters present in term are escaped. The resulting pattern is prefixed and suffixed with %.
     */
    public static String likeContainsPattern(String term) {
        return "%" + term.replace("_", "\\_").replace("%", "\\%") + "%";
    }
}
