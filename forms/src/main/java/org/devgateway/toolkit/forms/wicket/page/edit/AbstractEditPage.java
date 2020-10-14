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
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.TextContentModal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.util.Attributes;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import nl.dries.wicket.hibernate.dozer.DozerModel;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.exceptions.EntityNotFoundException;
import org.devgateway.toolkit.forms.exceptions.NullJpaServiceException;
import org.devgateway.toolkit.forms.exceptions.NullListPageClassException;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapCancelButton;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapDeleteButton;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapSubmitButton;
import org.devgateway.toolkit.forms.wicket.components.form.GenericBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.visitors.GenericBootstrapValidationVisitor;
import org.devgateway.toolkit.forms.wicket.liveping.LivePing;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mpostelnicu Page used to make editing easy, extend to get easy access
 * to one entity for editing
 */
public abstract class AbstractEditPage<T extends GenericPersistable> extends BasePage {
    private static final long serialVersionUID = -5928614890244382103L;

    public static final int DEFA_MAX_LENGTH = 254;
    public static final int LINK_MAX_LENGTH = 1024;

    private static final Logger logger = LoggerFactory.getLogger(AbstractEditPage.class);

    private final String referer;

    /**
     * Factory method for the new instance of the entity being editing. This
     * will be invoked only when the parameter PARAM_ID is null
     *
     * @return
     */
    private T newInstance() {
        return jpaService.newInstance();
    }

    /**
     * The repository used to fetch and save the entity, this is initialized in
     * subclasses
     */
    protected BaseJpaService<T> jpaService;

    /**
     * The page that is responsible for listing the entities (used here as a
     * return reference after successful save)
     */
    private Class<? extends BasePage> listPageClass;

    /**
     * The form used by all subclasses
     */
    protected EditForm editForm;

    /**
     * the entity id, or null if a new entity is requested
     */
    protected Long entityId;

    /**
     * This is a wrapper model that ensures we can easily edit the properties of
     * the entity
     */
    private CompoundPropertyModel<T> compoundModel;

    /**
     * generic submit button for the form
     */
    protected BootstrapSubmitButton saveButton;

    /**
     * generic delete button for the form
     */
    protected BootstrapDeleteButton deleteButton;

    protected TextContentModal deleteModal;

    protected TextContentModal deleteFailedModal;

    @SpringBean
    private EntityManager entityManager;

    @SpringBean(required = false)
    private MarkupCacheService markupCacheService;

    public EditForm getEditForm() {
        return editForm;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void flushReportingCaches() {
        if (markupCacheService != null) {
            markupCacheService.flushMarkupCache();
            markupCacheService.clearPentahoReportsCache();
            markupCacheService.clearAllCaches();
        }
    }

    public Class<?> getNewInstanceClass() {
        return newInstance().getClass();
    }

    public GenericBootstrapValidationVisitor getBootstrapValidationVisitor(final AjaxRequestTarget target) {
        return new GenericBootstrapValidationVisitor(target);
    }

    protected TextContentModal createDeleteModal() {
        final TextContentModal modal = new TextContentModal("deleteModal",
                Model.of("DELETE is an irreversible operation. Are you sure?"));
        modal.addCloseButton();

        final LaddaAjaxButton deleteButton = new LaddaAjaxButton("button", Buttons.Type.Danger) {
            private static final long serialVersionUID = 4167519861741213598L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);

                // close the modal
                deleteModal.appendCloseDialogJavaScript(target);
                onDelete(target);
            }
        };
        deleteButton.setDefaultFormProcessing(false);
        deleteButton.setLabel(Model.of("DELETE"));
        modal.addButton(deleteButton);

