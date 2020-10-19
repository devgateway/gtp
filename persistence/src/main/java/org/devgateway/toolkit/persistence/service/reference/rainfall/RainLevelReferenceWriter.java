package org.devgateway.toolkit.persistence.service.reference.rainfall;

import static org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceColumns.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorWriter;

/**
 * @author Nadejda Mandrescu
 */
public class RainLevelReferenceWriter extends AbstractExcelFileIndicatorWriter {

    private RainLevelReference rainLevelReference;

    public RainLevelReferenceWriter(RainLevelReference rainLevelReference) {
        this.rainLevelReference = rainLevelReference;
    }

    @Override
    protected void addStyles() {
        addDoubleStyle();
    }

    @Override
    protected void writeTableHeader() {
        sheet.setColumnWidth(ZONE_COL_ID, 15 * 256);
        sheet.setColumnWidth(LOCALITY_COL_ID, 25 * 256);
        for (int decColId = DECADAL_START_COL_ID; decColId <= DECADAL_END_COL_ID; decColId++) {
            sheet.setColumnWidth(decColId, 6 * 256);
        }

        XSSFRow row = this.createRow();
        RainLevelReferenceColumns columns = new RainLevelReferenceColumns();
        columns.getHeaders().forEach(colIdNamePair -> {
            row.createCell(colIdNamePair.getKey()).setCellValue(colIdNamePair.getValue().toString());
        });
    }

    @Override
    protected void writeTableBody() {
        rainLevelReference.getPostRainReferences().forEach(rainPostRef -> {
            XSSFRow row = this.createRow();

            PluviometricPost pp = rainPostRef.getPluviometricPost();
            Zone zone = pp.getDepartment().getRegion().getZone();
            row.createCell(ZONE_COL_ID).setCellValue(zone.getName().toUpperCase());
            row.createCell(LOCALITY_COL_ID).setCellValue(pp.getLabel());

            rainPostRef.getRainLevelMonthReferences().forEach(monthRef -> {
                if (monthRef.getRain() != null) {
                    int decColId = DECADAL_START_COL_ID + (monthRef.getMonth().getValue() - MAY_VALUE) * 3 +
                            monthRef.getDecadal().getValue() - 1;
                    XSSFCell cell = row.createCell(decColId);
                    cell.setCellStyle(doubleCellStyle);
                    cell.setCellValue(monthRef.getRain());
                }
            });
        });
    }
}
