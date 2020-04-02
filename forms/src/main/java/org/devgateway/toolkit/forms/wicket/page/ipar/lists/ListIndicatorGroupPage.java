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
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.filter.ipar.IndicatorGroupFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.EditIndicatorGroupPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndicatorGroup;
import org.devgateway.toolkit.persistence.service.category.IndicatorGroupService;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listIndicatorGroup")
public class ListIndicatorGroupPage extends AbstractListPage<IndicatorGroup> {
    private static final long serialVersionUID = -324298525712620234L;

    @SpringBean
    protected IndicatorGroupService service;

    public ListIndicatorGroupPage(final PageParameters pageParameters) {
        super(pageParameters, false);
        this.jpaService = service;
        this.editPageClass = EditIndicatorGroupPage.class;
        columns.add(new PropertyColumn<>(
                new StringResourceModel("nameFr", ListIndicatorGroupPage.this, null), "labelFr"));
        columns.add(new PropertyColumn<>(
                new StringResourceModel("name", ListIndicatorGroupPage.this, null), "label"));
    }

    @Override
    public IndicatorGroupFilterState newFilterState() {
        return new IndicatorGroupFilterState();
    }
}
