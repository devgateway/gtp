package org.devgateway.toolkit.forms.wicket.components.form;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.AbstractDateTextFieldConfig;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

/**
 * @author Nadejda Mandrescu
 */
public class LocalDateFixedYearFieldBootstrapFormComponent extends LocalDateFieldBootstrapFormComponent {
    private static final long serialVersionUID = 292039491024788108L;

    private Integer year;

    public LocalDateFixedYearFieldBootstrapFormComponent(String id, IModel<String> labelModel, IModel<LocalDate> model,
            Integer year) {
        super(id, labelModel, model);
        this.year = year;
    }

    public LocalDateFixedYearFieldBootstrapFormComponent(String id, Integer year) {
        super(id);
        this.year = year;
    }

    public LocalDateFixedYearFieldBootstrapFormComponent(String id, IModel<LocalDate> model, Integer year) {
        super(id, model);
        this.year = year;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        getDateSettings()
                .withView(AbstractDateTextFieldConfig.View.Year)
                .withStartDate(LocalDate.of(year, Month.JANUARY, 1).atStartOfDay())
                .withEndDate(LocalDate.of(year, Month.DECEMBER, 31).atTime(LocalTime.MAX));

        getField().add(new IValidator<LocalDate>() {
            private static final long serialVersionUID = -4324295187598133158L;
            @Override
            public void validate(IValidatable<LocalDate> validatable) {
                LocalDate date = validatable.getValue();
                if (date != null && date.getYear() != year) {
                    ValidationError error = new ValidationError();
                    error.addKey("invalidYear");
                    error.setVariable("year", year);
                    validatable.error(error);
                }
            }
        });
    }
}
