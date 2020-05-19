package org.devgateway.toolkit.persistence.dao;

import java.time.YearMonth;

/**
 * @author Nadejda Mandrescu
 */
public enum Decadal {
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

    public boolean isThird() {
        return this.equals(THIRD);
    }

    public int length(YearMonth yearMonth) {
        return value < 3 ? 10 : yearMonth.lengthOfMonth() - 20;
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
