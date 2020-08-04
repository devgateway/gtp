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
package org.devgateway.toolkit.forms.wicket.components.table;

import com.google.common.collect.ImmutableList;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelect;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.devgateway.toolkit.persistence.util.Function4;

/**
 * @author idobre
 * @since 11/25/14
 */

public class AjaxBootstrapNavigationToolbar extends AbstractToolbar {
    private static final long serialVersionUID = 230663553625059960L;

    public static final ImmutableList<Long> DEFAULT_PAGE_SIZES = ImmutableList.of(10L, 50L, 100L);

    private final boolean withPageSizeSelector;

    private Function4<String, IPageable, IPagingLabelProvider, BootstrapPagingNavigation> pagingNavCreator;

    public AjaxBootstrapNavigationToolbar(final DataTable<?, ?> table, boolean withPageSizeSelector) {
        super(table);
        this.withPageSizeSelector = withPageSizeSelector;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final WebMarkupContainer span = new WebMarkupContainer("span");
        span.add(AttributeModifier.replace("colspan",
                (IModel<String>) () -> String.valueOf(getTable().getColumns().size())));

        span.add(new Component[]{this.newPagingNavigator("navigator", getTable())});

        span.add(newPageSizeSelector("pageSizeSelector"));
        add(span);
    }

    private Component newPageSizeSelector(String id) {
        BootstrapSelect<Long> select =
                new BootstrapSelect<>("select", Model.of(getTable().getItemsPerPage()), DEFAULT_PAGE_SIZES);

        select.add(new AjaxFormComponentUpdatingBehavior("change") {
            private static final long serialVersionUID = 5892914997426397967L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                getTable().setItemsPerPage(select.getModelObject());

                target.add(getTable());
            }
        });

        WebMarkupContainer wrapper = new WebMarkupContainer(id);
        wrapper.add(select);
        wrapper.setVisibilityAllowed(withPageSizeSelector);
        return wrapper;
    }

    protected PagingNavigator newPagingNavigator(final String navigatorId, final DataTable<?, ?> table) {
        return new AjaxBootstrapNavigator(navigatorId, table).withPagingNavCreator(pagingNavCreator);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        this.setVisibilityAllowed(this.getTable().getPageCount() > 1L || withPageSizeSelector);
    }

    public AjaxBootstrapNavigationToolbar withPagingNavCreator(
            Function4<String, IPageable, IPagingLabelProvider, BootstrapPagingNavigation> pagingNavCreator) {
        this.pagingNavCreator = pagingNavCreator;
        return this;
    }
}
