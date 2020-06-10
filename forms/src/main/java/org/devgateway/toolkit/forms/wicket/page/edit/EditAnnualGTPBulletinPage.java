package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAnnualGTPBulletinsPage;
import org.devgateway.toolkit.persistence.dao.AnnualGTPBulletin;
import org.devgateway.toolkit.persistence.service.AnnualGTPBulletinService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_GTP_BULLETIN)
@MountPath("/annual-gtp-bulletin")
public class EditAnnualGTPBulletinPage extends AbstractEditPage<AnnualGTPBulletin> {

    @SpringBean
    private AnnualGTPBulletinService service;

    public EditAnnualGTPBulletinPage(PageParameters parameters) {
        super(parameters);

        setListPage(ListAnnualGTPBulletinsPage.class);
        jpaService = service;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.add(new TextFieldBootstrapFormComponent<>("year").setEnabled(false));

        editForm.add(new FileInputBootstrapFormComponent("uploads").maxFiles(1));

        deleteButton.setVisibilityAllowed(false);
    }
}
