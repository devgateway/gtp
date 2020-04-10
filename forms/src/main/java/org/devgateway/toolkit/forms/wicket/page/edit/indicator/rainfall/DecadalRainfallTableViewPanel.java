package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.TableViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallTableViewPanel extends TableViewSectionPanel<PluviometricPost, DecadalRainfall> {
    private static final long serialVersionUID = -4642683268764490497L;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    private Map<Long, PluviometricPostRainfallModel> pluviometricPostRainfallModel = new HashMap<>();

    public DecadalRainfallTableViewPanel(String id, IModel<DecadalRainfall> parentModel) {
        super(id, parentModel);

        init();

        this.dataProvider =  new SortableJpaServiceDataProvider<PluviometricPost>(pluviometricPostService);
        ((SortableJpaServiceDataProvider) dataProvider).setFilterState(newFilterState());
        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "department.name", "department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("label"), "label", "label"));
        addRainColumns();
    }

    private void init() {
        pluviometricPostService.findAll().forEach(p -> {
            PluviometricPostRainfallModel pm = new PluviometricPostRainfallModel(parentModel, Model.of(p));
            pluviometricPostRainfallModel.put(p.getId(), pm);
        });
        parentModel.getObject().getRainfalls().forEach(this::addRainfall);
    }

    private void addRainfall(Rainfall rainfall) {
        pluviometricPostRainfallModel.get(rainfall.getPluviometricPost().getId()).addRainfall(rainfall);
    }

    private void addRainColumns() {
        DecadalRainfall decadalRainfall = parentModel.getObject();
        Decadal decadal = decadalRainfall.getDecadal();
        int startDay = (decadal.getValue() - 1) * 10 + 1;
        int endDay = decadal.isThird() ? decadalRainfall.lengthOfMonth() : startDay + 9;

        for (int day = startDay; day <= endDay; day++) {
            addRainColumn(day);
        }
    }

    private void addRainColumn(Integer day) {
        columns.add(new AbstractColumn<PluviometricPost, String>(Model.of(day.toString())) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateItem(Item<ICellPopulator<PluviometricPost>> cellItem, String componentId,
                    IModel<PluviometricPost> rowModel) {
                PluviometricPostRainfallModel pprm = pluviometricPostRainfallModel.get(rowModel.getObject().getId());
                TextFieldBootstrapFormComponent<Double> rain = new TextFieldBootstrapFormComponent<>(
                        componentId, new PluviometricPostDayModel(pprm, day));
                rain.asDouble();
                rain.hideLabel();
                cellItem.add(rain);
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addButton.setVisible(false);
    }

    @Override
    protected void addActionColumn(final String sortingProperty) {
    }

    @Override
    protected PluviometricPost createNewChild() {
        return null;
    }

    @Override
    protected void deleteChild(IModel child, AjaxRequestTarget target) {
    }
}
