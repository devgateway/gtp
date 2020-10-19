package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Objects;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.UniquePropertyValidator;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListLivestockDiseasePage;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.service.category.LivestockDiseaseService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR)
@MountPath(value = "/livestock-disease")
public class EditLivestockDiseasePage extends AbstractEditPage<LivestockDisease> {
    private static final long serialVersionUID = 4570103434349810795L;

    @SpringBean
    private LivestockDiseaseService livestockDiseaseService;

    public EditLivestockDiseasePage(PageParameters parameters) {
        super(parameters);

        this.jpaService = livestockDiseaseService;
        setListPage(ListLivestockDiseasePage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> label = new TextFieldBootstrapFormComponent<>("label");
        label.required();
        label.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        label.getField().add(new UniquePropertyValidator<>(livestockDiseaseService,
                Objects.defaultIfNull(entityId, -1L), "label", this));
        editForm.add(label);

        deleteButton.setVisible(false);
    }
}
