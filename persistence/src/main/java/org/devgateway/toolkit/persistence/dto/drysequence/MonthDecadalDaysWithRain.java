package org.devgateway.toolkit.persistence.dto.drysequence;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.StationDecadalRainfall;

import java.time.Month;
import java.time.YearMonth;
import java.util.Comparator;

/**
 * @author Octavian Ciubotaru
 */
public class MonthDecadalDaysWithRain implements Comparable<MonthDecadalDaysWithRain> {

    private static final Comparator<MonthDecadalDaysWithRain> NATURAL =
            Comparator.comparing(MonthDecadalDaysWithRain::getMonth)
            .thenComparing(MonthDecadalDaysWithRain::getDecadal);

    private final Month month;
    private final Decadal decadal;
    private final int daysWithRain;
    private final int daysWithoutRain;

    public MonthDecadalDaysWithRain(StationDecadalRainfall sdr) {
        this(sdr.getYearlyRainfall().getYear(), sdr.getMonth(), sdr.getDecadal(), sdr.getRainyDaysCount());
    }

    public MonthDecadalDaysWithRain(int year, Month month, Decadal decadal, long daysWithRain) {
        this.month = month;
        this.decadal = decadal;
        this.daysWithRain = (int) daysWithRain;
        this.daysWithoutRain = decadal.length(YearMonth.of(year, month)) - (int) daysWithRain;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    public int getDaysWithRain() {
        return daysWithRain;
    }

    public int getDaysWithoutRain() {
        return daysWithoutRain;
    }

    @Override
    public int compareTo(MonthDecadalDaysWithRain o) {
        return NATURAL.compare(this, o);
    }
}
