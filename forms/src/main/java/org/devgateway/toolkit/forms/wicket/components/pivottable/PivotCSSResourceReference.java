package org.devgateway.toolkit.forms.wicket.components.pivottable;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * @author Octavian Ciubotaru
 */
public final class PivotCSSResourceReference extends CssResourceReference {

    private static final CssResourceReference SRC_REF =
            new PivotCSSResourceReference("pivot.css");

    private static final CssResourceReference MIN_REF =
            new PivotCSSResourceReference("pivot.min.css");

    private PivotCSSResourceReference(String name) {
        super(PivotCSSResourceReference.class, name);
    }

    public static HeaderItem getHeaderItem(Application application) {
        return CssHeaderItem.forReference(application.usesDeploymentConfig() ? MIN_REF : SRC_REF);
    }
}
