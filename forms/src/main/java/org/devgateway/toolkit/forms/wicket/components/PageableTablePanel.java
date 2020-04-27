package org.devgateway.toolkit.forms.wicket.components;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.comparators.NullComparator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
            dataProvider = new DefaultSortableDataProvider();
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

    protected class DefaultSortableDataProvider extends SortableDataProvider<T, String> {
        private static final long serialVersionUID = -5538626239393494264L;

        private SortParam<String> lastSortProp;

        @Override
        public Iterator<? extends T> iterator(long first, long count) {
            sort();
            long toIndex = first + count;
            return PageableTablePanel.this.getItems().subList((int) first, (int) toIndex).iterator();
        }

        @Override
        public long size() {
            return PageableTablePanel.this.getItems().size();
        }

        @Override
        public IModel<T> model(T object) {
            return new Model<>(object);
        }

        private void sort() {
            if (lastSortProp == null || getSort() != null && !lastSortProp.equals(getSort())) {
                lastSortProp = getSort();
                Comparator<T> comparator = new BeanComparator<>(getSort().getProperty(), new NullComparator<>(false));
                if (!lastSortProp.isAscending()) {
                    comparator = comparator.reversed();
                }
                Collections.sort(PageableTablePanel.this.getItems(), comparator);
            }
        }
    }
}