        return modal;
    }

    protected TextContentModal createDeleteFailedModal() {
        final TextContentModal modal = new TextContentModal("deleteFailedModal",
                new ResourceModel("delete_error_message"));
        final LaddaAjaxButton deleteButton = new LaddaAjaxButton("button", Buttons.Type.Info) {
            private static final long serialVersionUID = -3042033179053530499L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                setResponsePage(listPageClass);
            }
        };
        deleteButton.setDefaultFormProcessing(false);
        deleteButton.setLabel(Model.of("OK"));
        modal.addButton(deleteButton);

        modal.add(new AjaxEventBehavior("hidden.bs.modal") {
            private static final long serialVersionUID = -3158951048272870291L;

            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                setResponsePage(listPageClass);
            }
        });

        return modal;
    }


    public class EditForm extends BootstrapForm<T> {
        private static final long serialVersionUID = -9127043819229346784L;

        /**
         * wrap the model with a {@link CompoundPropertyModel} to ease editing
         * of fields
         *
         * @param model
         */
        public void setCompoundPropertyModel(final IModel<T> model) {
            compoundModel = new CompoundPropertyModel<T>(model);
            setModel(compoundModel);
        }

        public EditForm(final String id, final IModel<T> model) {
            this(id);
            setCompoundPropertyModel(model);
        }

        public EditForm(final String id) {
            super(id);

            setOutputMarkupId(true);

            saveButton = getSaveEditPageButton();
            add(saveButton);

            deleteButton = getDeleteEditPageButton();
            add(deleteButton);

            /*
            deleteModal = createDeleteModal();
            add(deleteModal);

            deleteFailedModal = createDeleteFailedModal();
            add(deleteFailedModal);
            */

            // don't display the delete button if we just create a new entity
            if (entityId == null) {
                deleteButton.setVisibilityAllowed(false);
            }

            add(getCancelButton("cancel"));
        }
    }

    protected BootstrapCancelButton getCancelButton(final String id) {
        return new BootstrapCancelButton(id, new StringResourceModel("cancelButton", this, null)) {
            private static final long serialVersionUID = -249084359200507749L;

            @Override
            protected String getOnClickScript() {
                return WebConstants.DISABLE_FORM_LEAVING_JS;
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                    scheduleRedirect();
            }
        };
    }

    /**
     * Generic funcionality for the save page button, this can be extended
     * further by subclasses
     *
     * @author mpostelnicu
     */
    public class SaveEditPageButton extends BootstrapSubmitButton {
        private static final long serialVersionUID = 9075809391795974349L;

        protected boolean redirect = true;

        protected boolean redirectToSelf = false;

        public SaveEditPageButton(final String id, final IModel<String> model) {
            super(id, model);
        }

        @Override
        protected String getOnClickScript() {
            return WebConstants.DISABLE_FORM_LEAVING_JS;
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target) {
            // save the object and go back to the list page
            T saveable = editForm.getModelObject();

            // saves the entity and flushes the changes
            jpaService.saveAndFlush(saveable);

            // clears session and detaches all entities that are currently
            // attached
            entityManager.clear();

            // we flush the mondrian/wicket/reports cache to ensure it gets rebuilt
            flushReportingCaches();

            // only redirect if redirect is true
            if (redirectToSelf) {
                // we need to close the blockUI if it's opened and enable all
                // the buttons
                target.appendJavaScript("$.unblockUI();");
                target.appendJavaScript("$('#" + editForm.getMarkupId() + " button').prop('disabled', false);");
            } else if (redirect) {
                scheduleRedirect();
            }

            // redirect is set back to true, which is the default behavior
            redirect = true;
            redirectToSelf = false;
        }

        @Override
        protected void onError(final AjaxRequestTarget target) {
            // make all errors visible
            GenericBootstrapValidationVisitor genericBootstrapValidationVisitor = getBootstrapValidationVisitor(target);
            editForm.visitChildren(GenericBootstrapFormComponent.class, genericBootstrapValidationVisitor);

            ValidationError error = new ValidationError();
            error.addKey("formHasErrors");
            error(error);

            target.add(feedbackPanel);

            // autoscroll down to the feedback panel
            target.appendJavaScript("$('html, body').animate({scrollTop: $(\".feedbackPanel\").offset().top}, 500);");
        }

        /**
         * @return the redirect
         */
        public boolean isRedirect() {
            return redirect;
        }

        /**
         * @param redirect the redirect to set
         */
        public void setRedirect(final boolean redirect) {
            this.redirect = redirect;
        }

        /**
         * @param redirectToSelf the redirectToSelf to set
         */
        public void setRedirectToSelf(final boolean redirectToSelf) {
            this.redirectToSelf = redirectToSelf;
        }

        /**
         * @return the redirectToSelf
         */
        public boolean isRedirectToSelf() {
            return redirectToSelf;
        }
    }

    protected void beforeSaveEntity(final T saveable) {
    }

    protected void beforeDeleteEntity(final T deleteable) {
    }

    public class DeleteEditPageButton extends BootstrapDeleteButton {
        private static final long serialVersionUID = 865330306716770506L;

        public DeleteEditPageButton(final String id, final IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onSubmit(final AjaxRequestTarget target) {
            /*
            deleteModal.show(true);
            target.add(deleteModal);
             */
            T deleteable = editForm.getModelObject();
            try {
                jpaService.delete(deleteable);

                // we flush the mondrian/wicket/reports cache to ensure it gets rebuilt
                flushReportingCaches();
            } catch (DataIntegrityViolationException e) {
                error(new NotificationMessage(
                        new StringResourceModel("delete_error_message", AbstractEditPage.this, null))
                        .hideAfter(Duration.NONE));
                target.add(feedbackPanel);

                return;
            }
            scheduleRedirect();
        }

        @Override
        protected void onError(final AjaxRequestTarget target) {
            super.onError(target);
            target.add(feedbackPanel);
        }

        @Override
        protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {

        }
    }

    /**
     * Override this to create new save buttons with additional behaviors
     *
     * @return
     */
    public SaveEditPageButton getSaveEditPageButton() {
        return getSaveEditPageButton("save", new StringResourceModel("saveButton", this, null));
    }

    public SaveEditPageButton getSaveEditPageButton(String id, IModel<String> labelModel) {
        return new SaveEditPageButton(id, labelModel);
    }

    /**
     * Override this to create new delete buttons if you need additional
     * behavior
     *
     * @return
     */
    public DeleteEditPageButton getDeleteEditPageButton() {
        return new DeleteEditPageButton("delete", new StringResourceModel("deleteButton", this, null));
    }

    public AbstractEditPage(final PageParameters parameters) {
        super(parameters);

        HttpServletRequest httpServletRequest =
                (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
        referer = httpServletRequest.getHeader("Referer");

        if (!parameters.get(WebConstants.PARAM_ID).isNull()) {
            entityId = parameters.get(WebConstants.PARAM_ID).toLongObject();
        }

        editForm = new EditForm("editForm") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);

                if (ComponentUtil.isViewMode()) {
                    Attributes.addClass(tag, "print-view");
                }
            }
        };

        // use this in order to avoid "ServletRequest does not contain multipart
        // content" error
        // this error appears when we have a file upload component that is
        // hidden or not present in the page when the form is created
        editForm.setMultiPart(true);

        add(editForm);

        // this fragment ensures extra buttons are added below the wicket:child
        // section in child
        Fragment fragment = new Fragment("extraButtons", "noButtons", this);
        editForm.add(fragment);

    }

    protected void onDelete(final AjaxRequestTarget target) {
        final T deleteable = editForm.getModelObject();
        try {
            beforeDeleteEntity(deleteable);

            jpaService.delete(deleteable);

            // we flush the mondrian/wicket/reports cache to ensure it gets rebuilt
            flushReportingCaches();
        } catch (DataIntegrityViolationException e) {
            deleteFailedModal.show(true);
            target.add(deleteFailedModal);
            return;
        }
        setResponsePage(listPageClass);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // we cant do anything if we dont have a jpaService here
        if (jpaService == null) {
            throw new NullJpaServiceException();
        }

        // we dont like receiving null list pages
        if (listPageClass == null) {
            throw new NullListPageClassException();
        }

        IModel<T> model = null;

        if (entityId != null) {
            model = new DozerModel<>(jpaService.findById(entityId).orElseThrow(EntityNotFoundException::new));
        } else {
            T instance = newInstance();
            if (instance != null) {
                model = new DozerModel<>(instance);
            }
        }

        if (model != null) {
            editForm.setCompoundPropertyModel(model);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        if (!isReadonly()) {
            // form leaving
            Map<String, Object> map = new HashMap<>();
            map.put("formLeavingWarning", new StringResourceModel("formLeavingWarning", this, null).getString());
            PackageTextTemplate formLeavingHelper = new PackageTextTemplate(LivePing.class, "formLeavingHelper.js");
            response.render(JavaScriptHeaderItem.forScript(formLeavingHelper.asString(map), "formLeavingHelper"));
            try {
                formLeavingHelper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isExisting() {
        T object = editForm.getModelObject();
        return object != null && !object.isNew();
    }

    protected boolean isReadonly() {
        return ComponentUtil.isViewMode();
    }

    public void setListPage(Class<? extends BasePage> listPageClass) {
        this.listPageClass = listPageClass;
    }

    public void scheduleRedirect() {
        CharSequence urlStr = RequestCycle.get().urlFor(listPageClass, new PageParameters());
        String listUrl = RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlStr));

        if (referer != null && referer.startsWith(listUrl)) {
            RequestCycle.get().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(referer));
        } else {
            RequestCycle.get().setResponsePage(listPageClass);
        }
    }
}
