package org.devgateway.toolkit.persistence.dao;

import org.devgateway.toolkit.persistence.time.AD3Clock;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;

/**
 * @author Octavian Ciubotaru
 */
public class YearMonthDecadal implements Comparable<YearMonthDecadal> {

    private static final Comparator<YearMonthDecadal> CMP = Comparator.comparing(YearMonthDecadal::getYear)
            .thenComparing(YearMonthDecadal::getMonth)
            .thenComparing(YearMonthDecadal::getDecadal);

    private final int year;
    private final Month month;
    private final Decadal decadal;

    public YearMonthDecadal(int year, MonthDecadal monthDecadal) {
        this(year, monthDecadal.getMonth(), monthDecadal.getDecadal());
    }

    public YearMonthDecadal(int year, Month month, Decadal decadal) {
        this.year = year;
        this.month = month;
        this.decadal = decadal;
    }

    public static YearMonthDecadal now() {
        LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
        return new YearMonthDecadal(
                now.getYear(),
                now.getMonth(),
                Decadal.fromDayOfMonth(now.getDayOfMonth()));
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
    public int compareTo(YearMonthDecadal o) {
        return CMP.compare(this, o);
    }

    public boolean isBefore(YearMonthDecadal other) {
        return this.compareTo(other) < 0;
    }
}
