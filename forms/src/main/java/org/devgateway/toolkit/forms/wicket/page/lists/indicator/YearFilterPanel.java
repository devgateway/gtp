package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import java.io.Serializable;
import java.time.LocalDate;

import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;
import org.devgateway.toolkit.persistence.time.AD3Clock;

/**
 * @author Octavian Ciubotaru
 */
public class YearFilterPanel<T extends GenericPersistable & Serializable>
        extends AbstractYearFilterPanel<T, Integer> {

    public YearFilterPanel(String id,
            ResettingFilterForm<? extends JpaFilterState<T>> filterForm,
            YearIndicatorGenerator<T, Integer> yearIndicatorGenerator) {
        super(id, filterForm, yearIndicatorGenerator);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addYearChoice(adminSettingsService.getYears(), LocalDate.now(AD3Clock.systemDefaultZone()).getYear());
    }
}
