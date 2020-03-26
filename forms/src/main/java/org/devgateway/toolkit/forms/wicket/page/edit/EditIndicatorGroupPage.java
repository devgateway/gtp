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

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.util.MarkupCacheService;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.ipar.lists.ListIndicatorGroupPage;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndicatorGroup;
import org.devgateway.toolkit.persistence.dao.ipar.categories.LocalizedCategoryLabel;
import org.devgateway.toolkit.persistence.service.ipar.ReleaseCacheService;
import org.devgateway.toolkit.persistence.service.category.IndicatorGroupService;
import org.devgateway.toolkit.persistence.util.Constants;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;

@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/editIndicatorGroup")
public class EditIndicatorGroupPage extends AbstractEditPage<IndicatorGroup> {

    private static final long serialVersionUID = -6069250112046118104L;

    @SpringBean
    protected IndicatorGroupService service;

    @SpringBean
    protected MarkupCacheService markupCacheService;

    @SpringBean
    private ReleaseCacheService cacheService;

    public EditIndicatorGroupPage(final PageParameters parameters) {
        super(parameters);
        this.jpaService = service;
        this.listPageClass = ListIndicatorGroupPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("label");
        name.required();
        name.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(name);

        final TextFieldBootstrapFormComponent<String> description =
                new TextFieldBootstrapFormComponent<>("description");
        description.required();
        description.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(description);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton() {
        return new SaveEditPageButton("save", new StringResourceModel("save", this, null)) {
            private static final long serialVersionUID = 5214537995514151323L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                IndicatorGroup model = editForm.getModelObject();
                redirectToSelf = false;
                if (StringUtils.isNotBlank(model.getDescription())) {
                    LocalizedCategoryLabel labelFr = null;
                    if (model.getLocalizedLabels() != null && model.getLocalizedLabels().size() > 0) {
                        labelFr = model.getLocalizedLabels().get(0);

                    } else {
                        labelFr = new LocalizedCategoryLabel();
                        labelFr.setLanguage(Constants.LANG_FR);
                        labelFr.setCategory(model);
                        List<LocalizedCategoryLabel> list = new ArrayList<>();
                        list.add(labelFr);
                        model.setLocalizedLabels(list);
                    }
                    labelFr.setLabel(model.getDescription());
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
