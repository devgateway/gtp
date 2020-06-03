package org.devgateway.toolkit.persistence.service.indicator;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class ReaderException extends Exception {

    private final List<String> errors;

    public ReaderException(List<String> errors) {
        this(errors, null);
    }

    public ReaderException(List<String> errors, Exception e) {
        super("Import failed with " + errors.size() + " errors", e);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
