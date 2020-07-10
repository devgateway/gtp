package org.devgateway.toolkit.forms.wicket.page.lists.category;

import static java.util.stream.Collectors.toList;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectMultiFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.ProductFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.category.EditProductPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/products")
public class ListProductsPage extends AbstractListPage<Product> {

    @SpringBean
    private ProductService productService;

    @SpringBean
    private ProductTypeService productTypeService;

    public ListProductsPage(PageParameters parameters) {
        super(parameters, false);

        this.jpaService = productService;
        this.editPageClass = EditProductPage.class;

        List<String> productTypeNames = productTypeService.findAll().stream()
                .map(ProductType::getLabel)
                .sorted()
                .collect(toList());

        List<String> productNames = productService.findAll().stream()
                .map(Product::getName)
                .sorted()
                .collect(toList());

        columns.add(new SelectMultiFilteredBootstrapPropertyColumn<>(new StringResourceModel("productType"),
                "productType", "productType", "productTypes", new ListModel<>(productTypeNames)));
        columns.add(new SelectMultiFilteredBootstrapPropertyColumn<>(new StringResourceModel("name"),
                "name", "name", "products", new ListModel<>(productNames)));
        columns.add(new PropertyColumn<>(new StringResourceModel("unit"), "unit", "unit"));
        // columns.add(new PropertyColumn<>(new StringResourceModel("priceTypes"), "priceTypes"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("productType.label", SortOrder.ASCENDING);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<Product>> getFilterForm(String id,
            IFilterStateLocator locator, AjaxFallbackBootstrapDataTable<Product, String> dataTable) {
        return new BookmarkableResettingFilterForm<ProductFilterState>(id, locator, dataTable, ListProductsPage.class,
                getPageParameters());
    }

    @Override
    public JpaFilterState<Product> newFilterState() {
        return new ProductFilterState();
    }
}
