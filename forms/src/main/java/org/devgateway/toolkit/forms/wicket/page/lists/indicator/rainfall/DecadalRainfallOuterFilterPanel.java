package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainfall;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.DecadalRainfallFilterState;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallOuterFilterPanel extends Panel {
    private static final long serialVersionUID = 1947240267168715858L;

    @SpringBean
    private AdminSettingsService adminSettingsService;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    private ResettingFilterForm<DecadalRainfallFilterState> filterForm;

    public DecadalRainfallOuterFilterPanel(String id, ResettingFilterForm<DecadalRainfallFilterState> filterForm) {
        super(id);
        this.filterForm = filterForm;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        IModel<Collection<Integer>> yearsModel = Model.of(adminSettingsService.getYears());
        SelectFilteredBootstrapPropertyColumn<String, Integer, Integer> yearFilter =
                new SelectFilteredBootstrapPropertyColumn(Model.of(""), "year", "year", yearsModel);
        Select2ChoiceBootstrapFormComponent yearChoice = yearFilter.getFilterWithLabel("year", filterForm);
        if (yearChoice.getModelObject() == null) {
            yearChoice.setDefaultModelObject(LocalDate.now().getYear());
        }
        Integer year = (Integer) yearChoice.getModelObject();
        if (!decadalRainfallService.existsByYear(year)) {
            decadalRainfallService.generate(year);
        }
        add(yearChoice);
    }
}
