package org.devgateway.toolkit.persistence.dto;


import static org.devgateway.toolkit.persistence.util.Constants.MINUS_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.SPACE_STRING;

/**
 * Created by Daniel Oliva
 */
public class GisDTOConsumption extends GisDTO {

    public GisDTOConsumption(Integer year, String code, Double value, String crop, String cropFr, String source) {
        super(year, code, value, crop, cropFr, source);
    }

    @Override
    public String getName(boolean isFR) {
        String prodLabel = isFR ? CONS_FR_STR : CONS_EN_STR;
        String cropLabel = isFR ? getCropFr() : getCrop();
        return prodLabel + MINUS_STRING + cropLabel + MINUS_STRING + getYear();
    }

    @Override
    public String getNameEnFr() {
        return CONS_FR_STR + SPACE_STRING + getCrop() + "/" + CONS_EN_STR + SPACE_STRING
                + getCropFr() + MINUS_STRING + getYear();
    }

    @Override
    public String getMeasure(boolean isFR) {
        return isFR ? CONS_MEASURE_FR : CONS_MEASURE_EN;
    }
}
