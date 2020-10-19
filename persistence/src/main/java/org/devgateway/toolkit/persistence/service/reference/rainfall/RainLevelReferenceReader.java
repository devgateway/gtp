package org.devgateway.toolkit.persistence.service.reference.rainfall;

import static org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceColumns.DECADAL_END_COL_ID;
import static org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceColumns.DECADAL_START_COL_ID;
import static org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceColumns.LOCALITY_COL_ID;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.AbstractRainfallExcelFileIndicatorReader;

import java.time.Month;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class RainLevelReferenceReader extends AbstractRainfallExcelFileIndicatorReader<RainLevelReference> {

    public RainLevelReferenceReader(List<Zone> zones, List<PluviometricPost> pluviometricPosts) {
        super(zones, pluviometricPosts);
    }

    @Override
    protected void readHeader() {
        checkHeaders(nextRow(), new RainLevelReferenceColumns().getHeaders());
    }

    @Override
    protected RainLevelReference readContent() {
        RainLevelReference rr = new RainLevelReference();

        while (rowNo <= sheet.getLastRowNum()) {
            XSSFRow row = nextRow();

            if (isEmptyRow(row, DECADAL_END_COL_ID)) {
                continue;
            }

            PluviometricPost pp = getPluviometricPost(row);
            Zone zone = getZone(row, pp);

            if (pp == null || zone == null) {
                continue;
            }

            RainLevelPluviometricPostReference rppr = new RainLevelPluviometricPostReference(pp);
            rr.addPostReference(rppr);

            int decColId = DECADAL_START_COL_ID;
            for (Month m : DBConstants.MONTHS) {
                for (Decadal d : Decadal.values()) {
                    Double rain = getRain(row, decColId++);
                    rppr.addRainLevelMonthReference(new RainLevelMonthReference(m, d, rain));
                }
            }
            if (!rppr.validate()) {
                addErrorAt(row.getCell(LOCALITY_COL_ID),
                        "Fournissez toutes les valeurs ou aucune, les valeurs doivent être ascendantes");
            }
        }
        return rr;
    }
}
