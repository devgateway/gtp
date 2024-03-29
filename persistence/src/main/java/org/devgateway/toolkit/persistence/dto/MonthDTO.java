package org.devgateway.toolkit.persistence.dto;

import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class MonthDTO implements Serializable {
    private static final long serialVersionUID = -8586829886221832615L;

    public static final Map<Month, MonthDTO> MAPPING = new HashMap<>();
    public static final List<MonthDTO> MONTHS = Arrays.stream(Month.values()).map(MonthDTO::new)
            .collect(Collectors.toList());

    private Month month;

    public MonthDTO(Month month) {
        this.month = month;
        MAPPING.put(month, this);
    }

    public Month getMonth() {
        return month;
    }

    public int getMonthValue() {
        return month.getValue();
    }

    public int lengthOfMonth(int year) {
        return YearMonth.of(year, month).lengthOfMonth();
    }

    public String getFullDisplayName() {
        return month.getDisplayName(TextStyle.FULL, Locale.FRENCH);
    }

    public String getShortDisplayName() {
        return month.getDisplayName(TextStyle.SHORT, Locale.FRENCH);
    }

    @Override
    public String toString() {
        return getFullDisplayName();
    }

    public static MonthDTO of(Month month) {
        return MAPPING.get(month);
    }
}
