package org.devgateway.toolkit.forms.wicket.page.edit.reference;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.PageableTablePanel;
import org.devgateway.toolkit.forms.wicket.components.form.MonthDayPickerFormPanel;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;

import java.time.format.TextStyle;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonStartTableViewPanel
        extends PageableTablePanel<RainSeasonPluviometricPostReferenceStart, RainSeasonStartReference> {
    private static final long serialVersionUID = -4392759969371109961L;

    public RainSeasonStartTableViewPanel(String id, IModel<RainSeasonStartReference> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("title", parentModel);

        addDepOrPostColumn("department", "pluviometricPost.department.name");
        addDepOrPostColumn("pluviometricPost", "pluviometricPost.label");
        addRefMonthColumn();
    }

    protected void addDepOrPostColumn(String resourceKey, String property) {
        columns.add(new PropertyColumn<RainSeasonPluviometricPostReferenceStart, String>(
                new StringResourceModel(resourceKey), property, property) {
            private static final long serialVersionUID = 6750315868833036919L;

            @Override
            public String getCssClass() {
                return "rain-reference-post-col";
            }
        });
    }

    private void addRefMonthColumn() {
        columns.add(new AbstractColumn<RainSeasonPluviometricPostReferenceStart, String>(
                new StringResourceModel("monthDay")) {
            private static final long serialVersionUID = -8262989595577009836L;
            @Override
            public void populateItem(Item<ICellPopulator<RainSeasonPluviometricPostReferenceStart>> cellItem,
                    String componentId, IModel<RainSeasonPluviometricPostReferenceStart> rowModel) {
                MonthDayPickerFormPanel monthDayPickerFormPanel = new MonthDayPickerFormPanel(componentId,
                        new RainSeasonPostRefStartModel(rowModel));
                monthDayPickerFormPanel.setMonthStyle(TextStyle.FULL);
                cellItem.add(monthDayPickerFormPanel);
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ((DefaultSortableDataProvider) dataProvider).setSort("pluviometricPost.department.name", SortOrder.ASCENDING);
    }

    @Override
    protected AjaxFallbackBootstrapDataTable getDataTable() {
        return new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, WebConstants.NO_PAGE_SIZE);
    }
}
