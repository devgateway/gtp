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
package org.devgateway.toolkit.forms.wicket.page.ipar.lists;

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
import org.devgateway.toolkit.forms.wicket.page.ipar.edit.EditAOIDatasetPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexDataset;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.service.ipar.DatasetService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_EDITOR})
@MountPath(value = "/listAgricultureOrientationIndex")
public class ListAOIIndicatorDatasetPage extends AbstractListPage<AgricultureOrientationIndexDataset> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean(name = "aoiDatasetService")
    protected DatasetService service;

    public ListAOIIndicatorDatasetPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditAOIDatasetPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("name", ListAOIIndicatorDatasetPage.this), "label",
                "label"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("organization", ListAOIIndicatorDatasetPage.this),
                "organization", "organization"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("approved", ListAOIIndicatorDatasetPage.this),
                "approved", "approvedStr"));
        columns.add(new LinkBootstrapPropertyColumn(
                new StringResourceModel("fileMetadata", ListAOIIndicatorDatasetPage.this), "fileMetadata"));
    }

    @Override
    public JpaFilterState<AgricultureOrientationIndexDataset> newFilterState() {
        Organization organization = SecurityUtil.getCurrentAuthenticatedPerson().getOrganization();
        if (organization != null && !isAdmin()) {
            return new DatasetFilterState(organization.getLabel());
        }
        return new DatasetFilterState();
    }
}
