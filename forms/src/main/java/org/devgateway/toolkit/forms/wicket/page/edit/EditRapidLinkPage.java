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
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListRapidLinkFormPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.RapidLink;
import org.devgateway.toolkit.persistence.dao.categories.RapidLinkPosition;
import org.devgateway.toolkit.persistence.repository.category.RapidLinkPositionRepository;
import org.devgateway.toolkit.persistence.service.RapidLinkService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @SpringBean
    private ReleaseCacheService cacheService;

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
        titleField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleField);

        TextFieldBootstrapFormComponent<String> titleFrField = new TextFieldBootstrapFormComponent<>("titleFr");
        titleFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(titleFrField);
        titleFrField.required();

        TextFieldBootstrapFormComponent<String> subtitleField = new TextFieldBootstrapFormComponent<>("subtitle");
        subtitleField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(subtitleField);

        TextFieldBootstrapFormComponent<String> subtitleFrField = new TextFieldBootstrapFormComponent<>("subtitleFr");
        subtitleFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(subtitleFrField);
        subtitleFrField.required();

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("link");
        linkField.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        linkField.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(linkField);
        linkField.required();

        List<RapidLinkPosition> positions = rlpRepo.findAllFetchingLocalizedLabels();
        GenericChoiceProvider<RapidLinkPosition> choiceProvider =
                new GenericChoiceProvider<RapidLinkPosition>(positions) {
            @Override
            public String getDisplayValue(RapidLinkPosition rapidLinkPosition) {
                return rapidLinkPosition.getLabelFr() + "/" + rapidLinkPosition.getLabel();
            }
        };

        Select2ChoiceBootstrapFormComponent<RapidLinkPosition> rapidLinkPosition =
                new Select2ChoiceBootstrapFormComponent<>("rapidLinkPosition", choiceProvider);
        editForm.add(rapidLinkPosition);

        FileInputBootstrapFormComponent fileInput = new FileInputBootstrapFormComponent("fileMetadata").maxFiles(1);
        fileInput.getField().add((IValidator) new InputImageFileValidator(getString("fileNotAdded"),
                getString("filenameError")));
        fileInput.required();
        editForm.add(fileInput);
        editForm.add(new Label("imageInstructions", new StringResourceModel("imageInstructions", this, null))
                .setEscapeModelStrings(false));

    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", EditRapidLinkPage.this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                RapidLink rapidLink = editForm.getModelObject();
                Optional<RapidLink> rl = rapidLink.getRapidLinkPosition() != null
                        ? service.findByRapidLinkPositionId(rapidLink.getRapidLinkPosition().getId())
                        : Optional.empty();
                if (rapidLink.getRapidLinkPosition() != null
                        && !rl.equals(Optional.empty()) && !rl.get().getId().equals(rapidLink.getId())) {
                    feedbackPanel.error(new StringResourceModel("positionError", this, null).getString());
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    jpaService.save(rapidLink);
                    cacheService.releaseCache();
                    setResponsePage(listPageClass);
                }
            }
        };
    }

    protected class InputImageFileValidator implements IValidator<Set<FileMetadata>> {
        private static final long serialVersionUID = 972971245491631372L;

        private String errorFileNotAdded;

        private String errorFilenameError;

        public InputImageFileValidator(String errorFileNotAdded, String errorFilenameError) {
            this.errorFileNotAdded = errorFileNotAdded;
            this.errorFilenameError = errorFilenameError;
        }

        @Override
        public void validate(final IValidatable<Set<FileMetadata>> validatable) {
            if (validatable.getValue().isEmpty()) {
                ValidationError error = new ValidationError(errorFileNotAdded);
                validatable.error(error);
            } else {
                validatable.getValue().stream().forEach(file -> {
                    if (!file.getName().toLowerCase().endsWith(".jpg")
                            && !file.getName().toLowerCase().endsWith(".png")
                            && !file.getName().toLowerCase().endsWith(".png")) {
                        ValidationError error = new ValidationError(errorFilenameError);
                        validatable.error(error);
                    }
                });
            }
        }
    }

}
