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
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextAreaFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.lists.ListIndicatorMetadataFormPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.categories.Indicator;
import org.devgateway.toolkit.persistence.repository.category.IndicatorRepository;
import org.devgateway.toolkit.persistence.service.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.TextSearchableAdapter;
import org.wicketstuff.annotation.mount.MountPath;

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

        TextFieldBootstrapFormComponent<String> introField = new TextFieldBootstrapFormComponent<>("intro");
        editForm.add(introField);
        introField.required();

        TextFieldBootstrapFormComponent<String> introFrField = new TextFieldBootstrapFormComponent<>("introFr");
        editForm.add(introFrField);
        introField.required();

        Select2ChoiceBootstrapFormComponent<Indicator> indicator =
                new Select2ChoiceBootstrapFormComponent<>("indicator",
                        new GenericPersistableJpaTextChoiceProvider<>(new TextSearchableAdapter<>(indicatorRepo)));
        editForm.add(indicator);

        TextAreaFieldBootstrapFormComponent<String> linkField = new TextAreaFieldBootstrapFormComponent<>("ansdLink");
        editForm.add(linkField);

        TextAreaFieldBootstrapFormComponent<String> sourceField = new TextAreaFieldBootstrapFormComponent<>("source");
        editForm.add(sourceField);
    }

}
