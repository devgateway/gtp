package org.devgateway.toolkit.forms.wicket.page.edit.category;

import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Collection;

import com.google.common.collect.ImmutableMap;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.CheckBoxYesNoToggleBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.DegreeBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2MultiChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListMarketsPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.MarketTypeService;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.util.MarketDaysUtil;
import org.springframework.data.domain.Sort;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Response;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/market")
public class EditMarketPage extends AbstractEditPage<Market> {

    @SpringBean
    private MarketService marketService;

    @SpringBean
    private MarketTypeService marketTypeService;

    @SpringBean
    private DepartmentService departmentService;

    private IModel<Boolean> permanentModel;

    public EditMarketPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = marketService;
        setListPage(ListMarketsPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Select2ChoiceBootstrapFormComponent<Department> department =
                new Select2ChoiceBootstrapFormComponent<>("department",
                        new GenericPersistableJpaTextChoiceProvider<>(departmentService, Sort.by("name")));
        department.required();
        editForm.add(department);

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        editForm.add(name);

        editForm.add(new UniqueMarketNameValidator(department.getField(), name.getField()));

        Select2ChoiceBootstrapFormComponent<MarketType> marketType =
                new Select2ChoiceBootstrapFormComponent<>("type",
                        new GenericPersistableJpaTextChoiceProvider<>(marketTypeService));
        marketType.required();
        editForm.add(marketType);

        permanentModel = Model.of(editForm.getModelObject().getMarketDays().equals(MarketDaysUtil.ALL_DAYS));
        CheckBoxYesNoToggleBootstrapFormComponent permanent =
                new CheckBoxYesNoToggleBootstrapFormComponent("permanent", permanentModel);
        permanent.required();
        editForm.add(permanent);

        DaysModel daysModel = new DaysModel(LambdaModel.of(editForm.getModel(),
                Market::getMarketDays, Market::setMarketDays));

        Select2MultiChoiceBootstrapFormComponent<DayOfWeek> marketDays =
                new Select2MultiChoiceBootstrapFormComponent<>("marketDays", new DaysChoiceProvider(), daysModel);
        marketDays.required();
        editForm.add(marketDays);

        permanent.addReverseBoundComponent(marketDays);

        DegreeBootstrapFormComponent latitude = new DegreeBootstrapFormComponent("latitude");
        latitude.required();
        latitude.getField().add(WebConstants.Validators.LATITUDE_RANGE);
        editForm.add(latitude);

        DegreeBootstrapFormComponent longitude = new DegreeBootstrapFormComponent("longitude");
        longitude.required();
        longitude.getField().add(WebConstants.Validators.LONGITUDE_RANGE);
        editForm.add(longitude);

        editForm.add(new UniqueLocationValidator(latitude.getField(), longitude.getField()));

        deleteButton.setVisible(false);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton(String id, IModel<String> labelModel) {
        return new MarketSaveEditPageButton(id, labelModel);
    }

    private class MarketSaveEditPageButton extends SaveEditPageButton {

        MarketSaveEditPageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target) {
            if (permanentModel.getObject()) {
                editForm.getModelObject().setMarketDays(MarketDaysUtil.ALL_DAYS);
            }

            super.onSubmit(target);
        }
    }

    private static class DaysModel implements IModel<Collection<DayOfWeek>> {

        private final IModel<Integer> targetModel;

        DaysModel(IModel<Integer> targetModel) {
            this.targetModel = targetModel;
        }

        @Override
        public Collection<DayOfWeek> getObject() {
            return MarketDaysUtil.unpack(targetModel.getObject());
        }

        @Override
        public void setObject(Collection<DayOfWeek> days) {
            targetModel.setObject(MarketDaysUtil.pack(days));
        }

        @Override
        public void detach() {
            targetModel.detach();
        }
    }

    private static class DaysChoiceProvider extends ChoiceProvider<DayOfWeek> {

        @Override
        public String getDisplayValue(DayOfWeek object) {
            return object.getDisplayName(TextStyle.FULL_STANDALONE, Session.get().getLocale());
        }

        @Override
        public String getIdValue(DayOfWeek object) {
            return String.valueOf(object.getValue());
        }

        @Override
        public void query(String term, int page, Response<DayOfWeek> response) {
            String lowerTerm = term != null ? term.toLowerCase() : null;
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                if (lowerTerm == null || getDisplayValue(dayOfWeek).toLowerCase().contains(lowerTerm)) {
                    response.add(dayOfWeek);
                }
            }
            response.setHasMore(false);
        }

        @Override
        public Collection<DayOfWeek> toChoices(Collection<String> ids) {
            return ids.stream().map(id -> DayOfWeek.of(Integer.parseInt(id))).collect(toList());
        }
    }

    private class UniqueMarketNameValidator extends AbstractFormValidator {

        private final FormComponent<?>[] dependentFormComponents;
        private final FormComponent<String> nameFC;

        UniqueMarketNameValidator(FormComponent<?> department, FormComponent<String> name) {
            nameFC = name;
            dependentFormComponents = new FormComponent[]{department, name};
        }

        @Override
        public FormComponent<?>[] getDependentFormComponents() {
            return dependentFormComponents;
        }

        @Override
        public void validate(Form<?> form) {
            String name = editForm.getModelObject().getName();
            Department department = editForm.getModelObject().getDepartment();
            Long id = editForm.getModelObject().getId();
            if (marketService.exists(department, name, id)) {
                error(nameFC, ImmutableMap.of("department", department.getName(), "marketName", name));
            }
        }
    }

    private class UniqueLocationValidator extends AbstractFormValidator {

        private final FormComponent<?> latitudeFc;
        private final FormComponent<?>[] dependentFormComponents;

        UniqueLocationValidator(FormComponent<?> latitude, FormComponent<?> longitude) {
            latitudeFc = latitude;
            dependentFormComponents = new FormComponent[]{latitude, longitude};
        }

        @Override
        public FormComponent<?>[] getDependentFormComponents() {
            return dependentFormComponents;
        }

        @Override
        public void validate(Form<?> form) {
            Double latitude = editForm.getModelObject().getLatitude();
            Double longitude = editForm.getModelObject().getLongitude();
            Long id = editForm.getModelObject().getId();
            if (marketService.exists(latitude, longitude, id)) {
                error(latitudeFc);
            }
        }
    }
}
