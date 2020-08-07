package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.models.WrapperNullableModel;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin.ListGTPBulletinPage;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.service.indicator.bulletin.GTPBulletinService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR)
@MountPath("/gtp-bulletin")
public class EditGTPBulletinPage extends AbstractEditPage<GTPBulletin> {
    private static final long serialVersionUID = 32211176148098678L;

    @SpringBean
    private GTPBulletinService gtpBulletinService;

    public EditGTPBulletinPage(PageParameters parameters) {
        super(parameters);

        setListPage(ListGTPBulletinPage.class);
        jpaService = gtpBulletinService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.add(new TextFieldBootstrapFormComponent<>("year").setEnabled(false));
        editForm.add(new TextFieldBootstrapFormComponent<>("month").setEnabled(false));
        editForm.add(new TextFieldBootstrapFormComponent<>("decadal").setEnabled(false));
        editForm.add(new TextFieldBootstrapFormComponent<>("department",
                new WrapperNullableModel(
                        new PropertyModel<String>(editForm.getModel(), "department.name"),
                        new StringResourceModel("national", EditGTPBulletinPage.this)))
                .setEnabled(false));

        editForm.add(new FileInputBootstrapFormComponent("uploads").maxFiles(1).allowedFileExtensions("pdf"));

        deleteButton.setVisibilityAllowed(false);
    }
}
