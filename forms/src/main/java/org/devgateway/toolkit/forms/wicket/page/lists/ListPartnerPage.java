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
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.EditPartnerPage;
import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.service.PartnerService;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listPartners")
public class ListPartnerPage extends AbstractListPage<Partner> {

    @SpringBean
    private PartnerService personService;

    public ListPartnerPage(final PageParameters pageParameters) {
        super(pageParameters);

        this.jpaService = personService;

        this.editPageClass = EditPartnerPage.class;
        columns.add(new LambdaColumn<>(new Model<>("Name"), "name", Partner::getName));
        columns.add(new LambdaColumn<>(new Model<>("Sector"), "sector", Partner::getSector));
        columns.add(new LambdaColumn<>(new Model<>("Focal Point"),
                "focalPoint.firstName;focalPoint.lastName", Partner::getFocalPoint));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // enable excel download
        excelForm.setVisibilityAllowed(true);
    }
}