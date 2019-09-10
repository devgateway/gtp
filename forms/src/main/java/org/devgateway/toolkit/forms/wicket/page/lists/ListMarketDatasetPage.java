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
import org.devgateway.toolkit.forms.security.SecurityUtil;
import org.devgateway.toolkit.forms.wicket.components.table.LinkBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DatasetFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditMarketDatasetPage;
import org.devgateway.toolkit.persistence.dao.MarketDataset;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listMarket")
public class ListMarketDatasetPage extends AbstractListPage<MarketDataset> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean(name = "marketDatasetService")
    protected DatasetService service;

    public ListMarketDatasetPage(final PageParameters pageParameters) {
        super(pageParameters);
        this.jpaService = service;
        this.editPageClass = EditMarketDatasetPage.class;
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("name", ListMarketDatasetPage.this)).getString()), "label",
                "label"));
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("organization", ListMarketDatasetPage.this)).getString()),
                "organization", "organization"));
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("approved", ListMarketDatasetPage.this)).getString()),
                "approved", "approved"));
        columns.add(new LinkBootstrapPropertyColumn(new Model<>((new StringResourceModel("fileMetadata",
                ListMarketDatasetPage.this)).getString()), "fileMetadata"));
    }

    @Override
    public JpaFilterState<MarketDataset> newFilterState() {
        return (JpaFilterState) new DatasetFilterState(SecurityUtil.getCurrentAuthenticatedPerson().getOrganization());
    }
}
