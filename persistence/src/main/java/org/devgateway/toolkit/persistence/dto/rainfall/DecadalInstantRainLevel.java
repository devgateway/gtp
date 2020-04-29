package org.devgateway.toolkit.persistence.dto.rainfall;

import java.time.Month;

import org.devgateway.toolkit.persistence.dao.Decadal;

/**
 * @author Octavian Ciubotaru
 */
public class DecadalInstantRainLevel extends AbstractRainLevel {

    private final int year;

    private final Month month;

    private final Decadal decadal;

    public DecadalInstantRainLevel(int year, Month month, Decadal decadal, double value) {
        super(value);
        this.year = year;
        this.month = month;
        this.decadal = decadal;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }
}
