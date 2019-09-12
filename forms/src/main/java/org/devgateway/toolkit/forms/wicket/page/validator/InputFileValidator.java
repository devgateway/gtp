package org.devgateway.toolkit.forms.wicket.page.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.persistence.dao.FileMetadata;

import java.util.Set;

public class InputFileValidator implements IValidator<Set<FileMetadata>> {

    private static final long serialVersionUID = -2412508063601996929L;
    public static final int FILENAME_LENGTH = 50;

    private String errorFileNotAdded;

    private String errorFilenameError;

    public InputFileValidator(String errorFileNotAdded, String errorFilenameError) {
        this.errorFileNotAdded = errorFileNotAdded;
        this.errorFilenameError = errorFilenameError;
    }

    @Override
    public void validate(final IValidatable<Set<FileMetadata>> validatable) {
        if (validatable.getValue().isEmpty()) {
            ValidationError error = new ValidationError(errorFileNotAdded);
            validatable.error(error);
        } else {
            validatable.getValue().stream().forEach(file -> {
                if (file.getName().length() > FILENAME_LENGTH) {
                    ValidationError error = new ValidationError(errorFilenameError);
                    validatable.error(error);
                }
            });
        }
    }
}