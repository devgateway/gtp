package org.devgateway.toolkit.forms.wicket.components.table;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nadejda Mandrescu
 */
public abstract class AbstractBootstrapPagingNavigationWithError extends BootstrapPagingNavigation {
    private static final long serialVersionUID = 114926099059943177L;

    public AbstractBootstrapPagingNavigationWithError(String id, IPageable pageable,
            IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);
    }

    @Override
    protected String getCssClass(long pageIndex) {
        return Stream.of(super.getCssClass(pageIndex), hasErrors(pageIndex) ? "with-error" : "")
                .filter(s -> !s.isEmpty()).collect(Collectors.joining(" "));
    }

    protected abstract boolean hasErrors(long pageIndex);
}
