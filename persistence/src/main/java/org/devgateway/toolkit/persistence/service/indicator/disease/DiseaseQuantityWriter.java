package org.devgateway.toolkit.persistence.service.indicator.disease;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorWriter;

import java.util.SortedSet;

import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.DISEASE_COL_IDX;
import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.REGION_COL_IDX;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityWriter extends AbstractExcelFileIndicatorWriter {

    private final DiseaseYearlySituation situation;

    private final DiseaseQuantityColumns columns;

    public DiseaseQuantityWriter(DiseaseYearlySituation situation) {
        this.situation = situation;
        this.columns = new DiseaseQuantityColumns(situation.getYear());
    }

    @Override
    protected void addStyles() {
        addIntegerStyle();
    }

    @Override
    protected void writeTableHeader() {
        sheet.setColumnWidth(REGION_COL_IDX, 15 * 256);
        sheet.setColumnWidth(DISEASE_COL_IDX, 25 * 256);

        XSSFRow row = this.createRow();
        row.createCell(REGION_COL_IDX).setCellValue("Région");
        row.createCell(DISEASE_COL_IDX).setCellValue("Maladies");

        Integer year = situation.getYear();
        this.columns.getMonths().forEach(monthDTO -> {
            int monthColId = DISEASE_COL_IDX + monthDTO.getMonthValue();
            sheet.setColumnWidth(monthColId, 11 * 256);
            row.createCell(monthColId).setCellValue(String.format("%s %s", monthDTO, year));
            columns.addMonthColId(monthDTO.getMonth(), monthColId);
        });

    }

    @Override
    protected void writeTableBody() {
        Region lastRegion = null;
        LivestockDisease lastDisease = null;
        XSSFRow row = null;
        SortedSet<DiseaseQuantity> quantities = situation.getQuantities();

        for (DiseaseQuantity diseaseQuantity : quantities) {
            if (row == null || !lastRegion.equals(diseaseQuantity.getRegion())
                    || !lastDisease.equals(diseaseQuantity.getDisease())) {
                lastRegion = diseaseQuantity.getRegion();
                lastDisease = diseaseQuantity.getDisease();
                row = this.createRow();
                row.createCell(REGION_COL_IDX).setCellValue(lastRegion.getLabel());
                row.createCell(DISEASE_COL_IDX).setCellValue(lastDisease.getLabel());
            }

            Long value = diseaseQuantity.getQuantity();
            if (value != null) {
                Cell cell = row.createCell(columns.getMonthColId(diseaseQuantity.getMonth()));
                cell.setCellValue(diseaseQuantity.getQuantity().doubleValue());
                cell.setCellStyle(integerCellStyle);
            }
        }
    }
}
