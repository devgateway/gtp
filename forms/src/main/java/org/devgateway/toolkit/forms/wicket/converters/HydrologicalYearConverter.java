package org.devgateway.toolkit.forms.wicket.converters;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;

/**
 * @author Octavian Ciubotaru
 */
public class HydrologicalYearConverter implements IConverter<HydrologicalYear> {

    @Override
    public HydrologicalYear convertToObject(String value, Locale locale) throws ConversionException {
        try {
            return new HydrologicalYear(Integer.valueOf(value.substring(0, 4)));
        } catch (IndexOutOfBoundsException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public String convertToString(HydrologicalYear value, Locale locale) {
        return value == null ? null : value.toString();
    }
}
