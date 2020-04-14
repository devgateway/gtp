package org.devgateway.toolkit.forms.wicket.components;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapAddButton;
import org.devgateway.toolkit.forms.wicket.components.form.BootstrapDeleteButton;
import org.devgateway.toolkit.forms.wicket.components.table.AjaxFallbackBootstrapDataTable;
import org.devgateway.toolkit.forms.wicket.components.table.filter.JpaFilterState;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> The current list data type
 * @param <PARENT> The parent field data type
 * Class that displays a table of T type with the possibility of adding/removing elements.
 */
public abstract class TableViewSectionPanel<T extends AbstractAuditableEntity, PARENT extends AbstractAuditableEntity>
        extends CompoundSectionPanel<List<T>> {
    private static final long serialVersionUID = -5941985746340230642L;

    protected AjaxFallbackBootstrapDataTable dataTable;

    protected List<IColumn<T, String>> columns = new ArrayList<>();

    protected ISortableDataProvider<T, String> dataProvider;

    protected BootstrapAddButton addButton;

    protected final IModel<PARENT> parentModel;

    protected int rowsPerPage = WebConstants.PAGE_SIZE;

    protected boolean isReadOnly = false;

    public TableViewSectionPanel(final String id, final IModel<PARENT> parentModel) {
        super(id);
        this.parentModel = parentModel;
    }

    protected void addActionColumn(final String sortingProperty) {
        columns.add(new AbstractColumn<T, String>(new StringResourceModel("actions", this), sortingProperty) {
            private static final long serialVersionUID = 4183365373750733883L;

            @Override
            public void populateItem(final Item<ICellPopulator<T>> cellItem, final String componentId,
                                     final IModel<T> rowModel) {
                cellItem.add(getActionPanel(componentId, parentModel, rowModel));
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (dataProvider == null) {
            throw new NullPointerException("dataProvider is null");
        }

        setOutputMarkupPlaceholderTag(true);

        if (showTooltip) {
            add(new LabelWithTooltip("panelTitle", getId()));
        } else {
            add(new Label("panelTitle", title));
        }

        add(getTablePanel("tablePanel"));

        add(getAddNewChildButton());
        addOtherButtonsOrLinks("otherButtonsOrLinks");
    }

    protected Component getAddNewChildButton() {
        addButton = new BootstrapAddButton("newButton", new ResourceModel("newButton")) {
            private static final long serialVersionUID = -1163665719225658381L;

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                createNewChild();
                dataTable.setCurrentPage(dataTable.getPageCount());
                target.add(dataTable);
            }
        };

        addButton.setOutputMarkupPlaceholderTag(true);
        return addButton;
    }

    protected Component getAddNewChildButtonPlaceHolder() {
        WebMarkupContainer newPlaceholder = new WebMarkupContainer("newButton");
        newPlaceholder.setVisible(false);
        return newPlaceholder;
    }

    protected void addOtherButtonsOrLinks(final String id) {
        add(new WebMarkupContainer(id));
    }

    protected abstract T createNewChild();

    protected abstract void deleteChild(IModel<T> child, AjaxRequestTarget target);

    protected JpaFilterState<T> newFilterState() {
        return new JpaFilterState<>();
    }

    public Component getActionPanel(final String id, final IModel<T> model) {
        return new ActionPanel(id, model);
    }

    public Component getActionPanel(final String id, final IModel<PARENT> parentModel, final IModel<T> model) {
        return getActionPanel(id, model);
    }

    public Component getTablePanel(final String id) {
        return new TablePanel(id);
    }

    protected class TablePanel extends Panel {
        private static final long serialVersionUID = -7137328047440156429L;

        public TablePanel(final String id) {
            super(id);

            dataTable = new AjaxFallbackBootstrapDataTable("table", columns, dataProvider, rowsPerPage);
            add(dataTable);
        }
    }

    private class ActionPanel extends Panel {
        private static final long serialVersionUID = -415017920877376294L;

        ActionPanel(final String id, final IModel<T> model) {
            super(id, model);

            final BootstrapDeleteButton deleteButton = new BootstrapDeleteButton("deleteButton") {
                private static final long serialVersionUID = -4209887524120372130L;

                @Override
                protected void onSubmit(final AjaxRequestTarget target) {
                    deleteChild(model, target);
                    target.add(dataTable);
                }
            };
            deleteButton.setEnabled(!isReadOnly);
            add(deleteButton);
        }
    }

}
