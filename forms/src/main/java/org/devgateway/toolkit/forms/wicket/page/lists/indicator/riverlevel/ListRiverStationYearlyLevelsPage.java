package org.devgateway.toolkit.forms.wicket.page.lists.indicator.riverlevel;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RiverStationYearlyLevelsFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.riverlevel.EditRiverStationYearlyLevelsPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.HydrologicalYearFilterPanel;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.RiverStationYearlyLevelsActionPanel;
import org.devgateway.toolkit.persistence.dao.indicator.RiverLevel;
import org.devgateway.toolkit.persistence.dao.indicator.RiverStationYearlyLevels;
import org.devgateway.toolkit.persistence.service.indicator.RiverStationYearlyLevelsService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RIVER_LEVEL_EDITOR)
@MountPath(value = "/river-levels")
public class ListRiverStationYearlyLevelsPage extends AbstractListPage<RiverStationYearlyLevels> {

    @SpringBean
    private RiverStationYearlyLevelsService riverStationYearlyLevelsService;

    public ListRiverStationYearlyLevelsPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = riverStationYearlyLevelsService;
        this.editPageClass = EditRiverStationYearlyLevelsPage.class;
        this.pageSize = WebConstants.NO_PAGE_SIZE;

        columns.add(new PropertyColumn<>(new StringResourceModel("river"), "station.river.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("station"), "station.name"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editPageLink.setVisible(false);
    }

    @Override
    public Panel getActionPanel(String id, IModel<RiverStationYearlyLevels> model) {
        return new RiverStationYearlyLevelsActionPanel<>(id, model, RiverLevel::new, editPageClass);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<RiverStationYearlyLevels>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<RiverStationYearlyLevels, String> dataTable) {
        return new BookmarkableResettingFilterForm<RiverStationYearlyLevelsFilterState>(id, locator, dataTable,
                ListRiverStationYearlyLevelsPage.class, this.getPageParameters());
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<RiverStationYearlyLevels>> filterForm) {
        return new HydrologicalYearFilterPanel<>(id, filterForm, riverStationYearlyLevelsService);
    }

    @Override
    public JpaFilterState<RiverStationYearlyLevels> newFilterState() {
        return new RiverStationYearlyLevelsFilterState();
    }
}
