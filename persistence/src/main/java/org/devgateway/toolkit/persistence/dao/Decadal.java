package org.devgateway.toolkit.persistence.dao;

import java.time.YearMonth;

/**
 * @author Nadejda Mandrescu
 */
public enum Decadal implements Comparable<Decadal> {
    FIRST(1),
    SECOND(2),
    THIRD(3);

    private final Integer value;

    Decadal(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isFirst() {
        return this.equals(FIRST);
    }
    public boolean isThird() {
        return this.equals(THIRD);
    }

    public int length(YearMonth yearMonth) {
        return value < 3 ? 10 : yearMonth.lengthOfMonth() - 20;
    }

    public int startDay() {
        switch (this) {
            case FIRST: return 1;
            case SECOND: return 11;
            default: return 21;
        }
    }

    public int endDay(YearMonth yearMonth) {
        return startDay() + length(yearMonth) - 1;
    }

    public static Decadal fromIndex(String index) {
        return fromIndex(Integer.parseInt(index));
    }

    public static Decadal fromIndex(int index) {
        for (Decadal d : values()) {
            if (d.getValue() == index) {
                return d;
            }
        }
        throw new IllegalArgumentException("" + index);
    }

    public static Decadal fromDayOfMonth(int dayOfMonth) {
        if (dayOfMonth <= 10) {
            return FIRST;
        } else if (dayOfMonth <= 20) {
            return SECOND;
        } else {
            return THIRD;
        }
    }
}
