package org.devgateway.toolkit.forms.wicket.components.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.models.DayOfMonthModel;
import org.devgateway.toolkit.forms.models.MonthDayModel;
import org.devgateway.toolkit.forms.models.MonthModel;
import org.devgateway.toolkit.forms.wicket.providers.FormattedMonthChoiceProvider;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Nadejda Mandrescu
 */
public class MonthDayPickerFormPanel extends Panel {
    private static final long serialVersionUID = -7708003466901779246L;

    private MonthDayModel monthDayModel;
    private IModel<String> monthLabel;
    private IModel<String> dayLabel;

    private TextStyle monthStyle;

    private Select2ChoiceBootstrapFormComponent<Integer> day;

    public MonthDayPickerFormPanel(String id, MonthDayModel monthDayModel) {
        this(id, null, null, monthDayModel);
    }

    public MonthDayPickerFormPanel(String id, IModel<String> monthLabel, IModel<String> dayLabel,
            MonthDayModel monthDayModel) {
        super(id, monthDayModel);
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
        this.monthDayModel = monthDayModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addMonthChoice();
        addDayChoice();
    }

    private void addMonthChoice() {
        Select2ChoiceBootstrapFormComponent<Month> month = new Select2ChoiceBootstrapFormComponent<Month>(
                "month", monthLabel, new MonthModel(monthDayModel), getChoiceProvider()) {
            private static final long serialVersionUID = -6085712392370043894L;

            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                super.onUpdate(target);
                updateDateOptions();
                target.add(day);
            }
        };
        add(month);
    }

    private void addDayChoice() {
        day = new Select2ChoiceBootstrapFormComponent<Integer>(
                "day", dayLabel, new DayOfMonthModel(monthDayModel), null);
        updateDateOptions();
        day.setOutputMarkupId(true);
        day.getField().getSettings().setAllowClear(false);
        day.getField().getSettings().setPlaceholder(null);
        add(day);
    }

    private void updateDateOptions() {
        MonthDay monthDay = monthDayModel.getObject();
        List<Integer> days = monthDay == null ? new ArrayList<>()
                : IntStream.rangeClosed(1, monthDay.getMonth().maxLength()).boxed().collect(Collectors.toList());
        day.provider(new GenericChoiceProvider<>(days));
        day.setEnabled(monthDayModel.getObject() != null);
    }

    protected GenericChoiceProvider<Month> getChoiceProvider() {
        List<Month> months = Arrays.asList(Month.values());
        if (monthStyle != null) {
            return new FormattedMonthChoiceProvider(months, monthStyle, getLocale());
        }
        return new GenericChoiceProvider<Month>(months);
    }

    public TextStyle getMonthStyle() {
        return monthStyle;
    }

    public void setMonthStyle(TextStyle monthStyle) {
        this.monthStyle = monthStyle;
    }

}
