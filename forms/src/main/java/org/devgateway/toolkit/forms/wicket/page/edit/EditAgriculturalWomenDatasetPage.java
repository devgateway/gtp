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
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAgriculturalWomenDatasetPage;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenDataset;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.devgateway.toolkit.persistence.service.ImportService;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath("/editAgriculturalWomen")
public class EditAgriculturalWomenDatasetPage extends AbstractEditDatasePage<AgriculturalWomenDataset> {

    private static final long serialVersionUID = -6069250112046118104L;
    private static final Logger logger = LoggerFactory.getLogger(EditAgriculturalWomenDatasetPage.class);

    @SpringBean(name = "agriculturalWomenIndicatorImporter")
    private transient ImportService importer;

    @SpringBean(name = "agriculturalWomenDatasetService")
    protected DatasetService service;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    public EditAgriculturalWomenDatasetPage(final PageParameters parameters) {
        super(parameters);
        this.jpaService = service;
        this.listPageClass = ListAgriculturalWomenDatasetPage.class;
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save",
                EditAgriculturalWomenDatasetPage.this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                logger.info("Check the file and process it");
                AgriculturalWomenDataset model = editForm.getModelObject();
                if (model.getId() != null) {
                    SecurityUtil.getCurrentAuthenticatedPerson();
                } else {
                    model.setOrganization(SecurityUtil.getCurrentAuthenticatedPerson().getOrganization());
                }
                redirectToSelf = false;
                ImportResults<AgriculturalWomenIndicator> results = importer.processFile(model);

                //process results
                if (!results.isImportOkFlag()) {
                    feedbackPanel.error(new StringResourceModel("uploadError", this, null).getString());
                    results.getErrorList().forEach(error -> feedbackPanel.error(error));
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    markupCacheService.clearAllCaches();
                }

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
