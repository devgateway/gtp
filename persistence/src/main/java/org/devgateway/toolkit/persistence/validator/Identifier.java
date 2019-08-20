package org.devgateway.toolkit.persistence.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Ensures that this is a valid identifier for use in PostgresSQL.
 *
 * <p>Maximum length cannot exceed 63. Only lowercase characters and underscores are allowed.</p>
 *
 * Cannot start with underscore because of Apache Derby used in tests.
 *
 * @author Octavian Ciubotaru
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Pattern(regexp = "[a-z][_a-z0-9]*")
@Size(max = 63)
@Constraint(validatedBy = { })
@ReportAsSingleViolation
public @interface Identifier {

    String message() default "{org.devgateway.toolkit.persistence.dao.Identifier.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
