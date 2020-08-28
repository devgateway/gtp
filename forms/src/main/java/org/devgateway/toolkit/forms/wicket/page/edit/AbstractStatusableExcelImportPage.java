package org.devgateway.toolkit.forms.wicket.page.edit;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FormStatus;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractStatusableExcelImportPage
        <T extends AbstractStatusAuditableEntity & AbstractImportableEntity> extends AbstractExcelImportPage<T> {
    private static final long serialVersionUID = -2629356746271144152L;

    public AbstractStatusableExcelImportPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        SaveEditPageButton draftButton = new StatusableSaveEditPageButton("save",
                new StringResourceModel("draftButton"), FormStatus.DRAFT);
        draftButton.setType(Buttons.Type.Warning);
        draftButton.setIconType(FontAwesomeIconType.thumbs_down);
        return draftButton;
    }

    @Override
    protected Fragment getChildExtraButtons(String id) {
        Fragment statusExtraButtons = new Fragment(id, "statusExtraButtons", this);
        statusExtraButtons.add(new StatusableSaveEditPageButton("publishButton",
                new StringResourceModel("publishButton"), FormStatus.PUBLISHED));
        return statusExtraButtons;
    }

    protected class StatusableSaveEditPageButton extends SaveEditPageButton {
        private static final long serialVersionUID = 5675499824385590167L;

        private FormStatus formStatus;

        StatusableSaveEditPageButton(String id, IModel<String> model, FormStatus formStatus) {
            super(id, model);
            this.formStatus = formStatus;
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target) {
            editForm.getModelObject().setFormStatus(formStatus);
            super.onSubmit(target);
        }
    }

}
