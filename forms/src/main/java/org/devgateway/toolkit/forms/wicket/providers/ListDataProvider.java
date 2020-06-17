package org.devgateway.toolkit.forms.wicket.providers;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.comparators.NullComparator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Simple data provider that works with lists of pre-loaded elements. Uses {@link BeanComparator} for sorting.
 *
 * <br>IMPORTANT: During sorting original list is modified!
 *
 * @author Octavian Ciubotaru
 */
public class ListDataProvider<T extends Serializable> extends SortableDataProvider<T, String> {

    private SortParam<String> lastSortProp;

    private final IModel<List<T>> model;

    public ListDataProvider(IModel<List<T>> model) {
        this.model = model;
    }

    @Override
    public Iterator<T> iterator(long first, long count) {
        sort();
        long toIndex = first + count;
        return model.getObject().subList((int) first, (int) toIndex).iterator();
    }

    @Override
    public long size() {
        return model.getObject().size();
    }

    @Override
    public IModel<T> model(T object) {
        return Model.of(object);
    }

    private void sort() {
        if (lastSortProp == null || getSort() != null && !lastSortProp.equals(getSort())) {
            lastSortProp = getSort();
            Comparator<T> comparator = new BeanComparator<>(getSort().getProperty(),
                    new NullComparator<>(false));
            if (!lastSortProp.isAscending()) {
                comparator = comparator.reversed();
            }
            model.getObject().sort(comparator);
        }
    }
}
