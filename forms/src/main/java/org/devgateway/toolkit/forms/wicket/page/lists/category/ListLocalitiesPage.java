package org.devgateway.toolkit.forms.wicket.page.lists.category;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.location.EditDepartmentPage;
import org.devgateway.toolkit.forms.wicket.page.edit.location.EditRegionPage;
import org.devgateway.toolkit.forms.wicket.page.edit.location.EditZonePage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractExcelDownloadForm;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.location.DepartmentService;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_ADMIN)
@MountPath(value = "/localities")
public class ListLocalitiesPage extends BasePage {
    private static final long serialVersionUID = 2157603595273363101L;

    private SortableJpaServiceDataProvider<Department> dataProvider;

    @SpringBean
    private DepartmentService departmentService;

    protected List<IColumn<Department, String>> columns = new ArrayList<>();
    private AjaxFallbackBootstrapDataTable<Department, String> dataTable;

    protected Form excelForm;

    /**
     * Construct.
     *
     * @param parameters current page parameters
     */
    public ListLocalitiesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        columns.add(new AbstractColumn<Department, String>(new StringResourceModel("zone"), "region.zone.name") {
            private static final long serialVersionUID = -3189105660986817602L;

            @Override
            public void populateItem(Item<ICellPopulator<Department>> cellItem, String componentId,
                    IModel<Department> rowModel) {
                cellItem.add(new ZoneActionPanel(componentId, Model.of(rowModel.getObject().getRegion().getZone())));
            }
        });
        columns.add(new AbstractColumn<Department, String>(new StringResourceModel("region"), "region.name") {
            private static final long serialVersionUID = 2536382932740351347L;

            @Override
            public void populateItem(Item<ICellPopulator<Department>> cellItem, String componentId,
                    IModel<Department> rowModel) {
                cellItem.add(new RegionActionPanel(componentId, Model.of(rowModel.getObject().getRegion())));
            }
        });
        columns.add(new AbstractColumn<Department, String>(new StringResourceModel("department"), "name") {
            private static final long serialVersionUID = -1196006155223424706L;

            @Override
            public void populateItem(Item<ICellPopulator<Department>> cellItem, String componentId,
                    IModel<Department> rowModel) {
                cellItem.add(new DepartmentActionPanel(componentId, rowModel));
            }
        });

        dataProvider = new SortableJpaServiceDataProvider<>(departmentService);
        dataProvider.setFilterState(new JpaFilterState<>());
        dataProvider.setSort("name", SortOrder.ASCENDING);

        dataTable = new AjaxFallbackBootstrapDataTable<>("table", columns, dataProvider, WebConstants.NO_PAGE_SIZE);
        add(dataTable);

        excelForm = new AbstractExcelDownloadForm<Department>("excelForm") {
            private static final long serialVersionUID = 7473116079763361962L;

            @Override
            protected BaseJpaService getJpaService() {
                return departmentService;
            }

            @Override
            protected SortableJpaServiceDataProvider getDataProvider() {
                return dataProvider;
            }
        };
        add(excelForm);
    }

    public static class ZoneActionPanel extends ActionPanel<Zone> {
        private static final long serialVersionUID = -3646196990467773210L;

        public ZoneActionPanel(String id, IModel<Zone> model) {
            super(id, model);
            editPageClass = EditZonePage.class;
        }
    }

    public static class RegionActionPanel extends ActionPanel<Region> {
        private static final long serialVersionUID = -5914129748242126458L;

        public RegionActionPanel(String id, IModel<Region> model) {
            super(id, model);
            editPageClass = EditRegionPage.class;
        }
    }

    public static class DepartmentActionPanel extends ActionPanel<Department> {
        private static final long serialVersionUID = -105597274091396930L;

        public DepartmentActionPanel(String id, IModel<Department> model) {
            super(id, model);
            editPageClass = EditDepartmentPage.class;
        }
    }

    public static class ActionPanel<T extends GenericPersistable & Serializable & Labelable> extends Panel {
        private static final long serialVersionUID = -4278217412662579474L;

        protected Class<? extends AbstractEditPage<T>> editPageClass;

        public ActionPanel(final String id, final IModel<T> model) {
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            T entity = (T) this.getDefaultModelObject();
            final PageParameters pageParameters = new PageParameters();
            pageParameters.set(WebConstants.PARAM_ID, entity.getId());

            BootstrapBookmarkablePageLink<T> editPageLink = new BootstrapBookmarkablePageLink<T>("edit", editPageClass,
                    pageParameters, Buttons.Type.Link);
            editPageLink.setLabel(Model.of(entity.getLabel()));
            add(editPageLink);
        }
    }
}
