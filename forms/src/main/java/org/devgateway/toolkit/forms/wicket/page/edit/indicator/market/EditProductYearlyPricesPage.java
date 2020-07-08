package org.devgateway.toolkit.forms.wicket.page.edit.indicator.market;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadProductPricesLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.market.ListProductYearlyPricesPage;
import org.devgateway.toolkit.persistence.dao.categories.Market;
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

import java.io.InputStream;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_MARKET_EDITOR)
@MountPath(value = "/product-prices-import")
public class EditProductYearlyPricesPage extends AbstractExcelImportPage<ProductYearlyPrices> {
    private static final long serialVersionUID = -7227946818986428498L;

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

        ProductType productType = editForm.getModelObject().getProductType();
        boolean hasPrices = productService.existsByProductType(productType);

        Label label = new Label("warning", new StringResourceModel("noProducts", this, editForm.getModel()));
        label.setVisibilityAllowed(!hasPrices);
        editForm.add(label);

        upload.setEnabled(hasPrices);
        download.setEnabled(hasPrices);
        downloadTemplate.setEnabled(hasPrices);
        saveButton.setEnabled(hasPrices);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadProductPricesLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        ProductYearlyPrices productYearlyPrices = editForm.getModelObject();

        ProductType productType = productYearlyPrices.getProductType();

        List<Product> products = productService.findByProductType(productType);

        List<Department> departments = departmentService.findAll();

        List<Market> markets = marketService.findByMarketType(productType.getMarketType());

        boolean productsOnSeparateRows = productType.areProductsOnSeparateRows();

        ProductPriceReader reader = new ProductPriceReader(productYearlyPrices.getYear(), products, departments,
                markets, productsOnSeparateRows);

        ProductYearlyPrices newEntity = reader.read(inputStream);

        JPAUtil.mergeSortedSet(newEntity.getPrices(), productYearlyPrices.getPrices(),
                productYearlyPrices::addPrice,
                (oldItem, newItem) -> oldItem.setPrice(newItem.getPrice()));

        JPAUtil.mergeSortedSet(newEntity.getQuantities(), productYearlyPrices.getQuantities(),
                productYearlyPrices::addQuantity,
                (oldItem, newItem) -> oldItem.setQuantity(newItem.getQuantity()));
    }
}
