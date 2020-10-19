package org.devgateway.toolkit.persistence.service.reference.rainfall;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dto.MonthDTO;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class RainLevelReferenceColumns {
    public static final int ZONE_COL_ID = 0;
    public static final int LOCALITY_COL_ID = 1;
    public static final int DECADAL_START_COL_ID = 2;
    public static final int DECADAL_END_COL_ID = DECADAL_START_COL_ID + 3 * 6 - 1;
    public static final int MAY_VALUE = Month.MAY.getValue();

    private final List<Pair<Integer, Object>> headers = new ArrayList<>();

    public RainLevelReferenceColumns() {
        headers.add(Pair.of(ZONE_COL_ID, "ZONES"));
        headers.add(Pair.of(LOCALITY_COL_ID, "LOCALITÉS"));

        int decColId = DECADAL_START_COL_ID;
        for (Month m : DBConstants.MONTHS) {
            String monthName = StringUtils.stripEnd(MonthDTO.of(m).getShortDisplayName(), ".");
            for (Decadal d : Decadal.values()) {
                headers.add(Pair.of(decColId++,monthName + "-" + d.getValue().toString()));
            }
        }
    }

    public List<Pair<Integer, Object>> getHeaders() {
        return headers;
    }
}
