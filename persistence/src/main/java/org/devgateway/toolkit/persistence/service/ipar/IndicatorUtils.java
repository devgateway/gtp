package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.GisSettingsDescription;
import org.devgateway.toolkit.persistence.dto.ipar.GisDTO;
import org.devgateway.toolkit.persistence.dto.ipar.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.dto.ipar.GisStatDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

public final class IndicatorUtils {

    private IndicatorUtils() {
    }

    protected static void fillIndicator(String lang, List<GisIndicatorDTO> ret, List<? extends GisDTO> gisDTOList,
                                        Optional<GisSettingsDescription> description, int type) {

        boolean isFR = lang != null && lang.equalsIgnoreCase(LANG_FR);
        int year = -1;
        String crop = EMPTY_STRING;
        GisIndicatorDTO dto = null;
        for (GisDTO p : gisDTOList) {

            if ((p.getYear() != year && p.getCrop() == null)
                    || (p.getCrop() != null && (p.getYear() != year || !crop.equals(p.getCrop())))) {
                dto = new GisIndicatorDTO();
                year = p.getYear();
                crop = p.getCrop() != null ? p.getCrop() : EMPTY_STRING;
                dto.setName(p.getName(isFR, type));
                dto.setName(p.getName(isFR, type));
                //TODO
                dto.setIndicatorGroup(dto.getName().split(" ")[0]);
                dto.setNameEnFr(p.getNameEnFr(type));
                String desc = null;
                if (description != null && description.isPresent()) {
                    if (isFR) {
                        desc = description.get().getDescriptionFr();
                    } else {
                        desc = description.get().getDescription();
                    }
                }
                dto.setDescription(desc);
                if (p.getValue() != null) {
                    dto.setMaxValue(p.getValue());
                    dto.setMinValue(p.getValue());
                } else {
                    dto.setMaxValue(0D);
                    dto.setMinValue(0D);
                }
                dto.setMeasure(p.getMeasure(isFR, type));
                dto.setRightMap(false);
                dto.setLeftMap(false);
                dto.setStats(new ArrayList<>());
                dto.setYear(year);
                if (p.getSource() != null) {
                    dto.getSources().add(p.getSource());
                }
                dto.setId((long) year * 1000 + dto.getNameEnFr().hashCode());
                ret.add(dto);
            }
            fillStat(dto, p.getCode(), p.getValue());
        }
    }

    protected static void fillStat(GisIndicatorDTO dto, String code, Double value) {
        GisStatDTO rs = new GisStatDTO();
        rs.setCode(code);
        rs.setValue(value);
        if (value != null && value > dto.getMaxValue()) {
            dto.setMaxValue(value);
        }
        if (value != null && value < dto.getMinValue()) {
            dto.setMinValue(value);
        }
        dto.getStats().add(rs);
    }
}
