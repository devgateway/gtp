package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;

import de.agilecoders.wicket.core.util.Dependencies;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.jqueryui.JQueryUIJavaScriptReference;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author Octavian Ciubotaru
 */
public final class PivotJavaScriptResourceReference extends JavaScriptResourceReference {

    private static final JavaScriptResourceReference SRC_REF =
            new PivotJavaScriptResourceReference("pivot.js");

    private static final JavaScriptResourceReference MIN_REF =
            new PivotJavaScriptResourceReference("pivot.min.js");

    private static final JavaScriptResourceReference SRC_FR_REF =
            new PivotJavaScriptResourceReference("pivot.fr.js");

    private static final JavaScriptResourceReference MIN_FR_REF =
            new PivotJavaScriptResourceReference("pivot.fr.min.js");

    private static final JavaScriptResourceReference SRC_PLOTLY_RENDERERS_REF =
            new PivotJavaScriptResourceReference("plotly_renderers.js");

    private PivotJavaScriptResourceReference(String name) {
        super(PivotJavaScriptResourceReference.class, name);
    }

    public static HeaderItem getHeaderItem(Application application) {
        return JavaScriptHeaderItem.forReference(application.usesDeploymentConfig() ? MIN_REF : SRC_REF);
    }

    public static HeaderItem getHeaderItemForFrench(Application application) {
        return JavaScriptHeaderItem.forReference(application.usesDeploymentConfig() ? MIN_FR_REF : SRC_FR_REF);
    }

    public static HeaderItem getHeaderItemForPlotlyRenderers() {
        return JavaScriptHeaderItem.forReference(SRC_PLOTLY_RENDERERS_REF);
    }

    @Override
    public List<HeaderItem> getDependencies() {
        return Dependencies.combine(super.getDependencies(),
                JQueryUIJavaScriptReference.asHeaderItem());
    }
}
