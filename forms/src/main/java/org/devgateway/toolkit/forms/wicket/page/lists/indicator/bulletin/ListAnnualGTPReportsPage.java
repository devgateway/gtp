package org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.util.CustomDownloadLink;
import org.devgateway.toolkit.forms.wicket.page.edit.EditAnnualGTPReportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.service.AnnualGTPReportService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR)
@MountPath("/annual-gtp-reports")
public class ListAnnualGTPReportsPage extends AbstractListPage<AnnualGTPReport> {

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
    public Panel getActionPanel(String id, IModel<AnnualGTPReport> model) {
        return new CustomActionPanel(id, model);
    }

    public class CustomActionPanel extends ActionPanel {

        public CustomActionPanel(String id, IModel<AnnualGTPReport> model) {
            super(id, model);

            boolean empty = model.getObject().getUploads().isEmpty();

            BootstrapLink<FileMetadata> download = new BootstrapLink<FileMetadata>("download",
                    model.map(AnnualGTPReport::getUpload), Buttons.Type.Info) {

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
