package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Objects;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.UniquePropertyValidator;
import org.devgateway.toolkit.forms.wicket.components.form.DegreeBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListPluviometricPostPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_EDITOR)
@MountPath(value = "/pluviometric-post")
public class EditPluviometricPostPage extends AbstractEditPage<PluviometricPost> {
    private static final long serialVersionUID = 8030297265857072575L;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    @SpringBean
    private DepartmentService departmentService;

    public EditPluviometricPostPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = pluviometricPostService;
        setListPage(ListPluviometricPostPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("label");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        name.getField().add(new UniquePropertyValidator<PluviometricPost, String>(pluviometricPostService,
                Objects.defaultIfNull(entityId, -1L), "label", this));
        editForm.add(name);

        DegreeBootstrapFormComponent latitude = new DegreeBootstrapFormComponent("latitude");
        latitude.required();
        latitude.getField().add(WebConstants.Validators.LATITUDE_RANGE);
        editForm.add(latitude);

        DegreeBootstrapFormComponent longitude = new DegreeBootstrapFormComponent("longitude");
        longitude.required();
        longitude.getField().add(WebConstants.Validators.LONGITUDE_RANGE);
        editForm.add(longitude);

        final Select2ChoiceBootstrapFormComponent<Department> department =
                new Select2ChoiceBootstrapFormComponent<>("department",
                new GenericPersistableJpaTextChoiceProvider<Department>(departmentService));
        department.required();
        editForm.add(department);

        deleteButton.setVisible(false);
    }
}
