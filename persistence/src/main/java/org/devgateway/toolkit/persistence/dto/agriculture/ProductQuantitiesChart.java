package org.devgateway.toolkit.persistence.dto.agriculture;

/**
 * @author Octavian Ciubotaru
 */
public class ProductQuantitiesChart {

    private final ProductQuantitiesChartConfig config;
    private final ProductQuantitiesChartFilter filter;
    private final ProductQuantitiesChartData data;

    public ProductQuantitiesChart(ProductQuantitiesChartConfig config,
            ProductQuantitiesChartFilter filter,
            ProductQuantitiesChartData data) {
        this.config = config;
        this.filter = filter;
        this.data = data;
    }

    public ProductQuantitiesChartConfig getConfig() {
        return config;
    }

    public ProductQuantitiesChartFilter getFilter() {
        return filter;
    }

    public ProductQuantitiesChartData getData() {
        return data;
    }
}
