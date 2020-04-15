package org.devgateway.toolkit.forms.wicket;

import org.apache.wicket.util.convert.converter.DoubleConverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nadejda Mandrescu
 */
public class FormattedDoubleConverter extends DoubleConverter {
    private static final long serialVersionUID = -3568137740308657728L;

    private static final ConcurrentHashMap<Integer, DecimalFormat> DECIMAL_FORMATS = new ConcurrentHashMap<>();

    private int fractionDigits;

    public FormattedDoubleConverter(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    @Override
    protected NumberFormat newNumberFormat(final Locale locale) {
        return DECIMAL_FORMATS.getOrDefault(fractionDigits, newDecimalFormat(locale, fractionDigits));
    }

    private DecimalFormat newDecimalFormat(final Locale locale, int fractionDigits) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(locale);
        decimalFormat.setMaximumFractionDigits(fractionDigits);
        decimalFormat.setMinimumFractionDigits(fractionDigits);

        /*
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        */
        return decimalFormat;
    }

}

