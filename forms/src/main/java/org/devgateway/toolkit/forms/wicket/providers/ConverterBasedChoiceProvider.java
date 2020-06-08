package org.devgateway.toolkit.forms.wicket.providers;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Session;

/**
 * @author Octavian Ciubotaru
 */
public class ConverterBasedChoiceProvider<T> extends GenericChoiceProvider<T> {

    private final Class<T> type;

    public ConverterBasedChoiceProvider(List<T> listOfElements, Class<T> type) {
        super(listOfElements);
        this.type = type;
    }

    @Override
    public String getDisplayValue(T object) {
        Locale locale = Session.get().getLocale();
        return Application.get()
                .getConverterLocator()
                .getConverter(type)
                .convertToString(object, locale);
    }
}
