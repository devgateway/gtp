package org.devgateway.toolkit.forms.wicket.page.edit.category;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public class RiverLevelReaderException extends Exception {

    private final List<String> errors;

    public RiverLevelReaderException(List<String> errors) {
        this(errors, null);
    }

    public RiverLevelReaderException(List<String> errors, Exception e) {
        super(String.join("\n", errors), e);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
