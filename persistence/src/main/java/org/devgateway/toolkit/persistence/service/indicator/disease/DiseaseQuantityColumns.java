package org.devgateway.toolkit.persistence.service.indicator.disease;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.devgateway.toolkit.persistence.service.indicator.DuplicateElementException;
import org.devgateway.toolkit.persistence.time.AD3Clock;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityColumns {

    public static final int REGION_COL_IDX = 0;
    public static final int DISEASE_COL_IDX = 1;
    public static final int MONTH_COL_IDX_START = 2;

    private final Map<Month, Integer> monthColIdx = new HashMap<>();
    private final List<MonthDTO> months;
    private final int monthColIdxEnd;

    public DiseaseQuantityColumns(Integer year) {
        List<MonthDTO> months = MonthDTO.MONTHS;
        LocalDate now = LocalDate.now(AD3Clock.systemDefaultZone());
        if (year == now.getYear()) {
            months = months.stream().filter(m -> m.getMonthValue() < now.getMonthValue()).collect(Collectors.toList());
        }
        this.months = ImmutableList.copyOf(months);
        this.monthColIdxEnd = MONTH_COL_IDX_START + this.months.size() - 1;
    }

    public void addMonthColId(Month month, Integer colId) {
        if (getMonthColId(month) == null) {
            monthColIdx.put(month, colId);
        } else {
            throw new DuplicateElementException();
        }
    }

    public Integer getMonthColId(Month month) {
        return monthColIdx.get(month);
    }

    public List<MonthDTO> getMonths() {
        return months;
    }

    public int getMonthColIdxEnd() {
        return monthColIdxEnd;
    }
}
