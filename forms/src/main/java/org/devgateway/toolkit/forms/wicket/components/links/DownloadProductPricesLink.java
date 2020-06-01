package org.devgateway.toolkit.forms.wicket.components.links;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.util.ProductTypeUtil;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.indicator.ProductPriceWriter;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadProductPricesLink extends AbstractGeneratedExcelDownloadLink<ProductYearlyPrices> {

    @SpringBean
    private MarketService marketService;

    @SpringBean
    private ProductService productService;

    public DownloadProductPricesLink(String id, IModel<ProductYearlyPrices> model) {
        super(id, model);
    }

    @Override
    protected String getFileName() {
        ProductYearlyPrices entity = getModelObject();
        return String.format("%s - %s.xlsx", entity.getProductType().getLabel(),
                entity.getYear());
    }

    @Override
    protected boolean isEmpty() {
        return getModelObject().getPrices().isEmpty();
    }

    @Override
    protected void generate(OutputStream outputStream) throws IOException {
        ProductYearlyPrices entity = getModelObject();

        List<Product> products = productService.findByProductType(entity.getProductType());

        SortedSet<ProductPrice> prices;
        if (entity.getPrices().isEmpty()) {
            prices = getExamplePrices(products);
        } else {
            prices = entity.getPrices();
        }

        boolean productsOnSeparateRows =
                ProductTypeUtil.areProductsOnSeparateRows(entity.getProductType());

        ProductPriceWriter writer = new ProductPriceWriter(products, productsOnSeparateRows);

        writer.write(prices, entity.getYear(), outputStream);
    }

    private SortedSet<ProductPrice> getExamplePrices(List<Product> products) {
        Product product = products.get(0);
        PriceType priceType = product.getPriceTypes().get(0);
        MonthDay monthDay = MonthDay.of(Month.JANUARY, 1);

        List<Market> markets = marketService.findAll();

        return markets.stream()
                .map(m -> new ProductPrice(product, m, monthDay, priceType, null))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
