package org.devgateway.toolkit.persistence.dto.agriculture;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPricesChartData {

    private final List<ProductPrice> prices;

    private final List<AveragePrice> previousYearAverages;

    public ProductPricesChartData(List<ProductPrice> prices,
            List<AveragePrice> previousYearAverages) {
        this.prices = prices;
        this.previousYearAverages = previousYearAverages;
    }

    public List<ProductPrice> getPrices() {
        return prices;
    }

    public List<AveragePrice> getPreviousYearAverages() {
        return previousYearAverages;
    }
}
