package org.devgateway.toolkit.forms.wicket.page.analysis;

import com.google.common.collect.ImmutableList;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.components.form.Select2ChoiceBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.pivottable.PivotTable;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.providers.GenericChoiceProvider;
import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.dao.Production;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@MountPath(value = "/analytics")
public class AnalysisPage extends BasePage {

    public AnalysisPage(PageParameters parameters) {
        super(parameters);

        Model<Class<?>> dataClassModel = Model.of(Production.class);

        PivotTable pivotTable = new PivotTable("pivotTable", dataClassModel);
        pivotTable.setOutputMarkupId(true);
        add(pivotTable);

        ImmutableList<Class<?>> dataClasses = ImmutableList.of(MarketPrice.class, Production.class, Consumption.class);

        GenericChoiceProvider<Class<?>> choiceProvider = new GenericChoiceProvider<Class<?>>(dataClasses) {

            @Override
            public String getDisplayValue(Class<?> object) {
                return getString(object.getSimpleName());
            }
        };

        add(new Select2ChoiceBootstrapFormComponent<Class<?>>("dataClass", choiceProvider, dataClassModel) {

            @Override
            protected void onInitialize() {
                super.onInitialize();
                field.getSettings().setAllowClear(false);
            }

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                super.onUpdate(target);
                target.add(pivotTable);
            }
        });
    }

    @Override
    public Boolean fluidContainer() {
        return true;
    }
}
