package org.devgateway.toolkit.forms.wicket.page.edit;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.panel.YearValuePanel;
import org.devgateway.toolkit.forms.wicket.page.lists.ListNationalIndicatorFormPage;
import org.devgateway.toolkit.persistence.dao.NationalIndicator;
import org.devgateway.toolkit.persistence.service.NationalIndicatorService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Daniel Oliva
 */
@AuthorizeInstantiation({SecurityConstants.Roles.ROLE_ADMIN, SecurityConstants.Roles.ROLE_FOCAL_POINT})
@MountPath(value = "/editNationalIndicator")
public class EditNationalIndicatorPage extends AbstractEditPage<NationalIndicator>  {
    private static final long serialVersionUID = -111L;

    @SpringBean
    private NationalIndicatorService service;

    public EditNationalIndicatorPage(PageParameters parameters) {
        super(parameters);
        this.jpaService = service;
        this.listPageClass = ListNationalIndicatorFormPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(name);
        name.required();

        TextFieldBootstrapFormComponent<String> description = new TextFieldBootstrapFormComponent<>("description");
        description.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(description);

        TextFieldBootstrapFormComponent<String> nameFr = new TextFieldBootstrapFormComponent<>("nameFr");
        nameFr.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(nameFr);
        nameFr.required();

        TextFieldBootstrapFormComponent<String> descriptionFr = new TextFieldBootstrapFormComponent<>("descriptionFr");
        descriptionFr.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(descriptionFr);

        TextFieldBootstrapFormComponent<String> source = new TextFieldBootstrapFormComponent<>("source");
        source.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(source);

        TextFieldBootstrapFormComponent<String> link = new TextFieldBootstrapFormComponent<>("link");
        link.getField().add(new UrlValidator(UrlValidator.ALLOW_2_SLASHES));
        link.getField().add(StringValidator.maximumLength(LINK_MAX_LENGTH));
        editForm.add(link);

        TextFieldBootstrapFormComponent<Double> referenceYear = new TextFieldBootstrapFormComponent<>("referenceYear");
        editForm.add(referenceYear);

        TextFieldBootstrapFormComponent<Double> referenceValue =
                new TextFieldBootstrapFormComponent<>("referenceValue");
        editForm.add(referenceValue);

        TextFieldBootstrapFormComponent<Double> targetValue = new TextFieldBootstrapFormComponent<>("targetValue");
        editForm.add(targetValue);

        TextFieldBootstrapFormComponent<String> measure = new TextFieldBootstrapFormComponent<>("measure");
        measure.getField().add(StringValidator.maximumLength(DEFA_MAX_LENGTH));
        editForm.add(measure);
        measure.required();

        editForm.add(new YearValuePanel("yearValue"));
    }
}
