package org.devgateway.toolkit.forms.wicket.components.form;

import org.apache.wicket.util.convert.IConverter;
import org.devgateway.toolkit.forms.wicket.converters.DegreeConverter;

/**
 * @author Octavian Ciubotaru
 */
public class DegreeBootstrapFormComponent extends TextFieldBootstrapFormComponent<Double> {

    public DegreeBootstrapFormComponent(String id) {
        super(id);
        asDouble();
    }

    @Override
    protected IConverter<?> createFieldConverter(Class<?> type) {
        return Double.class.isAssignableFrom(type) ? DegreeConverter.INSTANCE : null;
    }
}
