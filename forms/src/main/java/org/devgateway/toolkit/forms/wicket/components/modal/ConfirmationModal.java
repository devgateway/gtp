package org.devgateway.toolkit.forms.wicket.components.modal;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapCancelButton;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapSubmitButton;

/**
 * @author Viorel Chihai
 */
public class ConfirmationModal<T> extends Modal<T> {
    private static final long serialVersionUID = -5601425502218862510L;

    protected Form<T> form;

    protected Label description;

    public ConfirmationModal(String markupId, IModel<T> model) {
        super(markupId, model);

        header(new StringResourceModel("header", this));
        size(Modal.Size.Medium);

        form = new Form("confirmationForm");
        description = new Label("description", new StringResourceModel("description"));
        form.add(description);
        form.add(getSubmitButton());
        form.add(getCancelButton());

        add(form);
    }


    protected BootstrapSubmitButton getSubmitButton() {
        BootstrapSubmitButton submitButton = new BootstrapSubmitButton("submit", form,
                new StringResourceModel("submit")) {
            private static final long serialVersionUID = -7289266651689728814L;

            @Override
            public void onSubmit(final AjaxRequestTarget target) {
                ConfirmationModal.this.onSubmit(target);
                ConfirmationModal.this.close(target);
            }
        };
        submitButton.setType(Buttons.Type.Success);
        submitButton.setIconType(FontAwesomeIconType.check);

        return submitButton;
    }

    protected BootstrapCancelButton getCancelButton() {
        return new BootstrapCancelButton("cancel", new StringResourceModel("cancel")) {
            private static final long serialVersionUID = 1615052072972133479L;

            @Override
            public void onSubmit(final AjaxRequestTarget target) {
                ConfirmationModal.this.close(target);
            }
        };
    }

    protected void onSubmit(final AjaxRequestTarget target) {

    }
}
