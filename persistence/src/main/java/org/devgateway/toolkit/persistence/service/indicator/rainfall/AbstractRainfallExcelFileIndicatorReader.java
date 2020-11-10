package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.excel.indicator.AbstractExcelFileIndicatorReader;
import org.devgateway.toolkit.persistence.service.indicator.SearchableCollection;

import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractRainfallExcelFileIndicatorReader<T> extends AbstractExcelFileIndicatorReader<T> {

    protected final SearchableCollection<Zone> zones;

    protected final SearchableCollection<PluviometricPost> pluviometricPosts;

    protected final double rainLimit;

    public AbstractRainfallExcelFileIndicatorReader(List<Zone> zones,
            List<PluviometricPost> pluviometricPosts, double rainLimit) {
        this.zones = new SearchableCollection<>(zones, Zone::getName);

        this.pluviometricPosts = new SearchableCollection<>(pluviometricPosts, PluviometricPost::getLabel);

        this.rainLimit = rainLimit;
    }

    protected void checkHeaders(XSSFRow row, List<Pair<Integer, Object>> headers) {
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

    protected PluviometricPost getPluviometricPost(XSSFRow row) {
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

    protected Zone getZone(XSSFRow row, PluviometricPost pp) {
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

    protected Double getRain(XSSFRow row, int colId) {
        XSSFCell cell = row.getCell(colId);
        if (!isEmpty(cell)) {
            try {
                double rain = cell.getNumericCellValue();
                if (rain < 0 || rain > rainLimit) {
                    addErrorAt(cell, String.format("La pluie n'est pas comprise entre 0 et %.0f", rainLimit));
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
