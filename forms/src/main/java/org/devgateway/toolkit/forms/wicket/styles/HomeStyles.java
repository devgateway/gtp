package org.devgateway.toolkit.forms.wicket.styles;

import org.apache.wicket.request.resource.CssResourceReference;

public class HomeStyles extends CssResourceReference {
    public static final HomeStyles INSTANCE = new HomeStyles();

    public HomeStyles() {
        super(HomeStyles.class, "HomeStyles.css");
    }
}
