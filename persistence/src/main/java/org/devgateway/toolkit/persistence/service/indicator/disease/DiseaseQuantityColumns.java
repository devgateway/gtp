package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.devgateway.toolkit.persistence.service.indicator.DuplicateElementException;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityColumns {

    public static final int REGION_COL_IDX = 0;
    public static final int DISEASE_COL_IDX = 1;
    public static final int MONTH_COL_IDX_START = 2;
    public static final int MONTH_COL_IDX_END = MONTH_COL_IDX_START + 11;

    private final Map<Month, Integer> monthColIdx = new HashMap<>();

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
}
