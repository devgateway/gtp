package org.devgateway.toolkit.forms.wicket.converters;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.devgateway.toolkit.persistence.dao.Decadal;

/**
 * @author Octavian Ciubotaru
 */
public class DecadalConverter implements IConverter<Decadal> {

    @Override
    public Decadal convertToObject(String value, Locale locale) throws ConversionException {
        return Decadal.fromIndex(value);
    }

    @Override
    public String convertToString(Decadal value, Locale locale) {
        return value.getValue().toString();
    }
}
