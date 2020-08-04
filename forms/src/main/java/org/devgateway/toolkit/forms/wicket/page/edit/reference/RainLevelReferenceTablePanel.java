package org.devgateway.toolkit.forms.wicket.page.edit.reference;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;
import org.devgateway.toolkit.forms.wicket.FormattedDoubleConverter;
import org.devgateway.toolkit.forms.wicket.components.PageableTablePanel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.table.AbstractBootstrapPagingNavigationWithError;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxBootstrapNavigationToolbar;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxBootstrapNavigator;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BootstrapPagingNavigation;
import org.devgateway.toolkit.forms.wicket.providers.ListDataProvider;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.dto.MonthDTO;

import java.time.Month;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Nadejda Mandrescu
 */
public class RainLevelReferenceTablePanel
        extends PageableTablePanel<RainLevelPluviometricPostReference, RainLevelReference> {
    private static final long serialVersionUID = -5569420715151187889L;

    private Map<Month, AbstractColumn> map2col = new HashMap<>();
    private Map<Long, Label> postIdToLabel = new HashMap<>();

    private LoopItem currentPaginatorLoopItem;

    public RainLevelReferenceTablePanel(String id, IModel<RainLevelReference> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("panelTitle", parentModel);

        addZoneColumn("zone", "pluviometricPost.department.region.zone.name");
        addPostColumn("pluviometricPost", "pluviometricPost.label");
        addMonthColumns();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ((ListDataProvider) dataProvider).setSort("pluviometricPost.department.name", SortOrder.ASCENDING);
    }

    @Override
    protected AjaxFallbackBootstrapDataTable getDataTable() {
        AjaxFallbackBootstrapDataTable dataTable =
                new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, rowsPerPage);
        AjaxBootstrapNavigationToolbar navToolbar = dataTable.getNavigationToolbar();
        navToolbar.withPagingNavCreator(BootstrapPagingNavigationWithError::new);

        return dataTable;
    }

    private class BootstrapPagingNavigationWithError extends AbstractBootstrapPagingNavigationWithError {
        private static final long serialVersionUID = 5255361657673478469L;

        BootstrapPagingNavigationWithError(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
            super(id, pageable, labelProvider);
        }

        @Override
        protected boolean hasErrors(long pageIndex) {
            long from = rowsPerPage * pageIndex;
            long count = Math.min(dataProvider.size() - from, rowsPerPage);
            boolean isValid = true;
            Iterator<? extends RainLevelPluviometricPostReference> iterator = dataProvider.iterator(from, count);
            while (isValid && iterator.hasNext()) {
                isValid = iterator.next().isValid();
            }
            return !isValid;
        }

        @Override
        protected void populateItem(final LoopItem loopItem) {
            super.populateItem(loopItem);
            if (loopItem.getIndex() == this.pageable.getCurrentPage()) {
                currentPaginatorLoopItem = loopItem;
                currentPaginatorLoopItem.setOutputMarkupId(true);
            }
        }
    }

    protected AjaxFallbackBootstrapDataTable getDataTable2() {
        return new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, rowsPerPage) {
            private static final long serialVersionUID = -9037876417871373601L;


            protected AjaxBootstrapNavigationToolbar getNavigationToolbar(boolean withPageSizeSelector) {
                return new ErrorReportingBootstrapNavigationToolbar(this, withPageSizeSelector);
            }
        };
    }

    private class ErrorReportingBootstrapNavigationToolbar extends AjaxBootstrapNavigationToolbar {
        private static final long serialVersionUID = 3903143599793717493L;

        public ErrorReportingBootstrapNavigationToolbar(DataTable<?, ?> table, boolean withPageSizeSelector) {
            super(table, withPageSizeSelector);
        }

        @Override
        protected PagingNavigator newPagingNavigator(final String navigatorId, final DataTable<?, ?> table) {
            return new ErrorReportingBootstrapNavigator(navigatorId, table);
        }
    }

    private class ErrorReportingBootstrapNavigator extends AjaxBootstrapNavigator {
        private static final long serialVersionUID = -3392803786964762542L;

        public ErrorReportingBootstrapNavigator(String id, DataTable<?, ?> table) {
            super(id, table);
        }

        @Override
        protected PagingNavigation newNavigation(final String id, final IPageable pageable,
                final IPagingLabelProvider labelProvider) {
            return new BootstrapPagingNavigationWithError(id, pageable, labelProvider);
        }
    }

    private class BootstrapPagingNavigationWithError2 extends BootstrapPagingNavigation {
        private static final long serialVersionUID = 5255361657673478469L;
        private boolean isValid;

        public BootstrapPagingNavigationWithError2(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
            super(id, pageable, labelProvider);
        }

        @Override
        protected String getCssClass(long pageIndex) {
            String css = super.getCssClass(pageIndex);
            long from = rowsPerPage * pageIndex;
            long count = Math.min(dataProvider.size() - from, rowsPerPage);
            isValid = true;
            dataProvider.iterator(from, count).forEachRemaining(postRef -> {
                isValid = isValid && postRef.isValid();
            });
            return css + (isValid ? "" : " with-error");
        }

        @Override
        protected void populateItem(final LoopItem loopItem) {
            super.populateItem(loopItem);
            if (loopItem.getIndex() == this.pageable.getCurrentPage()) {
                currentPaginatorLoopItem = loopItem;
                currentPaginatorLoopItem.setOutputMarkupId(true);
            }
        }

        /*
        @Override
        protected Link<?> newPagingNavigationLink(String id, IPageable pageable, long pageIndex) {
            Link<?> link = super.newPagingNavigationLink(id, pageable, pageIndex);
            if (pageable.getCurrentPage() == pageIndex) {
                currentPageLink = link;
            }
            return link;
        }*/
    }

    protected void addZoneColumn(String resourceKey, String property) {
        columns.add(new PropertyColumn<RainLevelPluviometricPostReference, String>(
                new StringResourceModel(resourceKey), property, property) {
            private static final long serialVersionUID = 6750315868833036919L;

            @Override
            public String getCssClass() {
                return "zone";
            }
        });
    }

    protected void addPostColumn(String resourceKey, String property) {
        columns.add(new AbstractColumn<RainLevelPluviometricPostReference, String>(
                new StringResourceModel(resourceKey), property) {
            private static final long serialVersionUID = 5292491403652478000L;
            @Override
            public void populateItem(Item<ICellPopulator<RainLevelPluviometricPostReference>> cellItem,
                    String componentId, IModel<RainLevelPluviometricPostReference> postRefModel) {
                Label postLabel = new Label(componentId, new PropertyModel<>(postRefModel, property)) {
                    private static final long serialVersionUID = 5042156624961057056L;
                };
                postLabel.setOutputMarkupId(true);
                postIdToLabel.put(postRefModel.getObject().getId(), postLabel);
                postLabel.add(new AttributeModifier("class", new IModel<String>() {
                    private static final long serialVersionUID = 782534835604295922L;

                    @Override
                    public String getObject() {
                        return postRefModel.getObject().isValid() ? "" : "with-error";
                    }
                }));
                cellItem.add(postLabel);
            }
        });
    }

    protected void addMonthColumns() {
        for (Month month: MONTHS) {
            addMonthColumn(month);
        }
    }

    protected void addMonthColumn(Month month) {
        AbstractColumn<RainLevelPluviometricPostReference, String> column =
                new AbstractColumn<RainLevelPluviometricPostReference, String>(Model.of(month.toString())) {
            private static final long serialVersionUID = -8377959988985975626L;

            @Override
            public void populateItem(Item<ICellPopulator<RainLevelPluviometricPostReference>> cellItem,
                    String componentId, IModel<RainLevelPluviometricPostReference> rowModel) {
                cellItem.add(new MonthCell(componentId, Model.of(month), rowModel));
            }

            @Override
            public Component getHeader(final String componentId) {
                return new MonthHeader(componentId, Model.of(month));
            };

            @Override
            public String getCssClass() {
                return "rain-reference-month-col";
            }
        };
        map2col.put(month, column);
        columns.add(column);
    }

    private class MonthHeader extends AbstractMonthPanel {
        private static final long serialVersionUID = 864834676558762638L;

        MonthHeader(String id, IModel<Month> monthModel) {
            super(id, monthModel);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            Month month = getMonth();

            add(new Label("month", MonthDTO.of(month)));

            RepeatingView decadals = new RepeatingView("decadal");
            for (Decadal decadal : Decadal.values()) {
                decadals.add(new Label(decadals.newChildId(), decadal.getValue()));
            }
            add(decadals);
        }
    }

    private class MonthCell extends AbstractMonthPanel {
        private static final long serialVersionUID = -7081965164642325946L;

        private IModel<RainLevelPluviometricPostReference> postModel;

        MonthCell(String id, IModel<Month> monthModel, IModel<RainLevelPluviometricPostReference> postModel) {
            super(id, monthModel);
            this.postModel = postModel;
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            Month month = getMonth();

            RepeatingView values = new RepeatingView("decadalValue");
            Map<Decadal, RainLevelMonthReference> decadalRefs = postModel.getObject().getMonthReference(month);
            for (Decadal decadal : Decadal.values()) {
                IModel<RainLevelMonthReference> decRefModel = Model.of(decadalRefs.get(decadal));
                TextFieldBootstrapFormComponent<Double> rain = new TextFieldBootstrapFormComponent<Double>(
                        values.newChildId(), new PropertyModel<>(decRefModel, "rain")) {
                    private static final long serialVersionUID = 6567134766409820883L;
                    @Override
                    protected IConverter<?> createFieldConverter(Class<?> type) {
                        return new FormattedDoubleConverter(1);
                    }
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {
                        RainLevelPluviometricPostReference postRef =
                                decRefModel.getObject().getRainLevelPluviometricPostReference();
                        postRef.validate();
                        Label label = postIdToLabel.get(postRef.getId());
                        target.add(label);
                        target.add(currentPaginatorLoopItem);
                        super.onUpdate(target);
                    }
                };
                rain.getField().add(RangeValidator.range(0d, RainLevelMonthReference.MAX_RAIN));
                values.add(rain.asDouble().hideLabel());
            }
            add(values);
        }
    }

    private abstract class AbstractMonthPanel extends Panel {
        private static final long serialVersionUID = 7542979482179269335L;

        protected IModel<Month> monthModel;

        AbstractMonthPanel(String id, IModel<Month> monthModel) {
            super(id);
            this.monthModel = monthModel;
        }

        protected Month getMonth() {
            return monthModel.getObject();
        }
    }

    private class CumulativeValidator implements IValidator<Double> {
        private static final long serialVersionUID = 2475232644428160631L;

        private IModel<RainLevelMonthReference> decRefModel;
        private final boolean isFirst;

        CumulativeValidator(IModel<RainLevelMonthReference> decRefModel) {
            this.decRefModel = decRefModel;
            RainLevelMonthReference decRef = decRefModel.getObject();
            this.isFirst = decRef.getMonth().equals(Month.MAY) && decRef.getDecadal().isFirst();
        }

        @Override
        public void validate(IValidatable<Double> validatable) {
            Double decValue = validatable.getValue();
            Double otherValue = getSiblingValue();

            if (otherValue != null) {
                if (decValue == null) {
                    validatable.error(new ValidationError("CumulativeValidator.partial"));
                } else if (!isFirst && otherValue > decValue) {
                    validatable.error(new ValidationError("CumulativeValidator.ascending"));
                }
            }
        }

        private Double getSiblingValue() {
            RainLevelMonthReference decRef = decRefModel.getObject();
            RainLevelPluviometricPostReference postRef = decRef.getRainLevelPluviometricPostReference();

            Decadal decadal = decRef.getDecadal();
            Month otherM = decRef.getMonth().minus(isFirst ? 0 : (decadal.isFirst() ? 1 : 0));
            Decadal otherD = isFirst ? Decadal.SECOND :
                    (decadal.isFirst() ? Decadal.THIRD : Decadal.fromIndex(decadal.getValue() - 1));
            Map<Decadal, RainLevelMonthReference> otherMonthRef = postRef.getMonthReference(otherM);
            RainLevelMonthReference otherDecRef = otherMonthRef == null ? null : otherMonthRef.get(otherD);
            return otherDecRef == null ? null : otherDecRef.getRain();
        }
    }

}
