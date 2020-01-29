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
import org.devgateway.toolkit.forms.wicket.page.user.EditUserPageElevated;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.service.PersonService;
import org.wicketstuff.annotation.mount.MountPath;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/listUsers")
public class ListUserPage extends AbstractListPage<Person> {

    @SpringBean
    private PersonService personService;

    public ListUserPage(final PageParameters pageParameters) {
        super(pageParameters);

        this.jpaService = personService;

        this.editPageClass = EditUserPageElevated.class;
        columns.add(new LambdaColumn<>(new Model<>("Username"), "username", Person::getUsername));
        columns.add(new LambdaColumn<>(new Model<>("First Name"), "firstName", Person::getFirstName));
        columns.add(new LambdaColumn<>(new Model<>("Last Name"), "lastName", Person::getLastName));
        columns.add(new LambdaColumn<>(new Model<>("Organization"), "organization", Person::getOrganization));
        columns.add(new LambdaColumn<>(new Model<>("Roles"), Person::getRoles));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // enable excel download
        //excelForm.setVisibilityAllowed(true);
    }
}