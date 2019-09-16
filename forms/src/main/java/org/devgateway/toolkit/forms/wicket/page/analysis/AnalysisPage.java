package org.devgateway.toolkit.forms.wicket.page.analysis;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.components.pivottable.PivotTable;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@MountPath(value = "/analytics")
public class AnalysisPage extends BasePage {

    public AnalysisPage(PageParameters parameters) {
        super(parameters);

        add(new PivotTable("pivotTable", Model.of(MarketPrice.class)));
    }
}
