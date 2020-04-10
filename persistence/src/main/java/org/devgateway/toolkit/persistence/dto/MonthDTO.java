package org.devgateway.toolkit.persistence.dto;

import org.devgateway.toolkit.persistence.dao.DBConstants;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class MonthDTO {

    public static final Map<Month, MonthDTO> MAPPING = new HashMap<>();
    public static final List<MonthDTO> MONTHS = DBConstants.MONTHS.stream().map(MonthDTO::new)
            .collect(Collectors.toList());

    private Month month;

    public MonthDTO(Month month) {
        this.month = month;
        MAPPING.put(month, this);
    }

    @Override
    public String toString() {
        return month.getDisplayName(TextStyle.FULL, Locale.FRENCH);
    }

    public static MonthDTO of(Month month) {
        return MAPPING.get(month);
    }
}
