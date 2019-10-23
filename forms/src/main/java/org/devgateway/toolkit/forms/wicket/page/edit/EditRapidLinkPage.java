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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListRapidLinkFormPage;
import org.devgateway.toolkit.forms.wicket.page.validator.InputFileValidator;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition;
import org.devgateway.toolkit.persistence.repository.category.RapidLinkPositionRepository;
import org.devgateway.toolkit.persistence.service.RapidLinkService;
import org.devgateway.toolkit.persistence.service.TextSearchableAdapter;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.Optional;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/editRapidLink")
public class EditRapidLinkPage extends AbstractEditPage<RapidLink> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private RapidLinkService service;

    @SpringBean
    private RapidLinkPositionRepository rlpRepo;

    /**
     * @param parameters
     */
    public EditRapidLinkPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = ListRapidLinkFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> titleField = new TextFieldBootstrapFormComponent<>("title");
        editForm.add(titleField);
        titleField.required();

        TextFieldBootstrapFormComponent<String> subtitleField = new TextFieldBootstrapFormComponent<>("subtitle");
        editForm.add(subtitleField);
        subtitleField.required();

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("link");
        editForm.add(linkField);
        linkField.required();

        Select2ChoiceBootstrapFormComponent<RapidLinkPosition> rapidLinkPosition =
                new Select2ChoiceBootstrapFormComponent<>("rapidLinkPosition",
                        new GenericPersistableJpaTextChoiceProvider<>(new TextSearchableAdapter<>(rlpRepo)));
        editForm.add(rapidLinkPosition);

        FileInputBootstrapFormComponent fileInput = new FileInputBootstrapFormComponent("fileMetadata").maxFiles(1);
        fileInput.required();
        fileInput.getField().add((IValidator) new InputFileValidator(getString("fileNotAdded"),
                getString("filenameError")));
        editForm.add(fileInput);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", EditRapidLinkPage.this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                RapidLink rapidLink = editForm.getModelObject();
                if (rapidLink.getRapidLinkPosition() != null
                        && !service.findByRapidLinkPositionId(rapidLink.getRapidLinkPosition().getId())
                        .equals(Optional.empty())) {
                    feedbackPanel.error(new StringResourceModel("positionError", this, null).getString());
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    jpaService.save(rapidLink);
                    setResponsePage(listPageClass);
                }
            }
        };
    }

}
