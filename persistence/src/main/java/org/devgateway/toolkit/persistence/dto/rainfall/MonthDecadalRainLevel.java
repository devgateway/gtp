package org.devgateway.toolkit.persistence.dto.rainfall;

import java.time.Month;

import org.devgateway.toolkit.persistence.dao.Decadal;

/**
 * @author Octavian Ciubotaru
 */
public class MonthDecadalRainLevel extends AbstractRainLevel {

    private final Month month;

    private final Decadal decadal;

    public MonthDecadalRainLevel(Month month, Decadal decadal, double value) {
        super(value);
        this.month = month;
        this.decadal = decadal;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }
}
