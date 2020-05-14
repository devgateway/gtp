package org.devgateway.toolkit.forms.wicket.page.lists.category;

import java.util.List;
import java.util.stream.Collectors;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RiverStationYearlyLevelsReferenceFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditRiverStationYearlyLevelsReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.RiverStation;
import org.devgateway.toolkit.persistence.dao.RiverStationYearlyLevelsReference;
import org.devgateway.toolkit.persistence.service.RiverStationService;
import org.devgateway.toolkit.persistence.service.RiverStationYearlyLevelsReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/reference-river-levels")
public class ListRiverStationYearlyLevelsReferencesPage extends AbstractListPage<RiverStationYearlyLevelsReference> {

    @SpringBean
    private RiverStationYearlyLevelsReferenceService riverStationYearlyLevelsReferenceService;

    @SpringBean
    private RiverStationService riverStationService;

    public ListRiverStationYearlyLevelsReferencesPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = riverStationYearlyLevelsReferenceService;

        editPageClass = EditRiverStationYearlyLevelsReferencePage.class;

        List<String> stationNames = riverStationService.findAll().stream()
                .map(RiverStation::getName)
                .collect(Collectors.toList());

        columns.add(new SelectFilteredBootstrapPropertyColumn<>(
                new StringResourceModel("station"), "station.name", "station.name", "station",
                new ListModel<>(stationNames)));

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
        return new ActionPanel(id, model);
    }

    public class ActionPanel extends Panel {

        public ActionPanel(final String id, final IModel<RiverStationYearlyLevelsReference> model) {
            super(id, model);

            RiverStationYearlyLevelsReference entity = model.getObject();

            final PageParameters pageParameters = new PageParameters();
            pageParameters.set(WebConstants.PARAM_ID, entity.getId());

            BootstrapBookmarkablePageLink<RiverStationYearlyLevelsReference> editPageLink =
                    new BootstrapBookmarkablePageLink<>("edit", editPageClass, pageParameters, Buttons.Type.Info);

            String editResourceKey = entity.getLevels().isEmpty() ? "import" : "reimport";
            editPageLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Small)
                    .setLabel(new StringResourceModel(editResourceKey, this, null));
            add(editPageLink);

            DownloadRiverLevelsLink downloadButton = new DownloadRiverLevelsLink("download", model);
            downloadButton.setSize(Buttons.Size.Small);
            downloadButton.setVisibilityAllowed(!entity.getLevels().isEmpty());
            add(downloadButton);

            add(getPrintButton(pageParameters));
        }
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