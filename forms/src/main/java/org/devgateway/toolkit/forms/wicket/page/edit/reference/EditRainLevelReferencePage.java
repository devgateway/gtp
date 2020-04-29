package org.devgateway.toolkit.forms.wicket.page.edit.reference;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainLevelReferencePage;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.reference.RainLevelReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/rain-level-reference")
public class EditRainLevelReferencePage extends AbstractEditPage<RainLevelReference> {
    private static final long serialVersionUID = 6616477800881612646L;

    @SpringBean
    private RainLevelReferenceService rainLevelReferenceService;

    public EditRainLevelReferencePage(PageParameters parameters) {
        super(parameters);

        this.jpaService = rainLevelReferenceService;
        this.listPageClass = ListRainLevelReferencePage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        rainLevelReferenceService.initialize(editForm.getModelObject());

        editForm.add(new RainLevelReferenceTablePanel("postRainReferences", editForm.getModel()));

        deleteButton.setVisible(false);
    }
}
