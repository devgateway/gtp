package org.devgateway.toolkit.forms.wicket.converters;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.DoubleConverter;

/**
 * @author Octavian Ciubotaru
 */
public class DegreeConverter extends DoubleConverter {

    private static final int MAX_FRACTION_DIGITS = 4;

    public static final DegreeConverter INSTANCE = new DegreeConverter();

    @Override
    protected NumberFormat newNumberFormat(Locale locale) {
        NumberFormat format = super.newNumberFormat(locale);
        format.setMaximumFractionDigits(MAX_FRACTION_DIGITS);
        return format;
    }
}
