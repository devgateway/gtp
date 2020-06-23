package org.devgateway.toolkit.persistence.service.indicator;

import java.util.Collection;

/**
 * @author Octavian Ciubotaru
 */
public class ReaderException extends Exception {

    private final Collection<String> errors;

    public ReaderException(Collection<String> errors) {
        this(errors, null);
    }

    public ReaderException(Collection<String> errors, Exception e) {
        super("Import failed with " + errors.size() + " errors", e);
        this.errors = errors;
    }

    public Collection<String> getErrors() {
        return errors;
    }
}
