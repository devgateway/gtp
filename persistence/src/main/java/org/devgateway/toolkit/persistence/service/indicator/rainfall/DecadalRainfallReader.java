package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.dao.location.Zone;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallReader extends AbstractRainfallExcelFileIndicatorReader<DecadalRainfall> {

    private DecadalRainfall decadalRainfall;

    private final int dayEndColId;

    public DecadalRainfallReader(DecadalRainfall decadalRainfall, List<Zone> zones,
            List<PluviometricPost> pluviometricPosts) {
        super(zones, pluviometricPosts);
        this.decadalRainfall = decadalRainfall;

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
        checkHeaders(row, headers);
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

}
