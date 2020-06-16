package org.devgateway.toolkit.persistence.dto.rainfall;

import java.time.Month;
import java.util.Comparator;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;

/**
 * @author Octavian Ciubotaru
 */
public class DecadalInstantRainLevel extends AbstractRainLevel implements Comparable<DecadalInstantRainLevel> {

    private static final Comparator<DecadalInstantRainLevel> NATURAL =
            Comparator.comparingInt(DecadalInstantRainLevel::getYear)
            .thenComparing(DecadalInstantRainLevel::getMonth)
            .thenComparing(DecadalInstantRainLevel::getDecadal);

    private final int year;

    private final Month month;

    private final Decadal decadal;

    public DecadalInstantRainLevel(DecadalRainfall drf, double value) {
        this(drf.getYear(), drf.getMonth(), drf.getDecadal(), value);
    }

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

    @Override
    public int compareTo(DecadalInstantRainLevel o) {
        return NATURAL.compare(this, o);
    }
}
