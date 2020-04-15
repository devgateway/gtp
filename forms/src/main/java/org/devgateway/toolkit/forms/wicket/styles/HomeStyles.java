package org.devgateway.toolkit.forms.wicket.styles;

import org.apache.wicket.request.resource.CssResourceReference;

public class HomeStyles extends CssResourceReference {
    private static final long serialVersionUID = 575863175289821569L;

    public static final HomeStyles INSTANCE = new HomeStyles();

    public HomeStyles() {
        super(HomeStyles.class, "HomeStyles.css");
    }
}
