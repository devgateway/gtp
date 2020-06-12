package org.devgateway.toolkit.forms.wicket.page.edit.category;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableMap;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.TextContentModal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2MultiChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListProductsPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.service.category.MeasurementUnitService;
import org.devgateway.toolkit.persistence.service.category.PriceTypeService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/product")
public class EditProductPage extends AbstractEditPage<Product> {

    @SpringBean
    private ProductTypeService productTypeService;

    @SpringBean
    private ProductService productService;

    @SpringBean
    private MeasurementUnitService measurementUnitService;

    @SpringBean
    private PriceTypeService priceTypeService;

    @SpringBean
    private ProductYearlyPricesService productYearlyPricesService;

    private List<Long> priceTypeIds;

    private SaveEditPageButton savedSaveEditPageButton;
    private TextContentModal confirmationModal;

    public EditProductPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = productService;
        setListPage(ListProductsPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        priceTypeIds = editForm.getModelObject().getPriceTypes().stream()
                .map(AbstractPersistable::getId)
                .collect(toList());

        Select2ChoiceBootstrapFormComponent<ProductType> productType =
                new Select2ChoiceBootstrapFormComponent<>("productType",
                        new GenericPersistableJpaTextChoiceProvider<>(productTypeService));
        productType.required();
        editForm.add(productType);

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        editForm.add(name);

        editForm.add(new UniqueProductNameValidator(productType.getField(), name.getField()));

        Select2ChoiceBootstrapFormComponent<MeasurementUnit> unit =
                new Select2ChoiceBootstrapFormComponent<>("unit",
                        new GenericPersistableJpaTextChoiceProvider<>(measurementUnitService));
        unit.required();
        editForm.add(unit);

        Select2MultiChoiceBootstrapFormComponent<PriceType> priceTypes =
                new Select2MultiChoiceBootstrapFormComponent<>("priceTypes",
                        new GenericPersistableJpaTextChoiceProvider<>(priceTypeService));
        priceTypes.required();
        editForm.add(priceTypes);

        deleteButton.setVisible(false);

        savedSaveEditPageButton = super.getSaveEditPageButton("button", Model.of("Yes"));

        confirmationModal = new TextContentModal("confirmationModal", Model.of());
        confirmationModal.setOutputMarkupId(true);
        confirmationModal.header(new StringResourceModel("confirmationModal.header", this, null));
        confirmationModal.show(false).setFadeIn(true).setUseKeyboard(true).size(Modal.Size.Medium);
        confirmationModal.addButton(savedSaveEditPageButton);
        confirmationModal.addButton(new BootstrapAjaxLink<String>("button", Buttons.Type.Danger) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                confirmationModal.close(target);
            }
        }.setLabel(Model.of("Cancel")));
        editForm.add(confirmationModal);
    }

    @Override
    public SaveEditPageButton getSaveEditPageButton(String id, IModel<String> labelModel) {
        return new SaveEditPageButton(id, labelModel) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                Product product = editForm.getModelObject();

                List<Long> removed = new ArrayList<>(priceTypeIds);
                product.getPriceTypes().forEach(pt -> removed.remove(pt.getId()));

                if (!removed.isEmpty()
                        && productYearlyPricesService.hasPricesForProductAndPriceType(product.getId(), removed)) {

                    String removedNames = priceTypeService.findByIds(removed).stream()
                            .map(Category::getLabel)
                            .collect(joining(", "));

                    confirmationModal.setModelObject(new StringResourceModel("confirmationModal.content", this)
                            .setParameters(product.getName(), removedNames)
                            .getString());

                    confirmationModal.show(target);

                    target.add(confirmationModal);
                } else {
                    super.onSubmit(target);
                }
            }
        };
    }

    private class UniqueProductNameValidator extends AbstractFormValidator {

        private final FormComponent<?>[] dependentFormComponents;
        private final FormComponent<String> nameFC;

        UniqueProductNameValidator(FormComponent<?> productType, FormComponent<String> name) {
            nameFC = name;
            dependentFormComponents = new FormComponent[]{productType, name};
        }

        @Override
        public FormComponent<?>[] getDependentFormComponents() {
            return dependentFormComponents;
        }

        @Override
        public void validate(Form<?> form) {
            String name = editForm.getModelObject().getName();
            ProductType productType = editForm.getModelObject().getProductType();
            Long id = editForm.getModelObject().getId();
            if (productService.exists(productType, name, id)) {
                error(nameFC, ImmutableMap.of("productType", productType.getName(), "productName", name));
            }
        }
    }
}
