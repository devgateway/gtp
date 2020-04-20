package org.devgateway.toolkit.forms.models;

import org.apache.wicket.model.IModel;

import java.time.Month;
import java.time.MonthDay;

/**
 * @author Nadejda Mandrescu
 */
public abstract class MonthDayModel implements IModel<MonthDay> {
    private static final long serialVersionUID = -1259847691115867889L;

    public Month getMonth() {
        MonthDay monthDay = getObject();
        return monthDay == null ? null : monthDay.getMonth();
    }

    public void setMonth(Month month) {
        if (month == null) {
            setObject(null);
        } else {
            MonthDay monthDay = getObject();
            Integer day = monthDay == null ? 1 : Math.min(month.maxLength(), monthDay.getDayOfMonth());
            setObject(MonthDay.of(month, day));
        }
    }

    public Integer getDay() {
        MonthDay monthDay = getObject();
        return monthDay == null ? null : monthDay.getDayOfMonth();
    }

    public void setDay(Integer day) {
        MonthDay monthDay = getObject();
        if (monthDay != null) {
            setObject(MonthDay.of(monthDay.getMonth(), day));
        }
    }

}
