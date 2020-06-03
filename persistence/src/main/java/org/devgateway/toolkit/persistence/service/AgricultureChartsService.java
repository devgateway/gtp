package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface AgricultureChartsService {

    AgricultureConfig getAgricultureConfig();

    AgricultureChartsData getAgricultureChartsData();

    ProductPricesChartConfig getProductPricesChartConfig();

    ProductPricesChartData getProductPricesChartData(ProductPricesChartFilter filter);
}
