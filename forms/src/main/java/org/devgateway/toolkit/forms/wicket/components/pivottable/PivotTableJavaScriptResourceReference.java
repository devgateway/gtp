package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;

import de.agilecoders.wicket.core.util.Dependencies;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

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
        return Dependencies.combine(super.getDependencies(),
                PivotJavaScriptResourceReference.getHeaderItem(Application.get()),
                PivotJavaScriptResourceReference.getHeaderItemForFrench(Application.get()));
    }

    public static JavaScriptResourceReference get() {
        return REF;
    }
}
