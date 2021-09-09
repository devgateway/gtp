package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.categories.ProductType;

import java.time.Month;
import java.util.Map;

/**
 * @author Octavian Ciubotaru
 */
public class ProductTypeYearStatus {

    private final ProductType productType;

    private final Map<Month, DataEntryStatus> statuses;

    public ProductTypeYearStatus(ProductType productType,
            Map<Month, DataEntryStatus> statuses) {
        this.productType = productType;
        this.statuses = statuses;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Map<Month, DataEntryStatus> getStatuses() {
        return statuses;
    }
}
