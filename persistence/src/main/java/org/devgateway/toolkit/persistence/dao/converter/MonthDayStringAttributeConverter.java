package org.devgateway.toolkit.persistence.dao.converter;

import javax.persistence.AttributeConverter;
import java.time.MonthDay;

/**
 * @author Nadejda Mandrescu
 */
public class MonthDayStringAttributeConverter implements AttributeConverter<MonthDay, String> {
    @Override
    public String convertToDatabaseColumn(MonthDay attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public MonthDay convertToEntityAttribute(String dbData) {
        return dbData == null ? null : MonthDay.parse(dbData);
    }
}
