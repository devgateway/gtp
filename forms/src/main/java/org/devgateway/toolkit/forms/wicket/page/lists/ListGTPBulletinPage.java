package org.devgateway.toolkit.forms.wicket.page.lists;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
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
import org.devgateway.toolkit.forms.wicket.components.table.filter.GTPBulletinFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.util.CustomDownloadLink;
import org.devgateway.toolkit.forms.wicket.page.edit.EditGTPBulletinPage;
import org.devgateway.toolkit.forms.wicket.providers.ConverterBasedChoiceProvider;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.service.GTPBulletinService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN)
@MountPath("/gtp-bulletins")
public class ListGTPBulletinPage extends AbstractListPage<GTPBulletin> {

    @SpringBean
    private GTPBulletinService gtpBulletinService;

    public ListGTPBulletinPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = gtpBulletinService;
        editPageClass = EditGTPBulletinPage.class;

        gtpBulletinService.generate();

        columns.add(new SelectFilteredBootstrapPropertyColumn<GTPBulletin, Integer, String>(
                new StringResourceModel("year"), "year",
                new GenericChoiceProvider<>(gtpBulletinService.findYearsWithData()))
                .required());

        columns.add(new SelectFilteredBootstrapPropertyColumn<>(
                new StringResourceModel("month"), "month",
                new ConverterBasedChoiceProvider<>(Arrays.asList(Month.values()), Month.class)));

        columns.add(new SelectFilteredBootstrapPropertyColumn<>(
                new StringResourceModel("decadal"), "decadal",
                new ConverterBasedChoiceProvider<>(Arrays.asList(Decadal.values()), Decadal.class)));

        pageSize = GTPBulletin.MONTHS.size() * Decadal.values().length;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("year;month;decadal", SortOrder.ASCENDING);

        editPageLink.setVisibilityAllowed(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<GTPBulletin>> getFilterForm(
            final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<GTPBulletin, String> dataTable) {
        BookmarkableResettingFilterForm<GTPBulletinFilterState> form =
                new BookmarkableResettingFilterForm<GTPBulletinFilterState>(id, locator, dataTable,
                        ListGTPBulletinPage.class, getPageParameters());
        if (form.getModelObject().getYear() == null) {
            form.getModelObject().setYear(Year.now().getValue());
        }
        return form;
    }

    @Override
    public Panel getActionPanel(String id, IModel<GTPBulletin> model) {
        return new CustomActionPanel(id, model);
    }

    public class CustomActionPanel extends AbstractListPage<GTPBulletin>.ActionPanel {

        public CustomActionPanel(String id, IModel<GTPBulletin> model) {
            super(id, model);

            BootstrapLink<FileMetadata> download = new BootstrapLink<FileMetadata>("download",
                    model.map(b -> b.getUploads().iterator().next()), Buttons.Type.Info) {

                @Override
                public void onClick() {
                    CustomDownloadLink.respond(getModelObject());
                }
            };
            download.setLabel(new StringResourceModel("download", this));
            download.setIconType(FontAwesomeIconType.download);
            download.setSize(Buttons.Size.Small);
            download.setVisibilityAllowed(!model.getObject().getUploads().isEmpty());
            add(download);

            boolean empty = model.getObject().getUploads().isEmpty();
            editPageLink.setLabel(new StringResourceModel(empty ? "import" : "reimport", this));
        }
    }

    @Override
    public JpaFilterState<GTPBulletin> newFilterState() {
        return new GTPBulletinFilterState();
    }
}
