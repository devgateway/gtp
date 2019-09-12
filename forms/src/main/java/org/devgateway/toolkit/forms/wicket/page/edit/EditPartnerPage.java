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
package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.page.lists.ListPartnerPage;
import org.devgateway.toolkit.forms.wicket.validators.UniquePartnerValidator;
import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.repository.PartnerRepository;
import org.devgateway.toolkit.persistence.repository.PersonRepository;
import org.devgateway.toolkit.persistence.service.FocalPointTextSearchable;
import org.devgateway.toolkit.persistence.service.PartnerService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/partner")
public class EditPartnerPage extends AbstractEditPage<Partner> {

    @SpringBean
    private PartnerService partnerService;

    @SpringBean
    private PartnerRepository partnerRepository;

    @SpringBean
    private PersonRepository personRepository;

    public EditPartnerPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = partnerService;
        this.listPageClass = ListPartnerPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> name = ComponentUtil.addTextField(editForm, "name");
        name.getField().add(new PropertyValidator<>());
        name.getField().add(new UniquePartnerValidator(partnerRepository, editForm.getModelObject().getId()));

        ComponentUtil.addTextField(editForm, "sector").getField().add(new PropertyValidator<>());
        ComponentUtil.addSelect2ChoiceField(editForm, "focalPoint", new FocalPointTextSearchable(personRepository))
                .getField().add(new PropertyValidator<>());
    }
}
