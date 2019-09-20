package org.devgateway.toolkit.forms.wicket.styles;

import org.apache.wicket.Application;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * @author idobre
 * @since 3/6/17
 */
public class PlotlyJavaScript extends JavaScriptResourceReference {
    private static final long serialVersionUID = 1L;

    public static final PlotlyJavaScript INSTANCE = new PlotlyJavaScript("plotly.min.js");

    public static final PlotlyJavaScript FR = new PlotlyJavaScript("plotly-locale-fr.js");

    private static final PlotlyJavaScript SRC_INSTANCE = new PlotlyJavaScript("plotly.js");

    /**
     * Construct.
     */
    public PlotlyJavaScript(String name) {
        super(PlotlyJavaScript.class, "/assets/js/plotly/" + name);
    }

    public static PlotlyJavaScript get(Application application) {
        return application.usesDeploymentConfig() ? INSTANCE : SRC_INSTANCE;
    }
}
