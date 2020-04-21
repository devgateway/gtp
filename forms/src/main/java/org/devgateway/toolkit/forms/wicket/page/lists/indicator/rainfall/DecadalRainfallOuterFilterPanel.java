package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DecadalRainfallFilterState;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorOuterFilterPanel;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallOuterFilterPanel extends AbstractIndicatorOuterFilterPanel<DecadalRainfall> {
    private static final long serialVersionUID = 1947240267168715858L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public DecadalRainfallOuterFilterPanel(String id, ResettingFilterForm<DecadalRainfallFilterState> filterForm) {
        super(id, filterForm);

        this.yearIndicatorGenerator = decadalRainfallService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.addYearChoice();
    }
}
