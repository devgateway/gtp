package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceAndAvailabilityProgress extends DatasetProgress {

    private final List<ProductTypeYearStatus> productTypeYearStatuses;

    public ProductPriceAndAvailabilityProgress(List<ProductTypeYearStatus> productTypeYearStatuses) {
        super(IndicatorType.MARKET);
        this.productTypeYearStatuses = productTypeYearStatuses;
    }

    public List<ProductTypeYearStatus> getProductTypeYearStatuses() {
        return productTypeYearStatuses;
    }

    @Override
    protected Stream<DataEntryStatus> statusStream() {
        return productTypeYearStatuses.stream().flatMap(s -> s.getStatuses().values().stream());
    }
}
