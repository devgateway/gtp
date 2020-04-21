package org.devgateway.toolkit.forms.wicket.page.edit.reference;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.TableViewSectionPanel;
import org.devgateway.toolkit.forms.wicket.components.form.MonthDayPickerFormPanel;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonPluviometricPostReferenceStartService;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonStartTableViewPanel
        extends TableViewSectionPanel<RainSeasonPluviometricPostReferenceStart, RainSeasonStartReference> {
    private static final long serialVersionUID = -4392759969371109961L;

    @SpringBean
    private RainSeasonPluviometricPostReferenceStartService rainSeasonPostReferenceService;

    @SpringBean
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    private Map<Long, RainSeasonPostRefStartModel> postId2Model = new HashMap<>();

    public RainSeasonStartTableViewPanel(String id, IModel<RainSeasonStartReference> parentModel) {
        super(id, parentModel);

        this.title = new StringResourceModel("title", parentModel);

        init();

        SortableJpaServiceDataProvider provider = new SortableJpaServiceDataProvider<>(rainSeasonPostReferenceService);
        this.dataProvider = provider;
        provider.setFilterState(newFilterState());
        provider.setSort("pluviometricPost.department.name", SortOrder.ASCENDING);
        provider.setPageSize(WebConstants.NO_PAGE_SIZE);

        columns.add(new PropertyColumn<>(new StringResourceModel("department"), "pluviometricPost.department.name",
                "pluviometricPost.department.name"));
        columns.add(new PropertyColumn<>(new StringResourceModel("pluviometricPost"), "pluviometricPost.label",
                "pluviometricPost.label"));
        addRefMonthColumn();
    }

    private void init() {
        rainSeasonStartReferenceService.initialize(parentModel.getObject());
        parentModel.getObject().getPostReferences().forEach(postStart -> {
            Long postId = postStart.getPluviometricPost().getId();
            postId2Model.put(postId, new RainSeasonPostRefStartModel(Model.of(postStart)));
        });
    }

    private void addRefMonthColumn() {
        columns.add(new AbstractColumn<RainSeasonPluviometricPostReferenceStart, String>(
                new StringResourceModel("monthDay")) {
            private static final long serialVersionUID = -8262989595577009836L;
            @Override
            public void populateItem(Item<ICellPopulator<RainSeasonPluviometricPostReferenceStart>> cellItem,
                    String componentId, IModel<RainSeasonPluviometricPostReferenceStart> rowModel) {
                Long postId = rowModel.getObject().getPluviometricPost().getId();
                RainSeasonPostRefStartModel model = postId2Model.get(postId);
                MonthDayPickerFormPanel monthDayPickerFormPanel = new MonthDayPickerFormPanel(componentId,
                        model);
                monthDayPickerFormPanel.setMonthStyle(TextStyle.FULL);
                cellItem.add(monthDayPickerFormPanel);
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addButton.setVisible(false);
    }

    @Override
    protected RainSeasonPluviometricPostReferenceStart createNewChild() {
        return null;
    }

    @Override
    protected void deleteChild(IModel<RainSeasonPluviometricPostReferenceStart> child, AjaxRequestTarget target) {
    }

    @Override
    protected AjaxFallbackBootstrapDataTable getDataTable() {
        return new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, WebConstants.NO_PAGE_SIZE);
    }
}
