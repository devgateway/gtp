package org.devgateway.toolkit.persistence.service.indicator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartFilter;

/**
 * @author Octavian Ciubotaru
 */
public interface ProductYearlyPricesService extends YearIndicatorGenerator<ProductYearlyPrices, Integer> {

    List<ProductPrice> findPrices(ProductPricesChartFilter filter);

    List<AveragePrice> findPreviousYearAveragePrices(ProductPricesChartFilter filter);

    List<Integer> findYearsWithPrices();

    List<Integer> findYearsWithQuantities();

    boolean hasPrices(Integer year, Long productId);

    boolean hasPricesForProductAndPriceType(Long productId, Collection<Long> priceTypeIds);

    Long getProductIdWithPrices(Integer year);

    Long getMarketIdWithPrices(Integer year, Long productId);

    ProductYearlyPrices getExample(Integer year, ProductType productType);

    void export(ProductYearlyPrices productYearlyPrices, OutputStream outputStream) throws IOException;

    Long getMarketIdWithQuantities(Integer year, Long productTypeId);

    Long getProductTypeIdWithQuantities(Integer year);

    List<ProductQuantity> findQuantities(ProductQuantitiesChartFilter filter);
}
