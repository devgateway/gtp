package org.devgateway.toolkit.persistence.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.devgateway.toolkit.persistence.time.AD3Clock;

/**
 * <p>Hydrological year. For Senegal it starts on 1 May and ends on 30 April.</p>
 *
 * <p>Formatted as 2020-2021 or 2020-21.</p>
 *
 * <p>Hydrological year is designated by the calendar year in which it starts.
 * I.e. 2020 corresponds to 2020-2021 hydrological year.</p>
 *
 * @author Octavian Ciubotaru
 */
public class HydrologicalYear implements Serializable, Comparable<HydrologicalYear> {

    public static final MonthDay HYDROLOGICAL_YEAR_START = MonthDay.of(Month.MAY, 1);

    public static final Comparator<MonthDay> HYDROLOGICAL_MONTH_DAY_COMPARATOR = (l, r) -> {
        int lc = l.compareTo(HYDROLOGICAL_YEAR_START);
        int rc = r.compareTo(HYDROLOGICAL_YEAR_START);
        int sign = (lc < 0 && rc < 0) || (lc >= 0 && rc >= 0) ? 1 : -1;
        return sign * l.compareTo(r);
    };

    private static final Comparator<HydrologicalYear> NATURAL = Comparator.comparingInt(HydrologicalYear::getYear);

    @JsonValue
    private final int year;

    public HydrologicalYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d", year, (year + 1) % 100);
    }

    public List<MonthDay> getMonthDays() {
        Month startMonth = HYDROLOGICAL_YEAR_START.getMonth();
        int startDay = HYDROLOGICAL_YEAR_START.getDayOfMonth();
        LocalDate it = LocalDate.of(year, startMonth, startDay);
        LocalDate end = LocalDate.of(year + 1, startMonth, startDay);
        List<MonthDay> monthDays = new ArrayList<>();
        while (it.isBefore(end)) {
            monthDays.add(MonthDay.from(it));
            it = it.plus(1, ChronoUnit.DAYS);
        }
        return monthDays;
    }

    @Override
    public int compareTo(HydrologicalYear o) {
        return NATURAL.compare(this, o);
    }

    @JsonCreator
    public static HydrologicalYear fromString(String str) {
        return new HydrologicalYear(Integer.parseInt(str));
    }

    @JsonCreator
    public static HydrologicalYear fromInt(int val) {
        return new HydrologicalYear(val);
    }

    public static HydrologicalYear now() {
        LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
        int designatedYear;
        if (MonthDay.from(now).compareTo(HYDROLOGICAL_YEAR_START) < 0) {
            designatedYear = now.getYear() - 1;
        } else {
            designatedYear = now.getYear();
        }
        return new HydrologicalYear(designatedYear);
    }
}
