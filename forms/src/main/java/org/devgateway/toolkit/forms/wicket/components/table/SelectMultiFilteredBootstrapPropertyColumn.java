package org.devgateway.toolkit.forms.wicket.components.table;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.devgateway.toolkit.forms.wicket.components.form.Select2MultiChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;

/**
 * @author Octavian Ciubotaru
 */
public class SelectMultiFilteredBootstrapPropertyColumn<T, Y, S> extends FilteredPropertyColumn<T, S> {

    private final String filterPropertyExpression;

    private final IModel<? extends List<Y>> filterChoices;

    public SelectMultiFilteredBootstrapPropertyColumn(IModel<String> displayModel,
            S sortProperty,
            String propertyExpression,
            String filterPropertyExpression,
            IModel<? extends List<Y>> filterChoices) {
        super(displayModel, sortProperty, propertyExpression);

        this.filterPropertyExpression = filterPropertyExpression;

        this.filterChoices = filterChoices;
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        Select2MultiChoiceBootstrapFormComponent<Y> filter =
                new Select2MultiChoiceBootstrapFormComponent<Y>(componentId,
                        new GenericChoiceProvider<>(filterChoices.getObject()), getFilterModel(form));
        filter.getField().add(AttributeModifier.replace("onchange", "this.form.submit();"));
        filter.hideLabel();
        return filter;
    }

    protected IModel<Collection<Y>> getFilterModel(FilterForm<?> form) {
        return new PropertyModel<>(form.getDefaultModel(), filterPropertyExpression);
    }
}
