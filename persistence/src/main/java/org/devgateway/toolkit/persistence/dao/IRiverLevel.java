package org.devgateway.toolkit.persistence.dao;

import java.math.BigDecimal;
import java.time.MonthDay;
import java.util.Comparator;

/**
 * @author Octavian Ciubotaru
 */
public interface IRiverLevel extends Comparable<IRiverLevel> {

    Comparator<IRiverLevel> HYDROLOGICAL =
            Comparator.comparing(IRiverLevel::getMonthDay,
                    HydrologicalYear.HYDROLOGICAL_MONTH_DAY_COMPARATOR);

    MonthDay getMonthDay();

    BigDecimal getLevel();

    void setLevel(BigDecimal level);

    @Override
    default int compareTo(IRiverLevel o) {
        return HYDROLOGICAL.compare(this, o);
    }
}
