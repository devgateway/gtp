package org.devgateway.toolkit.forms.wicket.page.lists.reference;

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RainSeasonStartReferenceFilterState;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;

/**
 * @author Nadejda Mandrescu
 */
public class RainSeasonStartFilterForm extends BookmarkableResettingFilterForm<RainSeasonStartReferenceFilterState> {
    private static final long serialVersionUID = 5150756263907095730L;

    public RainSeasonStartFilterForm(final String id, final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<RainSeasonStartReference, String> dataTable,
            final PageParameters pageParameters) {
        super(id, locator, dataTable, ListRainSeasonStartReferencePage.class, pageParameters);

        getFilterState().setAntiCache(System.currentTimeMillis());
    }
}
