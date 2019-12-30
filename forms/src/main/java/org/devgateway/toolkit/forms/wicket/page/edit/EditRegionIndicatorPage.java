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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListRegionIndicatorPage;
import org.devgateway.toolkit.forms.wicket.page.validator.InputFileValidator;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dao.RegionStat;
import org.devgateway.toolkit.persistence.service.RegionIndicatorService;
import org.devgateway.toolkit.persistence.service.ImportRegionIndicatorService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath("/editRegionIndicator")
public class EditRegionIndicatorPage extends AbstractEditPage<RegionIndicator> {

    private static final long serialVersionUID = -6069250112046118104L;
    private static final Logger logger = LoggerFactory.getLogger(EditRegionIndicatorPage.class);

    @SpringBean
    private transient ImportRegionIndicatorService importService;

    @SpringBean
    protected RegionIndicatorService service;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;


    public EditRegionIndicatorPage(final PageParameters parameters) {
        super(parameters);
        this.jpaService = service;
        this.listPageClass = ListRegionIndicatorPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.getField().add(new StringValidator(null, DEFA_MAX_LENGTH));
        name.required();
        editForm.add(name);

        final TextFieldBootstrapFormComponent<String> description =
                new TextFieldBootstrapFormComponent<>("description");
        description.getField().add(new StringValidator(null, DEFA_MAX_LENGTH));
        editForm.add(description);

        final TextFieldBootstrapFormComponent<Integer> year = new TextFieldBootstrapFormComponent<>("year");
        year.getField().add(new RangeValidator<>("2010", "2030"));
        editForm.add(year);

        FileInputBootstrapFormComponent fileInput = new FileInputBootstrapFormComponent("fileMetadata").maxFiles(1);
        fileInput.required();
        fileInput.getField().add((IValidator) new InputFileValidator(getString("fileNotAdded"),
                getString("filenameError")));
        if (this.entityId != null) {
            fileInput.setOutputMarkupPlaceholderTag(true);
            fileInput.setVisible(false);
        }
        editForm.add(fileInput);

    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                logger.info("Check the file and process it");
                RegionIndicator model = editForm.getModelObject();
                ImportResults<RegionStat> results = null;
                if (model.getId() != null) {
                    SecurityUtil.getCurrentAuthenticatedPerson();
                    jpaService.saveAndFlush(model);
                } else {
                    model.setUploadedBy(SecurityUtil.getCurrentAuthenticatedPerson());
                    redirectToSelf = false;
                    results = importService.processFile(model);
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
