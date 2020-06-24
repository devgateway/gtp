package org.devgateway.toolkit.persistence.dto.agriculture;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;

/**
 * @author Octavian Ciubotaru
 */
public class ProductQuantitiesChartData {

    private final List<ProductQuantity> quantities;

    public ProductQuantitiesChartData(List<ProductQuantity> quantities) {
        this.quantities = quantities;
    }

    public List<ProductQuantity> getQuantities() {
        return quantities;
    }
}
