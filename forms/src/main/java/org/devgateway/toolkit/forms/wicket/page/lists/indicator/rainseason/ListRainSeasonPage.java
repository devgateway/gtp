package org.devgateway.toolkit.forms.wicket.page.lists.indicator.rainseason;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
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
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.PluviometricPostRainSeasonService;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.devgateway.toolkit.persistence.service.location.RegionService;
import org.devgateway.toolkit.persistence.service.location.ZoneService;
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
    private ZoneService zoneService;

    @SpringBean
    private RegionService regionService;

    @SpringBean
    private DepartmentService departmentService;

    @SpringBean
    private PluviometricPostRainSeasonService pluviometricPostRainSeasonService;

    private PluviometricPostRainSeasonFilterState filterState = new PluviometricPostRainSeasonFilterState();

    public ListRainSeasonPage(PageParameters parameters) {
        super(parameters, IndicatorType.RAINFALL_SEASON);

        this.jpaService = pluviometricPostRainSeasonService;
        this.editPageClass = EditRainSeasonPage.class;

        filterState = BookmarkableResettingFilterForm.configureRequest(parameters, filterState);

        List<String> zones = zoneService.findAll().stream().map(Zone::getName).collect(toList());
        columns.add(new SelectFilteredBootstrapPropertyColumn<>(new StringResourceModel("zone"),
                "pluviometricPost.department.region.zone.name", "pluviometricPost.department.region.zone.name", "zone",
                new ListModel<>(zones)));

        String zone = filterState.getZone();
        Set<String> regions = regionService.findAll().stream()
                .filter(r -> zone == null || r.getZone().getName().equals(zone))
                .map(Region::getName)
                .collect(toCollection(TreeSet::new));
        columns.add(new SelectFilteredBootstrapPropertyColumn<>(new StringResourceModel("region"),
                "pluviometricPost.department.region.name", "pluviometricPost.department.region.name", "region",
                new ListModel<>(new ArrayList<>(regions))));

        String region = filterState.getRegion();
        Set<String> departments = departmentService.findAll().stream()
                .filter(d -> regions.contains(d.getRegion().getName())
                        && (region == null || d.getRegion().getName().equals(region)))
                .map(Department::getName)
                .collect(toCollection(TreeSet::new));
        columns.add(new SelectFilteredBootstrapPropertyColumn<>(new StringResourceModel("department"),
                "pluviometricPost.department.name", "pluviometricPost.department.name", "department",
                new ListModel<>(new ArrayList<>(departments))));

        String department = filterState.getDepartment();
        Set<String> posts = pluviometricPostService.findAll().stream()
                .filter(p -> departments.contains(p.getDepartment().getName())
                        && (department == null || p.getDepartment().getName().equals(department)))
                .map(PluviometricPost::getLabel)
                .collect(toCollection(TreeSet::new));
        columns.add(new SelectFilteredBootstrapPropertyColumn<>(new StringResourceModel("pluviometricPost"),
                "pluviometricPost.label", "pluviometricPost.label", "pluviometricPost",
                new ListModel<>(new ArrayList<>(posts))));

        columns.add(new PropertyColumn<>(new StringResourceModel("startDate"), "startDate", "startDate"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("pluviometricPost", SortOrder.ASCENDING);
        editPageLink.setVisible(false);
    }

    @Override
    protected ResettingFilterForm<? extends JpaFilterState<PluviometricPostRainSeason>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<PluviometricPostRainSeason, String> dataTable) {
        return new BookmarkableResettingFilterForm<PluviometricPostRainSeasonFilterState>(id, locator, dataTable,
                ListRainSeasonPage.class);
    }

    @Override
    public JpaFilterState<PluviometricPostRainSeason> newFilterState() {
        return filterState;
    }

    @Override
    protected Component getOuterFilter(final String id,
            ResettingFilterForm<? extends JpaFilterState<PluviometricPostRainSeason>> filterForm) {
        return new RainSeasonOuterFilterPanel(id, filterForm);
    }

}
