package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
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
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
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

    private Map<Long, PluviometricPostRainfallModel> pluviometricPostIdToPostRainfallModel = new HashMap<>();

    private Map<Long, Label> totalComponent = new HashMap<>();
    private Map<Long, Label> rainyDaysComponent = new HashMap<>();

    public DecadalRainfallTableViewPanel(String id, IModel<DecadalRainfall> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("title", parentModel);
        this.isReadOnly = parentModel.getObject().isPublished();

        init();

        this.dataProvider = new SortableJpaServiceDataProvider<PluviometricPost>(pluviometricPostService);
        ((SortableJpaServiceDataProvider) dataProvider).setFilterState(newFilterState());
        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "department.name", "department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("label"), "label", "label"));
        addRainColumns();
        addTotalRainColumn();
        addRainyDaysColumn();
    }

    private void init() {
        DecadalRainfall decadalRainfall = parentModel.getObject();

        decadalRainfall.getPostRainfalls().forEach(pluviometricPostRainfall -> {
            pluviometricPostIdToPostRainfallModel.put(pluviometricPostRainfall.getPluviometricPost().getId(),
                    new PluviometricPostRainfallModel(Model.of(pluviometricPostRainfall)));
        });

        pluviometricPostService.findAll().forEach(pp -> {
            if (!pluviometricPostIdToPostRainfallModel.containsKey(pp.getId())) {
                PluviometricPostRainfall ppr = new PluviometricPostRainfall();
                ppr.setDecadalRainfall(decadalRainfall);
                ppr.setPluviometricPost(pp);
                pluviometricPostIdToPostRainfallModel.put(pp.getId(), new PluviometricPostRainfallModel(Model.of(ppr)));
            }
        });
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
        columns.add(new EnabelableAbstractColumn<PluviometricPost, String>(Model.of(day.toString())) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateEnabelableItem(Item<ICellPopulator<PluviometricPost>> cellItem, String componentId,
                    IModel<PluviometricPost> rowModel) {
                Long postId = rowModel.getObject().getId();
                PluviometricPostRainfallModel pprm = pluviometricPostIdToPostRainfallModel.get(postId);
                TextFieldBootstrapFormComponent<Double> rain = new TextFieldBootstrapFormComponent<Double>(
                        componentId, new PluviometricPostDayModel(pprm, day));
                rain.getField().add(new AjaxFormComponentUpdatingBehavior(rain.getUpdateEvent()) {
                    private static final long serialVersionUID = -2696538086634114609L;
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        target.add(totalComponent.get(postId));
                        target.add(rainyDaysComponent.get(postId));
                    }
                });
                rain.asDouble();
                rain.hideLabel();
                cellItem.add(rain);
            }
        });
    }

    private void addTotalRainColumn() {
        columns.add(new EnabelableAbstractColumn<PluviometricPost, String>(new StringResourceModel("total")) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateEnabelableItem(Item<ICellPopulator<PluviometricPost>> cellItem, String componentId,
                    IModel<PluviometricPost> rowModel) {
                Long postId = rowModel.getObject().getId();
                Label rainTotal = new Label(componentId,
                        new IModel<Double>() {
                            private static final long serialVersionUID = 4985144126228053340L;
                            @Override
                            public Double getObject() {
                                return pluviometricPostIdToPostRainfallModel.get(postId).getObject().getTotal();
                            }
                        });
                rainTotal.setOutputMarkupId(true);
                totalComponent.put(postId, rainTotal);
                cellItem.add(rainTotal);
            }
        });
    }

    private void addRainyDaysColumn() {
        columns.add(new EnabelableAbstractColumn<PluviometricPost, String>(new StringResourceModel("daysWithRain")) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateEnabelableItem(Item<ICellPopulator<PluviometricPost>> cellItem, String componentId,
                    IModel<PluviometricPost> rowModel) {
                Long postId = rowModel.getObject().getId();
                Label rainyDays = new Label(componentId,
                        new IModel<Long>() {
                            private static final long serialVersionUID = 4985144126228053340L;
                            @Override
                            public Long getObject() {
                                return pluviometricPostIdToPostRainfallModel.get(postId).getObject()
                                        .getRainyDaysCount();
                            }
                        });
                rainyDays.setOutputMarkupId(true);
                rainyDaysComponent.put(postId, rainyDays);
                cellItem.add(rainyDays);
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
