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
package org.devgateway.toolkit.forms.wicket.page.lists;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.components.table.LinkBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.ipar.DatasetFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditFoodLossDatasetPage;
import org.devgateway.toolkit.persistence.dao.ipar.FoodLossDataset;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.service.ipar.DatasetService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listPostHarvestLoss")
public class ListFoodLossDatasetPage extends AbstractListPage<FoodLossDataset> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean(name = "foodLossDatasetService")
    protected DatasetService service;

    public ListFoodLossDatasetPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditFoodLossDatasetPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("name", ListFoodLossDatasetPage.this), "label",
                "label"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("organization", ListFoodLossDatasetPage.this),
                "organization", "organization"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("approved", ListFoodLossDatasetPage.this),
                "approved", "approved"));
        columns.add(new LinkBootstrapPropertyColumn(new StringResourceModel("fileMetadata",
                ListFoodLossDatasetPage.this), "fileMetadata"));
    }

    @Override
    public JpaFilterState<FoodLossDataset> newFilterState() {
        Organization organization = SecurityUtil.getCurrentAuthenticatedPerson().getOrganization();
        if (organization != null && !isAdmin()) {
            return new DatasetFilterState(organization.getLabel());
        }
        return new DatasetFilterState();    }
}
