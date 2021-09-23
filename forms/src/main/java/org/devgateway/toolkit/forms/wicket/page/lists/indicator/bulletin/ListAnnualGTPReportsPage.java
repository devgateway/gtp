package org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
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
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.AnnualGTPReportFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.util.CustomDownloadLink;
import org.devgateway.toolkit.forms.wicket.page.edit.EditAnnualGTPReportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.AnnualGTPReportService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_ANNUAL_REPORT_EDITOR)
@MountPath("/annual-gtp-reports")
public class ListAnnualGTPReportsPage extends AbstractListPage<AnnualGTPReport> {
    private static final long serialVersionUID = 1406160527903712596L;

    @SpringBean
    private AnnualGTPReportService service;

    public ListAnnualGTPReportsPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = service;
        editPageClass = EditAnnualGTPReportPage.class;

        service.generate();

        columns.add(new PropertyColumn<>(new StringResourceModel("year"), "year"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("year", SortOrder.ASCENDING);

        editPageLink.setVisibilityAllowed(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<AnnualGTPReport>> getFilterForm(
            final String id, final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<AnnualGTPReport, String> dataTable) {
        return new BookmarkableResettingFilterForm<AnnualGTPReportFilterState>(id, locator, dataTable,
                        ListAnnualGTPReportsPage.class, getPageParameters());
    }

    @Override
    public JpaFilterState<AnnualGTPReport> newFilterState() {
        return new AnnualGTPReportFilterState();
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<AnnualGTPReport>> filterForm) {
        return new LocationFilterPanel<>(id, filterForm);
    }

    @Override
    public Panel getActionPanel(String id, IModel<AnnualGTPReport> model) {
        return new CustomActionPanel(id, model);
    }

    public class CustomActionPanel extends ActionPanel {
        private static final long serialVersionUID = 631093680516799164L;

        public CustomActionPanel(String id, IModel<AnnualGTPReport> model) {
            super(id, model);

            boolean empty = model.getObject().getUploads().isEmpty();

            BootstrapLink<FileMetadata> download = new BootstrapLink<FileMetadata>("download",
                    model.map(AnnualGTPReport::getUpload), Buttons.Type.Info) {
                private static final long serialVersionUID = 4911163535813519633L;

                @Override
                public void onClick() {
                    CustomDownloadLink.respond(getModelObject());
                }
            };
            download.setLabel(new StringResourceModel("download", this));
            download.setIconType(FontAwesomeIconType.download);
            download.setSize(Buttons.Size.Small);
            download.setVisibilityAllowed(!empty);
            add(download);

            editPageLink.setLabel(new StringResourceModel(empty ? "import" : "reimport", this));
        }
    }
}
