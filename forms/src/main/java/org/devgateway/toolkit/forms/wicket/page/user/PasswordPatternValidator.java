package org.devgateway.toolkit.forms.wicket.page.user;

import org.apache.wicket.validation.validator.PatternValidator;

/**
 * @author Octavian Ciubotaru
 */
public class PasswordPatternValidator extends PatternValidator {

    // 1 digit, 1 lower, 1 upper, 1 symbol "@#$%", from 6 to 20
    // private static final String PASSWORD_PATTERN =
    // "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    // 1 digit, 1 caps letter, from 10 to 20
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z]).{10,20})";

    public PasswordPatternValidator() {
        super(PASSWORD_PATTERN);
    }
}
