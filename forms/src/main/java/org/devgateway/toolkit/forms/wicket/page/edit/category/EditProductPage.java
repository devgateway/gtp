package org.devgateway.toolkit.forms.wicket.page.edit.category;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Objects;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.validators.UniquePropertyValidator;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2MultiChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListProductsPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.categories.MeasurementUnit;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.service.category.MeasurementUnitService;
import org.devgateway.toolkit.persistence.service.category.PriceTypeService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
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

    public EditProductPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = productService;
        setListPage(ListProductsPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Select2ChoiceBootstrapFormComponent<ProductType> productType =
                new Select2ChoiceBootstrapFormComponent<>("productType",
                        new GenericPersistableJpaTextChoiceProvider<>(productTypeService));
        productType.required();
        editForm.add(productType);

        TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        name.getField().add(new UniquePropertyValidator<>(productService,
                Objects.defaultIfNull(entityId, -1L), "name", this));
        editForm.add(name);

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
    }
}
