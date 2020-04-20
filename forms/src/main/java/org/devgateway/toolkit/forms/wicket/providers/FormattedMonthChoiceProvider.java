package org.devgateway.toolkit.forms.wicket.providers;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * @author Nadejda Mandrescu
 */
public class FormattedMonthChoiceProvider extends GenericChoiceProvider<Month> {
    private static final long serialVersionUID = 2721382999893449018L;

    private TextStyle monthStyle;

    private Locale locale;

    public FormattedMonthChoiceProvider(List<Month> listOfElements, TextStyle monthStyle, Locale locale) {
        super(listOfElements);
        this.monthStyle = monthStyle;
        this.locale = locale;
    }

    @Override
    public String getDisplayValue(final Month month) {
        return month.getDisplayName(monthStyle, locale);
    }
}
