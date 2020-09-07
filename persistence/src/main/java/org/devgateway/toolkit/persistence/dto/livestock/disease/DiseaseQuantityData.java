package org.devgateway.toolkit.persistence.dto.livestock.disease;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;

import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityData {

    private final List<DiseaseQuantity> quantities;

    public DiseaseQuantityData(List<DiseaseQuantity> quantities) {
        this.quantities = quantities;
    }

    public List<DiseaseQuantity> getQuantities() {
        return quantities;
    }
}
