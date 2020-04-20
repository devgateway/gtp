package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason;

import nl.dries.wicket.hibernate.dozer.DozerListModel;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.BookmarkableResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.SelectFilteredBootstrapPropertyColumn;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.components.table.filter.PluviometricPostRainSeasonFilterState;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainseason.EditRainSeasonPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.AbstractIndicatorListPage;
import org.devgateway.toolkit.persistence.dao.IndicatorType;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_EDITOR)
@MountPath(value = "/rain-seasons")
public class ListRainSeasonPage extends AbstractIndicatorListPage<PluviometricPostRainSeason> {
    private static final long serialVersionUID = 4253917551574616261L;

    @SpringBean
    private PluviometricPostService pluviometricPostService;

    @SpringBean
    private DepartmentService departmentService;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    public ListRainSeasonPage(PageParameters parameters) {
        super(parameters, IndicatorType.RAINFALL_SEASON);

        this.jpaService = pluviometricPostRainSeasonService;
        this.editPageClass = EditRainSeasonPage.class;

        columns.add(new PropertyColumn<>(new StringResourceModel("department"),
                "pluviometricPost.department.name", "pluviometricPost.department.name"));
        /*
        columns.add(new SelectFilteredBootstrapPropertyColumn<PluviometricPostRainSeason, Department, String>(
                new StringResourceModel("department"), "pluviometricPost.department.name", "pluviometricPost.department",
                new DozerListModel(departmentService.findAll())));
         */
        columns.add(new SelectFilteredBootstrapPropertyColumn<PluviometricPostRainSeason, PluviometricPost, String>(
                new StringResourceModel("pluviometricPost"), "pluviometricPost.label", "pluviometricPost",
                new DozerListModel(pluviometricPostService.findAll())));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<PluviometricPostRainSeason>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<PluviometricPostRainSeason, String> dataTable) {
        return new BookmarkableResettingFilterForm<PluviometricPostRainSeasonFilterState>(id, locator, dataTable,
                ListRainSeasonPage.class, this.getPageParameters());
    }

    @Override
    public JpaFilterState<PluviometricPostRainSeason> newFilterState() {
        return new PluviometricPostRainSeasonFilterState();
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<PluviometricPostRainSeason>> filterForm) {
        return new RainSeasonOuterFilterPanel(id, (ResettingFilterForm<PluviometricPostRainSeasonFilterState>) filterForm);
    }

}
