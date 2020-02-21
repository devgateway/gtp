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
import org.devgateway.toolkit.forms.wicket.components.table.filter.IndicatorMetadataFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditIndicatorMetadataPage;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.IndicatorMetadataService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listIndicatorMetadata")
public class ListIndicatorMetadataFormPage extends AbstractListPage<IndicatorMetadata> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    private IndicatorMetadataService service;


    public ListIndicatorMetadataFormPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditIndicatorMetadataPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("indicator", ListIndicatorMetadataFormPage.this),
                "indicator", "indicator"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        getEditPageLink().setEnabled(false);
        getEditPageLink().setVisibilityAllowed(false);
    }

    @Override
    public JpaFilterState<IndicatorMetadata> newFilterState() {
        return new IndicatorMetadataFilterState();
    }
}
