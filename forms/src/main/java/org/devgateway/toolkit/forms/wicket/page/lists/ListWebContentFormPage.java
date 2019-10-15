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
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.TextFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.WebContentFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditWebContentPage;
import org.devgateway.toolkit.persistence.dao.WebContent;
import org.devgateway.toolkit.persistence.service.WebContentService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listWebContent")
public class ListWebContentFormPage extends AbstractListPage<WebContent> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    private WebContentService service;


    public ListWebContentFormPage(final PageParameters pageParameters) {
        super(pageParameters);
        this.jpaService = service;
        this.editPageClass = EditWebContentPage.class;

        columns.add(new TextFilteredBootstrapPropertyColumn<>(new Model<>("Title"), "title", "title"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

    }

    @Override
    public JpaFilterState<WebContent> newFilterState() {
        return new WebContentFilterState();
    }
}
