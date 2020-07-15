package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;


/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallWriter extends AbstractExcelFileIndicatorWriter {

    public static final int ZONE_COL_ID = 0;
    public static final int LOCALITY_COL_ID = 1;
    public static final int DAY_START_COL_ID = 4;

    private final DecadalRainfall decadalRainfall;
    private final int dayEndColId;

    public DecadalRainfallWriter(DecadalRainfall decadalRainfall) {
        this.decadalRainfall = decadalRainfall;
        this.dayEndColId = getDayEndColId(decadalRainfall);
    }

    @Override
    public void addStyles() {
        addIntegerStyle();
        addDoubleStyle();
    }

    @Override
    protected String getSheetName() {
        return String.format("%02d%02d%02d", this.decadalRainfall.getMonth().getValue(),
                decadalRainfall.getDecadal().getValue(), this.decadalRainfall.getYear() % 100);
    }

    @Override
    protected void writeTableHeader() {
        sheet.setColumnWidth(ZONE_COL_ID, 15 * 256);
        sheet.setColumnWidth(LOCALITY_COL_ID, 25 * 256);
        sheet.setColumnWidth(2, 6 * 256);
        sheet.setColumnWidth(3, 6 * 256);
        for (int dayId = DAY_START_COL_ID; dayId <= this.dayEndColId; dayId++) {
            sheet.setColumnWidth(dayId, 6 * 256);
        }
        for (int lastSkipColId = this.dayEndColId + 1; lastSkipColId <= this.dayEndColId + 4; lastSkipColId++) {
            sheet.setColumnWidth(lastSkipColId, 6 * 256);
        }

        int hRowId = rowNo;
        XSSFRow row = this.createRow();
        XSSFRow row2 = this.createRow();

        row.createCell(ZONE_COL_ID).setCellValue("ZONES");
        sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId + 1, ZONE_COL_ID, ZONE_COL_ID));

        row.createCell(LOCALITY_COL_ID).setCellValue("LOCALITES");
        sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId + 1, LOCALITY_COL_ID, LOCALITY_COL_ID));

        for (int dayColId = DAY_START_COL_ID, d = decadalRainfall.getDecadal().startDay(); dayColId <= this.dayEndColId;
             dayColId++, d++) {
            XSSFCell cell = row.createCell(dayColId);
            cell.setCellStyle(integerCellStyle);
            cell.setCellValue(d);
            sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId + 1, dayColId, dayColId));
        }

        List<Pair<Integer, String>> skipGroups = ImmutableList.of(
                Pair.of(2, "ANTERIEUR"),
                Pair.of(this.dayEndColId + 1, "CUMUL DECADE"),
                Pair.of(this.dayEndColId + 3, "CUMUL SAISON"));
        skipGroups.forEach(colGroup -> {
            row.createCell(colGroup.getLeft()).setCellValue(colGroup.getValue());
            sheet.addMergedRegion(new CellRangeAddress(hRowId, hRowId, colGroup.getLeft(), colGroup.getLeft() + 1));
            row2.createCell(colGroup.getLeft()).setCellValue("CUM");
            row2.createCell(colGroup.getLeft() + 1).setCellValue("NBJ");
        });
    }

    @Override
    protected void writeTableBody() {
        Comparator<PluviometricPostRainfall> pprComparator
                = Comparator.comparing(
                (Function<PluviometricPostRainfall, String>) pluviometricPostRainfall ->
                        pluviometricPostRainfall.getPluviometricPost().getDepartment().getRegion().getZone().getName(),
                (z1, z2) -> z1.compareTo(z2)
        );
        List<PluviometricPostRainfall> postRainfalls = new ArrayList<>(this.decadalRainfall.getPostRainfalls());
        postRainfalls.sort(pprComparator);
        postRainfalls.forEach(pluviometricPostRainfall -> {
            XSSFRow row = this.createRow();

            PluviometricPost pp = pluviometricPostRainfall.getPluviometricPost();
            Zone zone = pp.getDepartment().getRegion().getZone();
            row.createCell(ZONE_COL_ID).setCellValue(zone.getName().toUpperCase());
            row.createCell(LOCALITY_COL_ID).setCellValue(pp.getLabel());

            int startDay = decadalRainfall.getDecadal().startDay();

            pluviometricPostRainfall.getRainfalls().forEach(rainfall -> {
                int dayColId = DAY_START_COL_ID + rainfall.getDay() - startDay;
                XSSFCell cell = row.createCell(dayColId);
                cell.setCellStyle(doubleCellStyle);
                cell.setCellValue(rainfall.getRain());
            });

        });
    }

    public static int getDayEndColId(DecadalRainfall decadalRainfall) {
        return DAY_START_COL_ID + decadalRainfall.lengthOfDecadal() - 1;
    }
}
