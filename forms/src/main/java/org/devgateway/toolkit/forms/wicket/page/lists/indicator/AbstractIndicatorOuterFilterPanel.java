package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.exceptions.NullJpaServiceException;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.indicator.YearIndicatorGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Nadejda Mandrescu
 */
public class AbstractIndicatorOuterFilterPanel<T extends GenericPersistable & Serializable> extends Panel {
    private static final long serialVersionUID = -6438264237463578293L;

    @SpringBean
    protected AdminSettingsService adminSettingsService;

    protected YearIndicatorGenerator<T> yearIndicatorGenerator;

    protected ResettingFilterForm<? extends JpaFilterState<T>> filterForm;

    public AbstractIndicatorOuterFilterPanel(String id, ResettingFilterForm<? extends JpaFilterState<T>> filterForm) {
        super(id);
        this.filterForm = filterForm;
    }

    protected void addYearChoice() {
        if (yearIndicatorGenerator == null) {
            throw new NullJpaServiceException();
        }

        IModel<Collection<Integer>> yearsModel = Model.of(adminSettingsService.getYears());
        SelectFilteredBootstrapPropertyColumn<T, Integer, Integer> yearFilter =
                new SelectFilteredBootstrapPropertyColumn(Model.of(""), "year", "year", yearsModel);
        Select2ChoiceBootstrapFormComponent yearChoice = yearFilter.getFilterWithLabel("year", filterForm);
        if (yearChoice.getModelObject() == null) {
            yearChoice.setDefaultModelObject(LocalDate.now().getYear());
        }
        Integer year = (Integer) yearChoice.getModelObject();
        if (!yearIndicatorGenerator.existsByYear(year)) {
            yearIndicatorGenerator.generate(year);
        }
        add(yearChoice);
    }
}
