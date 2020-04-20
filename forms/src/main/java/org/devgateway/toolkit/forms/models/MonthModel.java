package org.devgateway.toolkit.forms.models;

import org.apache.wicket.model.IModel;

import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class MonthModel implements IModel<Month> {
    private static final long serialVersionUID = -1238441649902276434L;

    private MonthDayModel parent;

    public MonthModel(final MonthDayModel parent) {
        this.parent = parent;
    }

    @Override
    public Month getObject() {
        return parent.getMonth();
    }

    @Override
    public void setObject(Month month) {
        parent.setMonth(month);
    }

}
