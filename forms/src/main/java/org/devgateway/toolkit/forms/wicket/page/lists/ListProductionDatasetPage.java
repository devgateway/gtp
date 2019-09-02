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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.LinkBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DatasetFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.TestFormFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditProductionDatasetPage;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.ProductionDataset;
import org.devgateway.toolkit.persistence.dao.TestForm;
import org.devgateway.toolkit.persistence.service.ProductionDatasetService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listprod")
public class ListProductionDatasetPage extends AbstractListPage<ProductionDataset> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean
    protected ProductionDatasetService productionDatasetService;

    public ListProductionDatasetPage(final PageParameters pageParameters) {
        super(pageParameters);
        this.jpaService = productionDatasetService;
        this.editPageClass = EditProductionDatasetPage.class;
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("name", ListProductionDatasetPage.this)).getString()), "label",
                "label"));
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("organization", ListProductionDatasetPage.this)).getString()),
                "organization", "organization"));
        columns.add(new LinkBootstrapPropertyColumn(new Model<>((new StringResourceModel("fileMetadata",
                ListProductionDatasetPage.this)).getString()), "fileMetadata"));
    }

    @Override
    public JpaFilterState<ProductionDataset> newFilterState() {
        return new DatasetFilterState();
    }
}
