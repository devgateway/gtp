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
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.SummernoteBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextAreaFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.page.lists.ListPartnerPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.dao.categories.PartnerGroup;
import org.devgateway.toolkit.persistence.repository.category.PartnerGroupRepository;
import org.devgateway.toolkit.persistence.service.PartnerService;
import org.devgateway.toolkit.persistence.service.TextSearchableAdapter;
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
    private PartnerGroupRepository partnerGroupRepository;

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
        name.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));

        Select2ChoiceBootstrapFormComponent<PartnerGroup> groupType = new Select2ChoiceBootstrapFormComponent<>(
                "groupType", new GenericPersistableJpaTextChoiceProvider<>(
                new TextSearchableAdapter<>(partnerGroupRepository)));
        groupType.required();
        editForm.add(groupType);

        FileInputBootstrapFormComponent logo = new FileInputBootstrapFormComponent("logo").maxFiles(1);
        editForm.add(logo);
        logo.required();

        TextAreaFieldBootstrapFormComponent<String> contactInfo =
                new TextAreaFieldBootstrapFormComponent<>("contactInfo");
        contactInfo.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(contactInfo);


        SummernoteBootstrapFormComponent description = new SummernoteBootstrapFormComponent("description");
        editForm.add(description);
        description.required();

        SummernoteBootstrapFormComponent descriptionFr = new SummernoteBootstrapFormComponent("descriptionFr");
        editForm.add(descriptionFr);
        descriptionFr.required();

        TextFieldBootstrapFormComponent<String> url = ComponentUtil.addTextField(editForm, "url");
        url.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        url.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        editForm.add(url);

    }
}
