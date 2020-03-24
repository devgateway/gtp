package org.devgateway.toolkit.persistence.dao.ipar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A field that are used in analysis tool.
 *
 * @author Octavian Ciubotaru
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PivotTableField {

    boolean hideInAggregators() default false;

    boolean hideInDragAndDrop() default false;
}
