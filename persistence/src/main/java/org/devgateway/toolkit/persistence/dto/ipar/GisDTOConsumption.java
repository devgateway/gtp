package org.devgateway.toolkit.persistence.dto.ipar;


import static org.devgateway.toolkit.persistence.util.Constants.CONS_DAILY_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_DAILY_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_DAILY_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_DAILY_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_DAILY_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_SIZE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_SIZE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_WEEKLY_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_MEASURE_WEEKLY_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_SIZE_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_SIZE_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_SIZE_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_WEEKLY_EN;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_WEEKLY_FR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_WEEKLY_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.MINUS_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.SPACE_STRING;

/**
 * Created by Daniel Oliva
 */
public class GisDTOConsumption extends GisDTO {

    public GisDTOConsumption(Integer year, String code, Double value, String crop, String cropFr, String source) {
        super(year, code, value, crop, cropFr, source);
    }

    @Override
    public String getName(boolean isFR, int consumptionType) {
        String prodLabel = isFR ? CONS_FR_STR : CONS_EN_STR;
        String cropLabel = isFR ? getCropFr() : getCrop();
        String consType = getConsumptionTypeName(isFR, consumptionType);

        return prodLabel + MINUS_STRING + cropLabel + MINUS_STRING + consType + MINUS_STRING + getYear();
    }

    private String getConsumptionTypeName(boolean isFR, int consumptionType) {
        String consType = null;
        switch (consumptionType) {
            case CONS_DAILY_TYPE :
                consType = isFR ? CONS_DAILY_FR : CONS_DAILY_EN;
                break;
            case CONS_WEEKLY_TYPE :
                consType = isFR ? CONS_WEEKLY_FR : CONS_WEEKLY_EN;
                break;
            case CONS_SIZE_TYPE :
            default :
                consType = isFR ? CONS_SIZE_FR : CONS_SIZE_EN;
                break;
        }
        return consType;
    }

    @Override
    public String getNameEnFr(int consumptionType) {
        String consTypeFr = getConsumptionTypeName(true, consumptionType);
        String consTypeEn = getConsumptionTypeName(false, consumptionType);
        return CONS_FR_STR + SPACE_STRING + getCrop() + SPACE_STRING + consTypeFr + "/"
                + CONS_EN_STR + SPACE_STRING + getCropFr() + SPACE_STRING + consTypeEn + MINUS_STRING + getYear();
    }

    @Override
    public String getMeasure(boolean isFR, int consumptionType) {
        String measure = null;
        switch (consumptionType) {
            case CONS_DAILY_TYPE :
                measure = isFR ? CONS_MEASURE_DAILY_FR : CONS_MEASURE_DAILY_EN;
                break;
            case CONS_WEEKLY_TYPE :
                measure = isFR ? CONS_MEASURE_WEEKLY_FR : CONS_MEASURE_WEEKLY_EN;
                break;
            case CONS_SIZE_TYPE :
            default :
                measure = isFR ? CONS_MEASURE_SIZE_FR : CONS_MEASURE_SIZE_EN;
                break;
        }
        return measure;
    }
}
