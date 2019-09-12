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
import org.devgateway.toolkit.forms.wicket.page.edit.EditPovertyIndicatorDatasetPage;
import org.devgateway.toolkit.persistence.dao.PovertyIndicatorDataset;
import org.devgateway.toolkit.persistence.service.DatasetService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listPoverty")
public class ListPovertyIndicatorDatasetPage extends AbstractListPage<PovertyIndicatorDataset> {
    private static final long serialVersionUID = -378945525712620234L;

    @SpringBean(name = "povertyIndicatorDatasetService")
    protected DatasetService service;

    public ListPovertyIndicatorDatasetPage(final PageParameters pageParameters) {
        super(pageParameters);
        this.jpaService = service;
        this.editPageClass = EditPovertyIndicatorDatasetPage.class;
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("name",
                        ListPovertyIndicatorDatasetPage.this)).getString()), "label",
                "label"));
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("organization",
                        ListPovertyIndicatorDatasetPage.this)).getString()),
                "organization", "organization"));
        columns.add(new PropertyColumn<>(
                new Model<>((new StringResourceModel("approved", ListPovertyIndicatorDatasetPage.this)).getString()),
                "approved", "approved"));
        columns.add(new LinkBootstrapPropertyColumn(new Model<>((new StringResourceModel("fileMetadata",
                ListPovertyIndicatorDatasetPage.this)).getString()), "fileMetadata"));
    }

    @Override
    public JpaFilterState<PovertyIndicatorDataset> newFilterState() {
        return (JpaFilterState) new DatasetFilterState(SecurityUtil.getCurrentAuthenticatedPerson().getOrganization());
    }
}
