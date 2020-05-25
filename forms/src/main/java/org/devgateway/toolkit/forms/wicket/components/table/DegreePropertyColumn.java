package org.devgateway.toolkit.forms.wicket.components.table;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.devgateway.toolkit.forms.wicket.converters.DegreeConverter;

/**
 * @author Octavian Ciubotaru
 */
public class DegreePropertyColumn<T> extends PropertyColumn<T, String> {

    public DegreePropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
        super(displayModel, sortProperty, propertyExpression);
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
        item.add(new Label(componentId, getDataModel(rowModel)) {

            @Override
            protected IConverter<?> createConverter(Class<?> type) {
                return Double.class.isAssignableFrom(type) ? DegreeConverter.INSTANCE : null;
            }
        });
    }
}
