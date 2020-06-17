package org.devgateway.toolkit.forms.wicket.components;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.providers.ListDataProvider;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplified pageable table panel
 * @param <T> the child element
 * @param <PARENT> the parent element
 *
 * @author Nadejda Mandrescu
 */
public class PageableTablePanel<T extends AbstractAuditableEntity & Serializable,
        PARENT extends AbstractAuditableEntity> extends CompoundSectionPanel<List<T>> {
    private static final long serialVersionUID = -7808948052573265743L;

    protected AjaxFallbackBootstrapDataTable dataTable;

    protected ISortableDataProvider<T, String> dataProvider;

    protected int rowsPerPage = WebConstants.PAGE_SIZE;

    protected List<IColumn<T, String>> columns = new ArrayList<>();

    public PageableTablePanel(String id, final IModel<PARENT> parentModel) {
        super(id, new PropertyModel<List<T>>(parentModel, id));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (dataProvider == null) {
            dataProvider = new ListDataProvider<>(getModel());
        }
        setOutputMarkupPlaceholderTag(true);

        add(new Label("panelTitle", title));

        add(getDataTable());
    }

    protected AjaxFallbackBootstrapDataTable getDataTable() {
        return new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, rowsPerPage);
    }

    protected List<T> getItems() {
        return getModelObject();
    }
}
