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

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.page.lists.ListProductionDatasetPage;
import org.devgateway.toolkit.persistence.dao.ProductionDataset;
import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.devgateway.toolkit.persistence.service.ImportService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath("/editProduction")
public class EditProductionDatasetPage extends AbstractEditDatasePage<ProductionDataset, Production> {

    private static final long serialVersionUID = -6069250112046118104L;
    private static final Logger logger = LoggerFactory.getLogger(EditProductionDatasetPage.class);
    public static final String TEMPLATE_XLSX = "productionDataset-Template.xlsx";

    @SpringBean(name = "productionImporter")
    private transient ImportService importService;

    @SpringBean(name = "productionDatasetService")
    protected DatasetService service;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    protected ReleaseCacheService cacheService;

    public EditProductionDatasetPage(final PageParameters parameters) {
        super(parameters, TEMPLATE_XLSX);
        this.jpaService = service;
        this.listPageClass = ListProductionDatasetPage.class;
        this.importer = importService;
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            @Transactional
            protected void onSubmit(final AjaxRequestTarget target) {
                logger.info("Check the file and process it");
                ProductionDataset model = editForm.getModelObject();
                ImportResults<ProductionDataset> results = null;
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
                    updateDepartmentsFakeIndicators();
                    updateRegionFakeIndicators();
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

    @Override
    public DeleteEditPageButton getDeleteEditPageButton() {
        return new DeleteEditPageButton("delete", new StringResourceModel("deleteButton", this, null)) {
            private static final long serialVersionUID = 5214537995514151321L;

            @Override
            @Transactional
            protected void onSubmit(final AjaxRequestTarget target) {
                ProductionDataset deleteable = editForm.getModelObject();
                try {
                    jpaService.delete(deleteable);
                    updateRegionFakeIndicators();
                    updateDepartmentsFakeIndicators();
                    flushReportingCaches();
                } catch (DataIntegrityViolationException e) {
                    error(new NotificationMessage(
                            new StringResourceModel("delete_error_message", this, null))
                            .hideAfter(Duration.NONE));
                    target.add(feedbackPanel);

                    return;
                }
                setResponsePage(listPageClass);
            }

        };
    }
}
