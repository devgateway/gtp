package org.devgateway.toolkit.forms.wicket.page.lists.reference;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.RainSeasonStartReferenceFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.reference.EditRainSeasonStartReferencePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.service.reference.RainSeasonStartReferenceService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_RAINFALL_SEASON_EDITOR)
@MountPath(value = "/rain-seasons-start-references")
public class ListRainSeasonStartReferencePage extends AbstractListPage<RainSeasonStartReference> {
    private static final long serialVersionUID = 4254066486647139261L;

    @SpringBean
    private RainSeasonStartReferenceService rainSeasonStartReferenceService;

    public ListRainSeasonStartReferencePage(PageParameters parameters) {
        super(parameters, false);

        if (parameters.get("antiCache").isNull()) {
            PageParameters antiCache = new PageParameters().set("antiCache", System.currentTimeMillis());
            setResponsePage(ListRainSeasonStartReferencePage.class, antiCache);
        }

        this.jpaService = rainSeasonStartReferenceService;
        this.editPageClass = EditRainSeasonStartReferencePage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("referenceYearStart"), "referenceYearStart"));
        columns.add(new PropertyColumn<>(new StringResourceModel("referenceYearEnd"), "referenceYearEnd"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.dataProvider.setSort("referenceYearEnd", SortOrder.DESCENDING);

        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<RainSeasonStartReference>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<RainSeasonStartReference, String> dataTable) {
        return new RainSeasonStartFilterForm(id, locator, dataTable, this.getPageParameters());
    }

    @Override
    public JpaFilterState<RainSeasonStartReference> newFilterState() {
        return new RainSeasonStartReferenceFilterState();
    }
}
