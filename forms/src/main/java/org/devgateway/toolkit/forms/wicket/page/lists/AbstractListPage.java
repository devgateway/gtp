/*******************************************************************************
 * Copyright (c) 2015 Development Gateway, Inc and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License (MIT)
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/MIT
 *
 * Contributors:
 * Development Gateway - initial API and implementation
 *******************************************************************************/
package org.devgateway.toolkit.forms.wicket.page.lists;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Size;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.exceptions.NullEditPageClassException;
import org.devgateway.toolkit.forms.exceptions.NullJpaServiceException;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.ResettingFilterForm;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.forms.wicket.page.BasePage;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.providers.SortableJpaServiceDataProvider;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mpostelnicu This class can be use to display a list of Categories
 * <p>
 * T - entity type
 */
public abstract class AbstractListPage<T extends GenericPersistable & Serializable> extends BasePage {
    private static final long serialVersionUID = 1958350868666244087L;

    protected Class<? extends AbstractEditPage<T>> editPageClass;

    private AjaxFallbackBootstrapDataTable<T, String> dataTable;

    protected List<IColumn<T, String>> columns;

    protected BaseJpaService<T> jpaService;

    protected SortableJpaServiceDataProvider<T> dataProvider;

    protected int pageSize = WebConstants.PAGE_SIZE;

    protected BootstrapBookmarkablePageLink<T> editPageLink;

    protected Form excelForm;

    public AbstractListPage(final PageParameters parameters) {
        this(parameters, true);
    }

    public AbstractListPage(final PageParameters parameters, boolean isIdColumnNeeded) {
        super(parameters);

        columns = new ArrayList<>();
        if (isIdColumnNeeded) {
            columns.add(new PropertyColumn<>(new Model<>("ID"), "id", "id"));
        }
    }

    public Panel getActionPanel(final String id, final IModel<T> model) {
        return new ActionPanel(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (jpaService == null) {
            throw new NullJpaServiceException();
        }
        if (editPageClass == null) {
            throw new NullEditPageClassException();
        }

        dataProvider = new SortableJpaServiceDataProvider<>(jpaService);
        dataProvider.setFilterState(newFilterState());

        // create the excel download form; by default this form is hidden and we should make it visible only to pages
        // where we want to export entities to excel file
        excelForm = new AbstractExcelDownloadForm<T>("excelForm") {
            private static final long serialVersionUID = 4664901661550801048L;
            @Override
            protected BaseJpaService getJpaService() {
                return jpaService;
            }

            @Override
            protected SortableJpaServiceDataProvider getDataProvider() {
                return dataProvider;
            }

            @Override
            protected String getFilenamePrefix() {
                String filenamePrefix = getExcelFilenamePrefix();
                return StringUtils.isNotBlank(filenamePrefix) ? StringUtils.stripAccents(filenamePrefix)
                        : super.getFilenamePrefix();
            }
        };
        excelForm.setVisibilityAllowed(false);
        add(excelForm);

        // add the 'Edit' button
        columns.add(new AbstractColumn<T, String>(new StringResourceModel("actionsColumn", this, null)) {
            private static final long serialVersionUID = -7447601118569862123L;

            @Override
            public void populateItem(final Item<ICellPopulator<T>> cellItem, final String componentId,
                                     final IModel<T> model) {
                cellItem.add(getActionPanel(componentId, model));
            }

            @Override
            public String getCssClass() {
                return "action-panel-column";
            }
        });
        dataTable = new AjaxFallbackBootstrapDataTable<>("table", columns, dataProvider, pageSize);

        ResettingFilterForm<? extends JpaFilterState<T>> filterForm =
                getFilterForm("filterForm", dataProvider, dataTable);
        filterForm.add(dataTable);
        filterForm.add(getOuterFilter("outerFilter", filterForm));
        add(filterForm);

        if (hasFilteredColumns()) {
            dataTable.addTopToolbar(new FilterToolbar(dataTable, filterForm));
        }

        PageParameters pageParameters = new PageParameters();
        pageParameters.set(WebConstants.PARAM_ID, null);

        editPageLink = new BootstrapBookmarkablePageLink<T>("new", editPageClass, pageParameters, Buttons.Type.Success);
        editPageLink.setIconType(FontAwesomeIconType.plus_circle).setSize(Size.Large)
                .setLabel(new StringResourceModel("new", AbstractListPage.this, null));

        add(editPageLink);
    }

    public class ActionPanel extends Panel {
        private static final long serialVersionUID = 5821419128121941939L;

        protected final BootstrapBookmarkablePageLink<T> editPageLink;

        /**
         * @param id
         * @param model
         */
        public ActionPanel(final String id, final IModel<T> model) {
            super(id, model);

            final PageParameters pageParameters = new PageParameters();

            @SuppressWarnings("unchecked")
            T entity = (T) ActionPanel.this.getDefaultModelObject();
            if (entity != null) {
                pageParameters.set(WebConstants.PARAM_ID, entity.getId());
            }

            editPageLink =
                    new BootstrapBookmarkablePageLink<>("edit", editPageClass, pageParameters, Buttons.Type.Info);
            editPageLink.setIconType(FontAwesomeIconType.edit).setSize(Size.Small)
                    .setLabel(new StringResourceModel("edit", AbstractListPage.this, null));
            add(editPageLink);

            add(getPrintButton(pageParameters));

        }
    }

    /**
     * Get a stub print button that does nothing
     *
     * @param pageParameters
     * @return
     */
    protected Component getPrintButton(final PageParameters pageParameters) {
        return new WebMarkupContainer("printButton").setVisibilityAllowed(false);
    }

    protected ResettingFilterForm<? extends JpaFilterState<T>> getFilterForm(final String id,
            final IFilterStateLocator locator,
            final AjaxFallbackBootstrapDataTable<T, String> dataTable) {
        return new ResettingFilterForm<>(id, locator, dataTable);
    }

    protected Component getOuterFilter(final String id, ResettingFilterForm<? extends JpaFilterState<T>> filterForm) {
        return new WebMarkupContainer(id);
    }

    private boolean hasFilteredColumns() {
        for (IColumn<?, ?> column : columns) {
            if (column instanceof IFilteredColumn) {
                return true;
            }
        }
        return false;
    }

    public JpaFilterState<T> newFilterState() {
        return new JpaFilterState<>();
    }

    public BootstrapBookmarkablePageLink<T> getEditPageLink() {
        return editPageLink;
    }

    protected String getExcelFilenamePrefix() {
        return getString("excelFileNamePrefix");
    }
}
