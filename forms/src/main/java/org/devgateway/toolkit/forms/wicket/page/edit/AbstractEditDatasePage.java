package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.CheckBoxPickerBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.validator.InputFileValidator;
import org.devgateway.toolkit.persistence.dao.Data;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.service.ImportService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Daniel Oliva
 */
public abstract class AbstractEditDatasePage<T extends Dataset, S extends Data> extends AbstractEditPage<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEditDatasePage.class);

    private String fileName;

    public AbstractEditDatasePage(PageParameters parameters, String fileName) {
        super(parameters);
        this.fileName = fileName;
    }

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;

    protected transient ImportService importer;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("label");
        name.getField().add(new StringValidator(null, DEFA_MAX_LENGTH));
        name.required();
        editForm.add(name);

        final TextFieldBootstrapFormComponent<String> source = new TextFieldBootstrapFormComponent<>("source");
        source.getField().add(new StringValidator(null, DEFA_MAX_LENGTH));
        source.required();
        editForm.add(source);

        final TextFieldBootstrapFormComponent<String> metadata = new TextFieldBootstrapFormComponent<>("metadata");
        metadata.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        metadata.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(metadata);

        final TextFieldBootstrapFormComponent<Integer> year = new TextFieldBootstrapFormComponent<>("year");
        year.getField().add(new RangeValidator<>("2000", "2030"));
        editForm.add(year);

        final CheckBoxPickerBootstrapFormComponent approved = new CheckBoxPickerBootstrapFormComponent("approved");
        if (!SecurityUtil.getCurrentAuthenticatedPerson().getRoles().stream()
                .anyMatch(str -> str.getAuthority().equals(SecurityConstants.Roles.ROLE_ADMIN))) {
            approved.setOutputMarkupPlaceholderTag(true);
            approved.setVisible(false);
        }
        editForm.add(approved);

        FileInputBootstrapFormComponent fileInput = new FileInputBootstrapFormComponent("fileMetadata").maxFiles(1);
        fileInput.getFileInputBootstrapFormComponentWrapper().setFeedbackPanel(feedbackPanel);
        fileInput.required();
        fileInput.getField().add((IValidator) new InputFileValidator(getString("fileNotAdded"),
                getString("filenameError")));
        if (this.entityId != null) {
            fileInput.setOutputMarkupPlaceholderTag(true);
            fileInput.setVisible(false);
        }
        editForm.add(fileInput);

        if (entityId != null && ((Dataset) this.editForm.getModelObject()).isApproved()
            && !SecurityUtil.getCurrentAuthenticatedPerson().getRoles().stream()
                .anyMatch(str -> str.getAuthority().equals(SecurityConstants.Roles.ROLE_ADMIN))) {
            deleteButton.setVisibilityAllowed(false);
        }

        TemplateLink link = new TemplateLink("fileId", fileName);
        editForm.add(link);

        Label linkInfo = new Label("linkInfo", new ResourceModel("linkInfo.label"));
        link.add(linkInfo);

        if (entityId != null) {
            linkInfo.setVisible(false);
            link.setVisible(false);
        }
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                logger.info("Check the file and process it");
                T model = editForm.getModelObject();
                ImportResults<S> results = null;
                if (model.getId() != null) {
                    SecurityUtil.getCurrentAuthenticatedPerson();
                    jpaService.saveAndFlush(model);
                } else {
                    model.setOrganization(SecurityUtil.getCurrentAuthenticatedPerson().getOrganization());
                    model.setUploadedBy(SecurityUtil.getCurrentAuthenticatedPerson());
                    redirectToSelf = false;
                    results = importer.processFile(model);
                }

                //process results
                if (results != null && !results.isImportOkFlag()) {
                    feedbackPanel.error(new StringResourceModel("uploadError", this, null).getString());
                    results.getErrorList().forEach(error -> feedbackPanel.error(error));
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    markupCacheService.clearAllCaches();
                }
                cacheService.releaseCache();
                redirect(target);
            }


            private void redirect(final AjaxRequestTarget target) {
                if (redirectToSelf) {
                    // we need to close the blockUI if it's opened and enable all
                    // the buttons
                    target.appendJavaScript("$.unblockUI();");
                } else if (redirect) {
                    setResponsePage(getResponsePage(), getParameterPage());
                }
            }
        };
    }
}
