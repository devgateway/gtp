package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
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
            boolean matches = expected instanceof String ? matchesString(cell, (String) expected) :
                    matchesLong(cell, (Long) expected);
            if (!matches) {
                addErrorAt(cell, "Valeur d'en-tête non valide");
            }
        });
    }

    @Override
    protected DecadalRainfall readContent() {
        DecadalRainfall dr = new DecadalRainfall();
        return dr;
    }
}
