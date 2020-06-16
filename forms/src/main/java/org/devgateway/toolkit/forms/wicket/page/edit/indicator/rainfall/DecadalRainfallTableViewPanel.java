package org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfall;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;
import org.devgateway.toolkit.forms.wicket.FormattedDoubleConverter;
import org.devgateway.toolkit.forms.wicket.components.ListDataProvider;
import org.devgateway.toolkit.forms.wicket.components.TableViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallTableViewPanel extends TableViewSectionPanel<PluviometricPostRainfall, DecadalRainfall> {
    private static final long serialVersionUID = -4642683268764490497L;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    private final Map<Long, Label> totalComponent = new HashMap<>();
    private final Map<Long, Label> rainyDaysComponent = new HashMap<>();

    public DecadalRainfallTableViewPanel(String id, IModel<DecadalRainfall> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("title", parentModel);
        this.isReadOnly = parentModel.getObject().isPublished();

        init();

        ListDataProvider<PluviometricPostRainfall> sortableProvider =
                new ListDataProvider<>(parentModel.map(DecadalRainfall::getPostRainfalls));
        sortableProvider.setSort("pluviometricPost.label", SortOrder.ASCENDING);
        dataProvider = sortableProvider;

        columns.add(new PropertyColumn<>(new StringResourceModel("department"),
                "pluviometricPost.department.name", "pluviometricPost.department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("label"),
                "pluviometricPost.label", "pluviometricPost.label"));
        addNoDataColumn();
        addRainColumns();
        addTotalRainColumn();
        addRainyDaysColumn();
    }

    private void init() {
        DecadalRainfall decadalRainfall = parentModel.getObject();

        Set<PluviometricPost> posts = decadalRainfall.getPostRainfalls().stream()
                .map(PluviometricPostRainfall::getPluviometricPost)
                .collect(Collectors.toSet());

        pluviometricPostService.findAll().stream()
                .filter(p -> !posts.contains(p))
                .map(pluviometricPost -> {
                    PluviometricPostRainfall pluviometricPostRainfall = new PluviometricPostRainfall(pluviometricPost);
                    pluviometricPostRainfall.setNoData(true);
                    return pluviometricPostRainfall;
                })
                .forEach(decadalRainfall::addPostRainfall);
    }

    private void addNoDataColumn() {
        columns.add(new AbstractColumn<PluviometricPostRainfall, String>(new StringResourceModel("noData", this)) {

            @Override
            public void populateItem(Item<ICellPopulator<PluviometricPostRainfall>> cellItem,
                    String componentId, IModel<PluviometricPostRainfall> rowModel) {

                InputWrapper wrapper = new InputWrapper(componentId);
                cellItem.add(wrapper);

                CheckBox checkBox = new CheckBox("input", LambdaModel.of(rowModel,
                        PluviometricPostRainfall::getNoData, PluviometricPostRainfall::setNoData));
                checkBox.setEnabled(!isReadOnly);
                checkBox.add(new OnChangeAjaxBehavior() {
                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        cellItem.getParent().visitChildren(TextFieldBootstrapFormComponent.class,
                                (c, o) -> target.add(c));
                    }
                });
                wrapper.add(checkBox);
            }
        });
    }

    private class InputWrapper extends Fragment {

        public InputWrapper(String id) {
            super(id, "inputWrapper", DecadalRainfallTableViewPanel.this);
        }
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
        columns.add(new AbstractColumn<PluviometricPostRainfall, String>(Model.of(day.toString())) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateItem(Item<ICellPopulator<PluviometricPostRainfall>> cellItem, String componentId,
                    IModel<PluviometricPostRainfall> rowModel) {
                Long postId = rowModel.getObject().getId();

                TextFieldBootstrapFormComponent<Double> rain = new TextFieldBootstrapFormComponent<Double>(
                        componentId, new PluviometricPostDayModel(rowModel, day)) {
                    private static final long serialVersionUID = 7475516706780292947L;
                    @Override
                    protected IConverter<?> createFieldConverter(Class<?> type) {
                        return new FormattedDoubleConverter(1);
                    }

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setEnabled(!rowModel.getObject().getNoData() && !isReadOnly);
                    }
                };

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
                rain.getField().add(RangeValidator.range(0d, DecadalRainfall.MAX_RAIN));
                cellItem.add(rain);
            }
        });
    }

    private void addTotalRainColumn() {
        columns.add(new AbstractColumn<PluviometricPostRainfall, String>(new StringResourceModel("total")) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateItem(Item<ICellPopulator<PluviometricPostRainfall>> cellItem, String componentId,
                    IModel<PluviometricPostRainfall> rowModel) {
                Long postId = rowModel.getObject().getId();
                Label rainTotal = new Label(componentId, rowModel.map(PluviometricPostRainfall::getTotal));
                rainTotal.setOutputMarkupId(true);
                totalComponent.put(postId, rainTotal);
                cellItem.add(rainTotal);
            }
        });
    }

    private void addRainyDaysColumn() {
        columns.add(new AbstractColumn<PluviometricPostRainfall, String>(new StringResourceModel("daysWithRain")) {
            private static final long serialVersionUID = 4599124013488314902L;

            @Override
            public void populateItem(Item<ICellPopulator<PluviometricPostRainfall>> cellItem, String componentId,
                    IModel<PluviometricPostRainfall> rowModel) {
                Long postId = rowModel.getObject().getId();
                Label rainyDays = new Label(componentId, rowModel.map(PluviometricPostRainfall::getRainyDaysCount));
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
    protected PluviometricPostRainfall createNewChild() {
        return null;
    }

    @Override
    protected void deleteChild(IModel<PluviometricPostRainfall> child, AjaxRequestTarget target) {
    }
}
