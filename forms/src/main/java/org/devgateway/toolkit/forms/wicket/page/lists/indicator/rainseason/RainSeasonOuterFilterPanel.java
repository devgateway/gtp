package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorOuterFilterPanel;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonOuterFilterPanel extends AbstractIndicatorOuterFilterPanel<PluviometricPostRainSeason> {
    private static final long serialVersionUID = -2263229548050781763L;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    public RainSeasonOuterFilterPanel(String id,
            ResettingFilterForm<? extends JpaFilterState<PluviometricPostRainSeason>> filterForm) {
        super(id, filterForm);
        this.yearIndicatorGenerator = pluviometricPostRainSeasonService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.addYearChoice();
    }
}
