package org.devgateway.toolkit.persistence.dto.drysequence;

import java.time.Month;
import java.time.YearMonth;

import org.devgateway.toolkit.persistence.dao.Decadal;

/**
 * @author Octavian Ciubotaru
 */
public class MonthDecadalDaysWithRain {

    private final Month month;
    private final Decadal decadal;
    private final int daysWithRain;
    private final int daysWithoutRain;

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
}
