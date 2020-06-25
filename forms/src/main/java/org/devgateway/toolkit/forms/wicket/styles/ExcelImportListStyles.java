package org.devgateway.toolkit.forms.wicket.styles;

import org.apache.wicket.request.resource.CssResourceReference;

public class ExcelImportListStyles extends CssResourceReference {

    public static final ExcelImportListStyles INSTANCE = new ExcelImportListStyles();

    public ExcelImportListStyles() {
        super(ExcelImportListStyles.class, "ExcelImportListStyles.css");
    }
}
