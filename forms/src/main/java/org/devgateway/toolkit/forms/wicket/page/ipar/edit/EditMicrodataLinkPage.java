/**
 * Copyright (c) 2015 Development Gateway, Inc and others.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 * <p>
 * Contributors:
 * Development Gateway - initial API and implementation
 */
/**
 *
 */
package org.devgateway.toolkit.forms.wicket.page.ipar.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.util.ComponentUtil;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.ipar.lists.ListMicrodataLinkFormPage;
import org.devgateway.toolkit.persistence.dao.ipar.MicrodataLink;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.repository.category.OrganizationRepository;
import org.devgateway.toolkit.persistence.service.ipar.MicrodataLinkService;
import org.devgateway.toolkit.persistence.service.TextSearchableAdapter;
import org.wicketstuff.annotation.mount.MountPath;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath("/editOtherDatasources")
public class EditMicrodataLinkPage extends AbstractEditPage<MicrodataLink> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private MicrodataLinkService service;

    @SpringBean
    private OrganizationRepository orgRepo;


    /**
     * @param parameters
     */
    public EditMicrodataLinkPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = ListMicrodataLinkFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> titleFrField = new TextFieldBootstrapFormComponent<>("titleFr");
        titleFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleFrField);
        titleFrField.required();

        TextFieldBootstrapFormComponent<String> descFrField = new TextFieldBootstrapFormComponent<>("descriptionFr");
        descFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(descFrField);
        descFrField.required();

        TextFieldBootstrapFormComponent<String> titleField = new TextFieldBootstrapFormComponent<>("title");
        titleField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleField);

        TextFieldBootstrapFormComponent<String> descField = new TextFieldBootstrapFormComponent<>("description");
        descField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(descField);

        Select2ChoiceBootstrapFormComponent<Organization> organization = ComponentUtil.addSelect2ChoiceField(editForm,
                "organization", new TextSearchableAdapter<>(orgRepo));
        editForm.add(organization);

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("link");
        linkField.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        linkField.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        linkField.required();
        editForm.add(linkField);
    }

}