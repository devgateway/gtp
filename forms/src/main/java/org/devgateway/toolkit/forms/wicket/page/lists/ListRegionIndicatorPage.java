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
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RegionIndicatorFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditRegionIndicatorPage;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.service.RegionIndicatorService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listRegionIndicator")
public class ListRegionIndicatorPage extends AbstractListPage<RegionIndicator> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean
    protected RegionIndicatorService service;

    public ListRegionIndicatorPage(final PageParameters pageParameters) {
        super(pageParameters);
        this.jpaService = service;
        this.editPageClass = EditRegionIndicatorPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("name", ListRegionIndicatorPage.this), "reducedName",
                "reducedName"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("description", ListRegionIndicatorPage.this),
                "reducedDesc", "reducedDesc"));
    }

    @Override
    public JpaFilterState<RegionIndicator> newFilterState() {
        return new RegionIndicatorFilterState();
    }
}
