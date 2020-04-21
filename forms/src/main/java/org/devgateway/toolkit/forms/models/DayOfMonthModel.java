package org.devgateway.toolkit.forms.models;

import org.apache.wicket.model.IModel;

/**
 * @author Nadejda Mandrescu
 */
public class DayOfMonthModel implements IModel<Integer> {
    private static final long serialVersionUID = 2148605684988351523L;

    private MonthDayModel parent;

    public DayOfMonthModel(final MonthDayModel parent) {
        this.parent = parent;
    }

    @Override
    public Integer getObject() {
        return parent.getDay();
    }

    @Override
    public void setObject(Integer day) {
        parent.setDay(day);
    }

}
