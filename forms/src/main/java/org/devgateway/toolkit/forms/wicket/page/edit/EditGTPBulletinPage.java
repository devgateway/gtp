package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListGTPBulletinPage;
import org.devgateway.toolkit.persistence.dao.GTPBulletin;
import org.devgateway.toolkit.persistence.service.GTPBulletinService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN_EDITOR)
@MountPath("/gtp-bulletin")
public class EditGTPBulletinPage extends AbstractEditPage<GTPBulletin> {

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

        editForm.add(new FileInputBootstrapFormComponent("uploads").maxFiles(1));

        deleteButton.setVisibilityAllowed(false);
    }
}
