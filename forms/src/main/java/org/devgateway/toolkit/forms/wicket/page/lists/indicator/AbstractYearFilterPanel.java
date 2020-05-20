package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.exceptions.NullJpaServiceException;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

/**
 * @param <T> entity type
 * @param <Y> year object type
 * @author Octavian Ciubotaru
 */
public class AbstractYearFilterPanel<T extends GenericPersistable & Serializable, Y extends Serializable>
        extends Panel {

    @SpringBean
    protected AdminSettingsService adminSettingsService;

    private final ResettingFilterForm<? extends JpaFilterState<T>> filterForm;
    private final YearIndicatorGenerator<T, Y> yearIndicatorGenerator;

    public AbstractYearFilterPanel(String id,
            ResettingFilterForm<? extends JpaFilterState<T>> filterForm,
            YearIndicatorGenerator<T, Y> yearIndicatorGenerator) {
        super(id);

        this.filterForm = filterForm;
        this.yearIndicatorGenerator = yearIndicatorGenerator;
    }

    protected void addYearChoice(List<Y> years, Y defaultYear) {
        if (yearIndicatorGenerator == null) {
            throw new NullJpaServiceException();
        }

        Select2ChoiceBootstrapFormComponent<Y> yearChoice = new Select2ChoiceBootstrapFormComponent<>(
                "year",
                new GenericChoiceProvider<>(years),
                new PropertyModel<>(filterForm.getDefaultModel(), "year"));

        yearChoice.getField().add(AttributeModifier.replace("onchange", "this.form.submit();"));

        if (yearChoice.getModelObject() == null) {
            yearChoice.setDefaultModelObject(defaultYear);
        }

        Y year = yearChoice.getModelObject();
        if (!yearIndicatorGenerator.existsByYear(year)) {
            yearIndicatorGenerator.generate(year);
        }

        add(yearChoice);
    }
}
