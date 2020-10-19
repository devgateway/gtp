package org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.reference.ListRainLevelReferencePage;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;
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
        setListPage(ListRainLevelReferencePage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        rainLevelReferenceService.initialize(editForm.getModelObject());
        editForm.getModel().getObject().getPostRainReferences().forEach(RainLevelPluviometricPostReference::validate);

        editForm.add(new RainLevelReferenceTablePanel("postRainReferences", editForm.getModel()));

        deleteButton.setVisible(false);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton(String id, IModel<String> labelModel) {
        return new RainLevelReferenceSaveEditPageButton(id, labelModel);
    }

    public class RainLevelReferenceSaveEditPageButton extends SaveEditPageButton {
        private static final long serialVersionUID = 5874368042704092709L;

        public RainLevelReferenceSaveEditPageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void validate() {
            super.validate();

            if (!hasErrorMessage()) {
                RainLevelReference rr = EditRainLevelReferencePage.this.getEditForm().getModel().getObject();
                boolean isValid = rr.getPostRainReferences().stream().map(RainLevelPluviometricPostReference::validate)
                        .reduce(true, (allValid, postRefValid) -> allValid && postRefValid);
                if (!isValid) {
                    error(new StringResourceModel("CumulativeValidator", EditRainLevelReferencePage.this).getString());
                }
            }
        }
    }
}
