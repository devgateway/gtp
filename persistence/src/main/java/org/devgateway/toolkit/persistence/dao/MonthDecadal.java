package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;
import java.time.Month;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Octavian Ciubotaru
 */
public class MonthDecadal implements Serializable, Comparable<MonthDecadal> {

    private static final Comparator<MonthDecadal> NATURAL =
            Comparator.comparing(MonthDecadal::getMonth)
                    .thenComparing(MonthDecadal::getDecadal);

    private final Month month;
    private final Decadal decadal;

    public MonthDecadal(Month month, Decadal decadal) {
        this.month = month;
        this.decadal = decadal;
    }

    public Month getMonth() {
        return month;
    }

    public Decadal getDecadal() {
        return decadal;
    }

    @Override
    public String toString() {
        return month + "-" + decadal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthDecadal)) {
            return false;
        }
        MonthDecadal that = (MonthDecadal) o;
        return month == that.month && decadal == that.decadal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, decadal);
    }

    @Override
    public int compareTo(MonthDecadal o) {
        return NATURAL.compare(this, o);
    }
}
