package org.devgateway.toolkit.persistence.dto;

import static org.devgateway.toolkit.persistence.util.Constants.MINUS_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_MEASURE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_MEASURE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_MEASURE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_MEASURE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_MEASURE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_MEASURE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.SPACE_STRING;

/**
 * Created by Daniel Oliva
 */
public class GisDTOProduction extends GisDTO {

    public GisDTOProduction(Integer year, String code, Double value, String crop, String cropFr, String source) {
        super(year, code, value, crop, cropFr, source);
    }

    @Override
    public String getName(boolean isFR, int type) {
        String prodLabel = isFR ? PROD_FR_STR : PROD_EN_STR;
        String cropLabel = isFR ? getCropFr() : getCrop();
        String consType = getProductionTypeName(isFR, type);
        return prodLabel + MINUS_STRING + cropLabel + MINUS_STRING + consType + MINUS_STRING + getYear();
    }

    @Override
    public String getNameEnFr(int type) {
        String prodTypeFr = getProductionTypeName(true, type);
        String prodTypeEn = getProductionTypeName(false, type);
        return PROD_FR_STR + SPACE_STRING + getCropFr() + SPACE_STRING + prodTypeFr +  "/"
                + PROD_EN_STR + SPACE_STRING + getCrop() + SPACE_STRING + prodTypeEn + MINUS_STRING + getYear();
    }

    @Override
    public String getMeasure(boolean isFR, int type) {
        String measure = null;
        switch (type) {
            case PROD_PROD_TYPE :
                measure = isFR ? PROD_PROD_MEASURE_FR : PROD_PROD_MEASURE_EN;
                break;
            case PROD_SURFACE_TYPE :
                measure = isFR ? PROD_SURFACE_MEASURE_FR : PROD_SURFACE_MEASURE_EN;
                break;
            case PROD_YIELD_TYPE :
            default :
                measure = isFR ? PROD_YIELD_MEASURE_FR : PROD_YIELD_MEASURE_EN;
                break;
        }
        return measure;
    }


    private String getProductionTypeName(boolean isFR, int type) {
        String typeName = null;
        switch (type) {
            case PROD_PROD_TYPE :
                typeName = isFR ? PROD_PROD_FR_STR : PROD_PROD_EN_STR;
                break;
            case PROD_SURFACE_TYPE :
                typeName = isFR ? PROD_SURFACE_FR_STR : PROD_SURFACE_EN_STR;
                break;
            case PROD_YIELD_TYPE :
            default :
                typeName = isFR ? PROD_YIELD_FR_STR : PROD_YIELD_EN_STR;
                break;
        }
        return typeName;
    }
}
