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
import org.devgateway.toolkit.forms.wicket.components.table.TextFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RapidLinkFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditRapidLinkPage;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.service.RapidLinkService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listRapidLink")
public class ListRapidLinkFormPage extends AbstractListPage<RapidLink> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    private RapidLinkService service;


    public ListRapidLinkFormPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditRapidLinkPage.class;

        columns.add(new TextFilteredBootstrapPropertyColumn<>(new StringResourceModel("rapidLinkPosition",
                ListRapidLinkFormPage.this), "rapidLinkPosition", "rapidLinkPosition"));
        columns.add(new TextFilteredBootstrapPropertyColumn<>(new StringResourceModel("title",
                ListRapidLinkFormPage.this), "title", "title"));
        columns.add(new LambdaColumn<>(new StringResourceModel("link",
                ListRapidLinkFormPage.this), "link", RapidLink::getReducedLink));
    }


    @Override
    public JpaFilterState<RapidLink> newFilterState() {
        return new RapidLinkFilterState();
    }
}
