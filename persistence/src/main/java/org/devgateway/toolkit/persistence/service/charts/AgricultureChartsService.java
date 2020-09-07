package org.devgateway.toolkit.persistence.service.charts;

import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureChartsData;
import org.devgateway.toolkit.persistence.dto.agriculture.AgricultureConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartConfig;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartData;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface AgricultureChartsService {

    AgricultureConfig getAgricultureConfig();

    AgricultureChartsData getAgricultureChartsData();

    ProductPricesChartConfig getProductPricesChartConfig();

    ProductPricesChartData getProductPricesChartData(ProductPricesChartFilter filter);

    ProductQuantitiesChartConfig getProductQuantitiesChartConfig();

    ProductQuantitiesChartData getProductQuantitiesChartData(ProductQuantitiesChartFilter filter);
}
