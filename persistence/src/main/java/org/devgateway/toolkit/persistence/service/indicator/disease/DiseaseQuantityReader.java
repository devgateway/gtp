package org.devgateway.toolkit.persistence.service.indicator.disease;

import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.DISEASE_COL_IDX;
import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.MONTH_COL_IDX_END;
import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.MONTH_COL_IDX_START;
import static org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityColumns.REGION_COL_IDX;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.LivestockDisease;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorReader;
import org.devgateway.toolkit.persistence.service.indicator.DuplicateElementException;
import org.devgateway.toolkit.persistence.service.indicator.SearchableCollection;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseQuantityReader extends AbstractExcelFileIndicatorReader<DiseaseYearlySituation> {

    private final SearchableCollection<Region> regions;

    private final SearchableCollection<LivestockDisease> diseases;

    private final SearchableCollection<MonthDTO> months;

    private final DiseaseQuantityColumns columns = new DiseaseQuantityColumns();

    private final Integer year;

    public DiseaseQuantityReader(List<Region> regions, List<LivestockDisease> diseases, Integer year) {
        this.regions = new SearchableCollection<>(regions, Region::getLabel);
        this.diseases = new SearchableCollection<>(diseases, LivestockDisease::getLabel);
        this.year = year;
        this.months = new SearchableCollection<>(MonthDTO.MONTHS, m -> String.format("%s %s", m.toString(), year));
    }

    @Override
    protected void readHeader() {
        XSSFRow row = nextRow();

        ImmutableList.of(Pair.of(REGION_COL_IDX, "Région"), Pair.of(DISEASE_COL_IDX, "Maladies")).forEach(header -> {
            XSSFCell cell = row.getCell(header.getLeft());
            if (!matchesString(cell, header.getRight())) {
                addErrorAt(cell, "Valeur d'en-tête non valide");
            }
        });

        for (int monthColId = MONTH_COL_IDX_START; monthColId < MONTH_COL_IDX_START + 12; monthColId++) {
            XSSFCell cell = row.getCell(monthColId);
            String monthCol = getAsString(cell);
            MonthDTO monthDTO = this.months.get(monthCol);
            if (monthDTO == null) {
                addErrorAt(cell, "Valeur d'en-tête non valide");
            } else {
                try {
                    columns.addMonthColId(monthDTO.getMonth(), monthColId);
                } catch (DuplicateElementException e) {
                    addErrorAt(cell, "En-tête en double");
                }
            }
        }
    }

    @Override
    protected DiseaseYearlySituation readContent() {
        DiseaseYearlySituation situation = new DiseaseYearlySituation(year);

        while (rowNo <= sheet.getLastRowNum()) {
            XSSFRow row = nextRow();

            if (isEmptyRow(row, MONTH_COL_IDX_END)) {
                continue;
            }

            Region r = getRegion(row);
            LivestockDisease d = getDisease(row);

            for (Month month : Month.values()) {
                int monthColId = columns.getMonthColId(month);
                Long quantity = getQuantity(row, monthColId);
                if (r != null && d != null && quantity != null) {
                    DiseaseQuantity dq = new DiseaseQuantity(r, d, month, quantity);
                    if (!situation.addDiseaseQuantity(dq)) {
                        addErrorAt(row.getCell(monthColId), "Entrée en double");
                    }
                }
            }

        }
        return situation;
    }

    private Region getRegion(XSSFRow row) {
        return getEntityFromString(row, REGION_COL_IDX, "Région", regions);
    }

    private LivestockDisease getDisease(XSSFRow row) {
        return getEntityFromString(row, DISEASE_COL_IDX, "Maladie", diseases);
    }

    private Long getQuantity(XSSFRow row, int colId) {
        XSSFCell cell = row.getCell(colId);
        if (!isEmpty(cell)) {
            try {
                double valueAsDouble = cell.getNumericCellValue();
                if (valueAsDouble <= 0) {
                    addErrorAt(cell, "La quantité doit être > 0");
                } else {
                    return Math.round(valueAsDouble);
                }
            } catch (Exception e) {
                addErrorAt(cell, "Quantité invalide");
            }
        }
        return null;
    }
}
