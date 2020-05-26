package org.devgateway.toolkit.persistence.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.devgateway.toolkit.persistence.dao.DBConstants;

/**
 * @author Octavian Ciubotaru
 */
@Min(DBConstants.MIN_LATITUDE)
@Max(DBConstants.MAX_LATITUDE)
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@Documented
public @interface SenegalLatitude {

    String message() default "{org.devgateway.toolkit.persistence.dao.categories.SenegalLatitude.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
