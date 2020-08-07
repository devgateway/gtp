package org.devgateway.toolkit.forms.models;

import org.apache.wicket.model.IModel;

/**
 * @author Nadejda Mandrescu
 */
public class WrapperNullableModel<T> implements IModel<T> {
    private static final long serialVersionUID = 1388416996453670787L;

    private IModel<T> mainModel;
    private IModel<T> fallbackModel;

    public WrapperNullableModel(IModel<T> mainModel, IModel<T> fallbackModel) {
        this.mainModel = mainModel;
        this.fallbackModel = fallbackModel;
    }

    @Override
    public T getObject() {
        T value = mainModel.getObject();
        return value == null ? fallbackModel.getObject() : value;
    }
}
