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

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.DateFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.FileInputBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListAgriculturalContentFormPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.AgriculturalContent;
import org.devgateway.toolkit.persistence.dao.categories.ContentType;
import org.devgateway.toolkit.persistence.repository.category.ContentTypeRepository;
import org.devgateway.toolkit.persistence.service.AgriculturalContentService;
import org.devgateway.toolkit.persistence.service.ReleaseCacheService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;


/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN})
@MountPath("/editAgriculturalContent")
public class EditAgriculturalContentPage extends AbstractEditPage<AgriculturalContent> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private AgriculturalContentService service;

    @SpringBean
    private ContentTypeRepository repo;

    @SpringBean
    private ReleaseCacheService cacheService;


    /**
     * @param parameters
     */
    public EditAgriculturalContentPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = ListAgriculturalContentFormPage.class;
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

        TextFieldBootstrapFormComponent<String> descField = new TextFieldBootstrapFormComponent<>("description");
        descField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(descField);

        TextFieldBootstrapFormComponent<String> descFrField = new TextFieldBootstrapFormComponent<>("descriptionFr");
        descFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(descFrField);
        descFrField.required();

        DateFieldBootstrapFormComponent publicationDate = new DateFieldBootstrapFormComponent("publicationDate");
        publicationDate.required();
        editForm.add(publicationDate);

        List<ContentType> contentTypes = repo.findAllPopulatedLang();
        GenericChoiceProvider<ContentType> choiceProvider = new GenericChoiceProvider<ContentType>(contentTypes) {
            @Override
            public String getDisplayValue(ContentType contentType) {
                return contentType.getNameEnFr();
            }
        };

        Select2ChoiceBootstrapFormComponent<ContentType> contentType =
                new Select2ChoiceBootstrapFormComponent<>("contentType", choiceProvider);
        editForm.add(contentType);
        contentType.required();

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("link");
        linkField.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        linkField.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        editForm.add(linkField);

        FileInputBootstrapFormComponent fileInput = new FileInputBootstrapFormComponent("fileMetadata").maxFiles(1);
        editForm.add(fileInput);
    }

    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                AgriculturalContent model = editForm.getModelObject();
                if (StringUtils.isEmpty(model.getLink()) && model.getFileMetadata().isEmpty()) {
                    feedbackPanel.error(new StringResourceModel("missingLinkOrFile", this, null).getString());
                    target.add(feedbackPanel);
                    redirectToSelf = true;
                } else {
                    jpaService.saveAndFlush(model);
                    cacheService.releaseCache();
                    setResponsePage(listPageClass);
                }
                redirect(target);
            }


            private void redirect(final AjaxRequestTarget target) {
                if (redirectToSelf) {
                    // we need to close the blockUI if it's opened and enable all
                    // the buttons
                    target.appendJavaScript("$.unblockUI();");
                } else if (redirect) {
                    setResponsePage(getResponsePage(), getParameterPage());
                }
            }
        };
    }
}
