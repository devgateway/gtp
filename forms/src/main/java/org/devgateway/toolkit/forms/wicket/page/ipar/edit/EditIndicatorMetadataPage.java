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

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.ipar.lists.ListIndicatorMetadataFormPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.ipar.categories.Indicator;
import org.devgateway.toolkit.persistence.repository.ipar.category.IndicatorRepository;
import org.devgateway.toolkit.persistence.service.ipar.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.ipar.ReleaseCacheService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

/**
 * Created by Daniel Oliva
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/editIndicatorMetadata")
public class EditIndicatorMetadataPage extends AbstractEditPage<IndicatorMetadata> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private IndicatorMetadataService service;

    @SpringBean
    private IndicatorRepository indicatorRepo;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;

    /**
     * @param parameters
     */
    public EditIndicatorMetadataPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = service;
        this.listPageClass = ListIndicatorMetadataFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> introFrField = new TextFieldBootstrapFormComponent<>("introFr");
        introFrField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(introFrField);
        introFrField.required();

        TextFieldBootstrapFormComponent<String> introField = new TextFieldBootstrapFormComponent<>("intro");
        introField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(introField);

        // List<Indicator> indicators = indicatorRepo.findAllFetchingLocalizedLabels();
        List<Indicator> indicators = indicatorRepo.findAll();
        Indicator currentIndicator = editForm.getModelObject().getIndicator();
        service.findAll().stream().forEach(x -> {
            if (currentIndicator == null || !currentIndicator.getId().equals(x.getIndicator().getId())) {
                indicators.remove(x.getIndicator());
            }
        });
        ImmutableList<Indicator> indicatorList = ImmutableList.copyOf(indicators);

        GenericChoiceProvider<Indicator> choiceProvider = new GenericChoiceProvider<Indicator>(indicatorList) {
            @Override
            public String getDisplayValue(Indicator indicator) {
                return indicator.getLabel();
            }
        };

        Select2ChoiceBootstrapFormComponent<Indicator> indicator =
                new Select2ChoiceBootstrapFormComponent<>("indicator", choiceProvider);
        editForm.add(indicator);
        indicator.setEnabled(false);

        TextFieldBootstrapFormComponent<String> linkField = new TextFieldBootstrapFormComponent<>("ansdLink");
        linkField.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        linkField.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        editForm.add(linkField);
        linkField.setVisibilityAllowed(false);

        TextFieldBootstrapFormComponent<String> sourceField = new TextFieldBootstrapFormComponent<>("source");
        sourceField.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(sourceField);

        deleteButton.setEnabled(false);
        deleteButton.setVisibilityAllowed(false);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                IndicatorMetadata model = editForm.getModelObject();
                redirectToSelf = false;
                if (StringUtils.isBlank(model.getIntro())) {
                    model.setIntro(model.getIntroFr());
                }
                jpaService.saveAndFlush(model);
                markupCacheService.clearAllCaches();
                cacheService.releaseCache();

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