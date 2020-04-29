package org.devgateway.toolkit.persistence.dto.rainfall;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractRainLevel {

    private final BigDecimal value;

    public AbstractRainLevel(double value) {
        this.value = new BigDecimal(value).setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getValue() {
        return value;
    }
}
