package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorReader;
import org.devgateway.toolkit.persistence.service.indicator.SearchableCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallReader extends AbstractExcelFileIndicatorReader<DecadalRainfall> {

    private DecadalRainfall decadalRainfall;

    private final SearchableCollection<Zone> zones;

    private final SearchableCollection<PluviometricPost> pluviometricPosts;

    private final int dayEndColId;

    public DecadalRainfallReader(DecadalRainfall decadalRainfall, List<Zone> zones,
            List<PluviometricPost> pluviometricPosts) {
        this.decadalRainfall = decadalRainfall;

        this.zones = new SearchableCollection<>(zones, Zone::getName);

        this.pluviometricPosts = new SearchableCollection<>(pluviometricPosts, PluviometricPost::getLabel);

        this.dayEndColId = DecadalRainfallWriter.getDayEndColId(decadalRainfall);
    }

    @Override
    protected void readHeader() {
        XSSFRow row = nextRow();
        rowNo++;

        List<Pair<Integer, Object>> headers = new ArrayList<>();
        headers.add(Pair.of(DecadalRainfallWriter.ZONE_COL_ID, "ZONES"));
        headers.add(Pair.of(DecadalRainfallWriter.LOCALITY_COL_ID, "LOCALITES"));
        for (int dayColId = DecadalRainfallWriter.DAY_START_COL_ID; dayColId <= this.dayEndColId; dayColId++) {
            Integer day = dayColId - DecadalRainfallWriter.DAY_START_COL_ID +  decadalRainfall.getDecadal().startDay();
            headers.add(Pair.of(dayColId, day.longValue()));
        }
        headers.forEach(colIdExpectedValuePair -> {
            XSSFCell cell = row.getCell(colIdExpectedValuePair.getLeft());
            Object expected = colIdExpectedValuePair.getRight();
            boolean matches = expected instanceof String ? matchesString(cell, (String) expected)
                    : matchesLong(cell, (Long) expected);
            if (!matches) {
                addErrorAt(cell, "Valeur d'en-tête non valide");
            }
        });
    }

    @Override
    protected DecadalRainfall readContent() {
        DecadalRainfall dr = new DecadalRainfall();

        while (rowNo <= sheet.getLastRowNum()) {
            XSSFRow row = nextRow();

            if (isEmptyRow(row, dayEndColId)) {
                continue;
            }

            PluviometricPost pp = getPluviometricPost(row);
            Zone zone = getZone(row, pp);

            if (pp == null || zone == null) {
                continue;
            }

            PluviometricPostRainfall ppr = new PluviometricPostRainfall(pp);
            dr.addPostRainfall(ppr);

            for (int dayColId = DecadalRainfallWriter.DAY_START_COL_ID, d = decadalRainfall.getDecadal().startDay();
                 dayColId <= this.dayEndColId; dayColId++, d++) {
                Double rain = getRain(row, dayColId);
                ppr.addRainfall(new Rainfall(d, rain));
            }
        }

        return dr;
    }

    private PluviometricPost getPluviometricPost(XSSFRow row) {
        XSSFCell cell = row.getCell(DecadalRainfallWriter.LOCALITY_COL_ID);
        if (isEmpty(cell)) {
            addErrorAt(cell, "Localité non spécifié");
        } else {
            try {
                String postName = getAsString(cell);
                PluviometricPost pp = pluviometricPosts.get(postName);
                if (pp == null) {
                    addErrorAt(cell, "Localité inconnu " + postName);
                }
                return pp;
            } catch (Exception e) {
                addErrorAt(cell, "Localité invalide");
            }
        }
        return null;
    }

    private Zone getZone(XSSFRow row, PluviometricPost pp) {
        XSSFCell cell = row.getCell(DecadalRainfallWriter.ZONE_COL_ID);
        if (isEmpty(cell)) {
            addErrorAt(cell, "Zone non spécifié");
        } else {
            try {
                String zoneName = getAsString(cell);
                Zone zone = zones.get(zoneName);
                if (zone == null) {
                    addErrorAt(cell, "Zone inconnu " + zoneName);
                } else if (pp != null && !pp.getDepartment().getRegion().getZone().equals(zone)) {
                    addErrorAt(cell, "Incohérence de zone");
                }
                return zone;
            } catch (Exception e) {
                addErrorAt(cell, "Zone invalide");
            }
        }
        return null;
    }

    private Double getRain(XSSFRow row, int colId) {
        XSSFCell cell = row.getCell(colId);
        if (!isEmpty(cell)) {
            try {
                double rain = cell.getNumericCellValue();
                if (rain < 0 || rain > DecadalRainfall.MAX_RAIN) {
                    addErrorAt(cell, String.format("La pluie n'est pas comprise entre 0 et %.0f",
                            DecadalRainfall.MAX_RAIN));
                } else {
                    return rain;
                }
            } catch (Exception e) {
                addErrorAt(cell, "Pluviométrie invalide");
            }
        }
        return null;
    }
}
