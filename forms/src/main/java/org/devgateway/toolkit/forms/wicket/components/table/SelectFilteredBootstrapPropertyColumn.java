package org.devgateway.toolkit.forms.wicket.components.table;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilteredPropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.wicketstuff.select2.ChoiceProvider;

/**
 * A FilteredPropertyColumn that uses Select2ChoiceBootstrapFormComponent as a filter.
 *
 * @author idobre
 * @since 12/20/16
 */
public class SelectFilteredBootstrapPropertyColumn<T, Y, S> extends FilteredPropertyColumn<T, S> {
    private static final long serialVersionUID = 8144699687674322360L;

    private final String filterFormPropertyExpression;

    protected final ChoiceProvider<Y> choiceProvider;

    private boolean required;

    public SelectFilteredBootstrapPropertyColumn(final IModel<String> displayModel,
            final String propertyExpression,
            final ChoiceProvider<Y> choiceProvider) {
        this(displayModel, null, propertyExpression, choiceProvider);
    }

    public SelectFilteredBootstrapPropertyColumn(final IModel<String> displayModel,
            final S sortProperty,
            final String propertyExpression,
            final ChoiceProvider<Y> choiceProvider) {
        this(displayModel, sortProperty, propertyExpression, propertyExpression, choiceProvider);
    }

    public SelectFilteredBootstrapPropertyColumn(final IModel<String> displayModel,
                                                 final S sortProperty,
                                                 final String propertyExpression,
                                                 final String filterFormPropertyExpression,
                                                 final ChoiceProvider<Y> choiceProvider) {
        super(displayModel, sortProperty, propertyExpression);
        this.filterFormPropertyExpression = filterFormPropertyExpression;
        this.choiceProvider = choiceProvider;
    }

    @Override
    public Component getFilter(final String componentId, final FilterForm<?> form) {
        Select2ChoiceBootstrapFormComponent<Y> selectorField = getFilterWithLabel(componentId, form);
        selectorField.hideLabel();
        return selectorField;
    }

    public Select2ChoiceBootstrapFormComponent<Y> getFilterWithLabel(final String componentId,
            final FilterForm<?> form) {
        final Select2ChoiceBootstrapFormComponent<Y> selectorField =
                new Select2ChoiceBootstrapFormComponent<Y>(componentId, choiceProvider, getFilterModel(form)) {

                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        field.getSettings().setAllowClear(!required);
                    }
                };

        if (enableAutoSubmit()) {
            selectorField.getField().add(AttributeModifier.replace("onchange", "this.form.submit();"));
        }

        if (required) {
            selectorField.required();
        }
        return selectorField;
    }

    public SelectFilteredBootstrapPropertyColumn<T, Y, S> required() {
        this.required = true;
        return this;
    }

    protected IModel<Y> getFilterModel(FilterForm<?> form) {
        return new PropertyModel<>(form.getDefaultModel(), filterFormPropertyExpression);
    }

    protected boolean enableAutoSubmit() {
        return true;
    }
}
