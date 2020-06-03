package org.devgateway.toolkit.persistence.dto.agriculture;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPricesChart {

    private final ProductPricesChartConfig config;
    private final ProductPricesChartFilter filter;
    private final ProductPricesChartData data;

    public ProductPricesChart(ProductPricesChartConfig config,
            ProductPricesChartFilter filter,
            ProductPricesChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public ProductPricesChartConfig getConfig() {
        return config;
    }

    public ProductPricesChartFilter getFilter() {
        return filter;
    }

    public ProductPricesChartData getData() {
        return data;
    }
}
