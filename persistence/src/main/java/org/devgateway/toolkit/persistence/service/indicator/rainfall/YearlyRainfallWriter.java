package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.StationDecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorWriter;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.COLUMNS_PER_DECADAL;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.COLUMNS_PER_MONTH;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.DECADAL_END_COL_ID;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.DECADAL_START_COL_ID;
import static org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallColumns.LOCALITY_COL_ID;


/**
 * @author Nadejda Mandrescu
 */
public class YearlyRainfallWriter extends AbstractExcelFileIndicatorWriter {

    private final YearlyRainfall yearlyRainfall;

    public YearlyRainfallWriter(YearlyRainfall yearlyRainfall) {
        this.yearlyRainfall = yearlyRainfall;
    }

    @Override
    public void addStyles() {
        addIntegerStyle();
        addDoubleStyle();
    }

    @Override
    protected String getSheetName() {
        return String.format("%d", yearlyRainfall.getYear());
    }

    @Override
    protected void writeTableHeader() {
        sheet.setColumnWidth(LOCALITY_COL_ID, 25 * 256);
        for (int decadalColId = DECADAL_START_COL_ID; decadalColId <= DECADAL_END_COL_ID; decadalColId++) {
            sheet.setColumnWidth(decadalColId, 10 * 256);
        }

        int hRowId = rowNo;
        XSSFRow row = this.createRow();
        XSSFRow row2 = this.createRow();

        List<Pair<Integer, Object>> headersRow1 = new ArrayList<>(YearlyRainfallColumns.INSTANCE.getHeadersRow1());
        headersRow1.removeIf(pair -> pair.getKey() == LOCALITY_COL_ID);

        row.createCell(LOCALITY_COL_ID).setCellValue("STATION");
        sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId + 1, LOCALITY_COL_ID, LOCALITY_COL_ID));

        headersRow1.forEach(pair -> {
            int monthDecadalColId = pair.getKey();
            XSSFCell cell = row.createCell(monthDecadalColId);
            cell.setCellValue(pair.getValue().toString());
            sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId, monthDecadalColId, monthDecadalColId + 1));
        });

        YearlyRainfallColumns.INSTANCE.getHeadersRow2().forEach(pair -> {
            XSSFCell cell = row2.createCell(pair.getKey());
            cell.setCellValue(pair.getValue().toString());
        });
    }

    @Override
    protected void writeTableBody() {
        Set<StationDecadalRainfall> stationDecadalRainfalls = new TreeSet<>(StationDecadalRainfall.NATURAL);
        stationDecadalRainfalls.addAll(yearlyRainfall.getStationDecadalRainfalls());

        PluviometricPost pp = null;
        XSSFRow row = null;
        Month firstMonth = MONTHS.get(0);
        for (StationDecadalRainfall stationDecadalRainfall : stationDecadalRainfalls) {
            if (pp == null || !pp.equals(stationDecadalRainfall.getPluviometricPost())) {
                row = this.createRow();
                pp = stationDecadalRainfall.getPluviometricPost();
                pp = stationDecadalRainfall.getPluviometricPost();
                row.createCell(LOCALITY_COL_ID).setCellValue(pp.getLabel());
            }

            int monthDelta =  stationDecadalRainfall.getMonth().getValue() - firstMonth.getValue();
            int decadalColId = DECADAL_START_COL_ID
                    + monthDelta * COLUMNS_PER_MONTH
                    + stationDecadalRainfall.getDecadal().ordinal() * COLUMNS_PER_DECADAL;

            XSSFCell cell = row.createCell(decadalColId);
            cell.setCellStyle(doubleCellStyle);
            if (stationDecadalRainfall.getRainfall() != null) {
                cell.setCellValue(stationDecadalRainfall.getRainfall());
            }

            cell = row.createCell(decadalColId + 1);
            cell.setCellStyle(integerCellStyle);
            if (stationDecadalRainfall.getRainyDaysCount() != null) {
                cell.setCellValue(stationDecadalRainfall.getRainyDaysCount());
            }
        }
    }
}
