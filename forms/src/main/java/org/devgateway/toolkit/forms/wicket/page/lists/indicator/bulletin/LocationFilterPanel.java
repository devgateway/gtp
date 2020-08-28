package org.devgateway.toolkit.forms.wicket.page.lists.indicator.bulletin;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nadejda Mandrescu
 */
public class LocationFilterPanel<T extends GenericPersistable & Serializable> extends Panel {
    private static final long serialVersionUID = 8847375272796270729L;

    @SpringBean
    private DepartmentService departmentService;

    private final IModel<Long> departmentIdFilterStateModel;
    private IModel<Department> depModel;


    public LocationFilterPanel(String id, ResettingFilterForm<? extends JpaFilterState<T>> filterForm) {
        super(id);
        this.departmentIdFilterStateModel = new PropertyModel<>(filterForm.getDefaultModel(), "departmentId");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Department defaultDepartment = new Department(getString("national"));
        List<Department> departmentList = Stream.of(defaultDepartment).collect(Collectors.toList());
        departmentList.addAll(this.departmentService.findAll(Sort.by("name")));

        if (departmentIdFilterStateModel.getObject() != null) {
            defaultDepartment = departmentService.findById(departmentIdFilterStateModel.getObject())
                    .orElse(defaultDepartment);
        }
        depModel = Model.of(defaultDepartment);

        Select2ChoiceBootstrapFormComponent<Department> depChoice = new Select2ChoiceBootstrapFormComponent<Department>(
                "department", new GenericChoiceProvider<>(departmentList),
                new IModel<Department>() {
                    private static final long serialVersionUID = -7704243780327134580L;

                    @Override
                    public Department getObject() {
                        return depModel.getObject();
                    }

                    @Override
                    public void setObject(Department department) {
                        depModel = Model.of(department);
                        departmentIdFilterStateModel.setObject(department.getId());
                    }
                }) {
            private static final long serialVersionUID = 363201404656189132L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
                field.getSettings().setAllowClear(false);
            }
        };
        depChoice.getField().add(AttributeModifier.replace("onchange", "this.form.submit();"));
        depChoice.required();
        add(depChoice);
    }
}
