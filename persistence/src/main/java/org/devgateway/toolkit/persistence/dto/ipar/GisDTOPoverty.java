package org.devgateway.toolkit.persistence.dto.ipar;

import static org.devgateway.toolkit.persistence.util.Constants.MINUS_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_MEASURE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_MEASURE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.SPACE_STRING;

/**
 * Created by Daniel Oliva
 */
public class GisDTOPoverty extends GisDTO {

    public GisDTOPoverty(Integer year, String code, Double value, String source) {
        super(year, code, value, source);
    }

    @Override
    public String getName(boolean isFR, int type) {
        String povertyLabel = isFR ? POVERTY_FR_STR : POVERTY_EN_STR;
        return povertyLabel + SPACE_STRING + getYear();
    }

    @Override
    public String getNameEnFr(int type) {
        return POVERTY_EN_STR + "/" + POVERTY_FR_STR + MINUS_STRING + getYear();
    }

    @Override
    public String getMeasure(boolean isFR, int type) {
        return isFR ? POVERTY_MEASURE_FR : POVERTY_MEASURE_EN;
    }
}
