package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.StationDecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.dao.location.Zone;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.COLUMNS_PER_DECADAL;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.DECADAL_END_COL_ID;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.DECADAL_START_COL_ID;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.LOCALITY_COL_ID;

/**
 * @author Nadejda Mandrescu
 */
public class YearlyRainfallReader extends AbstractRainfallExcelFileIndicatorReader<YearlyRainfall> {
    private static final double EPSILON = 0.000001;

    private YearlyRainfall yearlyRainfall;

    public YearlyRainfallReader(YearlyRainfall yearlyRainfall, List<Zone> zones,
            List<PluviometricPost> pluviometricPosts) {
        super(zones, pluviometricPosts, StationDecadalRainfall.MAX_RAIN);
        this.yearlyRainfall = yearlyRainfall;
    }

    @Override
    protected void readHeader() {
        checkHeaders(nextRow(), YearlyRainfallColumns.INSTANCE.getHeadersRow1());
        checkHeaders(nextRow(), YearlyRainfallColumns.INSTANCE.getHeadersRow2());
    }

    @Override
    protected YearlyRainfall readContent() {
        YearlyRainfall yr = new YearlyRainfall(yearlyRainfall.getYear());
        LocalDate now = LocalDate.now();

        while (rowNo <= sheet.getLastRowNum()) {
            XSSFRow row = nextRow();

            if (isEmptyRow(row, DECADAL_END_COL_ID)) {
                continue;
            }

            PluviometricPost pp = getPluviometricPost(row);
            if (pp == null) {
                continue;
            }
            int decadalColId = DECADAL_START_COL_ID;
            boolean isPastDecadal = true;
            for (Month month : MONTHS) {
                for (Decadal decadal : Decadal.values()) {
                    StationDecadalRainfall sdr = new StationDecadalRainfall(pp, month, decadal);
                    yr.addStationDecadalRainfall(sdr);

                    isPastDecadal = isPastDecadal && isPastDecadal(now, yr.getYear(), month, decadal);

                    Double rain = getRain(row, decadalColId);
                    if (rain != null && !isPastDecadal) {
                        errorFutureRainNotAllowed(row.getCell(decadalColId));
                    }
                    sdr.setRainfall(rain);

                    Integer maxRainyDays = isPastDecadal ? decadal.length(YearMonth.of(yr.getYear(), month)) : null;
                    Integer rainyDaysCount = getRainyDaysCount(row, decadalColId + 1, maxRainyDays);
                    sdr.setRainyDaysCount(rainyDaysCount);

                    if (rain != null && rainyDaysCount != null) {
                        if (rain > 0 && rainyDaysCount == 0) {
                            addErrorAt(row.getCell(decadalColId + 1),
                                    "Au moins un jour de pluie doit exister quand il y a un niveau de pluie");
                        } else if (rain == 0 && rainyDaysCount > 0) {
                            addErrorAt(row.getCell(decadalColId),
                                    "S'il y a des jours de pluie, il doit aussi y avoir un niveau de pluie");
                        }
                    } else if (rain != null) {
                        addErrorAt(row.getCell(decadalColId),
                                "Les précipitations sans jours de pluie ne sont pas autorisées");
                    } else if (rainyDaysCount != null) {
                        addErrorAt(row.getCell(decadalColId + 1),
                                "Les jours de pluie sans précipitations ne sont pas autorisées");
                    }
                    decadalColId += COLUMNS_PER_DECADAL;
                }
            }
        }

        return yr;
    }

    private Integer getRainyDaysCount(XSSFRow row, int colId, Integer maxRainyDays) {
        XSSFCell cell = row.getCell(colId);
        if (!isEmpty(cell)) {
            try {
                double countDouble = cell.getNumericCellValue();
                int count = (int) Math.round(countDouble);

                if (maxRainyDays == null) {
                    errorFutureRainNotAllowed(cell);
                } else if (Math.abs(countDouble - count) > EPSILON) {
                    addErrorAt(cell, "Les jours de pluie doivent être des nombres entiers");
                } else if (countDouble < 0 || countDouble > maxRainyDays) {
                    addErrorAt(cell,
                            String.format("Les jours de pluie ne sont pas compris entre 0 et %d", maxRainyDays));
                }
                return count;
            } catch (Exception e) {
                addErrorAt(cell, "Pluviométrie invalide");
            }
        }
        return null;
    }

    private boolean isPastDecadal(LocalDate now, int year, Month month, Decadal decadal) {
        LocalDate lastDayOfDecadal = LocalDate.of(year, month, decadal.endDay(YearMonth.of(year, month)));
        return lastDayOfDecadal.isBefore(now);
    }

    private void errorFutureRainNotAllowed(XSSFCell cell) {
        addErrorAt(cell, "Les précipitations peuvent être fournies pour des décades dans le passé seulement");
    }

    @Override
    protected int getLocalityColId() {
        return LOCALITY_COL_ID;
    }

    @Override
    protected int getZoneColId() {
        return -1;
    }

}
