package org.devgateway.toolkit.forms.wicket.page.lists.indicator.market;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.ProductYearlyPricesFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.market.EditProductYearlyPricesPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.YearFilterPanel;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.ProductYearlyPricesActionPanel;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Octavian Ciubotaru
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_MARKET_EDITOR)
@MountPath(value = "/product-prices")
public class ListProductYearlyPricesPage extends AbstractListPage<ProductYearlyPrices> {

    @SpringBean
    private ProductYearlyPricesService productYearlyPricesService;

    public ListProductYearlyPricesPage(PageParameters parameters) {
        super(parameters, false);

        jpaService = productYearlyPricesService;
        editPageClass = EditProductYearlyPricesPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("productType"),
                "productType.label", "productType.label"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("productType.label", SortOrder.ASCENDING);
    }

    @Override
    public Panel getActionPanel(String id, IModel<ProductYearlyPrices> model) {
        return new ProductYearlyPricesActionPanel(id, model, editPageClass);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<ProductYearlyPrices>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<ProductYearlyPrices, String> dataTable) {
        return new BookmarkableResettingFilterForm<ProductYearlyPricesFilterState>(id, locator, dataTable,
                ListProductYearlyPricesPage.class, getPageParameters());
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<ProductYearlyPrices>> filterForm) {
        return new YearFilterPanel<>(id, filterForm, productYearlyPricesService);
    }

    @Override
    public JpaFilterState<ProductYearlyPrices> newFilterState() {
        return new ProductYearlyPricesFilterState();
    }
}
