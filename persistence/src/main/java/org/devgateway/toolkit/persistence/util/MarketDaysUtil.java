package org.devgateway.toolkit.persistence.util;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public final class MarketDaysUtil {

    public static final int ALL_DAYS = (1 << DayOfWeek.values().length) - 1;

    private MarketDaysUtil() {
    }

    public static List<DayOfWeek> unpack(int flags) {
        List<DayOfWeek> days = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            if (((flags >> (day.getValue() - 1)) & 1) == 1) {
                days.add(day);
            }
        }
        return days;
    }

    public static int pack(Collection<DayOfWeek> days) {
        int flags = 0;
        for (DayOfWeek day : days) {
            flags ^= (1 << (day.getValue() - 1));
        }
        return flags;
    }
}
