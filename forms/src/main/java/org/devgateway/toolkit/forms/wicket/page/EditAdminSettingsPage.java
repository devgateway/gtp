package org.devgateway.toolkit.forms.wicket.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.form.CheckBoxToggleBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.providers.GenericPersistableJpaTextChoiceProvider;
import org.devgateway.toolkit.persistence.dao.AdminSettings;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.service.category.RiverStationService;
import org.springframework.data.domain.Sort;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

/**
 * @author idobre
 * @since 6/22/16
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/adminsettings")
public class EditAdminSettingsPage extends AbstractEditPage<AdminSettings> {

    private static final long serialVersionUID = 5742724046825803877L;

    private CheckBoxToggleBootstrapFormComponent rebootServer;

    @SpringBean
    private AdminSettingsService adminSettingsService;

    @SpringBean
    private RiverStationService riverStationService;

    @SpringBean
    private ProductService productService;

    @SpringBean
    private ProductTypeService productTypeService;

    public EditAdminSettingsPage(final PageParameters parameters) {
        super(parameters);

        this.jpaService = adminSettingsService;
        setListPage(Homepage.class);

        if (entityId == null) {
            final List<AdminSettings> listSettings = adminSettingsService.findAll();
            // just keep 1 entry for settings
            if (listSettings.size() == 1) {
                entityId = listSettings.get(0).getId();
            }
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editForm.add(new Label("systemTitle", new StringResourceModel("systemTitle", this, null)));

        rebootServer = new CheckBoxToggleBootstrapFormComponent("rebootServer");
        editForm.add(rebootServer);

        editForm.add(new Label("appSettingsTitle", new StringResourceModel("appSettingsTitle", this, null)));

        TextFieldBootstrapFormComponent<Integer> startingYear = new TextFieldBootstrapFormComponent<>("startingYear");
        startingYear.getField().add(RangeValidator.range(1981, 2050));
        startingYear.integer();
        startingYear.required();
        editForm.add(startingYear);

        Select2ChoiceBootstrapFormComponent<RiverStation> defaultRiverStation =
                new Select2ChoiceBootstrapFormComponent<>("defaultRiverStation",
                        new GenericPersistableJpaTextChoiceProvider<>(riverStationService, Sort.by("name")));
        defaultRiverStation.setShowTooltip(true);
        defaultRiverStation.required();
        editForm.add(defaultRiverStation);

        Select2ChoiceBootstrapFormComponent<Product> defaultProduct =
                new Select2ChoiceBootstrapFormComponent<>("defaultProduct",
                        new GenericPersistableJpaTextChoiceProvider<Product>(productService, Sort.by("name")) {
                            @Override
                            public String getDisplayValue(Product choice) {
                                return choice.getLabel() + " (" + choice.getProductType().getLabel() + ")";
                            }
                        });
        defaultProduct.setShowTooltip(true);
        defaultProduct.required();
        editForm.add(defaultProduct);

        Select2ChoiceBootstrapFormComponent<ProductType> defaultProductType =
                new Select2ChoiceBootstrapFormComponent<>("defaultProductType",
                        new GenericPersistableJpaTextChoiceProvider<>(productTypeService, Sort.by("label")));
        defaultProductType.setShowTooltip(true);
        defaultProductType.required();
        editForm.add(defaultProductType);
    }
}
