package org.devgateway.toolkit.persistence.dto.agriculture;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Octavian Ciubotaru
 */
public class ProductQuantitiesChartFilter {

    @NotNull
    private final int year;

    @NotNull
    private final Long productTypeId;

    @NotNull
    private final Long marketId;

    @JsonCreator
    public ProductQuantitiesChartFilter(@NotNull int year, @NotNull Long productTypeId,
            @NotNull Long marketId) {
        this.year = year;
        this.productTypeId = productTypeId;
        this.marketId = marketId;
    }

    public int getYear() {
        return year;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public Long getMarketId() {
        return marketId;
    }
}
