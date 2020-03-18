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
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DepartmentIndicatorFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditDepartmentIndicatorPage;
import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.service.DepartmentIndicatorService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listDepartmentIndicator")
public class ListDepartmentIndicatorPage extends AbstractListPage<DepartmentIndicator> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean
    protected DepartmentIndicatorService service;

    public ListDepartmentIndicatorPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditDepartmentIndicatorPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("nameFr", ListDepartmentIndicatorPage.this), "nameFr",
                "nameFr"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("name", ListDepartmentIndicatorPage.this), "name",
                "name"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("year", ListDepartmentIndicatorPage.this), "year",
                "year"));
    }

    @Override
    public JpaFilterState<DepartmentIndicator> newFilterState() {
        Organization organization = SecurityUtil.getCurrentAuthenticatedPerson().getOrganization();
        if (organization != null && !isAdmin()) {
            return new DepartmentIndicatorFilterState(organization.getLabel());
        }
        return new DepartmentIndicatorFilterState();
    }
}
