package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfallMap;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DecadalRainfallMapFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfallMap.EditDecadalRainfallMapPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.YearFilterPanel;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.DecadalRainfallMapActionPanel;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.service.indicator.rainfallMap.DecadalRainfallMapService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_MAP_EDITOR)
@MountPath("/rainfall-maps")
public class ListDecadalRainfallMapPage extends AbstractListPage<DecadalRainfallMap> {
    private static final long serialVersionUID = -5792790214333600526L;

    @SpringBean
    private DecadalRainfallMapService decadalRainfallMapService;

    public ListDecadalRainfallMapPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = decadalRainfallMapService;
        this.editPageClass = EditDecadalRainfallMapPage.class;
        this.pageSize = WebConstants.NO_PAGE_SIZE;

        columns.add(new PropertyColumn<>(new StringResourceModel("month"), "displayMonth"));
        columns.add(new PropertyColumn<>(new StringResourceModel("decadal"), "decadal.value"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        disableEdit();
        dataProvider.setSort("month;decadal", SortOrder.ASCENDING);
    }

    private void disableEdit() {
        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<DecadalRainfallMap>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<DecadalRainfallMap, String> dataTable) {
        return new BookmarkableResettingFilterForm<DecadalRainfallMapFilterState>(id, locator, dataTable,
                ListDecadalRainfallMapPage.class, this.getPageParameters());
    }

    @Override
    public JpaFilterState<DecadalRainfallMap> newFilterState() {
        return new DecadalRainfallMapFilterState();
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<DecadalRainfallMap>> filterForm) {
        return new YearFilterPanel<>(id, filterForm, decadalRainfallMapService);
    }

    @Override
    public Panel getActionPanel(final String id, final IModel<DecadalRainfallMap> model) {
        return new DecadalRainfallMapActionPanel(id, model);
    }
}
