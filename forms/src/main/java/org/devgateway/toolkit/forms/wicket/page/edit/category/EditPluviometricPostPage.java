package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
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

import static org.devgateway.toolkit.persistence.dao.DBConstants.MAX_LATITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MAX_LONGITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MIN_LATITUDE;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MIN_LONGITUDE;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
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
        this.listPageClass = ListPluviometricPostPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("label");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        editForm.add(name);

        final TextFieldBootstrapFormComponent<String> latitude = new TextFieldBootstrapFormComponent<>("latitude");
        latitude.required();
        latitude.asDouble();
        latitude.getField().add(RangeValidator.range(MIN_LATITUDE, MAX_LATITUDE));
        editForm.add(latitude);

        final TextFieldBootstrapFormComponent<String> longitude = new TextFieldBootstrapFormComponent<>("longitude");
        longitude.required();
        longitude.asDouble();
        longitude.getField().add(RangeValidator.range(MIN_LONGITUDE, MAX_LONGITUDE));
        editForm.add(longitude);

        final Select2ChoiceBootstrapFormComponent<Department> department =
                new Select2ChoiceBootstrapFormComponent<>("department",
                new GenericPersistableJpaTextChoiceProvider<Department>(departmentService));
        department.required();
        editForm.add(department);

        deleteButton.setVisible(false);
    }
}
