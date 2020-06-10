package org.devgateway.toolkit.forms.wicket.page.lists;

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
import org.devgateway.toolkit.forms.wicket.page.edit.EditAnnualGTPBulletinPage;
import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.service.AnnualGTPBulletinService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN)
@MountPath("/annual-gtp-bulletins")
public class ListAnnualGTPBulletinsPage extends AbstractListPage<AnnualGTPBulletin> {

    @SpringBean
    private AnnualGTPBulletinService service;

    public ListAnnualGTPBulletinsPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = service;
        editPageClass = EditAnnualGTPBulletinPage.class;

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
    public Panel getActionPanel(String id, IModel<AnnualGTPBulletin> model) {
        return new CustomActionPanel(id, model);
    }

    public class CustomActionPanel extends ActionPanel {

        public CustomActionPanel(String id, IModel<AnnualGTPBulletin> model) {
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
}
