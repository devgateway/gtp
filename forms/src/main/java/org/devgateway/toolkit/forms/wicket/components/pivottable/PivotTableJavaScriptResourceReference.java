package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;

import de.agilecoders.wicket.core.util.Dependencies;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.devgateway.toolkit.forms.wicket.styles.PlotlyJavaScript;

/**
 * @author Octavian Ciubotaru
 */
public class PivotTableJavaScriptResourceReference extends JavaScriptResourceReference {

    private static final JavaScriptResourceReference REF = new PivotTableJavaScriptResourceReference();

    public PivotTableJavaScriptResourceReference() {
        super(PivotTableJavaScriptResourceReference.class, "PivotTable.js");
    }

    @Override
    public List<HeaderItem> getDependencies() {
        Application application = Application.get();
        return Dependencies.combine(super.getDependencies(),
                JavaScriptHeaderItem.forReference(PlotlyJavaScript.get(application)),
                JavaScriptHeaderItem.forReference(PlotlyJavaScript.FR),
                PivotJavaScriptResourceReference.getHeaderItem(application),
                PivotJavaScriptResourceReference.getHeaderItemForPlotlyRenderers(),
                PivotJavaScriptResourceReference.getHeaderItemForFrench(application));
    }

    public static JavaScriptResourceReference get() {
        return REF;
    }
}
