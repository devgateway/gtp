package org.devgateway.toolkit.forms.wicket.page.edit.indicator.market;

import java.io.InputStream;
import java.util.List;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadProductPricesLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.market.ListProductYearlyPricesPage;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.MarketType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.indicator.ProductPriceReader;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.util.JPAUtil;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_MARKET_EDITOR)
@MountPath(value = "/product-prices-import")
public class EditProductYearlyPricesPage extends AbstractExcelImportPage<ProductYearlyPrices> {

    @SpringBean
    private ProductService productService;

    @SpringBean
    private MarketService marketService;

    @SpringBean
    private DepartmentService departmentService;

    @SpringBean
    private ProductYearlyPricesService productYearlyPricesService;

    public EditProductYearlyPricesPage(PageParameters parameters) {
        super(parameters);

        jpaService = productYearlyPricesService;

        setListPage(ListProductYearlyPricesPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        deleteButton.setVisibilityAllowed(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id) {
        return new DownloadProductPricesLink(id, editForm.getModel());
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        ProductYearlyPrices productYearlyPrices = editForm.getModelObject();

        ProductType productType = productYearlyPrices.getProductType();

        List<Product> products = productService.findByProductType(productType);

        String marketTypeName = MarketType.MARKET_TYPE_BY_PRODUCT_TYPE.get(productType.getName());

        List<Department> departments = departmentService.findAll();

        List<Market> markets = marketService.findByMarketTypeName(marketTypeName);

        boolean productsOnSeparateRows = productType.areProductsOnSeparateRows();

        ProductPriceReader reader = new ProductPriceReader(products, departments, markets, productsOnSeparateRows);

        ProductYearlyPrices newEntity = reader.read(productYearlyPrices.getYear(), inputStream);

        JPAUtil.mergeSortedSet(newEntity.getPrices(), productYearlyPrices.getPrices(),
                productYearlyPrices::addPrice,
                (oldItem, newItem) -> oldItem.setPrice(newItem.getPrice()));

        JPAUtil.mergeSortedSet(newEntity.getQuantities(), productYearlyPrices.getQuantities(),
                productYearlyPrices::addQuantity,
                (oldItem, newItem) -> oldItem.setQuantity(newItem.getQuantity()));
    }
}
