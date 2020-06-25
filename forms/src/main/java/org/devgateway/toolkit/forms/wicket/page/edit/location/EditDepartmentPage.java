package org.devgateway.toolkit.forms.wicket.page.edit.location;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.service.location.RegionService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath("/department")
public class EditDepartmentPage extends EditAbstractLocation<Department> {
    private static final long serialVersionUID = 7204796706652802434L;

    @SpringBean
    private RegionService regionService;

    @SpringBean
    private DepartmentService departmentService;

    public EditDepartmentPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = departmentService;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final Select2ChoiceBootstrapFormComponent<Region> region = new Select2ChoiceBootstrapFormComponent<>("region",
                new GenericPersistableJpaTextChoiceProvider<>(regionService));
        region.required();
        region.setEnabled(false);
        editForm.add(region);

        name.getField().add(new UniqueDepartmentValidator());
    }

    private class UniqueDepartmentValidator implements IValidator<String> {

        @Override
        public void validate(IValidatable<String> validatable) {
            Long id = editForm.getModelObject().getId();
            if (departmentService.exists(validatable.getValue(), id)) {
                ValidationError error = new ValidationError(this);
                error.setVariable("department", validatable.getValue());
                validatable.error(error);
            }
        }
    }
}
