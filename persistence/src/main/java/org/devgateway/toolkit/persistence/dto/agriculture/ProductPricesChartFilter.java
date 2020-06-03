package org.devgateway.toolkit.persistence.dto.agriculture;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPricesChartFilter {

    @NotNull
    private final Integer year;

    @NotNull
    private final Long productId;

    @NotNull
    private final Long marketId;

    @JsonCreator
    public ProductPricesChartFilter(@NotNull Integer year, @NotNull Long productId,
            @NotNull Long marketId) {
        this.year = year;
        this.productId = productId;
        this.marketId = marketId;
    }

    public Integer getYear() {
        return year;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMarketId() {
        return marketId;
    }
}
