package org.devgateway.toolkit.forms.wicket.page.lists.reference;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
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
import org.devgateway.toolkit.forms.wicket.components.table.filter.RiverStationYearlyLevelsReferenceFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditRiverStationYearlyLevelsReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractExcelImportListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.RiverStationYearlyLevelsActionPanel;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.reference.RiverLevelReference;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.dao.reference.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.service.category.RiverStationService;
import org.devgateway.toolkit.persistence.service.reference.RiverStationYearlyLevelsReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/reference-river-levels")
public class ListRiverStationYearlyLevelsReferencesPage
        extends AbstractExcelImportListPage<RiverStationYearlyLevelsReference> {

    @SpringBean
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    @SpringBean
    private RiverStationService riverStationService;

    public ListRiverStationYearlyLevelsReferencesPage(PageParameters parameters) {
        super(parameters);

        jpaService = riverStationYearlyLevelsReferenceService;

        editPageClass = EditRiverStationYearlyLevelsReferencePage.class;

        List<String> stationNames = riverStationService.findAll().stream()
                .map(RiverStation::getName)
                .collect(Collectors.toList());

        columns.add(new SelectFilteredBootstrapPropertyColumn<>(
                new StringResourceModel("station"), "station.name", "station.name", "station",
                new GenericChoiceProvider<>(stationNames)));

        columns.add(new PropertyColumn<>(new StringResourceModel("year"), "year", "year"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("year", SortOrder.ASCENDING);

        editPageLink.setVisibilityAllowed(false);
    }

    @Override
    public Panel getActionPanel(String id, IModel<RiverStationYearlyLevelsReference> model) {
        return new RiverStationYearlyLevelsActionPanel<>(id, model,
                RiverStationYearlyLevelsReference::new, RiverLevelReference::new, editPageClass);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<RiverStationYearlyLevelsReference>> getFilterForm(
            final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<RiverStationYearlyLevelsReference, String> dataTable) {
        return new BookmarkableResettingFilterForm<RiverStationYearlyLevelsReferenceFilterState>(id, locator, dataTable,
                ListRiverStationYearlyLevelsReferencesPage.class, getPageParameters());
    }

    @Override
    public JpaFilterState<RiverStationYearlyLevelsReference> newFilterState() {
        return new RiverStationYearlyLevelsReferenceFilterState();
    }
}
