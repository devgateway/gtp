package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dto.MonthDTO;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

/**
 * @author Nadejda Mandrescu
 */
public final class YearlyRainfallColumns {
    public static final int LOCALITY_COL_ID = 0;
    public static final int DECADAL_START_COL_ID = 1;
    public static final int COLUMNS_PER_DECADAL = 2;
    public static final int COLUMNS_PER_MONTH = Decadal.values().length * COLUMNS_PER_DECADAL;
    public static final int DECADAL_COLUMNS_COUNT = MONTHS.size() * COLUMNS_PER_MONTH;
    public static final int DECADAL_END_COL_ID = DECADAL_START_COL_ID + DECADAL_COLUMNS_COUNT - 1;

    private static final Map<Decadal, String> DECADAL_TITLE = ImmutableMap.of(
            Decadal.FIRST, "1ère décade",
            Decadal.SECOND, "2ème décade",
            Decadal.THIRD, "3ème décade"
    );

    private static final List<Integer> MONTHS_START_WITH_VOWEL_IN_FR =
            Arrays.asList(Month.APRIL.ordinal(), Month.AUGUST.ordinal(), Month.OCTOBER.ordinal());
    private static final Map<Month, String> MONTH_SUFFIX = MONTHS.stream()
            .collect(Collectors.toMap(Function.identity(), month -> {
                String prefix = MONTHS_START_WITH_VOWEL_IN_FR.contains(month.ordinal()) ? "d'" : "de ";
                String monthName = MonthDTO.of(month).getShortDisplayName();
                return prefix + monthName;
            }));

    public static final YearlyRainfallColumns INSTANCE = new YearlyRainfallColumns();

    private List<Pair<Integer, Object>> headersRow1 = new ArrayList<>();
    private List<Pair<Integer, Object>> headersRow2 = new ArrayList<>();

    private YearlyRainfallColumns() {
        headersRow1.add(Pair.of(LOCALITY_COL_ID, "STATION"));

        int decadalColId = DECADAL_START_COL_ID;
        for (Month month : MONTHS) {
            for (Decadal decadal : Decadal.values()) {
                String monthTitle = String.format("%s %s", DECADAL_TITLE.get(decadal), MONTH_SUFFIX.get(month));
                headersRow1.add(Pair.of(decadalColId, monthTitle));

                headersRow2.add(Pair.of(decadalColId, "CUM"));
                headersRow2.add(Pair.of(decadalColId + 1, "NBJ"));
                decadalColId += COLUMNS_PER_DECADAL;
            }
        }
        headersRow1 = Collections.unmodifiableList(headersRow1);
        headersRow2 = Collections.unmodifiableList(headersRow2);
    }

    public List<Pair<Integer, Object>> getHeadersRow1() {
        return headersRow1;
    }

    public List<Pair<Integer, Object>> getHeadersRow2() {
        return headersRow2;
    }
}
