/*******************************************************************************
 * Copyright (c) 2015 Development Gateway, Inc and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 *
 * Contributors:
 * Development Gateway - initial API and implementation
 *******************************************************************************/
package org.devgateway.toolkit.forms.wicket.page.edit;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.TextContentModal;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.form.GenericBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.events.EditingDisabledEvent;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.wicketstuff.select2.Select2Choice;

import java.io.Serializable;

/**
 * @author mpostelnicu Page used to make editing easy, extend to get easy access
 *         to one entity for editing
 */
public abstract class AbstractEditStatusEntityPage<T extends AbstractStatusAuditableEntity & Serializable>
        extends AbstractEditPage<T> {
    private static final long serialVersionUID = -3901115475640437745L;

    protected Fragment entityButtonsFragment;

    protected SaveEditPageButton saveSubmitButton;

    protected SaveEditPageButton saveApproveButton;

    protected SaveEditPageButton revertToDraftPageButton;

    protected Label statusLabel;

    protected Fragment extraStatusEntityButtonsFragment;

    public AbstractEditStatusEntityPage(final PageParameters parameters) {
        super(parameters);
    }

    public class ButtonContentModal extends TextContentModal {
        private static final long serialVersionUID = 3131820847783689431L;
        private final Buttons.Type buttonType;
        private LaddaAjaxButton button;
        private IModel<String> buttonModel;
        private ModalSaveEditPageButton modalSavePageButton;

        public ButtonContentModal(final String markupId, final IModel<String> model, final IModel<String> buttonModel,
                final Buttons.Type buttonType) {
            super(markupId, model);
            addCloseButton();
            this.buttonModel = buttonModel;
            this.buttonType = buttonType;
        }

        public ButtonContentModal modalSavePageButton(final ModalSaveEditPageButton modalSavePageButton) {
            this.modalSavePageButton = modalSavePageButton;
            return this;
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            button = new LaddaAjaxButton("button", buttonType) {
                private static final long serialVersionUID = 9099575377352302020L;
                @Override
                protected void onSubmit(final AjaxRequestTarget target) {
                    modalSavePageButton.continueSubmit(target);
                }
            };
            addButton(button);
            button.setDefaultFormProcessing(false);
            button.setLabel(buttonModel);
        }
    }

    public class ModalSaveEditPageButton extends SaveEditPageButton {
        private static final long serialVersionUID = 1L;
        private TextContentModal modal;

        public ModalSaveEditPageButton(final String id, final IModel<String> model, final TextContentModal modal) {
            super(id, model);
            this.modal = modal;
        }

        @Override
        protected String getOnClickScript() {
            return WebConstants.DISABLE_FORM_LEAVING_JS;
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target) {
            modal.show(true);
            target.add(modal);
        }

        public void continueSubmit(final AjaxRequestTarget target) {
            super.onSubmit(target);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        statusLabel = addStatusLabel();
        editForm.add(statusLabel);

        boolean isPublished = editForm.getModelObject().getFormStatus().isPublished();
        saveButton.setVisible(!isPublished);

        entityButtonsFragment = new Fragment("extraButtons", "entityButtons", this);
        editForm.replace(entityButtonsFragment);

        Fragment fragment = new Fragment("extraStatusEntityButtons", "noButtons", this);
        entityButtonsFragment.add(fragment);

        saveSubmitButton = getSaveSubmitPageButton();
        saveSubmitButton.setVisible(!isPublished);
        entityButtonsFragment.add(saveSubmitButton);

        /*
        saveApproveButton = getSaveApprovePageButton();
        entityButtonsFragment.add(saveApproveButton);
         */

        revertToDraftPageButton = getRevertToDraftPageButton();
        revertToDraftPageButton.setVisible(isPublished);
        entityButtonsFragment.add(revertToDraftPageButton);

        if (isPublished) {
            send(editForm, Broadcast.DEPTH, new EditingDisabledEvent());
        }
    }

    private Label addStatusLabel() {
        statusLabel = new Label("statusLabel",
                new StringResourceModel(editForm.getModelObject().getFormStatus().name()));
        statusLabel.add(new AttributeModifier("class", new Model<>("label " + getStatusLabelClass())));
        return statusLabel;
    }

    private String getStatusLabelClass() {
        if (editForm.getModelObject().getFormStatus() == null) {
            return "";
        }

        switch (editForm.getModelObject().getFormStatus()) {
            case PUBLISHED:
                return "label-success";
            case DRAFT:
                return "label-warning";
            case NOT_STARTED:
                return "label-danger";
            default:
                return "";
        }
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return getSaveDraftEditPageButton();
    }

    private SaveEditPageButton getSaveDraftEditPageButton() {
        final SaveEditPageButton button = new SaveEditPageButton("save",
                new StringResourceModel("saveButton", this, null)) {
            private static final long serialVersionUID = -6276016565141388747L;

            @Override
            protected String getOnClickScript() {
                return WebConstants.DISABLE_FORM_LEAVING_JS;
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                editForm.visitChildren(GenericBootstrapFormComponent.class,
                        new AllowNullForCertainInvalidFieldsVisitor());
                setStatus(onSaveAsDraftStatus());
                super.onSubmit(target);
            }
        };
        return button;
    }

    protected FormStatus onSaveAsDraftStatus() {
        return FormStatus.DRAFT;
    }

    private SaveEditPageButton getSaveSubmitPageButton() {
        final SaveEditPageButton button = new SaveEditPageButton("saveSubmit",
                new StringResourceModel("saveSubmit", this, null)) {
            private static final long serialVersionUID = 5210526642959264298L;

            @Override
            protected String getOnClickScript() {
                return WebConstants.DISABLE_FORM_LEAVING_JS;
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                setStatus(FormStatus.PUBLISHED);
                super.onSubmit(target);
            }
        };

        button.setIconType(FontAwesomeIconType.send);
        return button;
    }

    private SaveEditPageButton getSaveApprovePageButton() {
        final SaveEditPageButton saveEditPageButton = new SaveEditPageButton("approve",
                new StringResourceModel("approve", this, null)) {
            private static final long serialVersionUID = -6116451592283782337L;

            @Override
            protected String getOnClickScript() {
                return WebConstants.DISABLE_FORM_LEAVING_JS;
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                // setStatus(FormStatus.APPROVED);
                super.onSubmit(target);
            }
        };
        saveEditPageButton.setIconType(FontAwesomeIconType.thumbs_up);
        return saveEditPageButton;
    }

    protected SaveEditPageButton getRevertToDraftPageButton() {
        final SaveEditPageButton saveEditPageButton = new SaveEditPageButton("revertToDraft",
                new StringResourceModel("revertToDraft", this, null)) {
            private static final long serialVersionUID = -3260375805066444254L;

            @Override
            protected String getOnClickScript() {
                return WebConstants.DISABLE_FORM_LEAVING_JS;
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                setStatus(revertStatus());
                super.onSubmit(target);
                target.add(editForm);
            }
        };
        saveEditPageButton.setIconType(FontAwesomeIconType.thumbs_down);
        return saveEditPageButton;
    }

    protected FormStatus revertStatus() {
        return FormStatus.DRAFT;
    }

    protected void setStatus(final FormStatus status) {
        final T saveable = editForm.getModelObject();

        if (status.equals(saveable.getFormStatus())) {
            saveable.setFormStatus(status);
            return;
        }
        saveable.setFormStatus(status);
    }

    private void scrollToPreviousPosition(final IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript(String.format(
                "var vPosition= +%s, mHeight = +%s, cmHeight=$(document).height();"
                        + "if(mHeight!=0) $(window).scrollTop(vPosition*cmHeight/mHeight)",
                getPageParameters().get(WebConstants.V_POSITION).toDouble(0),
                getPageParameters().get(WebConstants.MAX_HEIGHT).toDouble(0))));
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        scrollToPreviousPosition(response);
    }

    /**
     * Allow null saving for draft entities even if the field is required. Bypass
     * validation for this purpose.
     *
     * @author mpostelnicu
     */
    public class AllowNullForCertainInvalidFieldsVisitor
            implements IVisitor<GenericBootstrapFormComponent<?, ?>, Void> {
        @Override
        public void component(final GenericBootstrapFormComponent<?, ?> object, final IVisit<Void> visit) {
            // we found the GenericBootstrapFormComponent, stop doing useless
            // things like traversing inside the GenericBootstrapFormComponent itself
            visit.dontGoDeeper();

            // do not process disabled fields
            if (!object.isEnabledInHierarchy()) {
                return;
            }
            object.getField().processInput();

            // we try validate the field
            object.getField().validate();

            // still, if the field is invalid, its input is null, and field is
            // of a certain type, we turn
            // the input into a null. This helps us to save empty REQUIRED
            // fields when saving as draft
            if (!object.getField().isValid() && Strings.isEmpty(object.getField().getInput())) {
                // for text/select fields we just make the object model null
                if (object.getField() instanceof TextField<?> || object.getField() instanceof TextArea<?>
                        || object.getField() instanceof Select2Choice<?>) {
                    object.getField().getModel().setObject(null);
                }

            }
        }
    }
}
