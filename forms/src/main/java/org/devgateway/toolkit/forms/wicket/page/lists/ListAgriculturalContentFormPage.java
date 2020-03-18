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
import org.devgateway.toolkit.forms.wicket.components.table.TextFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.AgriculturalContentFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditAgriculturalContentPage;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.service.AgriculturalContentService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN})
@MountPath(value = "/listAgriculturalContent")
public class ListAgriculturalContentFormPage extends AbstractListPage<AgriculturalContent> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    private AgriculturalContentService service;


    public ListAgriculturalContentFormPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditAgriculturalContentPage.class;

        columns.add(new TextFilteredBootstrapPropertyColumn<>(new StringResourceModel("titleFr",
                ListAgriculturalContentFormPage.this), "titleFr", "reducedTitleFr"));
        columns.add(new TextFilteredBootstrapPropertyColumn<>(new StringResourceModel("title",
                ListAgriculturalContentFormPage.this), "title", "reducedTitle"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("publicationDateFormatted", ListAgriculturalContentFormPage.this),
                "publicationDate", "publicationDateFormatted"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("contentType", ListAgriculturalContentFormPage.this), "contentTypeFr"));

    }

    @Override
    public JpaFilterState<AgriculturalContent> newFilterState() {
        return new AgriculturalContentFilterState();
    }
}
