package org.devgateway.toolkit.persistence.service.indicator;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface ProductYearlyPricesService extends YearIndicatorGenerator<ProductYearlyPrices, Integer> {

    List<ProductPrice> findPrices(ProductPricesChartFilter filter);

    List<AveragePrice> findPreviousYearAveragePrices(ProductPricesChartFilter filter);

    List<Integer> findYearsWithPrices();

    boolean hasPrices(Integer year, Long productId);

    Long getProductIdWithPrices(Integer year);

    Long getMarketIdWithPrices(Integer year, Long productId);
}
