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
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.ipar.edit.EditPartnerPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.ipar.Partner;
import org.devgateway.toolkit.persistence.service.ipar.PartnerService;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listPartners")
public class ListPartnerPage extends AbstractListPage<Partner> {

    @SpringBean
    private PartnerService partnerService;

    public ListPartnerPage(final PageParameters pageParameters) {
        super(pageParameters, false);

        this.jpaService = partnerService;

        this.editPageClass = EditPartnerPage.class;
        columns.add(new LambdaColumn<>(new StringResourceModel("name", ListPartnerPage.this),
                "name", Partner::getName));
        columns.add(new LambdaColumn<>(new StringResourceModel("groupType", ListPartnerPage.this),
                Partner::getGroupType));
        columns.add(new LambdaColumn<>(new StringResourceModel("url", ListPartnerPage.this),
                Partner::getReducedUrl));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // enable excel download
        //excelForm.setVisibilityAllowed(true);
    }
}