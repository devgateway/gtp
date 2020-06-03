package org.devgateway.toolkit.persistence.dto.agriculture;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;

/**
 * @author Octavian Ciubotaru
 */
public class AveragePrice {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("priceTypeId")
    private final PriceType priceType;

    private final Double average;

    public AveragePrice(PriceType priceType, Double average) {
        this.priceType = priceType;
        this.average = average;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public Double getAverage() {
        return average;
    }
}
