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

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.lists.ListFoodLossDatasetPage;
import org.devgateway.toolkit.persistence.dao.FoodLossDataset;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.devgateway.toolkit.persistence.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath("/editPostHarvestLoss")
public class EditFoodLossDatasetPage extends AbstractEditDatasePage<FoodLossDataset, FoodLossIndicator> {

    private static final long serialVersionUID = -6069250112046118104L;
    private static final Logger logger = LoggerFactory.getLogger(EditFoodLossDatasetPage.class);
    public static final String TEMPLATE_XLSX = "postHarvestLossDataset-Template.xlsx";

    @SpringBean(name = "foodLossIndicatorImporter")
    private transient ImportService importService;

    @SpringBean(name = "foodLossDatasetService")
    protected DatasetService service;


    public EditFoodLossDatasetPage(final PageParameters parameters) {
        super(parameters, TEMPLATE_XLSX);
        this.jpaService = service;
        this.listPageClass = ListFoodLossDatasetPage.class;
        this.importer = importService;
    }

}
