package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DecadalRainfallFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall.EditDecadalRainfallPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorWithStatusListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.YearFilterPanel;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/rainfalls")
public class ListDecadalRainfallPage extends AbstractIndicatorWithStatusListPage<DecadalRainfall> {
    private static final long serialVersionUID = 395649903982652614L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public ListDecadalRainfallPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = decadalRainfallService;
        this.editPageClass = EditDecadalRainfallPage.class;
        this.pageSize = WebConstants.NO_PAGE_SIZE;

        columns.add(new PropertyColumn<>(new StringResourceModel("month"), "displayMonth"));
        columns.add(new PropertyColumn<>(new StringResourceModel("decadal"), "decadal.value"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<DecadalRainfall>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<DecadalRainfall, String> dataTable) {
        return new BookmarkableResettingFilterForm<DecadalRainfallFilterState>(id, locator, dataTable,
                ListDecadalRainfallPage.class, this.getPageParameters());
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<DecadalRainfall>> filterForm) {
        return new YearFilterPanel<>(id, filterForm, decadalRainfallService);
    }

    @Override
    public JpaFilterState<DecadalRainfall> newFilterState() {
        return new DecadalRainfallFilterState();
    }
}
