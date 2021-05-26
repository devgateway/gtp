package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.YearlyRainfallFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall.EditYearlyRainfallImportPage;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall.EditYearlyRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorWithStatusListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.YearlyRainfallActionPanel;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Collections;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rainfalls")
public class ListYearlyRainfallPage extends AbstractIndicatorWithStatusListPage<YearlyRainfall> {
    private static final long serialVersionUID = 395649903982652614L;

    @SpringBean
    private YearlyRainfallService yearlyRainfallService;

    @SpringBean
    protected AdminSettingsService adminSettingsService;

    public ListYearlyRainfallPage(PageParameters parameters) {
        super(parameters, true);

        this.jpaService = yearlyRainfallService;
        this.editPageClass = EditYearlyRainfallPage.class;

        List<Integer> years = adminSettingsService.getYears();
        Collections.reverse(years);
        years.forEach(year -> {
            if (!yearlyRainfallService.existsByYear(year)) {
                yearlyRainfallService.generate(year);
            }
        });

        columns.add(new SelectFilteredBootstrapPropertyColumn<>(
                new StringResourceModel("year"), "year", "year", "year",
                new GenericChoiceProvider<>(years)
        ));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("year", SortOrder.DESCENDING);

        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<YearlyRainfall>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<YearlyRainfall, String> dataTable) {
        return new BookmarkableResettingFilterForm<YearlyRainfallFilterState>(id, locator, dataTable,
                ListYearlyRainfallPage.class, this.getPageParameters());
    }

    @Override
    public JpaFilterState<YearlyRainfall> newFilterState() {
        return new YearlyRainfallFilterState();
    }

    @Override
    public Panel getActionPanel(final String id, final IModel<YearlyRainfall> model) {
        return new YearlyRainfallActionPanel(id, model, EditYearlyRainfallImportPage.class, null);
    }
}
