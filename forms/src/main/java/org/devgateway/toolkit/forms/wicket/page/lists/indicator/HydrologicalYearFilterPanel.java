package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import java.io.Serializable;

import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

/**
 * @author Octavian Ciubotaru
 */
public class HydrologicalYearFilterPanel<T extends GenericPersistable & Serializable>
        extends AbstractYearFilterPanel<T, HydrologicalYear> {


    public HydrologicalYearFilterPanel(String id,
            ResettingFilterForm<? extends JpaFilterState<T>> filterForm,
            YearIndicatorGenerator<T, HydrologicalYear> yearIndicatorGenerator) {
        super(id, filterForm, yearIndicatorGenerator);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addYearChoice(adminSettingsService.getHydrologicalYears(), HydrologicalYear.now());
    }
}
