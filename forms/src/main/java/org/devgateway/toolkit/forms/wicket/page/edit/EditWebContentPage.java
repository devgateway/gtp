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
package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.SummernoteBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListWebContentFormPage;
import org.devgateway.toolkit.persistence.dao.WebContent;
import org.devgateway.toolkit.persistence.service.WebContentService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/editWebContent")
public class EditWebContentPage extends AbstractEditPage<WebContent> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private WebContentService service;

    /**
     * @param parameters
     */
    public EditWebContentPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = ListWebContentFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> titleField = new TextFieldBootstrapFormComponent<>("title");
        titleField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleField);
        titleField.required();

        TextFieldBootstrapFormComponent<String> subtitleField = new TextFieldBootstrapFormComponent<>("subtitle");
        subtitleField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(subtitleField);


        TextFieldBootstrapFormComponent<String> titleFrField = new TextFieldBootstrapFormComponent<>("titleFr");
        titleFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleFrField);
        titleFrField.required();

        TextFieldBootstrapFormComponent<String> subtitleFrField = new TextFieldBootstrapFormComponent<>("subtitleFr");
        subtitleFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(subtitleFrField);

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("link");
        linkField.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES + UrlValidator.NO_FRAGMENTS));
        editForm.add(linkField);

        SummernoteBootstrapFormComponent summernote = new SummernoteBootstrapFormComponent("summernote");
        editForm.add(summernote);

        SummernoteBootstrapFormComponent summernoteFr = new SummernoteBootstrapFormComponent("summernoteFr");
        editForm.add(summernoteFr);
    }

}
