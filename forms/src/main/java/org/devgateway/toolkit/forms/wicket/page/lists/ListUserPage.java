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
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.user.EditUserPageElevated;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.dao.Person_;
import org.devgateway.toolkit.persistence.service.PersonService;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listUsers")
public class ListUserPage extends AbstractListPage<Person> {

    @SpringBean
    private PersonService personService;

    public ListUserPage(final PageParameters pageParameters) {
        super(pageParameters, false);

        this.jpaService = personService;

        this.editPageClass = EditUserPageElevated.class;
        columns.add(new LambdaColumn<>(new StringResourceModel("username", ListUserPage.this),
                "username", Person::getUsername));
        columns.add(new LambdaColumn<>(new StringResourceModel("firstName", ListUserPage.this),
                "firstName", Person::getFirstName));
        columns.add(new LambdaColumn<>(new StringResourceModel("lastName", ListUserPage.this),
                "lastName", Person::getLastName));
        columns.add(new LambdaColumn<>(new StringResourceModel("organization", ListUserPage.this),
                "organization", Person::getOrganization));
        columns.add(new LambdaColumn<>(new StringResourceModel("roles", ListUserPage.this),
                Person::getRoles));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // enable excel download
        //excelForm.setVisibilityAllowed(true);
    }

    @Override
    public JpaFilterState<Person> newFilterState() {
        return new JpaFilterState(Person_.USERNAME);
    }
}