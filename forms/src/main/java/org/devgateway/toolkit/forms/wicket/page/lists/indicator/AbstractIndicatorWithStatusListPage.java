package org.devgateway.toolkit.forms.wicket.page.lists.indicator;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.AbstractStatusAuditableEntity;

import java.io.Serializable;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractIndicatorWithStatusListPage<T extends AbstractStatusAuditableEntity & Serializable>
        extends AbstractListPage<T> {

    private final boolean isSortByStatus;

    public AbstractIndicatorWithStatusListPage(PageParameters parameters) {
        this(parameters, false);
    }

    public AbstractIndicatorWithStatusListPage(PageParameters parameters, boolean isSortByStatus) {
        super(parameters, false);
        this.isSortByStatus = isSortByStatus;
    }

    @Override
    protected void onInitialize() {
        columns.add(new AbstractColumn<T, String>(new StringResourceModel("formStatus"),
                isSortByStatus ? "formStatus" : null) {
            private static final long serialVersionUID = -5425273193416180043L;
            @Override
            public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
                String statusName = rowModel.getObject().getFormStatus().name();
                cellItem.add(new Label(componentId, new StringResourceModel(statusName)));
            }
        });

        super.onInitialize();
    }
}
