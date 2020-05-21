package org.devgateway.toolkit.persistence.dao.converter;

import javax.persistence.AttributeConverter;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;

/**
 * @author Octavian Ciubotaru
 */
public class HydrologicalYearConverter implements AttributeConverter<HydrologicalYear, Integer> {

    @Override
    public Integer convertToDatabaseColumn(HydrologicalYear attribute) {
        return attribute == null ? null : attribute.getYear();
    }

    @Override
    public HydrologicalYear convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : HydrologicalYear.fromInt(dbData);
    }
}
