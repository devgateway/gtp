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
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.DirectLinkBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.page.edit.EditNationalIndicatorPage;
import org.devgateway.toolkit.persistence.dao.NationalIndicator;
import org.devgateway.toolkit.persistence.service.NationalIndicatorService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/listNationalIndicator")
public class ListNationalIndicatorFormPage extends AbstractListPage<NationalIndicator> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    private NationalIndicatorService service;


    public ListNationalIndicatorFormPage(final PageParameters pageParameters) {
        super(pageParameters, false);

        this.jpaService = service;

        this.editPageClass = EditNationalIndicatorPage.class;
        columns.add(new LambdaColumn<>(new StringResourceModel("name", ListNationalIndicatorFormPage.this),
                "name", NationalIndicator::getName));
        columns.add(new LambdaColumn<>(new StringResourceModel("nameFr", ListNationalIndicatorFormPage.this),
                "nameFr", NationalIndicator::getNameFr));
        columns.add(new LambdaColumn<>(new StringResourceModel("source", ListNationalIndicatorFormPage.this),
                "source", NationalIndicator::getSource));
        columns.add(new DirectLinkBootstrapPropertyColumn(new StringResourceModel("link",
                ListNationalIndicatorFormPage.this), "link"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
