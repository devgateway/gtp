package org.devgateway.toolkit.persistence.dto.agriculture;

import org.devgateway.toolkit.persistence.dto.CommonConfig;

/**
 * @author Octavian Ciubotaru
 */
public class AgricultureChartsData {

    private final CommonConfig commonConfig;

    private final AgricultureConfig agricultureConfig;

    private final ProductPricesChart productPricesChart;

    private final ProductQuantitiesChart productQuantitiesChart;

    public AgricultureChartsData(CommonConfig commonConfig,
            AgricultureConfig agricultureConfig,
            ProductPricesChart productPricesChart,
            ProductQuantitiesChart productQuantitiesChart) {
        this.commonConfig = commonConfig;
        this.agricultureConfig = agricultureConfig;
        this.productPricesChart = productPricesChart;
        this.productQuantitiesChart = productQuantitiesChart;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }

    public AgricultureConfig getAgricultureConfig() {
        return agricultureConfig;
    }

    public ProductPricesChart getProductPricesChart() {
        return productPricesChart;
    }

    public ProductQuantitiesChart getProductQuantitiesChart() {
        return productQuantitiesChart;
    }
}
