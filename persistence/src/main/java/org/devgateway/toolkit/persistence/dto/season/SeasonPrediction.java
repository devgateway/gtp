package org.devgateway.toolkit.persistence.dto.season;

import java.time.LocalDate;
import java.time.MonthDay;

/**
 * @author Octavian Ciubotaru
 */
public class SeasonPrediction {

    private final Long pluviometricPostId;

    private final MonthDay planned;

    private final MonthDay actual;

    private final Long difference;

    public SeasonPrediction(Long pluviometricPostId, MonthDay plannedMonthDay, LocalDate actualDate) {
        this.pluviometricPostId = pluviometricPostId;
        this.planned = plannedMonthDay;
        this.actual = MonthDay.from(actualDate);

        if (plannedMonthDay != null) {
            LocalDate plannedDate = localDateFrom(actualDate.getYear(), plannedMonthDay);
            difference = actualDate.toEpochDay() - plannedDate.toEpochDay();
        } else {
            difference = null;
        }
    }

    public Long getPluviometricPostId() {
        return pluviometricPostId;
    }

    public MonthDay getPlanned() {
        return planned;
    }

    public MonthDay getActual() {
        return actual;
    }

    public Long getDifference() {
        return difference;
    }

    private static LocalDate localDateFrom(int year, MonthDay planned) {
        int plannedDayOfMonth = planned.isValidYear(year)
                ? planned.getDayOfMonth() : planned.getDayOfMonth() - 1;
        return LocalDate.of(year, planned.getMonth(), plannedDayOfMonth);
    }
}
